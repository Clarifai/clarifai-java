package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInputsStatus;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GetInputsStatusRequest extends ClarifaiRequest.Builder<ClarifaiInputsStatus> {

  public GetInputsStatusRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull @Override protected JSONUnmarshaler<ClarifaiInputsStatus> unmarshaler() {
    return new JSONUnmarshaler<ClarifaiInputsStatus>() {
      @Nullable @Override
      public ClarifaiInputsStatus fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return gson.fromJson(json.getAsJsonObject().getAsJsonObject("counts"), ClarifaiInputsStatus.class);
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    return new Request.Builder()
        .url(buildURL("/v2/inputs/status"))
        .get()
        .build();
  }
}
