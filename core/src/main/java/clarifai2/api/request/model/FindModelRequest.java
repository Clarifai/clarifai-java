package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class FindModelRequest
    extends ClarifaiPaginatedRequest.Builder<List<Model<?>>, FindModelRequest> {

  @Nullable private String name;
  @Nullable private ModelType modelType;

  public FindModelRequest(@NotNull final BaseClarifaiClient client) {
    super(client);
  }

  @NotNull public FindModelRequest withName(@Nullable String name) {
    this.name = name;
    return this;
  }

  @NotNull public FindModelRequest withModelType(@NotNull ModelType modelType) {
    this.modelType = modelType;
    return this;
  }

  @NotNull @Override protected JSONUnmarshaler<List<Model<?>>> unmarshaler() {
    return new JSONUnmarshaler<List<Model<?>>>() {
      @Nullable @Override
      public List<Model<?>> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return InternalUtil.fromJson(
            gson,
            json.getAsJsonObject().get("models"),
            new TypeToken<List<Model<?>>>() {}
        );
      }
    };
  }

  @NotNull @Override protected Request buildRequest(final int page) {
    final JsonObject body = new JSONObjectBuilder()
        .add("model_query", new JSONObjectBuilder()
            .add("name", name)
            .add("type", modelType != null ? modelType.typeName() : null)
        )
        .build();
    return new Request.Builder()
        .url(buildURL("/v2/models/searches", page))
        .post(toRequestBody(body, page))
        .build();
  }
}
