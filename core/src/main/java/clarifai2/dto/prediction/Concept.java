package clarifai2.dto.prediction;

import clarifai2.dto.HasClarifaiID;
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

import java.util.Date;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.isJsonNull;
import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Concept.Adapter.class)
public abstract class
Concept extends Prediction implements HasClarifaiID {

  @NotNull public static Concept forID(@NotNull String id) {
    return new AutoValue_Concept(id, null, null, null, 1.0F, null);
  }

  @NotNull public static Concept forName(@NotNull String name) {
    return new AutoValue_Concept(null, name, null, null, 1.0F, null);
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

  /**
   * @param name the name to compose the new Concept with
   * @return a Concept object identical to this one, but with the given name
   */
  @NotNull public abstract Concept withName(@NotNull String name);

  /**
   * @param value the value to compose the new Concept with
   * @return a Concept object identical to this one, but with the given value
   */
  @NotNull public abstract Concept withValue(@Nullable float value);

  /**
   * @param value the value to compose the new Concept with
   * @return a Concept object identical to this one, but with the given value
   */
  @NotNull public final Concept withValue(@NotNull boolean value) {
    return withValue(value ? 1.0F : 0.0F);
  }


  Concept() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<Concept> {
    @Nullable @Override protected Serializer<Concept> serializer() {
      return new Serializer<Concept>() {
        @NotNull @Override public JsonElement serialize(@Nullable Concept value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          final JSONObjectBuilder builder = new JSONObjectBuilder();
          return builder
              .add("id", value.id())
              .add("name", value.name())
              .add("created_at", toJson(gson, value.createdAt(), Date.class))
              .add("app_id", value.appID())
              .add("value", value.value())
              .build();
        }
      };
    }

    @Nullable @Override protected Deserializer<Concept> deserializer() {
      return new Deserializer<Concept>() {
        @Nullable @Override
        public Concept deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Concept> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          return new AutoValue_Concept(
              root.get("id").getAsString(),
              root.get("name").getAsString(),
              fromJson(gson, root.get("created_at"), Date.class),
              InternalUtil.<String>nullSafeTraverse(root, "app_id"),
              isJsonNull(root.get("value")) ? 1.0F : root.get("value").getAsFloat()
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<Concept> typeToken() {
      return new TypeToken<Concept>() {};
    }
  }
}
