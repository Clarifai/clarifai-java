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
@JsonAdapter(ClarifaiFileVideo.Adapter.class)
public abstract class ClarifaiFileVideo extends ClarifaiVideo {

  ClarifaiFileVideo() {} // AutoValue instances only

  @NotNull public final byte[] videoBytes() {
    // Return a defensive copy so that the underlying data can't be modified
    final byte[] bytes = bytes();
    return Arrays.copyOf(bytes, bytes.length);
  }

  @SuppressWarnings("mutable") @NotNull abstract byte[] bytes();

  static class Adapter extends JSONAdapterFactory<ClarifaiFileVideo> {
    @Nullable @Override protected Serializer<ClarifaiFileVideo> serializer() {
      return new Serializer<ClarifaiFileVideo>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiFileVideo value, @NotNull Gson gson) {
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

    @NotNull @Override protected TypeToken<ClarifaiFileVideo> typeToken() {
      return new TypeToken<ClarifaiFileVideo>() {};
    }
  }
}

