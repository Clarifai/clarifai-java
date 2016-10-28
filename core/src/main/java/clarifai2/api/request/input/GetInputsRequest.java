package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class GetInputsRequest extends ClarifaiPaginatedRequest.Builder<List<ClarifaiInput>, GetInputsRequest> {
  public GetInputsRequest(@NotNull final BaseClarifaiClient client) {
    super(client);
  }

  @NotNull @Override protected JSONUnmarshaler<List<ClarifaiInput>> unmarshaler() {
    return new JSONUnmarshaler<List<ClarifaiInput>>() {
      @NotNull @Override
      public List<ClarifaiInput> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return InternalUtil.fromJson(
            gson,
            json.getAsJsonObject().get("inputs"),
            new TypeToken<List<ClarifaiInput>>() {}
        );
      }
    };
  }

  @NotNull @Override protected Request buildRequest(final int page) {
    return new Request.Builder()
        .url(buildURL("/v2/inputs", page))
        .get()
        .build();
  }
}
