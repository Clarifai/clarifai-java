package clarifai2.grpc;

import clarifai2.exception.ClarifaiException;
import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonChannel extends io.grpc.Channel {

  public static final CallOptions.Key<String> CLARIFAI_METHOD_OPTION = CallOptions.Key.create("CLARIFAI_METHOD_OPTION");
  public static final CallOptions.Key<String> CLARIFAI_BASE_URL_OPTION =
      CallOptions.Key.create("CLARIFAI_BASE_URL_OPTION");
  public static final CallOptions.Key<String> CLARIFAI_SUB_URL_OPTION =
      CallOptions.Key.create("CLARIFAI_SUB_URL_OPTION");

  private final ClarifaiHttpClient clarifaiHttpClient;

  public JsonChannel(ClarifaiHttpClient clarifaiHttpClient) {
    this.clarifaiHttpClient = clarifaiHttpClient;
  }

  @Override
  public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(
          MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions
  ) {
    final String method = callOptions.getOption(CLARIFAI_METHOD_OPTION);
    if (method == null) {
        throw new ClarifaiException("The request doesn't have the CLARIFAI_METHOD_OPTION set.");
    }

    final String baseUrl = callOptions.getOption(CLARIFAI_BASE_URL_OPTION);
    if (baseUrl == null) {
      throw new ClarifaiException("The request doesn't have the CLARIFAI_BASE_URL_OPTION set.");
    }

    final String subUrl = callOptions.getOption(CLARIFAI_SUB_URL_OPTION);
    if (subUrl == null) {
        throw new ClarifaiException("The request doesn't have the CLARIFAI_SUB_URL_OPTION set.");
    }

    String url = baseUrl + subUrl;
    return new JsonClientCall<>(clarifaiHttpClient, method, url, methodDescriptor);
  }

  @Override
  public String authority() {
    return null;
  }
}


class JsonClientCall<RequestT, ResponseT> extends ClientCall<RequestT, ResponseT> {

  private final ClarifaiHttpClient clarifaiHttpClient;
  private final String method;
  private final String url;
  private final MethodDescriptor<RequestT, ResponseT> methodDescriptor;

  private Listener<ResponseT> responseListener;

  JsonClientCall(ClarifaiHttpClient clarifaiHttpClient, String method, String url,
      MethodDescriptor<RequestT, ResponseT> methodDescriptor) {
    this.clarifaiHttpClient = clarifaiHttpClient;
    this.method = method;
    this.url = url;
    this.methodDescriptor = methodDescriptor;
    }

  @Override
  public void start(Listener<ResponseT> responseListener, Metadata headers) {
    responseListener.onReady();
    this.responseListener = responseListener;
  }

  @Override
  public void request(int numMessages) {
    // This never gets called.
  }

  @Override
  public void cancel(@Nullable String message, @Nullable Throwable cause) {
    // This never gets called.
  }

  @Override
  public void halfClose() {
    // This never gets called.
  }

  @Override
  public void sendMessage(Object message) {
    MethodDescriptor.Marshaller<RequestT> requestMarshaller = methodDescriptor.getRequestMarshaller();
    MethodDescriptor.Marshaller<ResponseT> responseMarshaller = methodDescriptor.getResponseMarshaller();

    InputStream stream = requestMarshaller.stream((RequestT) message);

    java.util.Scanner s = new java.util.Scanner(stream, "UTF-8").useDelimiter("\\A");
    String requestString = s.hasNext() ? s.next() : "";
    s.close();

    String responseString = clarifaiHttpClient.executeRequest(url, method, requestString);

    ResponseT responseObject = responseMarshaller.parse(
      new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8))
    );
    responseListener.onMessage(responseObject);
    responseListener.onClose(Status.OK, new Metadata());
  }
}
