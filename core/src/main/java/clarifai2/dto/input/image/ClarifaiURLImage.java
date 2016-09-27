package clarifai2.dto.input.image;

import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.net.URL;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiURLImage.Adapter.class)
public abstract class ClarifaiURLImage extends ClarifaiImage {

  @NotNull public abstract URL url();

  @NotNull @Override public abstract ClarifaiURLImage withCrop(@NotNull Crop crop);

  ClarifaiURLImage() {} // AutoValue instances only

  static class Adapter implements JsonSerializer<ClarifaiURLImage>, JsonDeserializer<ClarifaiURLImage> {
    @Override public JsonElement serialize(ClarifaiURLImage src, Type typeOfSrc, JsonSerializationContext context) {
      return new JSONObjectBuilder()
          .add("url", src.url().toString())
          .add("crop", context.serialize(src.crop()))
          .build();
    }

    @Override
    public ClarifaiURLImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
        JsonParseException {
      final JsonObject root = json.getAsJsonObject();
      return ClarifaiImage.of(root.get("url").getAsString())
          .withCrop(context.<Crop>deserialize(root.get("crop"), Crop.class));
    }
  }
}