package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.feedback.RegionFeedback;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.prediction.Concept;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class PatchInputRequest extends ClarifaiRequest.Builder<ClarifaiInput> {
  @NotNull private final String action;
  @NotNull private final String inputID;

  @NotNull private final List<Concept> concepts = new ArrayList<>();
  @NotNull private final List<RegionFeedback> regionFeedbacks = new ArrayList<>();

  public PatchInputRequest(@NotNull BaseClarifaiClient client, @NotNull String inputID, @NotNull String action) {
    super(client);
    this.inputID = inputID;
    this.action = action;
  }

  @NotNull public PatchInputRequest plus(@NotNull Concept... concepts) {
    return plus(Arrays.asList(concepts));
  }

  @NotNull public PatchInputRequest plus(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull public PatchInputRequest plusRegionFeedbacks(@NotNull RegionFeedback... regionFeedbacks) {
    return plusRegionFeedbacks(Arrays.asList(regionFeedbacks));
  }

  @NotNull public PatchInputRequest plusRegionFeedbacks(@NotNull Collection<RegionFeedback> regionFeedbacks) {
    this.regionFeedbacks.addAll(regionFeedbacks);
    return this;
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
        DataOuterClass.Data.Builder dataBuilder = DataOuterClass.Data.newBuilder();
        if (!concepts.isEmpty()) {
          List<ConceptOuterClass.Concept> conceptsGrpc = new ArrayList<>();
          for (Concept concept : concepts) {
            conceptsGrpc.add(concept.serialize());
          }
          dataBuilder.addAllConcepts(conceptsGrpc);
        }
        if (!regionFeedbacks.isEmpty()) {
          List<DataOuterClass.Region> regionFeedbacksGrpc = new ArrayList<>();
          for (RegionFeedback regionFeedback : regionFeedbacks) {
            regionFeedbacksGrpc.add(regionFeedback.serialize());
          }
          dataBuilder.addAllRegions(regionFeedbacksGrpc);
        }
        return stub().patchInputs(
            InputOuterClass.PatchInputsRequest.newBuilder()
                .setAction(action)
                .addInputs(InputOuterClass.Input.newBuilder().setId(inputID).setData(dataBuilder))
                .build()
        );
      }

      @NotNull @Override public ClarifaiInput unmarshalerGrpc(Object returnedObject) {
        InputOuterClass.MultiInputResponse inputsResponse = (InputOuterClass.MultiInputResponse) returnedObject;
        return ClarifaiInput.deserialize(inputsResponse.getInputs(0));
      }
    };
  }
}
