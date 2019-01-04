package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.internal.grpc.api.OutputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class PredictRequest<PREDICTION extends Prediction>
    extends ClarifaiRequest.Builder<List<ClarifaiOutput<PREDICTION>>> {

  @NotNull private final String modelID;
  @NotNull private final List<ClarifaiInput> inputData = new ArrayList<>();

  @Nullable private String modelVersionID = null;
  @Nullable private String language = null;

  @Nullable private Double minValue = null;
  @Nullable private Integer maxConcepts = null;

  @Nullable private Integer sampleMs = null;

  @NotNull private final List<Concept> concepts = new ArrayList<>();

  public PredictRequest(@NotNull final BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  @NotNull public PredictRequest<PREDICTION> withInputs(@NotNull ClarifaiInput... inputData) {
    return withInputs(Arrays.asList(inputData));
  }

  @NotNull public PredictRequest<PREDICTION> withInputs(@NotNull Collection<ClarifaiInput> inputData) {
    this.inputData.addAll(inputData);
    return this;
  }

  @NotNull public PredictRequest<PREDICTION> withVersion(@NotNull ModelVersion version) {
    this.modelVersionID = version.id();
    return this;
  }

  @NotNull public PredictRequest<PREDICTION> withVersion(@NotNull String versionID) {
    this.modelVersionID = versionID;
    return this;
  }

  @NotNull public PredictRequest<PREDICTION> withLanguage(@NotNull String language) {
    this.language = language;
    return this;
  }

  @NotNull public final PredictRequest<PREDICTION> withMinValue(@Nullable Double minValue) {
    this.minValue = minValue;
    return this;
  }

  @NotNull public final PredictRequest<PREDICTION> withMaxConcepts(@Nullable Integer maxConcepts) {
    this.maxConcepts = maxConcepts;
    return this;
  }

  /**
   * If added, only these concepts will be considered in the prediction.
   * @param concepts the concepts
   * @return PredictRequest instance
   */
  @NotNull public PredictRequest<PREDICTION> selectConcepts(@NotNull Concept... concepts) {
    return selectConcepts(Arrays.asList(concepts));
  }

  /**
   * See {@link PredictRequest#selectConcepts(Concept...)}.
   * @param concepts the concepts
   * @return PredictRequest instance
   */
  @NotNull public PredictRequest<PREDICTION> selectConcepts(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull public final PredictRequest<PREDICTION> withSampleMs(@Nullable Integer sampleMs) {
    this.sampleMs = sampleMs;
    return this;
  }

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl() {
    if (modelVersionID == null) {
      return "/v2/models/" + modelID + "/outputs";
    }
    return "/v2/models/" + modelID + "/versions/" + modelVersionID + "/outputs";
  }

  @NotNull @Override protected DeserializedRequest<List<ClarifaiOutput<PREDICTION>>> request() {
    return new DeserializedRequest<List<ClarifaiOutput<PREDICTION>>>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        List<InputOuterClass.Input> inputs = new ArrayList<>();
        for (ClarifaiInput input : inputData) {
          inputs.add(input.serialize());
        }

        boolean anyOutputConfig = false;
        ModelOuterClass.OutputConfig.Builder outputConfigBuilder = ModelOuterClass.OutputConfig.newBuilder();
        if (language != null) {
          outputConfigBuilder.setLanguage(language);
          anyOutputConfig = true;
        }
        if (minValue != null) {
          outputConfigBuilder.setMinValue(minValue.floatValue());
          anyOutputConfig = true;
        }
        if (maxConcepts != null) {
          outputConfigBuilder.setMaxConcepts(maxConcepts);
          anyOutputConfig = true;
        }
        if (sampleMs != null) {
          outputConfigBuilder.setSampleMs(sampleMs);
          anyOutputConfig = true;
        }
        if (!concepts.isEmpty()) {
          List<ConceptOuterClass.Concept> selectConceptsGrpc = new ArrayList<>();
          for (Concept concept : concepts) {
            selectConceptsGrpc.add(concept.serialize());
          }
          outputConfigBuilder.addAllSelectConcepts(selectConceptsGrpc);
          anyOutputConfig = true;
        }
        InputOuterClass.PostModelOutputsRequest.Builder requestBuilder =
            InputOuterClass.PostModelOutputsRequest.newBuilder()
                .addAllInputs(inputs);

        if (anyOutputConfig) {
          requestBuilder.setModel(
              ModelOuterClass.Model.newBuilder()
                  .setOutputInfo(ModelOuterClass.OutputInfo.newBuilder().setOutputConfig(outputConfigBuilder))
          );
        }

        return stub().postModelOutputs(requestBuilder.build());
      }

      @NotNull @Override public List<ClarifaiOutput<PREDICTION>> unmarshalerGrpc(Object returnedObject) {
        OutputOuterClass.MultiOutputResponse response = (OutputOuterClass.MultiOutputResponse) returnedObject;

        List<ClarifaiOutput<PREDICTION>> outputs = new ArrayList<>();
        for (OutputOuterClass.Output output : response.getOutputsList()) {
          outputs.add(ClarifaiOutput.<PREDICTION>deserialize(output, client));
        }

        return outputs;
      }
    };
  }
}
