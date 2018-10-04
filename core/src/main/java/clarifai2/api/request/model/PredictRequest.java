package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
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

  @NotNull @Override protected DeserializedRequest<List<ClarifaiOutput<PREDICTION>>> request() {
    return new DeserializedRequest<List<ClarifaiOutput<PREDICTION>>>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder()
            .add("inputs", new JSONArrayBuilder()
                .addAll(inputData, new Func1<ClarifaiInput, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull ClarifaiInput model) {
                    return client.gson.toJsonTree(model);
                  }
                }));
        if (language != null || minValue != null || maxConcepts != null || concepts.size() > 0) {
          JSONObjectBuilder outputConfig = new JSONObjectBuilder()
              .addIfNotNull("language", language)
              .addIfNotNull("min_value", minValue)
              .addIfNotNull("max_concepts", maxConcepts);
          if (concepts.size() > 0) {
            outputConfig.add("select_concepts", new JSONArrayBuilder()
                .addAll(concepts, new Func1<Concept, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                    return client.gson.toJsonTree(concept);
                  }
                }));
          }
          bodyBuilder.add("model", new JSONObjectBuilder()
              .add("output_info", new JSONObjectBuilder()
                  .add("output_config", outputConfig
                  )));
        }
        final JsonObject body = bodyBuilder.build();
        if (modelVersionID == null) {
          return postRequest("/v2/models/" + modelID + "/outputs", body);
        }
        return postRequest("/v2/models/" + modelID + "/versions/" + modelVersionID + "/outputs", body);
      }

      @NotNull @Override public JSONUnmarshaler<List<ClarifaiOutput<PREDICTION>>> unmarshaler() {
        return new JSONUnmarshaler<List<ClarifaiOutput<PREDICTION>>>() {
          @NotNull @Override
          public List<ClarifaiOutput<PREDICTION>> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return InternalUtil.fromJson(
                gson,
                json.getAsJsonObject().get("outputs"),
                new TypeToken<List<ClarifaiOutput<PREDICTION>>>() {}
            );
          }
        };
      }
    };
  }
}
