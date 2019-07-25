package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ModelVersion;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class GetModelInputsRequest
    extends ClarifaiPaginatedRequest.Builder<List<ClarifaiInput>, GetModelInputsRequest> {

  @NotNull private final String modelID;

  @Nullable private String modelVersionID;

  public GetModelInputsRequest(@NotNull final BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  @NotNull public GetModelInputsRequest fromSpecificModelVersion(@NotNull String modelVersionID) {
    this.modelVersionID = modelVersionID;
    return this;
  }

  @NotNull public GetModelInputsRequest fromSpecificModelVersion(@NotNull ModelVersion modelVersion) {
    return fromSpecificModelVersion(modelVersion.id());
  }

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl(final int page) {
    String url;
    if (modelVersionID != null) {
      url = "v2/models/" + modelID + "/versions/" + modelVersionID + "/inputs";
    } else {
      url = "v2/models/" + modelID + "/inputs";
    }
    return buildURL(url, page);
  }

  @NotNull @Override protected List<ClarifaiInput> unmarshalerGrpc(Object returnedObject) {
    InputOuterClass.MultiInputResponse inputsResponse = (InputOuterClass.MultiInputResponse) returnedObject;
    List<ClarifaiInput> inputs = new ArrayList<>();
    for (InputOuterClass.Input input : inputsResponse.getInputsList()) {
      inputs.add(ClarifaiInput.deserialize(input));
    }
    return inputs;
  }

  @NotNull @Override protected ListenableFuture buildRequestGrpc(int page) {
    return stub(page).listModelInputs(InputOuterClass.ListModelInputsRequest.newBuilder().build());
  }
}
