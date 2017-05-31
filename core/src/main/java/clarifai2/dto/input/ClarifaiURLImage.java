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

import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiURLImage.Adapter.class)
public abstract class ClarifaiURLImage extends ClarifaiImage {

  ClarifaiURLImage() {} // AutoValue instances only

  @NotNull public abstract URL url();

  @NotNull @Override public abstract ClarifaiURLImage withCrop(@NotNull Crop crop);


  static class Adapter extends JSONAdapterFactory<ClarifaiURLImage> {

    @Nullable @Override protected Serializer<ClarifaiURLImage> serializer() {
      return new Serializer<ClarifaiURLImage>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiURLImage value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("url", value.url().toString())
              .add("crop", toJson(gson, value.crop(), Crop.class))
              .build();
        }
      };
    }

    @Nullable @Override protected Deserializer<ClarifaiURLImage> deserializer() {
      return new Deserializer<ClarifaiURLImage>() {
        @Nullable @Override
        public ClarifaiURLImage deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiURLImage> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          return ClarifaiImage.of(root.get("url").getAsString())
              .withCrop(gson.fromJson(root.get("crop"), Crop.class));
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiURLImage> typeToken() {
      return new TypeToken<ClarifaiURLImage>() {};
    }
  }
}