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

import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.isJsonNull;
import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(FaceConceptsOutputInfo.Adapter.class)
public abstract class FaceConceptsOutputInfo extends OutputInfo {

  FaceConceptsOutputInfo() {} // AutoValue instances only

  @NotNull public static FaceConceptsOutputInfo forConcepts(@NotNull Concept... concepts) {
    return forConcepts(Arrays.asList(concepts));
  }

  @NotNull public static FaceConceptsOutputInfo forConcepts(@NotNull List<Concept> concepts) {
    return new AutoValue_FaceConceptsOutputInfo(concepts, false, false, null);
  }

  @Nullable public abstract List<Concept> concepts();

  @NotNull public abstract boolean areConceptsMutuallyExclusive();

  @NotNull public abstract boolean isEnvironmentClosed();

  @Nullable public abstract String language();

  @NotNull public final FaceConceptsOutputInfo withLanguage(@NotNull String language) {
    return new AutoValue_FaceConceptsOutputInfo(concepts(), areConceptsMutuallyExclusive(), isEnvironmentClosed(), language);
  }

  @NotNull public final FaceConceptsOutputInfo areConceptsMutuallyExclusive(boolean areConceptsMutuallyExclusive) {
    return withAreConceptsMutuallyExclusive(areConceptsMutuallyExclusive);
  }

  @NotNull public final FaceConceptsOutputInfo isEnvironmentClosed(boolean isEnvironmentClosed) {
    return withIsEnvironmentClosed(isEnvironmentClosed);
  }

  // These are awful method names, but auto-value-with needs these particular names, so we'll expose better ones publicly
  @NotNull abstract FaceConceptsOutputInfo withAreConceptsMutuallyExclusive(boolean areConceptsMutuallyExclusive);

  @NotNull abstract FaceConceptsOutputInfo withIsEnvironmentClosed(boolean isEnvironmentClosed);


  static class Adapter extends JSONAdapterFactory<FaceConceptsOutputInfo> {
    @Nullable @Override protected Serializer<FaceConceptsOutputInfo> serializer() {
      return new Serializer<FaceConceptsOutputInfo>() {
        @NotNull @Override public JsonElement serialize(@Nullable FaceConceptsOutputInfo value, @NotNull final Gson gson) {
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

    @Nullable @Override protected Deserializer<FaceConceptsOutputInfo> deserializer() {
      return new Deserializer<FaceConceptsOutputInfo>() {
        @Nullable @Override
        public FaceConceptsOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<FaceConceptsOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = json.getAsJsonObject();

          JsonObject data = root.getAsJsonObject("data");

          List<Concept> concepts = isJsonNull(data)
              ? Collections.<Concept>emptyList()
              : fromJson(
                  gson,
                  data.getAsJsonArray("concepts"),
                  new TypeToken<List<Concept>>() {}
              );
          if (concepts == null) {
            concepts = Collections.<Concept>emptyList();
          }

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
          return new AutoValue_FaceConceptsOutputInfo(concepts, areConceptsMutuallyExclusive, isEnvironmentClosed, language);
        }
      };
    }

    @NotNull @Override protected TypeToken<FaceConceptsOutputInfo> typeToken() {
      return new TypeToken<FaceConceptsOutputInfo>() {};
    }
  }
}
