package clarifai2.api.request.feedback;

import clarifai2.internal.grpc.api.Feedback;
import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.internal.grpc.api.Search;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SearchesFeedbackRequest extends ClarifaiRequest.Builder<JsonNull> {
  @Nullable private String id;

  private String endUserId;
  private String sessionId;
  private String eventType;
  private String searchId;

  public SearchesFeedbackRequest(@NotNull final BaseClarifaiClient client) {
    super(client);
  }

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl() {
    return "/v2/searches/feedback/";
  }

  @NotNull public SearchesFeedbackRequest withId(@Nullable String id) {
    this.id = id;
    return this;
  }

  @NotNull public SearchesFeedbackRequest withEndUserId(@NotNull String endUserId) {
    this.endUserId = endUserId;
    return this;
  }

  @NotNull public SearchesFeedbackRequest withSessionId(@NotNull String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  @NotNull public SearchesFeedbackRequest withEventType(@NotNull String eventType) {
    this.eventType = eventType;
    return this;
  }

  @NotNull public SearchesFeedbackRequest withSearchId(@NotNull String searchId) {
    this.searchId = searchId;
    return this;
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        Feedback.FeedbackInfo.Builder feedbackInfoBuilder = Feedback.FeedbackInfo.newBuilder();
        if (endUserId != null) {
          feedbackInfoBuilder.setEndUserId(endUserId);
        }
        if (sessionId != null) {
          feedbackInfoBuilder.setSessionId(sessionId);
        }
        if (eventType != null) {
          feedbackInfoBuilder.setEventType(Feedback.EventType.valueOf(eventType));
        }
        if (searchId != null) {
          feedbackInfoBuilder.setSearchId(searchId);
        }
        InputOuterClass.Input.Builder inputBuilder = InputOuterClass.Input.newBuilder()
            .setFeedbackInfo(feedbackInfoBuilder);
        if (id != null) {
          inputBuilder.setId(id);
        }
        return stub().postSearchFeedback(Search.PostSearchFeedbackRequest.newBuilder().setInput(inputBuilder).build());
      }

      @NotNull @Override public JsonNull unmarshalerGrpc(Object returnedObject) {
        return JsonNull.INSTANCE;
      }
    };
  }
}
