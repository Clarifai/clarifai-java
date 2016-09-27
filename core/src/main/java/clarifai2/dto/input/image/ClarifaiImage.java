package clarifai2.dto.input.image;

import clarifai2.internal.ClarifaiUtil;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;

@SuppressWarnings("NullableProblems")
@JsonAdapter(ClarifaiImage.Adapter.class)
public abstract class ClarifaiImage {

  @NotNull public static ClarifaiFileImage of(@NotNull byte[] imageBytes) {
    return new AutoValue_ClarifaiFileImage(Crop.create(), imageBytes);
  }

  @NotNull public static ClarifaiFileImage of(@NotNull File imageFile) {
    return of(ClarifaiUtil.read(imageFile));
  }

  @NotNull public static ClarifaiURLImage of(@NotNull String imageURL) {
    return of(ClarifaiUtil.parseURL(imageURL));
  }

  @NotNull public static ClarifaiURLImage of(@NotNull URL imageURL) {
    return new AutoValue_ClarifaiURLImage(Crop.create(), imageURL);
  }

  @Nullable public abstract Crop crop();

  @NotNull public abstract ClarifaiImage withCrop(@NotNull Crop crop);

  static class Adapter implements JsonDeserializer<ClarifaiImage> {
    @Override
    public ClarifaiImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      return context.deserialize(
          json,
          json.getAsJsonObject().has("url") ? ClarifaiURLImage.class : ClarifaiFileImage.class
      );
    }
  }
}
