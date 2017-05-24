package clarifai2.dto.input.image;

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

import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiVideo.Adapter.class)
public abstract class ClarifaiVideo extends ClarifaiImage {

  @NotNull public abstract URL url();

  static class Adapter extends JSONAdapterFactory<ClarifaiVideo> {

    @Nullable @Override protected Serializer<ClarifaiVideo> serializer() {
      return new Serializer<ClarifaiVideo>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiVideo value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("url", value.url().toString())
              .build();
        }
      };
    }

    @Nullable @Override protected Deserializer<ClarifaiVideo> deserializer() {
      return new Deserializer<ClarifaiVideo>() {
        @Nullable @Override
        public ClarifaiVideo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiVideo> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          return ClarifaiImage.ofVideo(root.get("url").getAsString());
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiVideo> typeToken() {
      return new TypeToken<ClarifaiVideo>() {};
    }
  }
}
