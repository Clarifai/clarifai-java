package clarifai2.api;

import clarifai2.BuildConfig;
import clarifai2.exception.ClarifaiClientClosedException;
import clarifai2.exception.DeprecationException;
import clarifai2.grpc.ClarifaiHttpClient;
import clarifai2.grpc.ClarifaiHttpClientImpl;
import clarifai2.grpc.FkClarifaiHttpClient;
import clarifai2.internal.AutoValueTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;

public abstract class BaseClarifaiClient implements ClarifaiClient {

  @NotNull
  public final Gson gson;

  @NotNull
  public final OkHttpClient httpClient;

  @NotNull
  public final ClarifaiHttpClient clarifaiHttpClient;

  @NotNull
  public final HttpUrl baseURL;

  @Nullable
  private final String apiKey;

  private boolean closed = false;

  BaseClarifaiClient(@NotNull ClarifaiBuilder builder) {
    this.apiKey = builder.apiKey;

    this.gson = vendGson();

    this.baseURL = builder.baseURL;

    final OkHttpClient unmodifiedClient = builder.client;

    this.httpClient = unmodifiedClient.newBuilder().addInterceptor(new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        final Request.Builder requestBuilder = chain.request().newBuilder()
            .header("X-Clarifai-Client", String.format("java:%s:%s", BuildConfig.VERSION, System.getProperty("java.version", "?")));
        if (closed) {
          throw new ClarifaiClientClosedException("This " + ClarifaiClient.class.getSimpleName() + " has already been closed");
        }
        requestBuilder.addHeader("Authorization", "Key " + apiKey);
        return chain.proceed(requestBuilder.build());
      }
    }).build();

    clarifaiHttpClient = builder.clarifaiHttpClient;
    clarifaiHttpClient.client(this.httpClient);
  }

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

  @Override public boolean hasValidToken() {
    throw new DeprecationException(
        "Using app ID/secret is deprecated, and the hasValidToken method as well. Please switch to using API key. "
            + "See here how: http://help.clarifai.com/api/account-related/all-about-api-keys"
    );
  }

  @NotNull @Override public ClarifaiToken getToken() throws IllegalStateException {
    throw new DeprecationException(
        "Using app ID/secret is deprecated, and the getToken method as well. Please switch to using API key. "
            + "See here how: http://help.clarifai.com/api/account-related/all-about-api-keys"
    );
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
    closeOkHttpClient(httpClient);
  }
}
