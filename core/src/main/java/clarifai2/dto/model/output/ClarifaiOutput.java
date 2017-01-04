package clarifai2.dto.model.output;

import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.HasClarifaiIDRequired;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import clarifai2.dto.prediction.Focus;
import clarifai2.dto.prediction.Prediction;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiOutput.Adapter.class)
public abstract class ClarifaiOutput<PREDICTION extends Prediction> implements HasClarifaiIDRequired {

  @NotNull public abstract Date createdAt();
  @NotNull public abstract Model<PREDICTION> model();
  @NotNull public abstract ClarifaiInput input();
  @NotNull public abstract List<PREDICTION> data();
  @NotNull public abstract ClarifaiStatus status();

  ClarifaiOutput() {} // AutoValue instances only

  @SuppressWarnings("rawtypes")
  static class Adapter extends JSONAdapterFactory<ClarifaiOutput> {
    @Nullable @Override protected Deserializer<ClarifaiOutput> deserializer() {
      return new Deserializer<ClarifaiOutput>() {
        @Nullable @Override
        public ClarifaiOutput deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiOutput> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);

          final Class<? extends Prediction> predictionType =
              ModelType.determineFromDataRoot(root.getAsJsonObject("data")).predictionType();

          final List<Prediction> allPredictions = new ArrayList<>();
          for (final Map.Entry<String, JsonElement> data : root.getAsJsonObject("data").entrySet()) {
            final JsonArray array = data.getValue().isJsonArray() ? data.getValue().getAsJsonArray() : new JsonArray();
            for (final JsonElement predictionJSON : array) {
              allPredictions.add(fromJson(gson, predictionJSON, predictionType));
            }
          }
          return new AutoValue_ClarifaiOutput<>(
              root.get("id").getAsString(),
              fromJson(gson, root.get("created_at"), Date.class),
              fromJson(gson, root.get("model"), new TypeToken<Model<Prediction>>() {}),
              fromJson(gson, root.get("input"), ClarifaiInput.class),
              allPredictions
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiOutput> typeToken() {
      return new TypeToken<ClarifaiOutput>() {};
    }
  }
}
