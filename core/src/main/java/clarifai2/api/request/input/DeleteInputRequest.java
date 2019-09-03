package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonNull;
import org.jetbrains.annotations.NotNull;

public final class DeleteInputRequest extends ClarifaiRequest.Builder<JsonNull> {

  private final String inputID;

  public DeleteInputRequest(@NotNull BaseClarifaiClient client, @NotNull String inputID) {
    super(client);
    this.inputID = inputID;
  }

  @NotNull @Override protected String method() {
    return "DELETE";
  }

  @NotNull @Override protected String subUrl() {
    return "v2/inputs/" + inputID;
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().deleteInput(InputOuterClass.DeleteInputRequest.newBuilder().build());
      }

      @NotNull @Override public JsonNull unmarshalerGrpc(Object returnedObject) {
        return JsonNull.INSTANCE;
      }
    };
  }
}
