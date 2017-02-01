package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
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

  @Nullable private ModelVersion version = null;
  @Nullable private String language = null;

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
    this.version = version;
    return this;
  }

  @NotNull public PredictRequest<PREDICTION> withLanguage(@NotNull String language) {
    this.language = language;
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
        if (language != null) {
          bodyBuilder.add("model", new JSONObjectBuilder()
              .add("output_info", new JSONObjectBuilder()
                  .add("output_config", new JSONObjectBuilder()
                      .add("language", language))));
        }
        final JsonObject body = bodyBuilder.build();
        if (version == null) {
          return postRequest("/v2/models/" + modelID + "/outputs", body);
        }
        return postRequest("/v2/models/" + modelID + "/versions/" + version.id() + "/outputs", body);
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
