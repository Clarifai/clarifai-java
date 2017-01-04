package clarifai2.dto.prediction;

import clarifai2.dto.input.image.Crop;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(FaceDetection.Adapter.class)
public abstract class FaceDetection extends Prediction {

  @NotNull public abstract Crop boundingBox();

  FaceDetection() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<FaceDetection> {
    @Nullable @Override protected Deserializer<FaceDetection> deserializer() {
      return new Deserializer<FaceDetection>() {
        @Nullable @Override
        public FaceDetection deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<FaceDetection> type,
            @NotNull Gson gson
        ) {
          final JsonObject box = assertJsonIs(json, JsonObject.class)
              .getAsJsonObject("region_info")
              .getAsJsonObject("bounding_box");
          return new AutoValue_FaceDetection(
              fromJson(gson, box, Crop.class)
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<FaceDetection> typeToken() {
      return new TypeToken<FaceDetection>() {};
    }
  }
}