package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public class ModifyModelRequest extends CreateModelRequest {

  public ModifyModelRequest(@NotNull BaseClarifaiClient client, @NotNull String modelID) {
    super(client, modelID);
  }

  @NotNull @Override protected DeserializedRequest<ConceptModel> request() {
    return new DeserializedRequest<ConceptModel>() {
      @NotNull @Override public Request httpRequest() {
        final JsonObject body = new JSONObjectBuilder()
            .add("models", new JSONArrayBuilder()
                .add(buildJSONOfModel())
            )
            .build();
        return patchRequest("/v2/models", body);
      }

      @NotNull @Override public JSONUnmarshaler<ConceptModel> unmarshaler() {
        return new JSONUnmarshaler<ConceptModel>() {
          @NotNull @Override public ConceptModel fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return gson.fromJson(
                json.getAsJsonObject().getAsJsonArray("models").get(0),
                new TypeToken<Model<Concept>>() {}.getType()
            );
          }
        };
      }
    };
  }
}
