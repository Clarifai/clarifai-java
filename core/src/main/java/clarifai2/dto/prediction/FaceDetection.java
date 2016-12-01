package clarifai2.dto.prediction;

import clarifai2.dto.input.image.Crop;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(FaceDetection.Adapter.class)
public abstract class FaceDetection extends Prediction {

  @NotNull public abstract Crop boundingBox();

  FaceDetection() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<FaceDetection> {

    @Override
    public FaceDetection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject box = json.getAsJsonObject().getAsJsonObject("region_info").getAsJsonObject("bounding_box");
      return new AutoValue_FaceDetection(
          fromJson(context, box, Crop.class)
      );
    }
  }
}