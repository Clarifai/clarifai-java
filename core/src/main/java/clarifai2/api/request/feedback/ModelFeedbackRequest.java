package clarifai2.api.request.feedback;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.internal.grpc.api.Feedback;
import clarifai2.internal.grpc.api.ImageOuterClass;
import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.feedback.ConceptFeedback;
import clarifai2.dto.feedback.RegionFeedback;
import clarifai2.dto.model.ModelVersion;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonNull;
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

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl() {
    if (version == null) {
      return "/v2/models/" + modelID + "/feedback";
    }
    return "/v2/models/" + modelID + "/versions/" + version.id() + "/feedback";
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        DataOuterClass.Data.Builder dataBuilder = DataOuterClass.Data.newBuilder();
        if (imageUrl != null) {
          dataBuilder.setImage(ImageOuterClass.Image.newBuilder().setUrl(imageUrl));
        }
        if (!concepts.isEmpty()) {
          List<ConceptOuterClass.Concept> conceptsGrpc = new ArrayList<>();
          for (ConceptFeedback concept : concepts) {
            conceptsGrpc.add(concept.serialize());
          }
          dataBuilder.addAllConcepts(conceptsGrpc);
        }
        if (!regions.isEmpty()) {
          List<DataOuterClass.Region> regionsGrpc = new ArrayList<>();
          for (RegionFeedback region : regions) {
            regionsGrpc.add(region.serialize());
          }
          dataBuilder.addAllRegions(regionsGrpc);
        }

        Feedback.FeedbackInfo.Builder feedbackInfo = Feedback.FeedbackInfo.newBuilder();
        if (endUserId != null) {
          feedbackInfo.setEndUserId(endUserId);
        }
        if (sessionId != null) {
          feedbackInfo.setSessionId(sessionId);
        }
        if (eventType != null) {
          feedbackInfo.setEventType(Feedback.EventType.valueOf(eventType));
        }
        if (outputId != null) {
          feedbackInfo.setOutputId(outputId);
        }

        InputOuterClass.Input.Builder inputBuilder = InputOuterClass.Input.newBuilder()
            .setData(dataBuilder)
            .setFeedbackInfo(feedbackInfo);
        if (inputID != null) {
          inputBuilder.setId(inputID);
        }
        return stub().postModelFeedback(
            InputOuterClass.PostModelFeedbackRequest.newBuilder().setInput(inputBuilder).build()
        );
      }

      @NotNull @Override public JsonNull unmarshalerGrpc(Object returnedObject) {
        return JsonNull.INSTANCE;
      }
    };
  }
}
