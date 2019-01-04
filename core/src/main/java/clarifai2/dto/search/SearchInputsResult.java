package clarifai2.dto.search;

import clarifai2.internal.grpc.api.Search;
import clarifai2.dto.input.SearchHit;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class SearchInputsResult {

  SearchInputsResult() {} // AutoValue instances only

  @NotNull public abstract String id();

  @NotNull public abstract List<SearchHit> searchHits();


  public static SearchInputsResult deserialize(Search.MultiSearchResponse searchesResponse) {
    List<SearchHit> hits = new ArrayList<>();
    for (Search.Hit hit : searchesResponse.getHitsList()) {
      hits.add(SearchHit.deserialize(hit));
    }
    return new AutoValue_SearchInputsResult(searchesResponse.getId(), hits);
  }
}

