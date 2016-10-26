package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ModelVersion;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public final class DeleteModelVersionRequest extends ClarifaiRequest.Builder<List<ModelVersion>> {

  @NotNull private final String modelID;
  @NotNull private final String versionID;

  public DeleteModelVersionRequest(
      @NotNull BaseClarifaiClient client,
      @NotNull String modelID,
      @NotNull String versionID
  ) {
    super(client);
    this.modelID = modelID;
    this.versionID = versionID;
  }

  @NotNull @Override protected DeserializedRequest<List<ModelVersion>> request() {
    return new DeserializedRequest<List<ModelVersion>>() {
      @NotNull @Override public Request httpRequest() {
        return deleteRequest(String.format("/v2/models/%s/versions/%s", modelID, versionID), new JsonObject());
      }

      @NotNull @Override public JSONUnmarshaler<List<ModelVersion>> unmarshaler() {
        return new JSONUnmarshaler<List<ModelVersion>>() {
          @NotNull @Override public List<ModelVersion> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return Collections.emptyList();
          }
        };
      }
    };
  }
}
