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
  public final OkHttpClient client;

  @NotNull
  public final HttpUrl baseURL;

  @NotNull
  private final String appID;

  @NotNull
  private final String appSecret;

  @NotNull
  private final OkHttpClient tokenRefreshClient;

  @Nullable
  private ClarifaiToken currentClarifaiToken = null;

  BaseClarifaiClient(@NotNull ClarifaiBuilder builder) {
    this.appID = notNullOrThrow(builder.appID, "appID == null");
    this.appSecret = notNullOrThrow(builder.appSecret, "appSecret == null");

    this.gson = vendGson();

    this.baseURL = builder.baseURL;

    final OkHttpClient unmodifiedClient = builder.client == null ? new OkHttpClient() : builder.client;

    this.client = unmodifiedClient.newBuilder().addInterceptor(new Interceptor() {
      @Override
      public okhttp3.Response intercept(Chain chain) throws IOException {
        final Request request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer " + getCredential().getAccessToken())
            .header("X-Clarifai-Client", "java: " + BuildConfig.VERSION)
            .build();
        return chain.proceed(request);
       }
    }).build();

    this.tokenRefreshClient = unmodifiedClient.newBuilder().build();

    getCredential();
  }

  @NotNull
  synchronized ClarifaiToken getCredential() {
    if (currentClarifaiToken == null ||
        System.currentTimeMillis() >= currentClarifaiToken.getExpiresAt() - 60000) {
      currentClarifaiToken = refresh();
    }
    return currentClarifaiToken;
  }

  @NotNull
  private ClarifaiToken refresh() {
    try {
      return tokenRefreshClient.dispatcher().executorService().invokeAny(Collections.singletonList(
          new Callable<ClarifaiToken>() {
            @Override public ClarifaiToken call() throws Exception {
              final Response tokenResponse = tokenRefreshClient.newCall(new Request.Builder()
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
              }
              throw new ClarifaiException(message("API call to refresh token unsuccessful",
                  "Provided AppID: " + appID,
                  "Provided AppSecret: " + appSecret,
                  "Provided BaseURL: " + baseURL,
                  "Status code: " + tokenResponse.code(),
                  "Message: " + tokenResponse.message(),
                  "Details: " + tokenResponse.body().string()
              ));
            }
          }
      ));
    } catch (InterruptedException | ExecutionException e) {
      throw new ClarifaiException(e);
    }
  }


  @NotNull
  private static String message(String header, String... tabbedInLines) {
    final StringBuilder builder = new StringBuilder(header).append('\n');
    for (String tabbedInLine : tabbedInLines) {
      builder.append('\t')
          .append(tabbedInLine)
          .append('\n');
    }
    builder.setLength(builder.length() - 1);
    return builder.toString();
  }

  @NotNull
  private static <T> T notNullOrThrow(@Nullable T check, String errorMsg) {
    if (check == null) {
      throw new IllegalArgumentException(errorMsg);
    }
    return check;
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

}

