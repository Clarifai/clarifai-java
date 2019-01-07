package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.Search;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.search.SearchInputsResult;
import com.google.common.util.concurrent.ListenableFuture;
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

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl(final int page) {
    return buildURL("/v2/searches", page);
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

  @NotNull @Override protected SearchInputsResult unmarshalerGrpc(Object returnedObject) {
    Search.MultiSearchResponse searchesResponse = (Search.MultiSearchResponse) returnedObject;
    return SearchInputsResult.deserialize(searchesResponse);
  }

  @NotNull @Override protected ListenableFuture buildRequestGrpc(int page) {
    Search.Query.Builder searchBuilder = Search.Query.newBuilder();
    for (SearchClause andClause : andClauses) {
      searchBuilder.addAnds(andClause.serialize());
    }
    if (language != null) {
      searchBuilder.setLanguage(language);
    }

    return stub(page).postSearches(Search.PostSearchesRequest.newBuilder().setQuery(searchBuilder).build());
  }
}
