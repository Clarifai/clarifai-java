package clarifai2.dto.model.output;

import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import clarifai2.dto.prediction.Prediction;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiOutput.Adapter.class)
public abstract class ClarifaiOutput<PREDICTION extends Prediction> {

  @NotNull public abstract String id();
  @NotNull public abstract Date createdAt();
  @NotNull public abstract Model<PREDICTION> model();
  @NotNull public abstract ClarifaiInput input();
  @NotNull public abstract List<PREDICTION> data();

  ClarifaiOutput() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<ClarifaiOutput<?>> {
    @Override
    public ClarifaiOutput<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();

      final Class<? extends Prediction> predictionType =
          ModelType.determineFromDataRoot(root.getAsJsonObject("data"))
              .predictionType();

      final List<Prediction> allPredictions = new ArrayList<>();
      for (final Map.Entry<String, JsonElement> data : root.getAsJsonObject("data").entrySet()) {
        final JsonArray array = data.getValue().isJsonArray() ? data.getValue().getAsJsonArray() : new JsonArray();
        for (final JsonElement predictionJSON : array) {
          allPredictions.add(context.<Prediction>deserialize(predictionJSON, predictionType));
        }
      }
      return new AutoValue_ClarifaiOutput<>(
          root.get("id").getAsString(),
          context.<Date>deserialize(root.get("created_at"), Date.class),
          context.<Model<Prediction>>deserialize(root.get("model"), Model.class),
          context.<ClarifaiInput>deserialize(root.get("input"), ClarifaiInput.class),
          allPredictions
      );
    }
  }
}
