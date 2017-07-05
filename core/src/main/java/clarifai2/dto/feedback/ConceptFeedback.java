package clarifai2.dto.feedback;

import clarifai2.dto.HasClarifaiID;
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

import static clarifai2.internal.InternalUtil.assertJsonIs;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ConceptFeedback.Adapter.class)
public abstract class ConceptFeedback implements HasClarifaiID {
  ConceptFeedback() {} // AutoValue instances only

  @NotNull public static ConceptFeedback forIdAndValue(@NotNull String id, boolean value) {
    return new AutoValue_ConceptFeedback(id, value);
  }

  /**
   * @return whether this concept is present
   */
  @NotNull public abstract boolean value();

  static class Adapter extends JSONAdapterFactory<ConceptFeedback> {
    @Nullable @Override protected Serializer<ConceptFeedback> serializer() {
      return new Serializer<ConceptFeedback>() {
        @NotNull @Override public JsonElement serialize(@Nullable ConceptFeedback value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          final JSONObjectBuilder builder = new JSONObjectBuilder();
          return builder
              .add("id", value.id())
              .add("value", value.value())
              .build();
        }
      };
    }

    @Nullable @Override protected Deserializer<ConceptFeedback> deserializer() {
      return new Deserializer<ConceptFeedback>() {
        @Nullable @Override
        public ConceptFeedback deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ConceptFeedback> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          return new AutoValue_ConceptFeedback(
              root.get("id").getAsString(),
              root.get("value").getAsBoolean()
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ConceptFeedback> typeToken() {
      return new TypeToken<ConceptFeedback>() {};
    }
  }
}
