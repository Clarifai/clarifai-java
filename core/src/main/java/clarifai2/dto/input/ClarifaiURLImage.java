package clarifai2.dto.input;

import clarifai2.internal.grpc.api.ImageOuterClass;
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
  @Nullable private Boolean allowDuplicateUrl = null;

  ClarifaiURLImage() {} // AutoValue instances only

  @NotNull public abstract URL url();

  /**
   * @param allowDuplicateUrl should allow duplicate url
   * @return a {@link ClarifaiInput}
   */
  @NotNull public final ClarifaiURLImage withAllowDuplicateUrl(@NotNull boolean allowDuplicateUrl) {
    this.allowDuplicateUrl = allowDuplicateUrl;
    return this;
  }

  /**
   * @return should allow duplicate url
   */
  @Nullable public Boolean allowDuplicateUrl() {
    return allowDuplicateUrl;
  }

  @NotNull @Override public ImageOuterClass.Image serialize(boolean allowDuplicateUrl) {
    ImageOuterClass.Image image = ImageOuterClass.Image.newBuilder()
        .setUrl(url().toString())
        .setAllowDuplicateUrl(allowDuplicateUrl)
        .build();
    return image;
  }


  @NotNull public static ClarifaiURLImage deserializeInner(ImageOuterClass.Image imageResponse) {
    ClarifaiURLImage image = ClarifaiURLImage.of(imageResponse.getUrl());
    return image;
  }

  static class Adapter extends JSONAdapterFactory<ClarifaiURLImage> {

    @Nullable @Override protected Serializer<ClarifaiURLImage> serializer() {
      return new Serializer<ClarifaiURLImage>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiURLImage value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("url", value.url().toString())
              .addIfNotNull("allow_duplicate_url", value.allowDuplicateUrl()).build();
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
          return ClarifaiImage.of(root.get("url").getAsString());
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiURLImage> typeToken() {
      return new TypeToken<ClarifaiURLImage>() {};
    }
  }
}
