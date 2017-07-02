package clarifai2.dto.multimodel;

import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Prediction;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static clarifai2.internal.InternalUtil.fromJson;

/*
 * NOTE: This class is a part of a future api.  Details of both the interface and
 * implementation may change!
 */

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(MultiModelResult.Adapter.class)
public abstract class MultiModelResult<PREDICTION extends Prediction> {

  MultiModelResult() {} // AutoValue instances only

  @Nullable public abstract ClarifaiStatus status();
  @Nullable public abstract ClarifaiInput input();
  @Nullable public abstract List<ClarifaiOutput<PREDICTION>> predictions();


  static class Adapter extends JSONAdapterFactory<MultiModelResult> {
    @Nullable @Override protected Deserializer<MultiModelResult> deserializer() {
      return new Deserializer<MultiModelResult>() {
        @Nullable @Override
        public MultiModelResult deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<MultiModelResult> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          ClarifaiStatus status = fromJson(gson, root.get("status"), ClarifaiStatus.class);
          ClarifaiInput input = fromJson(gson, root.get("input"), ClarifaiInput.class);
          ClarifaiOutput<Prediction> onePrediction = fromJson(gson,
              root.get("outputs").getAsJsonArray().get(0),
              new TypeToken<ClarifaiOutput<Prediction>>() {}
          );
          List<ClarifaiOutput<Prediction>> predictions =
              fromJson(gson, root.get("outputs"), new TypeToken<List<ClarifaiOutput<Prediction>>>() {});
          return new AutoValue_MultiModelResult(status, input, predictions);
        }
      };
    }

    @NotNull @Override protected TypeToken<MultiModelResult> typeToken() {
      return new TypeToken<MultiModelResult>() {};
    }
  }
}

