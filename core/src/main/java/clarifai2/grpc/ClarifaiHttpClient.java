package clarifai2.grpc;

import okhttp3.OkHttpClient;

public interface ClarifaiHttpClient {
  String apiKey();
  String executeRequest(String url, String method, String requestString);
  void client(OkHttpClient client);
}
