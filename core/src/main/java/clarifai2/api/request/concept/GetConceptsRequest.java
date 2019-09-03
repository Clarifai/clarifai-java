package clarifai2.api.request.concept;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.prediction.Concept;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class GetConceptsRequest extends ClarifaiPaginatedRequest.Builder<List<Concept>, GetConceptsRequest> {

  public GetConceptsRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl(final int page) {
    return buildURL("v2/concepts", page);
  }

  @NotNull @Override protected List<Concept> unmarshalerGrpc(Object returnedObject) {
    ConceptOuterClass.MultiConceptResponse conceptsResponse = (ConceptOuterClass.MultiConceptResponse) returnedObject;

    List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : conceptsResponse.getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }

    return concepts;
  }

  @NotNull @Override protected ListenableFuture<ConceptOuterClass.MultiConceptResponse> buildRequestGrpc(int page) {
    return stub(page).listConcepts(ConceptOuterClass.ListConceptsRequest.newBuilder().build());
  }
}
