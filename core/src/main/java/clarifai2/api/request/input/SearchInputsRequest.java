package clarifai2.api.request.input;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.search.SearchInputsResult;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class SearchInputsRequest extends ClarifaiPaginatedRequest.Builder<SearchInputsResult, SearchInputsRequest> {

  private final List<SearchClause> andClauses = new ArrayList<>();

  @Nullable private String language = null;

  public SearchInputsRequest(@NotNull final BaseClarifaiClient client, List<SearchClause> searchClauses) {
    super(client);
    this.andClauses.addAll(searchClauses);
  }

  @NotNull @Override protected JSONUnmarshaler<SearchInputsResult> unmarshaler() {
    return new JSONUnmarshaler<SearchInputsResult>() {
      @NotNull @Override public SearchInputsResult fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return InternalUtil.fromJson(gson, json.getAsJsonObject(), new TypeToken<SearchInputsResult>() {});
      }
    };
  }

  /**
   * @param language the language to be used. If none is supplied, the default language for the application will be
   *                 used. Should be in ISO 639 - 1 format eg: "en" = English, "zh" = Chinese, "ja" = Japanese.
   * @return this request builder
   */
  @NotNull public SearchInputsRequest withLanguage(@NotNull String language) {
    this.language = language;
    return this;
  }

  /**
   * Adds the given {@link SearchClause} to the request. It is ANDed with all of the other clauses.
   *
   * @param searchClause the clause to AND with all of your other clauses
   * @return this request builder
   */
  @NotNull public SearchInputsRequest and(@NotNull SearchClause searchClause) {
    this.andClauses.add(searchClause);
    return this;
  }

  /**
   * Adds all of the given {@link SearchClause}s to the request, ANDed with all of the existing clauses.
   *
   * @param clauses the clauses to AND with all of your other clauses
   * @return this request builder
   */
  @NotNull public SearchInputsRequest ands(@NotNull Collection<SearchClause> clauses) {
    this.andClauses.addAll(clauses);
    return this;
  }

  /**
   * Adds all of the given {@link SearchClause}s to the request, ANDed with all of the existing clauses.
   *
   * @param clauses the clauses to AND with all of your other clauses
   * @return this request builder
   */
  @NotNull public SearchInputsRequest ands(@NotNull SearchClause... clauses) {
    return ands(Arrays.asList(clauses));
  }


  @NotNull @Override protected Request buildRequest(final int page) {
    final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder();
    final JSONObjectBuilder queryBuilder = new JSONObjectBuilder();
    queryBuilder.add("ands", new JSONArrayBuilder()
        .addAll(andClauses, new Func1<SearchClause, JsonElement>() {
          @NotNull @Override public JsonElement call(@NotNull SearchClause model) {
            return SearchInputsRequest.this.gson.toJsonTree(model);
          }
        })
    );
    if (language != null) {
      queryBuilder.add("language", language);
    }
    bodyBuilder.add("query", queryBuilder.build());
    final JsonObject body = bodyBuilder.build();
    return new Request.Builder()
        .url(buildURL("/v2/searches", page))
        .post(toRequestBody(body, page))
        .build();
  }
}
