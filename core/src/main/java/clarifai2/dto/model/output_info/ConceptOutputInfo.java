package clarifai2.dto.model.output_info;

import clarifai2.Func1;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.isJsonNull;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ConceptOutputInfo.Adapter.class)
public abstract class ConceptOutputInfo extends OutputInfo {

  @NotNull public static ConceptOutputInfo forConcepts(@NotNull Concept... concepts) {
    return forConcepts(Arrays.asList(concepts));
  }

  @NotNull public static ConceptOutputInfo forConcepts(@NotNull List<Concept> concepts) {
    return new AutoValue_ConceptOutputInfo(concepts, false, false);
  }

  @NotNull public abstract List<Concept> concepts();
  @NotNull public abstract boolean areConceptsMutuallyExclusive();
  @NotNull public abstract boolean isEnvironmentClosed();

  @NotNull public final ConceptOutputInfo areConceptsMutuallyExclusive(boolean areConceptsMutuallyExclusive) {
    return withAreConceptsMutuallyExclusive(areConceptsMutuallyExclusive);
  }

  @NotNull public final ConceptOutputInfo isEnvironmentClosed(boolean isEnvironmentClosed) {
    return withIsEnvironmentClosed(isEnvironmentClosed);
  }

  // These are awful method names, but auto-value-with needs these particular names, so we'll expose better ones publicly
  @NotNull abstract ConceptOutputInfo withAreConceptsMutuallyExclusive(boolean areConceptsMutuallyExclusive);
  @NotNull abstract ConceptOutputInfo withIsEnvironmentClosed(boolean isEnvironmentClosed);

  ConceptOutputInfo() {} // AutoValue instances only

  static class Adapter implements JsonSerializer<ConceptOutputInfo>, JsonDeserializer<ConceptOutputInfo> {
    @Override
    public JsonElement serialize(ConceptOutputInfo src, Type typeOfSrc, final JsonSerializationContext context) {
      return new JSONObjectBuilder()
          .add("data", new JSONObjectBuilder()
              .add("concepts", new JSONArrayBuilder()
                  .addAll(src.concepts(), new Func1<Concept, JsonElement>() {
                    @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                      return context.serialize(concept);
                    }
                  })
              )
          )
          .add("output_config", new JSONObjectBuilder()
              .add("concepts_mutually_exclusive", src.areConceptsMutuallyExclusive())
              .add("closed_environment", src.isEnvironmentClosed())
              .build()
          )
          .build();
    }

    @Override
    public ConceptOutputInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();

      final List<Concept> concepts;
      if (isJsonNull(root.getAsJsonObject("data"))) {
        concepts = Collections.emptyList();
      } else {
        concepts = fromJson(
            context,
            root.getAsJsonObject("data").getAsJsonArray("concepts"),
            new TypeToken<List<Concept>>() {}
        );
      }
      boolean areConceptsMutuallyExclusive = false;
      boolean isEnvironmentClosed = false;
      {
        final JsonObject outputConfig = root.getAsJsonObject("output_config");
        if (outputConfig != null) {
          areConceptsMutuallyExclusive = outputConfig.get("concepts_mutually_exclusive").getAsBoolean();
          isEnvironmentClosed = outputConfig.get("closed_environment").getAsBoolean();
        }
      }
      return new AutoValue_ConceptOutputInfo(concepts, areConceptsMutuallyExclusive, isEnvironmentClosed);
    }
  }
}
