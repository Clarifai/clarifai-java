package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.input.ClarifaiInput;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class GetInputsRequest extends ClarifaiPaginatedRequest.Builder<List<ClarifaiInput>, GetInputsRequest> {
  public GetInputsRequest(@NotNull final BaseClarifaiClient client) {
    super(client);
  }

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl(final int page) {
    return buildURL("/v2/inputs", page);
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
    return stub(page).listInputs(
        InputOuterClass.ListInputsRequest.newBuilder()
            .build()
    );
  }
}
