package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.Model;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GetModelRequest extends ClarifaiRequest.Builder<Model<?>> {

  @NotNull private final String modelID;

  public GetModelRequest(
      @NotNull final BaseClarifaiClient helper,
      @NotNull String modelID
  ) {
    super(helper);
    this.modelID = modelID;
  }

  @NotNull @Override protected JSONUnmarshaler<Model<?>> unmarshaler() {
    return new JSONUnmarshaler<Model<?>>() {
      @Nullable @Override
      public Model<?> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return gson.fromJson(
            json.getAsJsonObject().get("model"),
            new TypeToken<Model<?>>() {}.getType()
        );
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    return new Request.Builder()
        .url(buildURL("/v2/models/" + modelID + "/output_info"))
        .get()
        .build();
  }
}
