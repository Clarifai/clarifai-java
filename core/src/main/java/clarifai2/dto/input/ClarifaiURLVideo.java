package clarifai2.dto.input;

import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiURLVideo.Adapter.class)
public abstract class ClarifaiURLVideo extends ClarifaiVideo {

  @NotNull public abstract URL url();

  @NotNull @Override public abstract ClarifaiURLVideo withCrop(@NotNull Crop crop);

  static class Adapter extends JSONAdapterFactory<ClarifaiURLVideo> {

    @Nullable @Override protected Serializer<ClarifaiURLVideo> serializer() {
      return new Serializer<ClarifaiURLVideo>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiURLVideo value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("url", value.url().toString())
              .build();
        }
      };
    }

    @Nullable @Override protected Deserializer<ClarifaiURLVideo> deserializer() {
      return new Deserializer<ClarifaiURLVideo>() {
        @Nullable @Override
        public ClarifaiURLVideo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiURLVideo> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          return ClarifaiVideo.of(root.get("url").getAsString());
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiURLVideo> typeToken() {
      return new TypeToken<ClarifaiURLVideo>() {};
    }
  }
}
