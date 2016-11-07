package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.InternalUtil;
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
  @NotNull private final String action;

  @NotNull private final List<Concept> concepts = new ArrayList<>();

  public PatchModelRequest(
      @NotNull final BaseClarifaiClient helper,
      @NotNull String modelID,
      @NotNull String action
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
            .add("action", action)
            .add("models", new JSONArrayBuilder()
                .add(new JSONObjectBuilder()
                    .add("id", modelID)
                    .add("output_info", new JSONObjectBuilder()
                        .add("data", new JSONObjectBuilder()
                            .add("concepts", new JSONArrayBuilder()
                                .addAll(concepts, new Func1<Concept, JsonElement>() {
                                  @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                                    return client.gson.toJsonTree(concept);
                                  }
                                })
                            )
                        )
                    )
                )
            )
            .build();
        return patchRequest("/v2/models/", body);
      }

      @NotNull @Override public JSONUnmarshaler<ConceptModel> unmarshaler() {
        return new JSONUnmarshaler<ConceptModel>() {
          @NotNull @Override public ConceptModel fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            final JsonElement firstModel = json.getAsJsonObject().getAsJsonArray("models").get(0);
            return InternalUtil.fromJson(gson, firstModel, new TypeToken<ConceptModel>() {});
          }
        };
      }
    };
  }
}
