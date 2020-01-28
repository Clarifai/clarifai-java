package clarifai2.solutions.moderation.dto;

import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.HasClarifaiIDRequired;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.FaceConcepts;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Frame;
import clarifai2.dto.prediction.Prediction;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import static clarifai2.internal.InternalUtil.isJsonNull;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ModerationOutput.Adapter.class)
public abstract class ModerationOutput<PREDICTION extends Prediction> implements HasClarifaiIDRequired {

  ModerationOutput() {} // AutoValue instances only

  @NotNull public abstract Date createdAt();

  @NotNull public abstract Model<PREDICTION> model();

  @Nullable public abstract ClarifaiInput input();

  @NotNull public abstract List<PREDICTION> data();

  @NotNull public abstract ClarifaiStatus status();

  @Nullable public abstract ModerationStatus moderationStatus();


  @SuppressWarnings("rawtypes")
  static class Adapter extends JSONAdapterFactory<ModerationOutput> {
    @Nullable @Override protected Deserializer<ModerationOutput> deserializer() {
      return new Deserializer<ModerationOutput>() {
        @Nullable @Override
        public ModerationOutput deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ModerationOutput> type,
            @NotNull Gson gson
        ) {

          final JsonObject root = assertJsonIs(json, JsonObject.class);

          final List<Prediction> allPredictions = new ArrayList<>();

          if (!root.get("data").isJsonNull()) {
            JsonObject dataRoot = root.getAsJsonObject("data");

            for (final Map.Entry<String, JsonElement> data : dataRoot.entrySet()) {
              final JsonArray array =
                  data.getValue().isJsonArray() ? data.getValue().getAsJsonArray() : new JsonArray();
              for (JsonElement predictionJSON : array) {
                // Only Concept class is currently supported.
                allPredictions.add(fromJson(gson, predictionJSON, Concept.class));
              }
            }
          }

          return new AutoValue_ModerationOutput<>(
              root.get("id").getAsString(),
              fromJson(gson, root.get("created_at"), Date.class),
              fromJson(gson, root.get("model"), new TypeToken<Model<Prediction>>() {}),
              isJsonNull(root.get("input")) ? null : fromJson(gson, root.get("input"), ClarifaiInput.class),
              allPredictions,
              fromJson(gson, root.get("status"), ClarifaiStatus.class),
              isJsonNull(root.get("moderation")) ?
                  null :
                  fromJson(gson, root.getAsJsonObject("moderation").get("status"), ModerationStatus.class)
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ModerationOutput> typeToken() {
      return new TypeToken<ModerationOutput>() {};
    }
  }
}
