package clarifai2.solutions.moderation.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONUnmarshaler;
import clarifai2.solutions.moderation.dto.ModerationStatus;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import static clarifai2.internal.InternalUtil.assertJsonIs;

public class GetModerationStatusRequest extends ClarifaiRequest.Builder<ModerationStatus> {
  @NotNull private final String inputID;

  public GetModerationStatusRequest(@NotNull BaseClarifaiClient client, @NotNull String inputID) {
    super(client, MediaType.parse("application/json"));
    this.inputID = inputID;
  }

  @NotNull @Override protected DeserializedRequest<ModerationStatus> request() {
    return new DeserializedRequest<ModerationStatus>() {
      @NotNull @Override public Request httpRequest() {
        return getRequest("/v2/inputs/" + inputID + "/outputs");
      }

      @NotNull @Override public JSONUnmarshaler<ModerationStatus> unmarshaler() {
        return new JSONUnmarshaler<ModerationStatus>() {
          @NotNull @Override
          public ModerationStatus fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            final JsonObject root = assertJsonIs(json, JsonObject.class);
            return InternalUtil.fromJson(
                gson,
                root.getAsJsonObject("moderation").getAsJsonObject("status"),
                new TypeToken<ModerationStatus>() {}
            );
          }
        };
      }
    };
  }
}
