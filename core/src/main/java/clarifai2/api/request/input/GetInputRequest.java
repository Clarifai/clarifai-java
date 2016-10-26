package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class GetInputRequest extends ClarifaiRequest.Builder<ClarifaiInput> {

  @NotNull private final String inputID;

  public GetInputRequest(@NotNull final BaseClarifaiClient helper, @NotNull String inputID) {
    super(helper);
    this.inputID = inputID;
  }

  @NotNull @Override protected DeserializedRequest<ClarifaiInput> request() {
    return new DeserializedRequest<ClarifaiInput>() {
      @NotNull @Override public Request httpRequest() {
        return getRequest("/v2/inputs/" + inputID);
      }

      @NotNull @Override public JSONUnmarshaler<ClarifaiInput> unmarshaler() {
        return new JSONUnmarshaler<ClarifaiInput>() {
          @NotNull @Override public ClarifaiInput fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return InternalUtil.fromJson(gson, json.getAsJsonObject().get("input"), TypeToken.get(ClarifaiInput.class));
          }
        };
      }
    };
  }
}
