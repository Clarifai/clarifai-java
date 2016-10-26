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

public final class GetModelRequest extends ClarifaiRequest.Builder<Model<?>> {

  @NotNull private final String modelID;

  public GetModelRequest(
      @NotNull final BaseClarifaiClient helper,
      @NotNull String modelID
  ) {
    super(helper);
    this.modelID = modelID;
  }

  @NotNull @Override protected DeserializedRequest<Model<?>> request() {
    return new DeserializedRequest<Model<?>>() {
      @NotNull @Override public Request httpRequest() {
        return getRequest("/v2/models/" + modelID + "/output_info");
      }

      @NotNull @Override public JSONUnmarshaler<Model<?>> unmarshaler() {
        return new JSONUnmarshaler<Model<?>>() {
          @NotNull @Override public Model<?> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return gson.fromJson(
                json.getAsJsonObject().get("model"),
                new TypeToken<Model<?>>() {}.getType()
            );
          }
        };
      }
    };
  }
}
