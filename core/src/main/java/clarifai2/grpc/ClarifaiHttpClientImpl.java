package clarifai2.grpc;

import clarifai2.exception.ClarifaiException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static clarifai2.internal.InternalUtil.MEDIA_TYPE_JSON;

public class ClarifaiHttpClientImpl implements ClarifaiHttpClient {
  private final String apiKey;
  private OkHttpClient okHttpClient;

  public ClarifaiHttpClientImpl(@NotNull String apiKey) {
    this.apiKey = apiKey;
    this.okHttpClient = new OkHttpClient();
  }

  @Override @NotNull public String apiKey() {
    return this.apiKey;
  }

  @Override public void client(OkHttpClient client) {
    this.okHttpClient = client;
  }

  @Override @NotNull public String executeRequest(String url, String method, String requestString) {
    RequestBody body = null;
    if (!method.toUpperCase().equals("GET")) {
      body = RequestBody.create(MEDIA_TYPE_JSON, requestString);
    }

//    RequestBody.create(MEDIA_TYPE_JSON, requestString);
    Request request = new Request.Builder()
        .url(url)
        .method(method, body)
        .build();

    String responseString;
    try {
      Response response = okHttpClient.newCall(request).execute();
      responseString = response.body().string();
    } catch (IOException e) {
      throw new ClarifaiException(e);
    }
    return responseString;
  }
}
