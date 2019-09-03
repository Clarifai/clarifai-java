package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ModelVersionOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ModelVersion;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

public final class GetModelVersionRequest extends ClarifaiRequest.Builder<ModelVersion> {

  @NotNull private final String modelID;
  @NotNull private final String versionID;

  public GetModelVersionRequest(
      @NotNull BaseClarifaiClient client,
      @NotNull String modelID,
      @NotNull String versionID
  ) {
    super(client);
    this.modelID = modelID;
    this.versionID = versionID;
  }

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl() {
    return String.format("v2/models/%s/versions/%s", modelID, versionID);
  }

  @NotNull @Override protected DeserializedRequest<ModelVersion> request() {
    return new DeserializedRequest<ModelVersion>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().getModelVersion(ModelVersionOuterClass.GetModelVersionRequest.newBuilder().build());
      }

      @NotNull @Override public ModelVersion unmarshalerGrpc(Object returnedObject) {
        ModelVersionOuterClass.SingleModelVersionResponse modelVersionResponse =
            (ModelVersionOuterClass.SingleModelVersionResponse) returnedObject;
        return ModelVersion.deserialize(modelVersionResponse.getModelVersion());
      }
    };
  }
}
