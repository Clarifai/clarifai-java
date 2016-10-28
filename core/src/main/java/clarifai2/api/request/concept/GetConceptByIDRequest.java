package clarifai2.api.request.concept;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public final class GetConceptByIDRequest extends ClarifaiRequest.Builder<Concept> {

  @NotNull private final String conceptID;

  public GetConceptByIDRequest(@NotNull final BaseClarifaiClient helper, @NotNull String conceptID) {
    super(helper);
    this.conceptID = conceptID;
  }
  @NotNull @Override protected DeserializedRequest<Concept> request() {
    return new DeserializedRequest<Concept>() {
      @NotNull @Override public Request httpRequest() {
        return getRequest("/v2/concepts/" + conceptID);
      }

      @NotNull @Override public JSONUnmarshaler<Concept> unmarshaler() {
        return new JSONUnmarshaler<Concept>() {
          @NotNull @Override public Concept fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return gson.fromJson(json.getAsJsonObject().get("concept"), Concept.class);
          }
        };
      }
    };
  }
}
