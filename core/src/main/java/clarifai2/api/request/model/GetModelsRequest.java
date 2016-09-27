package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.model.Model;
import clarifai2.internal.ClarifaiUtil;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GetModelsRequest
    extends ClarifaiPaginatedRequest.Builder<List<Model<?>>, GetModelsRequest> {

  public GetModelsRequest(@NotNull final BaseClarifaiClient client) {
    super(client);
  }

  @NotNull @Override protected JSONUnmarshaler<List<Model<?>>> unmarshaler() {
    return new JSONUnmarshaler<List<Model<?>>>() {
      @Nullable @Override
      public List<Model<?>> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return ClarifaiUtil.fromJson(
            gson,
            json.getAsJsonObject().get("models"),
            new TypeToken<List<Model<?>>>() {}
        );
      }
    };
  }

  @NotNull @Override protected Request buildRequest(final int page) {
    return new Request.Builder()
        .url(buildURL("/v2/models", page))
        .get()
        .build();
  }
}
