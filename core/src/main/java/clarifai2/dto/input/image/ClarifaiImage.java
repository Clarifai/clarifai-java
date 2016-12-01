package clarifai2.dto.input.image;

import clarifai2.exception.ClarifaiException;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@JsonAdapter(ClarifaiImage.Adapter.class)
public abstract class ClarifaiImage {

  @NotNull public static ClarifaiFileImage of(@NotNull byte[] imageBytes) {
    return new AutoValue_ClarifaiFileImage(Crop.create(), imageBytes);
  }

  @NotNull public static ClarifaiFileImage of(@NotNull File imageFile) {
    return of(InternalUtil.read(imageFile));
  }

  @NotNull public static ClarifaiURLImage of(@NotNull String imageURL) {
    final URL result;
    try {
      result = new URL(imageURL);
    } catch (MalformedURLException e) {
      throw new ClarifaiException("Could not parse URL " + imageURL, e);
    }
    return of(result);
  }

  @NotNull public static ClarifaiURLImage of(@NotNull URL imageURL) {
    return new AutoValue_ClarifaiURLImage(Crop.create(), imageURL);
  }

  @Nullable public abstract Crop crop();

  @NotNull public abstract ClarifaiImage withCrop(@NotNull Crop crop);

  static class Adapter implements JsonDeserializer<ClarifaiImage> {
    @Override
    public ClarifaiImage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      return fromJson(
          context,
          json,
          json.getAsJsonObject().has("url") ? ClarifaiURLImage.class : ClarifaiFileImage.class
      );
    }
  }
}
