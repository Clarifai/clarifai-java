package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.model.ModelVersion;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GetModelVersionsRequest
    extends ClarifaiPaginatedRequest.Builder<List<ModelVersion>, GetModelVersionsRequest> {

  @NotNull private final String modelID;

  public GetModelVersionsRequest(@NotNull final BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  @NotNull @Override protected JSONUnmarshaler<List<ModelVersion>> unmarshaler() {
    return new JSONUnmarshaler<List<ModelVersion>>() {
      @Nullable @Override
      public List<ModelVersion> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return InternalUtil.fromJson(
            gson,
            json.getAsJsonObject().get("model_versions"),
            new TypeToken<List<ModelVersion>>() {}
        );
      }
    };
  }

  @NotNull @Override protected Request buildRequest(final int page) {
    return new Request.Builder()
        .url(buildURL("/v2/models/" + modelID + "/versions", page))
        .get()
        .build();
  }
}
