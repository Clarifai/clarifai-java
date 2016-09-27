package clarifai2.api.request.concept;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GetConceptByIDRequest extends ClarifaiRequest.Builder<Concept> {

  @NotNull private final String conceptID;

  public GetConceptByIDRequest(@NotNull final BaseClarifaiClient helper, @NotNull String conceptID) {
    super(helper);
    this.conceptID = conceptID;
  }

  @NotNull @Override protected JSONUnmarshaler<Concept> unmarshaler() {
    return new JSONUnmarshaler<Concept>() {
      @Nullable @Override public Concept fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return gson.fromJson(json.getAsJsonObject().get("concept"), Concept.class);
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    return new Request.Builder()
        .url(buildURL("/v2/concepts/" + conceptID))
        .get()
        .build();
  }
}
