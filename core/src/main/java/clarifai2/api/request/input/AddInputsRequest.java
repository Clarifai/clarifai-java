package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class AddInputsRequest extends ClarifaiRequest.Builder<List<ClarifaiInput>> {

  private static final int MAX_NUM_INPUTS = 128;

  @NotNull private final List<ClarifaiInput> inputs = new ArrayList<>();

  private boolean allowDuplicateURLs = false;

  public AddInputsRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl() {
    return "/v2/inputs";
  }

  @NotNull public AddInputsRequest plus(@NotNull ClarifaiInput... inputs) {
    return plus(Arrays.asList(inputs));
  }

  @NotNull public AddInputsRequest plus(@NotNull Collection<ClarifaiInput> inputs) {
    if (inputs.size() > MAX_NUM_INPUTS) {
      throw new IllegalArgumentException(String.format("Can't add more than %d inputs in one request", MAX_NUM_INPUTS));
    }
    this.inputs.addAll(inputs);
    return this;
  }

  /**
   * @param allowDuplicateURLs whether the API should allow uploading inputs with URLs that are already part of other
   *                           inputs in this app.
   * @return this request builder
   */
  @NotNull public AddInputsRequest allowDuplicateURLs(boolean allowDuplicateURLs) {
    this.allowDuplicateURLs = allowDuplicateURLs;
    return this;
  }

  @NotNull @Override protected DeserializedRequest<List<ClarifaiInput>> request() {
    return new DeserializedRequest<List<ClarifaiInput>>() {

      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        List<InputOuterClass.Input> inputsGrpc = new ArrayList<>();
        for (ClarifaiInput input : inputs) {
          // Because allow duplicate URL is actually a property of an input, and not this outer request, we have to
          // pass it in to the serialization method.
          inputsGrpc.add(input.serialize(allowDuplicateURLs));
        }
        return stub().postInputs(InputOuterClass.PostInputsRequest.newBuilder().addAllInputs(inputsGrpc).build());
      }

      @NotNull @Override public List<ClarifaiInput> unmarshalerGrpc(Object returnedObject) {
        InputOuterClass.MultiInputResponse inputsResponse = (InputOuterClass.MultiInputResponse) returnedObject;

        List<ClarifaiInput> result = new ArrayList<>();
        for (InputOuterClass.Input input : inputsResponse.getInputsList()) {
          result.add(ClarifaiInput.deserialize(input));
        }
        return result;
      }
    };
  }
}
