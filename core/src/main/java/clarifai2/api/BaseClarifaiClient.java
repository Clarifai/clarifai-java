package clarifai2.api;

import clarifai2.BuildConfig;
import clarifai2.exception.ClarifaiException;
import clarifai2.internal.AutoValueTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.Cache;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static clarifai2.internal.InternalUtil.MEDIA_TYPE_JSON;

public abstract class BaseClarifaiClient implements ClarifaiClient {

  @NotNull
  public final Gson gson;

  @NotNull
  public final OkHttpClient httpClient;

  @NotNull
  public final HttpUrl baseURL;

  @NotNull
  private final String appID;

  @NotNull
  private final String appSecret;

  @NotNull
  private final OkHttpClient tokenRefreshHTTPClient;

  @Nullable
  private ClarifaiToken currentClarifaiToken = null;

  private boolean closed = false;

  BaseClarifaiClient(@NotNull ClarifaiBuilder builder) {
    this.appID = notNullOrThrow(builder.appID, "appID == null");
    this.appSecret = notNullOrThrow(builder.appSecret, "appSecret == null");

    this.gson = vendGson();

    this.baseURL = builder.baseURL;

    final OkHttpClient unmodifiedClient = builder.client;

    this.httpClient = unmodifiedClient.newBuilder().addInterceptor(new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        final Request.Builder requestBuilder = chain.request().newBuilder()
            .header("X-Clarifai-Client", "java: " + BuildConfig.VERSION);
        final ClarifaiToken credential = refreshIfNeeded();
        if (credential != null) {
          requestBuilder.addHeader("Authorization", "Bearer " + credential.getAccessToken());
        }
        return chain.proceed(requestBuilder.build());
      }
    }).build();

    this.tokenRefreshHTTPClient = unmodifiedClient.newBuilder().build();

    refreshIfNeeded();
  }

  @NotNull
  private static <T> T notNullOrThrow(@Nullable T check, String errorMsg) {
    if (check == null) {
      throw new IllegalArgumentException(errorMsg);
    }
    return check;
  }

  @SuppressWarnings("PMD.EmptyCatchBlock")
  private static void closeOkHttpClient(@NotNull OkHttpClient client) {
    client.dispatcher().executorService().shutdown();
    client.connectionPool().evictAll();
    final Cache cache = client.cache();
    if (cache != null) {
      try {
        cache.close();
      } catch (IOException ignored) {
      }
    }
  }

  @Override public final boolean hasValidToken() {
    return currentClarifaiToken != null && System.currentTimeMillis() <= currentClarifaiToken.getExpiresAt();
  }

  @NotNull @Override public ClarifaiToken getToken() throws IllegalStateException {
    if (!hasValidToken()) {
      throw new IllegalStateException("No valid token in this " + ClarifaiClient.class.getSimpleName() + ". "
          + "Use hasValidToken() to check if a token exists before invoking this method to avoid this exception.");
    }
    //noinspection ConstantConditions
    return currentClarifaiToken;
  }

  @Nullable
  private ClarifaiToken refreshIfNeeded() {
    synchronized (this) {
      if (closed) {
        throw new ClarifaiException("This " + ClarifaiClient.class.getSimpleName() + " has already been closed");
      }
      if (!hasValidToken()) {
        currentClarifaiToken = refresh();
      }
      return currentClarifaiToken;
    }
  }

  @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "PMD.PreserveStackTrace"})
  @Nullable
  private ClarifaiToken refresh() {
    try {
      return tokenRefreshHTTPClient.dispatcher().executorService().invokeAny(Collections.singletonList(
          new Callable<ClarifaiToken>() {
            @Override public ClarifaiToken call() throws Exception {
              final Response tokenResponse = tokenRefreshHTTPClient.newCall(new Request.Builder()
                  .url(baseURL.newBuilder().addPathSegments("v2/token").build())
                  .header("Authorization", Credentials.basic(appID, appSecret))
                  .header("X-Clarifai-Client", "java:" + BuildConfig.VERSION)
                  .post(RequestBody.create(MEDIA_TYPE_JSON, "\"grant_type\":\"client_credentials\""))
                  .build()
              ).execute();
              if (tokenResponse.isSuccessful()) {
                final JsonObject response = gson.fromJson(tokenResponse.body().string(), JsonObject.class);
                return new ClarifaiToken(response.get("access_token").getAsString(),
                    response.get("expires_in").getAsInt()
                );
              } else if (tokenResponse.code() == 401) {
                throw new ClarifaiException("Clarifai app ID and/or app secret are incorrect");
              }
              return null;
            }
          }
      ));
    } catch (InterruptedException | ExecutionException e) {
      final Throwable cause = e.getCause();
      if (cause instanceof ClarifaiException) {
        throw ((ClarifaiException) cause);
      }
      return null;
    }
  }

  @NotNull
  private Gson vendGson() {
    return new GsonBuilder()
        .registerTypeAdapter(ClarifaiClient.class, new JsonDeserializer<ClarifaiClient>() {
          @Override
          public ClarifaiClient deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            // This is a huge hack... basically using our Serialization library as a DI library now.
            return BaseClarifaiClient.this;
          }
        })
        .registerTypeAdapterFactory(new AutoValueTypeAdapterFactory())
        .create();
  }

  @Override public void close() {
    closed = true;
    closeOkHttpClient(tokenRefreshHTTPClient);
    closeOkHttpClient(httpClient);
  }
}

