package clarifai2.dto.model.output_info;

import clarifai2.Func1;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONArrayBuilder;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static clarifai2.internal.InternalUtil.*;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ConceptOutputInfo.Adapter.class)
public abstract class ConceptOutputInfo extends OutputInfo {

  @NotNull public static ConceptOutputInfo forConcepts(@NotNull Concept... concepts) {
    return forConcepts(Arrays.asList(concepts));
  }

  @NotNull public static ConceptOutputInfo forConcepts(@NotNull List<Concept> concepts) {
    return new AutoValue_ConceptOutputInfo(concepts, false, false, null);
  }

  @Nullable public abstract List<Concept> concepts();
  @NotNull public abstract boolean areConceptsMutuallyExclusive();
  @NotNull public abstract boolean isEnvironmentClosed();

  @Nullable public abstract String language();

  @NotNull public final ConceptOutputInfo withLanguage(@NotNull String language) {
    return new AutoValue_ConceptOutputInfo(concepts(), areConceptsMutuallyExclusive(), isEnvironmentClosed(), language);
  }

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

  static class Adapter extends JSONAdapterFactory<ConceptOutputInfo> {
    @Nullable @Override protected Serializer<ConceptOutputInfo> serializer() {
      return new Serializer<ConceptOutputInfo>() {
        @NotNull @Override public JsonElement serialize(@Nullable ConceptOutputInfo value, @NotNull final Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          JSONObjectBuilder body = new JSONObjectBuilder();
          body.add("data", new JSONObjectBuilder()
              .add("concepts", new JSONArrayBuilder()
                  .addAll(value.concepts(), new Func1<Concept, JsonElement>() {
                    @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                      return toJson(gson, concept, Concept.class);
                    }
                  })
              )
          );
          JSONObjectBuilder outputConfig = new JSONObjectBuilder();
          outputConfig.add("concepts_mutually_exclusive", value.areConceptsMutuallyExclusive());
          outputConfig.add("closed_environment", value.isEnvironmentClosed());
          if (value.language() != null) {
            outputConfig.add("language", value.language());
          }
          body.add("output_config", outputConfig.build());
          return body.build();
        }
      };
    }

    @Nullable @Override protected Deserializer<ConceptOutputInfo> deserializer() {
      return new Deserializer<ConceptOutputInfo>() {
        @Nullable @Override
        public ConceptOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ConceptOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = json.getAsJsonObject();

          final List<Concept> concepts = isJsonNull(root.getAsJsonObject("data"))
              ? Collections.<Concept>emptyList()
              : fromJson(
                  gson,
                  root.getAsJsonObject("data").getAsJsonArray("concepts"),
                  new TypeToken<List<Concept>>() {}
              );
          boolean areConceptsMutuallyExclusive = false;
          boolean isEnvironmentClosed = false;
          String language = null;
          {
            final JsonObject outputConfig = root.getAsJsonObject("output_config");
            if (outputConfig != null) {
              areConceptsMutuallyExclusive = outputConfig.get("concepts_mutually_exclusive").getAsBoolean();
              isEnvironmentClosed = outputConfig.get("closed_environment").getAsBoolean();
              if (outputConfig.get("language") != null) {
                language = outputConfig.get("language").getAsString();
              }
            }
          }
          return new AutoValue_ConceptOutputInfo(concepts, areConceptsMutuallyExclusive, isEnvironmentClosed, language);
        }
      };
    }

    @NotNull @Override protected TypeToken<ConceptOutputInfo> typeToken() {
      return new TypeToken<ConceptOutputInfo>() {};
    }
  }
}
