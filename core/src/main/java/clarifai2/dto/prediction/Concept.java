package clarifai2.dto.prediction;

import clarifai2.dto.HasClarifaiID;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Date;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Concept.Adapter.class)
public abstract class Concept extends Prediction implements HasClarifaiID {

  @NotNull public static Concept forID(@NotNull String id) {
    return new AutoValue_Concept(id, null, null, null, 1.0F);
  }

  @NotNull public static Concept forName(@NotNull String name) {
    return new AutoValue_Concept(null, name, null, null, 1.0F);
  }

  /**
   * @return this concept's name (meant to be human-readable). This can be the same as the ID in some situations.
   */
  @Nullable public abstract String name();

  @Nullable public abstract Date createdAt();

  /**
   * @return the ID of the app to which this concept belongs
   */
  @Nullable public abstract String appID();

  /**
   * @return the probability of this concept being present
   */
  @NotNull public abstract float value();

  @NotNull public abstract Concept withName(@NotNull String name);

  @NotNull public abstract Concept withValue(@Nullable float value);

  @NotNull public final Concept withValue(@NotNull boolean value) {
    return withValue(value ? 1.0F : 0.0F);
  }


  Concept() {} // AutoValue instances only

  static class Adapter implements JsonSerializer<Concept>, JsonDeserializer<Concept> {
    @Override public JsonElement serialize(Concept src, Type typeOfSrc, JsonSerializationContext context) {
      final JSONObjectBuilder builder = new JSONObjectBuilder();
      return builder
          .add("id", src.id())
          .add("name", src.name())
          .add("created_at", context.serialize(src.createdAt()))
          .add("app_id", src.appID())
          .add("value", src.value())
          .build();
    }

    @Override public Concept deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      return new AutoValue_Concept(
          root.get("id").getAsString(),
          root.get("name").getAsString(),
          context.<Date>deserialize(root.get("created_at"), Date.class),
          InternalUtil.<String>nullSafeTraverse(root, "app_id"),
          root.has("value") && !root.get("value").isJsonNull() ? root.get("value").getAsFloat() : 1.0F
      );
    }
  }
}
