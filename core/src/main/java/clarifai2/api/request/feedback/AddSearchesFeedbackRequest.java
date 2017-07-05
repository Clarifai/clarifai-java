package clarifai2.api.request.feedback;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AddSearchesFeedbackRequest extends ClarifaiRequest.Builder<JsonNull> {
  @Nullable private String id;

  private String endUserId;
  private String sessionId;
  private String eventType;
  private String searchId;

  public AddSearchesFeedbackRequest(@NotNull final BaseClarifaiClient client) {
    super(client);
  }

  @NotNull public AddSearchesFeedbackRequest withId(@Nullable String id) {
    this.id = id;
    return this;
  }

  @NotNull public AddSearchesFeedbackRequest withEndUserId(@NotNull String endUserId) {
    this.endUserId = endUserId;
    return this;
  }

  @NotNull public AddSearchesFeedbackRequest withSessionId(@NotNull String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  @NotNull public AddSearchesFeedbackRequest withEventType(@NotNull String eventType) {
    this.eventType = eventType;
    return this;
  }

  @NotNull public AddSearchesFeedbackRequest withSearchId(@NotNull String searchId) {
    this.searchId = searchId;
    return this;
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public Request httpRequest() {
        final JsonObject body = new JSONObjectBuilder()
            .add("input", new JSONObjectBuilder()
                .add("id", id)
                .add("feedback_info", makeFeedbackInfoJsonObject()
                )
            )
            .build();
        System.out.println(body);
        return postRequest("/v2/searches/feedback/", body);
      }

      @NotNull private JSONObjectBuilder makeFeedbackInfoJsonObject() {
        return new JSONObjectBuilder()
            .add("end_user_id", endUserId)
            .add("session_id", sessionId)
            .add("event_type", eventType)
            .add("search_id", searchId);
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
