package clarifai2.dto.input;

import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static clarifai2.internal.InternalUtil.toJson;

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

  static class Adapter extends JSONAdapterFactory<ClarifaiFileImage> {
    @Nullable @Override protected Serializer<ClarifaiFileImage> serializer() {
      return new Serializer<ClarifaiFileImage>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiFileImage value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("base64", ByteString.of(value.bytes()).base64())
              .add("crop", toJson(gson, value.crop(), Crop.class))
              .build();
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiFileImage> typeToken() {
      return new TypeToken<ClarifaiFileImage>() {};
    }
  }
}