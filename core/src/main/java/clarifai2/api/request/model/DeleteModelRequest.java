package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonNull;
import org.jetbrains.annotations.NotNull;

public final class DeleteModelRequest extends ClarifaiRequest.Builder<JsonNull> {

  @NotNull private final String modelID;

  public DeleteModelRequest(@NotNull final BaseClarifaiClient helper, @NotNull String modelID) {
    super(helper);
    this.modelID = modelID;
  }

  @NotNull @Override protected String method() {
    return "DELETE";
  }

  @NotNull @Override protected String subUrl() {
    return "v2/models/" + modelID;
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().deleteModel(ModelOuterClass.DeleteModelRequest.newBuilder().build());
      }

      @NotNull @Override public JsonNull unmarshalerGrpc(Object returnedObject) {
        return JsonNull.INSTANCE;
      }
    };
  }
}
