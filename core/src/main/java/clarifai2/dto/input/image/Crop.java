package clarifai2.dto.input.image;

import clarifai2.internal.JSONArrayBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

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

  static class Adapter implements JsonSerializer<Crop>, JsonDeserializer<Crop> {
    @Override public JsonElement serialize(Crop src, Type typeOfSrc, JsonSerializationContext context) {
      return new JSONArrayBuilder()
          .add(src.top())
          .add(src.left())
          .add(src.bottom())
          .add(src.right())
          .build();
    }

    @Override public Crop deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonArray array = json.getAsJsonArray();
      return new AutoValue_Crop(
          array.get(0).getAsFloat(),
          array.get(1).getAsFloat(),
          array.get(2).getAsFloat(),
          array.get(3).getAsFloat()
      );
    }
  }
}