package com.clarifai.api;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;

import com.clarifai.api.exception.ClarifaiBadRequestException;
import com.clarifai.api.exception.ClarifaiException;
import com.clarifai.api.exception.ClarifaiNotAuthorizedException;
import com.clarifai.api.exception.ClarifaiThrottledException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/** Manages a single request to the Clarifai API. */
class ClarifaiRequester<T> {
  static final Gson GSON = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT)
      .registerTypeAdapter(RecognitionResult.class, new RecognitionResult.Deserializer())
      .create();

  static enum Method {
    GET, POST
  }

  static class BaseResponse {
    String statusCode;
    String statusMsg;
    JsonElement results;
  }

  private final ConnectionFactory connectionFactory;
  private final CredentialManager credentialManager;
  private final Method method;
  private final String path;
  private final Class<T> resultClass;
  private final int maxAttempts;

  ClarifaiRequester(ConnectionFactory connectionFactory, CredentialManager credentialManager,
      Method method, String path, Class<T> resultClass, int maxAttempts) {
    this.connectionFactory = connectionFactory;
    this.credentialManager = credentialManager;
    this.method = method;
    this.path = path;
    this.resultClass = resultClass;
    this.maxAttempts = maxAttempts;
  }

  T execute(ClarifaiRequest request) throws ClarifaiException {
    for (int i = maxAttempts - 1; i >= 0; i--) {
      try {
        return executeOnce(request);
      } catch (ClarifaiNotAuthorizedException e) {
        credentialManager.invalidateCredential();
        if (i == 0) throw e;
      } catch (ClarifaiThrottledException e) {
        if (i == 0) throw e;
        waitForSeconds(e.getWaitSeconds());
      } catch (ClarifaiBadRequestException e) {
        throw e;  // Retrying will not help.
      } catch (ClarifaiException e) {
        if (i == 0) throw e;
      }
    }
    throw new IllegalStateException();
  }

  T executeOnce(ClarifaiRequest request) throws ClarifaiException {
    try {
      // Send request:
      HttpURLConnection conn;
      if (method == Method.POST) {
        conn = connectionFactory.newPost(path, credentialManager.getCredential());
      } else {
        conn = connectionFactory.newGet(path, credentialManager.getCredential());
      }
      if (request != null) {
        conn.setRequestProperty("Content-Type", request.getContentType());
        BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
        try {
          request.writeContent(out);
        } finally {
          out.close();
        }
      }

      // Parse result:
      checkHttpStatus(conn);
      BaseResponse response = parseJsonAndClose(conn.getInputStream(), BaseResponse.class);
      if (resultClass == Void.class) {
        return null;
      } else {
        return GSON.fromJson(response.results, resultClass);
      }
    } catch (IOException e) {
      throw new ClarifaiException("IOException", e);
    } catch (JsonParseException e) {
      throw new ClarifaiException("JSON parse error", e);
    }
  }

  private static void waitForSeconds(int seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ClarifaiException("Interrupted", e);
    }
  }

  static void checkHttpStatus(HttpURLConnection conn) throws IOException, ClarifaiException {
    int code = conn.getResponseCode();
    if (code >= 200 && code < 300) {
      return;  // Looks good.
    }

    // The API returns error messages in a JSON payload. Try to parse this.
    String errorMessage = null;
    try {
      BaseResponse response = parseJsonAndClose(conn.getErrorStream(), BaseResponse.class);
      if (response != null && response.statusCode != null && response.statusMsg != null) {
        errorMessage = response.statusCode + " " + response.statusMsg;
      }
    } catch (JsonParseException e) {
      // There was a problem with the payload, but throwing this would obscure the actual error.
    }

    if (errorMessage == null) {
      // Failed to extract a message from the payload. Use the HTTP status.
      errorMessage = code + " " + conn.getResponseMessage();
    }

    if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
      throw new ClarifaiNotAuthorizedException(errorMessage);
    } else if (code == 429) {  // Too Many Requests
      int waitSeconds = conn.getHeaderFieldInt("X-Throttle-Wait-Seconds", 10);
      throw new ClarifaiThrottledException(errorMessage, waitSeconds);
    } else if (code >= 400 && code < 500) {
      throw new ClarifaiBadRequestException(errorMessage);
    } else if (code >= 500 && code < 600) {
      throw new ClarifaiException(errorMessage);
    } else {
      throw new ClarifaiException("Unexpected HTTP status code (" + code + "): " + errorMessage);
    }
  }

  static <T> T parseJsonAndClose(InputStream in, Class<T> cls) throws JsonParseException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    try {
      return GSON.fromJson(reader, cls);
    } finally {
      try {
        reader.close();
      } catch (IOException e) { /* ignored */ }
    }
  }
}
