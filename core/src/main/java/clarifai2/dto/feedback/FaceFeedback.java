package clarifai2.dto.feedback;

import clarifai2.Func1;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(FaceFeedback.Adapter.class)
public abstract class FaceFeedback {
  FaceFeedback() {} // AutoValue instances only

  @NotNull public static FaceFeedback make(@NotNull Collection<ConceptFeedback> identityConceptFeedbacks,
      @NotNull Collection<ConceptFeedback> ageConceptFeedbacks) {
    return new AutoValue_FaceFeedback(identityConceptFeedbacks, ageConceptFeedbacks);
  }

  @NotNull public abstract Collection<ConceptFeedback> identityConceptFeedback();
  @NotNull public abstract Collection<ConceptFeedback> ageConceptFeedback();

  static class Adapter extends JSONAdapterFactory<FaceFeedback> {
    @Nullable @Override protected Serializer<FaceFeedback> serializer() {
      return new Serializer<FaceFeedback>() {
        @NotNull @Override public JsonElement serialize(@Nullable FaceFeedback value, @NotNull final Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          final JSONObjectBuilder builder = new JSONObjectBuilder();
          return builder
              .add("identity", new JSONObjectBuilder()
                  .add("concepts", new JSONArrayBuilder()
                      .addAll(value.identityConceptFeedback(), new Func1<ConceptFeedback, JsonElement>() {
                        @NotNull @Override public JsonElement call(@NotNull ConceptFeedback concept) {
                          return toJson(gson, concept, ConceptFeedback.class);
                        }
                      })
                  ))
              .add("age_appearance", new JSONObjectBuilder()
                  .add("concepts", new JSONArrayBuilder()
                      .addAll(value.ageConceptFeedback(), new Func1<ConceptFeedback, JsonElement>() {
                        @NotNull @Override public JsonElement call(@NotNull ConceptFeedback concept) {
                          return toJson(gson, concept, ConceptFeedback.class);
                        }
                      })
                  ))
              .build();
        }
      };
    }

    @NotNull @Override protected TypeToken<FaceFeedback> typeToken() {
      return new TypeToken<FaceFeedback>() {};
    }
  }
}
