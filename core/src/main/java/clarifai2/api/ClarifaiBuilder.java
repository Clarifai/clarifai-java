package clarifai2.api;

import clarifai2.exception.DeprecationException;
import clarifai2.grpc.ClarifaiHttpClient;
import clarifai2.grpc.ClarifaiHttpClientImpl;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ClarifaiBuilder {

  @NotNull
  final String apiKey;

  @NotNull
  OkHttpClient client = new OkHttpClient();

  @NotNull
  HttpUrl baseURL = HttpUrl.parse("https://api.clarifai.com");

  @NotNull
  ClarifaiHttpClient clarifaiHttpClient;

  public ClarifaiBuilder(@NotNull String appID, @NotNull String appSecret) {
    throw new DeprecationException(
        "Using app ID/secret is deprecated. Please switch to using API key. See here how: " +
            "http://help.clarifai.com/api/account-related/all-about-api-keys"
    );
  }

  public ClarifaiBuilder(@NotNull String apiKey) {
    this(new ClarifaiHttpClientImpl(apiKey));
  }

  /**
   * This constructor is only mean to be used internally to facilitate the unit testing environment.
   */
  public ClarifaiBuilder(@NotNull ClarifaiHttpClient clarifaiHttpClient) {
    this.clarifaiHttpClient = clarifaiHttpClient;
    this.apiKey = clarifaiHttpClient.apiKey();
  }

  @NotNull
  public ClarifaiBuilder client(@NotNull OkHttpClient client) {
    this.client = client;
    clarifaiHttpClient.client(client);
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
          "Cannot specify a baseURL for the Clarifai client that has path segments (it should be just a base host "
              + "URL). You specified: " + baseURL
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
