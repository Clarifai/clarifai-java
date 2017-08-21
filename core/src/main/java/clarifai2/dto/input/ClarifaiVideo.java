package clarifai2.dto.input;

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

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@JsonAdapter(ClarifaiVideo.Adapter.class)
public abstract class ClarifaiVideo implements ClarifaiInputValue {

  @Nullable public abstract Crop crop();

  @NotNull public abstract ClarifaiVideo withCrop(@NotNull Crop crop);

  @NotNull public static ClarifaiURLVideo of(@NotNull URL videoURL) {
    return new AutoValue_ClarifaiURLVideo(Crop.create(), videoURL);
  }

  @NotNull public static ClarifaiURLVideo of(@NotNull String videoURL) {
    final URL result;
    try {
      result = new URL(videoURL);
    } catch (MalformedURLException e) {
      throw new ClarifaiException("Could not parse URL " + videoURL, e);
    }
    return of(result);
  }

  @NotNull public static ClarifaiFileVideo of(@NotNull byte[] videoBytes) {
    return new AutoValue_ClarifaiFileVideo(Crop.create(), videoBytes);
  }

  @NotNull public static ClarifaiFileVideo of(@NotNull File videoFile) {
    return of(InternalUtil.read(videoFile));
  }

  static class Adapter extends JSONAdapterFactory<ClarifaiVideo> {

    @Nullable @Override protected Serializer<ClarifaiVideo> serializer() {
      return new Serializer<ClarifaiVideo>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiVideo value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return gson.toJsonTree(value, value.getClass());
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
          return fromJson(
              gson,
              json,
              assertJsonIs(json, JsonObject.class).has("url") ? ClarifaiURLVideo.class : ClarifaiFileVideo.class
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiVideo> typeToken() {
      return new TypeToken<ClarifaiVideo>() {};
    }
  }
}
