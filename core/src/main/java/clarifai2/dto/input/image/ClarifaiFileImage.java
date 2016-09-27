package clarifai2.dto.input.image;

import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Arrays;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiFileImage.Adapter.class)
public abstract class ClarifaiFileImage extends ClarifaiImage {

  @NotNull public final byte[] imageBytes() {
    // Return a defensive copy so that the underlying data can't be modified
    final byte[] bytes = bytes();
    return Arrays.copyOf(bytes, bytes.length);
  }

  @NotNull @Override public abstract ClarifaiFileImage withCrop(@NotNull Crop crop);

  @SuppressWarnings("mutable") @NotNull abstract byte[] bytes();

  ClarifaiFileImage() {} // AutoValue instances only

  static class Adapter implements JsonSerializer<ClarifaiFileImage> {
    @Override public JsonElement serialize(ClarifaiFileImage src, Type typeOfSrc, JsonSerializationContext context) {
      return new JSONObjectBuilder()
          .add("base64", ByteString.of(src.bytes()).base64())
          .add("crop", context.serialize(src.crop()))
          .build();
    }
  }
}