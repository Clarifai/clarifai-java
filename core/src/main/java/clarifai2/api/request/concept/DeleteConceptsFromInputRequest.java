package clarifai2.api.request.concept;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiInputUpdateAction;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DeleteConceptsFromInputRequest extends ClarifaiRequest.Builder<ClarifaiInput> {

  @NotNull private final String inputID;
  @NotNull private final List<Concept> concepts = new ArrayList<>();

  public DeleteConceptsFromInputRequest(
      @NotNull final BaseClarifaiClient helper,
      @NotNull String inputID
  ) {
    super(helper);
    this.inputID = inputID;
  }

  @NotNull public final DeleteConceptsFromInputRequest plus(@NotNull Concept... concepts) {
    return plus(Arrays.asList(concepts));
  }

  @NotNull public final DeleteConceptsFromInputRequest plus(@NotNull List<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }


  @NotNull @Override protected DeserializedRequest<ClarifaiInput> request() {
    return new DeserializedRequest<ClarifaiInput>() {
      @NotNull @Override public Request httpRequest() {
        final List<JsonElement> conceptJSONs = new ArrayList<>(concepts.size());
        for (final Concept concept : concepts) {
          conceptJSONs.add(client.gson.toJsonTree(concept));
        }
        final JsonObject body = new JSONObjectBuilder()
            .add("concepts", new JSONArrayBuilder(conceptJSONs).build())
            .add("action", client.gson.toJsonTree(ClarifaiInputUpdateAction.DELETE_CONCEPTS))
            .build();
        return patchRequest("/v2/inputs/" + inputID + "/data/concepts", body);
      }

      @NotNull @Override public JSONUnmarshaler<ClarifaiInput> unmarshaler() {
        return new JSONUnmarshaler<ClarifaiInput>() {
          @NotNull @Override public ClarifaiInput fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return gson.fromJson(json.getAsJsonObject().get("input"), ClarifaiInput.class);
          }
        };
      }
    };
  }
}
