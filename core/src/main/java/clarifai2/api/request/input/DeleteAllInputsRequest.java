package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonNull;
import org.jetbrains.annotations.NotNull;

public class DeleteAllInputsRequest extends ClarifaiRequest.Builder<JsonNull> {


  public DeleteAllInputsRequest(@NotNull BaseClarifaiClient client) {
    super(client);
  }

  @NotNull @Override protected String method() {
    return "DELETE";
  }

  @NotNull @Override protected String subUrl() {
    return "/v2/inputs";
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().deleteInputs(InputOuterClass.DeleteInputsRequest.newBuilder().setDeleteAll(true).build());
      }

      @NotNull @Override public JsonNull unmarshalerGrpc(Object returnedObject) {
        return JsonNull.INSTANCE;
      }
    };
  }
}
