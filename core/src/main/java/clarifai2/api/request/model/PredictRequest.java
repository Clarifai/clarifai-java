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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class PredictRequest<PREDICTION extends Prediction>
    extends ClarifaiRequest.Builder<List<ClarifaiOutput<PREDICTION>>> {

  @NotNull private final String modelID;
  @NotNull private final List<ClarifaiInput> inputData = new ArrayList<>();

  @Nullable private ModelVersion version = null;

  public PredictRequest(@NotNull final BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  @NotNull public PredictRequest<PREDICTION> withInputs(@NotNull Collection<ClarifaiInput> inputData) {
    this.inputData.addAll(inputData);
    return this;
  }

  @NotNull public PredictRequest<PREDICTION> withInputs(@NotNull ClarifaiInput... inputData) {
    Collections.addAll(this.inputData, inputData);
    return this;
  }

  @NotNull public PredictRequest<PREDICTION> withVersion(@NotNull ModelVersion version) {
    this.version = version;
    return this;
  }

  @NotNull @Override protected JSONUnmarshaler<List<ClarifaiOutput<PREDICTION>>> unmarshaler() {
    return new JSONUnmarshaler<List<ClarifaiOutput<PREDICTION>>>() {
      @Nullable @Override
      public List<ClarifaiOutput<PREDICTION>> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return InternalUtil.fromJson(
            gson,
            json.getAsJsonObject().get("outputs"),
            new TypeToken<List<ClarifaiOutput<PREDICTION>>>() {}
        );
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    final JsonObject body = new JSONObjectBuilder()
        .add("inputs", new JSONArrayBuilder()
            .addAll(inputData, new Func1<ClarifaiInput, JsonElement>() {
              @NotNull @Override public JsonElement call(@NotNull ClarifaiInput model) {
                return gson.toJsonTree(model);
              }
            }))
        .build();
    return new Request.Builder()
        .url(buildURL(version != null
            ? String.format("/v2/models/%s/versions/%s/outputs", modelID, version.id())
            : String.format("/v2/models/%s/outputs", modelID)
        ))
        .post(toRequestBody(body))
        .build();
  }
}
