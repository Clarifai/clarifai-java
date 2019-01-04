package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

public final class GetInputRequest extends ClarifaiRequest.Builder<ClarifaiInput> {

  @NotNull private final String inputID;

  public GetInputRequest(@NotNull final BaseClarifaiClient helper, @NotNull String inputID) {
    super(helper);
    this.inputID = inputID;
  }

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl() {
    return "/v2/inputs/" + inputID;
  }

  @NotNull @Override protected DeserializedRequest<ClarifaiInput> request() {
    return new DeserializedRequest<ClarifaiInput>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub()
            .getInput(InputOuterClass.GetInputRequest.newBuilder().build());
      }

      @NotNull @Override public ClarifaiInput unmarshalerGrpc(Object returnedObject) {
        InputOuterClass.SingleInputResponse inputResponse = (InputOuterClass.SingleInputResponse) returnedObject;
        return ClarifaiInput.deserialize(inputResponse.getInput());
      }
    };
  }
}
