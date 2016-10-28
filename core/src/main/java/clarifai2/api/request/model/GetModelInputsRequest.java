package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.input.ClarifaiInput;
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

public final class GetModelInputsRequest
    extends ClarifaiPaginatedRequest.Builder<List<ClarifaiInput>, GetModelInputsRequest> {

  @NotNull private final String modelID;

  @Nullable private String modelVersionID;

  public GetModelInputsRequest(@NotNull final BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  @NotNull public GetModelInputsRequest fromSpecificModelVersion(@NotNull String modelVersionID) {
    this.modelVersionID = modelVersionID;
    return this;
  }

  @NotNull public GetModelInputsRequest fromSpecificModelVersion(@NotNull ModelVersion modelVersion) {
    return fromSpecificModelVersion(modelVersion.id());
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
    final StringBuilder url = new StringBuilder("/v2/models/").append(modelID);
    if (modelVersionID != null) {
      url.append("/versions/").append(modelVersionID);
    }
    url.append("/inputs");
    return new Request.Builder()
        .url(buildURL(url.toString(), page))
        .get()
        .build();
  }
}
