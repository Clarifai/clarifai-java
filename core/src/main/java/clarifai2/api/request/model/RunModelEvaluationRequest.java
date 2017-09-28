package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ModelVersion;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class RunModelEvaluationRequest extends ClarifaiRequest.Builder<ModelVersion> {
  @NotNull private String modelID;
  @NotNull private String versionID;

  public RunModelEvaluationRequest(@NotNull BaseClarifaiClient client, @NotNull String modelID,
      @NotNull String versionID) {
    super(client);
    this.modelID = modelID;
    this.versionID = versionID;
  }

  @NotNull @Override protected DeserializedRequest<ModelVersion> request() {
    return new DeserializedRequest<ModelVersion>() {
      @NotNull @Override public Request httpRequest() {
        return postRequest("/v2/models/" + modelID + "/versions/" + versionID + "/metrics", JsonNull.INSTANCE);
      }

      @NotNull @Override public JSONUnmarshaler<ModelVersion> unmarshaler() {
        return new JSONUnmarshaler<ModelVersion>() {
          @NotNull @Override public ModelVersion fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return InternalUtil.fromJson(
                gson,
                json.getAsJsonObject().get("model_version"),
                new TypeToken<ModelVersion>() {}
            );
          }
        };
      }
    };
  }
}
