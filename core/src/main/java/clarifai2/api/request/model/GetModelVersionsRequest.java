package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ModelVersionOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.model.ModelVersion;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class GetModelVersionsRequest
    extends ClarifaiPaginatedRequest.Builder<List<ModelVersion>, GetModelVersionsRequest> {

  @NotNull private final String modelID;

  public GetModelVersionsRequest(@NotNull final BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl(int page) {
    return buildURL("v2/models/" + modelID + "/versions", page);
  }

  @NotNull @Override protected List<ModelVersion> unmarshalerGrpc(Object returnedObject) {
    ModelVersionOuterClass.MultiModelVersionResponse modelVersionsResponse =
        (ModelVersionOuterClass.MultiModelVersionResponse) returnedObject;

    List<ModelVersion> modelVersions = new ArrayList<>();
    for (ModelVersionOuterClass.ModelVersion modelVersion : modelVersionsResponse.getModelVersionsList()) {
      modelVersions.add(ModelVersion.deserialize(modelVersion));
    }

    return modelVersions;
  }

  @NotNull @Override protected ListenableFuture buildRequestGrpc(int page) {
    return stub(page).listModelVersions(ModelVersionOuterClass.ListModelVersionsRequest.newBuilder().build());
  }
}
