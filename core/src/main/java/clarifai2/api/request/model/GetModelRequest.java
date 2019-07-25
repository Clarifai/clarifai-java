package clarifai2.api.request.model;

import clarifai2.dto.model.ModelVersion;
import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.Model;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GetModelRequest extends ClarifaiRequest.Builder<Model<?>> {

  @NotNull private final String modelID;
  @Nullable private String modelVersionID;

  public GetModelRequest(
      @NotNull final BaseClarifaiClient helper,
      @NotNull String modelID
  ) {
    super(helper);
    this.modelID = modelID;
  }

  @NotNull public GetModelRequest withVersion(@NotNull ModelVersion version) {
    return withVersion(version.id());
  }

  @NotNull public GetModelRequest withVersion(@NotNull String versionID) {
    this.modelVersionID = versionID;
    return this;
  }


  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl() {
    if (modelVersionID == null) {
      return "v2/models/" + modelID + "/output_info";
    }
    return "v2/models/" + modelID + "/versions/" + modelVersionID + "/output_info";
  }

  @NotNull @Override protected DeserializedRequest<Model<?>> request() {
    return new DeserializedRequest<Model<?>>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().getModel(ModelOuterClass.GetModelRequest.newBuilder().build());
      }

      @NotNull @Override public Model<?> unmarshalerGrpc(Object returnedObject) {
        ModelOuterClass.SingleModelResponse modelResponse = (ModelOuterClass.SingleModelResponse) returnedObject;
        return Model.deserialize(modelResponse.getModel(), client);
      }
    };
  }
}
