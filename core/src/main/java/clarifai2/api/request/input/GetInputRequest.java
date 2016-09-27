package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.internal.ClarifaiUtil;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GetInputRequest extends ClarifaiRequest.Builder<ClarifaiInput> {

  @NotNull private final String inputID;

  public GetInputRequest(@NotNull final BaseClarifaiClient helper, @NotNull String inputID) {
    super(helper);
    this.inputID = inputID;
  }

  @NotNull @Override protected JSONUnmarshaler<ClarifaiInput> unmarshaler() {
    return new JSONUnmarshaler<ClarifaiInput>() {
      @Nullable @Override public ClarifaiInput fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return ClarifaiUtil.fromJson(gson, json.getAsJsonObject().get("input"), TypeToken.get(ClarifaiInput.class));
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    return new Request.Builder()
        .url(buildURL("/v2/inputs/" + inputID))
        .get()
        .build();
  }
}
