package clarifai2.api.request.concept;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.prediction.Concept;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class ModifyConceptsRequest extends ClarifaiRequest.Builder<List<Concept>> {
  @NotNull private final String action;
  @NotNull private final List<Concept> concepts = new ArrayList<>();

  public ModifyConceptsRequest(@NotNull BaseClarifaiClient client, @NotNull String action) {
    super(client);
    this.action = action;
  }

  @NotNull public ModifyConceptsRequest plus(@NotNull Concept... concepts) {
    return plus(Arrays.asList(concepts));
  }

  @NotNull public ModifyConceptsRequest plus(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull @Override protected String method() {
    return "PATCH";
  }

  @NotNull @Override protected String subUrl() {
    return "v2/concepts";
  }

  @NotNull @Override protected DeserializedRequest<List<Concept>> request() {
    return new DeserializedRequest<List<Concept>>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        List<ConceptOuterClass.Concept> conceptsGrpc = new ArrayList<>();
        for (Concept concept : concepts) {
          conceptsGrpc.add(concept.serialize());
        }
        return stub().patchConcepts(
            ConceptOuterClass.PatchConceptsRequest.newBuilder().setAction(action).addAllConcepts(conceptsGrpc).build()
        );
      }

      @NotNull @Override public List<Concept> unmarshalerGrpc(Object returnedObject) {
        ConceptOuterClass.MultiConceptResponse conceptsResponse =
            (ConceptOuterClass.MultiConceptResponse) returnedObject;
        List<Concept> conceptsResult = new ArrayList<>();
        for (ConceptOuterClass.Concept concept : conceptsResponse.getConceptsList()) {
          conceptsResult.add(Concept.deserialize(concept));
        }
        return conceptsResult;
      }
    };
  }
}
