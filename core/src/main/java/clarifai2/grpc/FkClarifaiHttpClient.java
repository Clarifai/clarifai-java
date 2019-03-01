package clarifai2.grpc;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

public class FkClarifaiHttpClient implements ClarifaiHttpClient {
  private final String response;
  private String requestUrl;
  private String requestMethod;
  private String requestBody;

  public FkClarifaiHttpClient(String response) {
    this.response = response;
  }

  @NotNull public String requestUrl() {
    return requestUrl;
  }

  @NotNull public String requestMethod() {
    return requestMethod;
  }

  @NotNull public String requestBody() {
    return requestBody;
  }

  @Override public String apiKey() {
    return "FAKE-API-KEY";
  }

  @Override public void client(OkHttpClient client) {
  }

  @Override @NotNull public String executeRequest(String url, String method, String body) {
    requestUrl = url;
    requestMethod = method;
    requestBody = body;
    return response;
  }
}
