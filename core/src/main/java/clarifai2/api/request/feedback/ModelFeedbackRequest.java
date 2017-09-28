package clarifai2.api.request.feedback;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.feedback.ConceptFeedback;
import clarifai2.dto.feedback.RegionFeedback;
import clarifai2.dto.model.ModelVersion;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class ModelFeedbackRequest extends ClarifaiRequest.Builder<JsonNull> {
  @NotNull private String modelID;
  @Nullable private String inputID;
  @Nullable private String imageUrl;
  @NotNull private final List<ConceptFeedback> concepts = new ArrayList<>();
  @NotNull private final List<RegionFeedback> regions = new ArrayList<>();
  @Nullable private ModelVersion version;

  @Nullable private String endUserId;
  @Nullable private String sessionId;
  @Nullable private String eventType;
  @Nullable private String outputId;

  public ModelFeedbackRequest(@NotNull final BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  @NotNull public ModelFeedbackRequest withInputId(@NotNull String inputID) {
    this.inputID = inputID;
    return this;
  }

  @NotNull public ModelFeedbackRequest withImageUrl(@NotNull String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  @NotNull public ModelFeedbackRequest withConcepts(@NotNull ConceptFeedback... concepts) {
    return withConcepts(Arrays.asList(concepts));
  }

  @NotNull public ModelFeedbackRequest withConcepts(@NotNull Collection<ConceptFeedback> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull public ModelFeedbackRequest withRegions(@NotNull RegionFeedback... regions) {
    return withRegions(Arrays.asList(regions));
  }

  @NotNull public ModelFeedbackRequest withRegions(@NotNull Collection<RegionFeedback> regions) {
    this.regions.addAll(regions);
    return this;
  }

  @NotNull public ModelFeedbackRequest withVersion(@NotNull ModelVersion version) {
    this.version = version;
    return this;
  }

  @NotNull public ModelFeedbackRequest withEndUserId(@NotNull String endUserId) {
    this.endUserId = endUserId;
    return this;
  }

  @NotNull public ModelFeedbackRequest withSessionId(@NotNull String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  @NotNull public ModelFeedbackRequest withEventType(@NotNull String eventType) {
    this.eventType = eventType;
    return this;
  }

  @NotNull public ModelFeedbackRequest withOutputId(@NotNull String outputId) {
    this.outputId = outputId;
    return this;
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public Request httpRequest() {
        JSONObjectBuilder dataJsonObject = new JSONObjectBuilder()
            .add("image", new JSONObjectBuilder()
                .add("url", imageUrl));
        if (concepts.size() > 0) {
          dataJsonObject.add("concepts", makeConceptsJsonArray());
        }
        if (regions.size() > 0) {
          dataJsonObject.add("regions", makeRegionsJsonArray());
        }
        final JsonObject body = new JSONObjectBuilder()
            .add("input", new JSONObjectBuilder()
                .add("id", inputID)
                .add("data", dataJsonObject.build())
                .add("feedback_info", makeFeedbackInfoJsonObject()
                )
            )
            .build();
        if (version == null) {
          return postRequest("/v2/models/" + modelID + "/feedback", body);
        }
        return postRequest("/v2/models/" + modelID + "/versions/" + version.id() + "/feedback", body);
      }

      @NotNull private JSONArrayBuilder makeConceptsJsonArray() {
        return new JSONArrayBuilder()
            .addAll(concepts, new Func1<ConceptFeedback, JsonElement>() {
              @NotNull @Override public JsonElement call(@NotNull ConceptFeedback concept) {
                return new JSONObjectBuilder()
                    .add("id", concept.id())
                    .add("value", concept.value())
                    .build();
              }
            });
      }

      @NotNull private JSONArrayBuilder makeRegionsJsonArray() {
        return new JSONArrayBuilder()
            .addAll(regions, new Func1<RegionFeedback, JsonElement>() {
              @NotNull @Override public JsonElement call(@NotNull RegionFeedback concept) {
                return client.gson.toJsonTree(concept);
              }
            });
      }

      @NotNull private JSONObjectBuilder makeFeedbackInfoJsonObject() {
        return new JSONObjectBuilder()
            .add("end_user_id", endUserId)
            .add("session_id", sessionId)
            .add("event_type", eventType)
            .add("output_id", outputId);
      }

      @NotNull @Override public JSONUnmarshaler<JsonNull> unmarshaler() {
        return new JSONUnmarshaler<JsonNull>() {
          @NotNull @Override public JsonNull fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return JsonNull.INSTANCE;
          }
        };
      }
    };
  }
}
