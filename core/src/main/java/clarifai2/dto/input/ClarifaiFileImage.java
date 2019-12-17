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
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiFileImage.Adapter.class)
public abstract class ClarifaiFileImage extends ClarifaiImage {

  ClarifaiFileImage() {} // AutoValue instances only

  @NotNull public final byte[] imageBytes() {
    // Return a defensive copy so that the underlying data can't be modified
    final byte[] bytes = bytes();
    return Arrays.copyOf(bytes, bytes.length);
  }

  @SuppressWarnings("mutable") @NotNull abstract byte[] bytes();

  public static ClarifaiFileImage deserializeInner(ImageOuterClass.Image imageResponse) {
    ClarifaiFileImage image = ClarifaiImage.of(imageResponse.getBase64().toByteArray());
    return image;
  }

  @NotNull @Override public ImageOuterClass.Image serialize(boolean allowDuplicateUrl) {
    ImageOuterClass.Image image = ImageOuterClass.Image.newBuilder()
        .setBase64(com.google.protobuf.ByteString.copyFrom(bytes()))
        .setAllowDuplicateUrl(allowDuplicateUrl)
        .build();
    return image;
  }

  static class Adapter extends JSONAdapterFactory<ClarifaiFileImage> {
    @Nullable @Override protected Serializer<ClarifaiFileImage> serializer() {
      return new Serializer<ClarifaiFileImage>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiFileImage value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("base64", ByteString.of(value.bytes()).base64())
              .build();
        }
      };
    }

    @Nullable @Override protected Deserializer<ClarifaiFileImage> deserializer() {
      return new Deserializer<ClarifaiFileImage>() {
        @Nullable @Override
        public ClarifaiFileImage deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiFileImage> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          String base64 = root.get("base64").getAsString();
          byte[] bytes = ByteString.decodeBase64(base64).toByteArray();
          return ClarifaiImage.of(bytes);
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiFileImage> typeToken() {
      return new TypeToken<ClarifaiFileImage>() {};
    }
  }
}
