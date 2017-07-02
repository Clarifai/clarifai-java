package clarifai2.api;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ClarifaiBuilder {

  @Nullable
  final String appID;

  @Nullable
  final String appSecret;

  @Nullable
  final String apiKey;

  @NotNull
  OkHttpClient client = new OkHttpClient();

  @NotNull
  HttpUrl baseURL = HttpUrl.parse("https://api.clarifai.com");

  public ClarifaiBuilder(@NotNull String appID, @NotNull String appSecret) {
    this.appID = appID;
    this.appSecret = appSecret;
    this.apiKey = null;
  }

  public ClarifaiBuilder(@NotNull String apiKey) {
    this.apiKey = apiKey;
    this.appID = null;
    this.appSecret = null;
  }

  @NotNull
  public ClarifaiBuilder client(@NotNull OkHttpClient client) {
    this.client = client;
    return this;
  }

  @NotNull
  public ClarifaiBuilder baseURL(@NotNull String baseURL) {
    return baseURL(HttpUrl.parse(baseURL));
  }

  @NotNull
  public ClarifaiBuilder baseURL(@NotNull URL baseURL) {
    return baseURL(HttpUrl.get(baseURL));
  }

  @NotNull
  public ClarifaiBuilder baseURL(@NotNull HttpUrl baseURL) {
    final List<String> segments = baseURL.pathSegments();
    // According to the docs, pathSegments() is never empty, but can have one empty string
    final boolean isURLOnlyBase = (segments.size() == 1) && (segments.get(0).equals(""));
    if (!isURLOnlyBase) {
      throw new IllegalArgumentException(
          "Cannot specify a baseURL for the Clarifai client that has path segments (it should be just a base host URL). "
              + "You specified: " + baseURL
      );
    }
    this.baseURL = baseURL;
    return this;
  }

  /**
   * Builds the API client, blocking until an access token has been retrieved.
   * <p>
   * This involves a network call, but the token is refreshed on another thread while this one
   * waits, so Android users don't have to worry about NetworkOnMainThreadException, for example.
   *
   * @return a {@link ClarifaiClient} instance
   */
  @NotNull
  public ClarifaiClient buildSync() {
    return new ClarifaiClientImpl(this);
  }

  /**
   * Begins building the API client instance in the background, and returns immediately
   *
   * @return a {@link Future} whose value will be present when the client has successfully made
   * a network request and retrieved an access token
   */
  @NotNull
  public Future<ClarifaiClient> build() {
      final ExecutorService executor = Executors.newFixedThreadPool(1);
      final Future<ClarifaiClient> future = executor.submit(new Callable<ClarifaiClient>() {
        @Override public ClarifaiClient call() throws Exception {
          return new ClarifaiClientImpl(ClarifaiBuilder.this);
        }
      });
      executor.shutdown();
      return future;
    }
}
