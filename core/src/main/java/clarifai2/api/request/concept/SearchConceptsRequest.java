package clarifai2.api.request.concept;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SearchConceptsRequest
    extends ClarifaiPaginatedRequest.Builder<List<Concept>, SearchConceptsRequest> {


  @NotNull private final String conceptSearchQuery;

  @Nullable private String language = null;

  public SearchConceptsRequest(@NotNull final BaseClarifaiClient client, @NotNull String conceptSearchQuery) {
    super(client);
    this.conceptSearchQuery = conceptSearchQuery;
  }

  @NotNull public SearchConceptsRequest withLanguage(@NotNull String language) {
    this.language = language;
    return this;
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
    final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder();
    final JSONObjectBuilder conceptQueryBuilder = new JSONObjectBuilder();
    conceptQueryBuilder.add("name", conceptSearchQuery);
    if (language != null) {
      conceptQueryBuilder.add("language", language);
    }
    bodyBuilder.add("concept_query", conceptQueryBuilder.build());
    final JsonObject body = bodyBuilder.build();

    return new Request.Builder()
        .post(toRequestBody(body, page))
        .url(buildURL("/v2/concepts/searches", page))
        .build();
  }
}
