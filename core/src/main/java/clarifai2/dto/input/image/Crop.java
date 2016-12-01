package clarifai2.dto.input.image;

import clarifai2.exception.ClarifaiException;
import clarifai2.internal.JSONArrayBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.isJsonNull;

import static clarifai2.internal.InternalUtil.isJsonNull;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Crop.Adapter.class)
public abstract class Crop {

  @NotNull public static Crop create() {
    return new AutoValue_Crop(0.0F, 0.0F, 1.0F, 1.0F);
  }

  @NotNull public abstract float top();
  @NotNull public abstract float left();
  @NotNull public abstract float bottom();
  @NotNull public abstract float right();

  @NotNull public final Crop top(@NotNull float top) {
    return withTop(top);
  }

  @NotNull public final Crop left(@NotNull float left) {
    return withLeft(left);
  }

  @NotNull public final Crop bottom(@NotNull float bottom) {
    return withBottom(bottom);
  }

  @NotNull public final Crop right(@NotNull float right) {
    return withRight(right);
  }

  // These are not great method names, so we'll alias them above
  @NotNull abstract Crop withTop(@NotNull float top);
  @NotNull abstract Crop withLeft(@NotNull float left);
  @NotNull abstract Crop withBottom(@NotNull float bottom);
  @NotNull abstract Crop withRight(@NotNull float right);

  Crop() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<Crop> {
    @Nullable @Override protected Serializer<Crop> serializer() {
      return new Serializer<Crop>() {
        @NotNull @Override public JsonElement serialize(@Nullable Crop value, @NotNull Gson gson) {
          // Keep the bloat in our outgoing request size down by skipping all default crops
          if (value == null || value.equals(Crop.create())) {
            return JsonNull.INSTANCE;
          }
          return new JSONArrayBuilder()
              .add(value.top())
              .add(value.left())
              .add(value.bottom())
              .add(value.right())
              .build();
        }
      };
    }

    @Override public Crop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      if (isJsonNull(json)) {
        return Crop.create();
      }
      if (json.isJsonArray()) {
        final JsonArray array = json.getAsJsonArray();
        return new AutoValue_Crop(
            array.get(0).getAsFloat(),
            array.get(1).getAsFloat(),
            array.get(2).getAsFloat(),
            array.get(3).getAsFloat()
        );
      }
      if (json.isJsonObject()) {
        final JsonObject root = json.getAsJsonObject();
        if (root.has("top_row")) {
          return create()
              .top(root.get("top_row").getAsFloat())
              .left(root.get("left_col").getAsFloat())
              .bottom(root.get("bottom_row").getAsFloat())
              .right(root.get("right_col").getAsFloat());
        }
      }
      throw new ClarifaiException(String.format("Can't parse JSON %s as a Crop object", json));
    }
  }
}