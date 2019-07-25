package clarifai2.api.request.concept;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.prediction.Concept;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl(int page) {
    return buildURL("v2/concepts/searches", page);
  }

  @NotNull @Override protected List<Concept> unmarshalerGrpc(Object returnedObject) {
    ConceptOuterClass.MultiConceptResponse conceptsResponse = (ConceptOuterClass.MultiConceptResponse) returnedObject;

    List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : conceptsResponse.getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }

    return concepts;
  }

  @NotNull @Override protected ListenableFuture buildRequestGrpc(int page) {
    ConceptOuterClass.ConceptQuery.Builder conceptQuery = ConceptOuterClass.ConceptQuery.newBuilder()
        .setName(conceptSearchQuery);
    if (language != null) {
      conceptQuery.setLanguage(language);
    }
    return stub(page).postConceptsSearches(
        ConceptOuterClass.PostConceptsSearchesRequest.newBuilder().setConceptQuery(conceptQuery).build()
    );
  }
}
