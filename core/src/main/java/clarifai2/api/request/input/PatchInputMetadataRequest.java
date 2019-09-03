package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.grpc.MetadataConverter;
import clarifai2.internal.InternalUtil;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonObject;
import com.google.protobuf.Struct;
import org.jetbrains.annotations.NotNull;

public final class PatchInputMetadataRequest extends ClarifaiRequest.Builder<ClarifaiInput> {

  @NotNull private final String inputID;
  @NotNull private final JsonObject metadata;

  public PatchInputMetadataRequest(
      @NotNull BaseClarifaiClient client,
      @NotNull String inputID,
      @NotNull JsonObject metadata
  ) {
    super(client);
    this.inputID = inputID;
    this.metadata = metadata;
    InternalUtil.assertMetadataHasNoNulls(metadata);
  }

  @NotNull @Override protected String method() {
    return "PATCH";
  }

  @NotNull @Override protected String subUrl() {
    return "v2/inputs";
  }

  @NotNull @Override protected DeserializedRequest<ClarifaiInput> request() {
    return new DeserializedRequest<ClarifaiInput>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        Struct metadataGrpc = MetadataConverter.jsonObjectToStruct(metadata);

        InputOuterClass.Input.Builder inputGrpc = InputOuterClass.Input.newBuilder()
            .setId(inputID)
            .setData(DataOuterClass.Data.newBuilder().setMetadata(metadataGrpc));
        return stub().patchInputs(
            InputOuterClass.PatchInputsRequest.newBuilder().setAction("overwrite").addInputs(inputGrpc).build()
        );
      }

      @NotNull @Override public ClarifaiInput unmarshalerGrpc(Object returnedObject) {
        InputOuterClass.MultiInputResponse inputsResponse = (InputOuterClass.MultiInputResponse) returnedObject;
        return ClarifaiInput.deserialize(inputsResponse.getInputs(0));
      }
    };
  }
}
