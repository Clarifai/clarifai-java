package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInputsStatus;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

public final class GetInputsStatusRequest extends ClarifaiRequest.Builder<ClarifaiInputsStatus> {

  public GetInputsStatusRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl() {
    return "/v2/inputs/status";
  }

  @NotNull @Override protected DeserializedRequest<ClarifaiInputsStatus> request() {
    return new DeserializedRequest<ClarifaiInputsStatus>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().getInputCount(InputOuterClass.GetInputCountRequest.newBuilder().build());
      }

      @NotNull @Override public ClarifaiInputsStatus unmarshalerGrpc(Object returnedObject) {
        InputOuterClass.SingleInputCountResponse inputCountResponse =
            (InputOuterClass.SingleInputCountResponse) returnedObject;
        return ClarifaiInputsStatus.deserialize(inputCountResponse.getCounts());
      }
    };
  }
}
