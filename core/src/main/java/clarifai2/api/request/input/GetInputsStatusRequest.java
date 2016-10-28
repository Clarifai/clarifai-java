package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInputsStatus;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class GetInputsStatusRequest extends ClarifaiRequest.Builder<ClarifaiInputsStatus> {

  public GetInputsStatusRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull @Override protected DeserializedRequest<ClarifaiInputsStatus> request() {
    return new DeserializedRequest<ClarifaiInputsStatus>() {
      @NotNull @Override public Request httpRequest() {
        return getRequest("/v2/inputs/status");
      }

      @NotNull @Override public JSONUnmarshaler<ClarifaiInputsStatus> unmarshaler() {
        return new JSONUnmarshaler<ClarifaiInputsStatus>() {
          @NotNull @Override public ClarifaiInputsStatus fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return gson.fromJson(json.getAsJsonObject().getAsJsonObject("counts"), ClarifaiInputsStatus.class);
          }
        };
      }
    };
  }

}
