package clarifai2.api.request.concept;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.prediction.Concept;
import clarifai2.grpc.JsonChannel;
import clarifai2.internal.JSONUnmarshaler;
import com.google.common.util.concurrent.ListenableFuture;
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

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl() {
    return "v2/concepts/" + conceptID;
  }

  @NotNull @Override protected DeserializedRequest<Concept> request() {
    return new DeserializedRequest<Concept>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub()
            .getConcept(ConceptOuterClass.GetConceptRequest.newBuilder().build());
      }

      @NotNull @Override public Concept unmarshalerGrpc(Object returnedObject) {
        ConceptOuterClass.SingleConceptResponse conceptResponse =
            (ConceptOuterClass.SingleConceptResponse) returnedObject;
        ConceptOuterClass.Concept concept = conceptResponse.getConcept();

        return Concept.deserialize(concept);
      }
    };
  }
}
