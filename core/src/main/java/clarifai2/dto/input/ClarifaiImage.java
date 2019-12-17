package clarifai2.dto.input;

import clarifai2.internal.grpc.api.ImageOuterClass;
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
import java.util.Collections;
import java.util.List;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@JsonAdapter(ClarifaiImage.Adapter.class)
public abstract class ClarifaiImage implements ClarifaiInputValue {

  @NotNull public static ClarifaiFileImage of(@NotNull byte[] imageBytes) {
    return new AutoValue_ClarifaiFileImage(imageBytes);
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
    return new AutoValue_ClarifaiURLImage(imageURL);
  }

  @NotNull public ImageOuterClass.Image serialize() {
    return serialize(false);
  }

  @NotNull abstract public ImageOuterClass.Image serialize(boolean allowDuplicateUrl);

  @NotNull public static ClarifaiImage deserialize(ImageOuterClass.Image image) {
    return !image.getUrl().equals("") ?
        ClarifaiURLImage.deserializeInner(image) :
        ClarifaiFileImage.deserializeInner(image);
  }

  static class Adapter extends JSONAdapterFactory<ClarifaiImage> {

    @Nullable @Override protected Serializer<ClarifaiImage> serializer() {
      return new Serializer<ClarifaiImage>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiImage value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return gson.toJsonTree(value, value.getClass());
        }
      };
    }

    @Nullable @Override protected Deserializer<ClarifaiImage> deserializer() {
      return new Deserializer<ClarifaiImage>() {
        @Nullable @Override
        public ClarifaiImage deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiImage> type,
            @NotNull Gson gson) {
          return fromJson(
              gson,
              json,
              assertJsonIs(json, JsonObject.class).has("url") ? ClarifaiURLImage.class : ClarifaiFileImage.class
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiImage> typeToken() {
      return new TypeToken<ClarifaiImage>() {};
    }
  }
}
