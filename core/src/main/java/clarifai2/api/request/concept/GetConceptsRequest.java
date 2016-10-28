package clarifai2.api.request.concept;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class GetConceptsRequest extends ClarifaiPaginatedRequest.Builder<List<Concept>, GetConceptsRequest> {

  public GetConceptsRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull @Override protected JSONUnmarshaler<List<Concept>> unmarshaler() {
    return new JSONUnmarshaler<List<Concept>>() {
      @NotNull @Override
      public List<Concept> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return InternalUtil.fromJson(
            gson,
            json.getAsJsonObject().get("concepts"),
            new TypeToken<List<Concept>>() {}
        );
      }
    };
  }

  @NotNull @Override protected Request buildRequest(final int page) {
    return new Request.Builder()
        .url(buildURL("/v2/concepts", page))
        .get()
        .build();
  }
}
