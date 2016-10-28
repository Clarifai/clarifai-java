package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInputUpdateAction;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class PatchModelRequest extends ClarifaiRequest.Builder<ConceptModel> {

  @NotNull private final String modelID;
  @NotNull private final ClarifaiInputUpdateAction action;
  @NotNull private final List<Concept> concepts = new ArrayList<>();

  public PatchModelRequest(
      @NotNull final BaseClarifaiClient helper,
      @NotNull String modelID,
      @NotNull ClarifaiInputUpdateAction action
  ) {
    super(helper);
    this.modelID = modelID;
    this.action = action;
  }

  @NotNull public PatchModelRequest plus(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull public PatchModelRequest plus(@NotNull Concept... concepts) {
    Collections.addAll(this.concepts, concepts);
    return this;
  }

  @NotNull @Override protected DeserializedRequest<ConceptModel> request() {
    return new DeserializedRequest<ConceptModel>() {
      @NotNull @Override public Request httpRequest() {
        final JsonObject body = new JSONObjectBuilder()
            .add("action", client.gson.toJsonTree(action))
            .add("concepts", new JSONArrayBuilder()
                .addAll(concepts, new Func1<Concept, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                    return client.gson.toJsonTree(concept);
                  }
                }))
            .build();
        return patchRequest("/v2/models/" + modelID + "/output_info/data/concepts", body);
      }

      @NotNull @Override public JSONUnmarshaler<ConceptModel> unmarshaler() {
        return new JSONUnmarshaler<ConceptModel>() {
          @NotNull @Override public ConceptModel fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return gson.fromJson(json.getAsJsonObject().get("model"), new TypeToken<Model<Concept>>() {}.getType());
          }
        };
      }
    };
  }
}
