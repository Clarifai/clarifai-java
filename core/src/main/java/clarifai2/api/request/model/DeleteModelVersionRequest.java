package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ModelVersionOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ModelVersion;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

// The returned object here should be JsonNull since the response does not actually return any ModelVersions.
// But changing this would break client code.
public final class DeleteModelVersionRequest extends ClarifaiRequest.Builder<List<ModelVersion>> {

  @NotNull private final String modelID;
  @NotNull private final String versionID;

  public DeleteModelVersionRequest(
      @NotNull BaseClarifaiClient client,
      @NotNull String modelID,
      @NotNull String versionID
  ) {
    super(client);
    this.modelID = modelID;
    this.versionID = versionID;
  }

  @NotNull @Override protected String method() {
    return "DELETE";
  }

  @NotNull @Override protected String subUrl() {
    return String.format("/v2/models/%s/versions/%s", modelID, versionID);
  }

  @NotNull @Override protected DeserializedRequest<List<ModelVersion>> request() {
    return new DeserializedRequest<List<ModelVersion>>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().deleteModelVersion(ModelVersionOuterClass.DeleteModelVersionRequest.newBuilder().build());
      }

      @NotNull @Override public List<ModelVersion> unmarshalerGrpc(Object returnedObject) {
        return Collections.emptyList();
      }
    };
  }
}
