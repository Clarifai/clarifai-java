package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.prediction.Concept;
import clarifai2.grpc.FkClarifaiHttpClient;
import com.google.gson.JsonNull;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConceptsUnitTests extends BaseUnitTest {

  @Test public void addConcepts() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("addConcepts_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.addConcepts()
        .plus(Concept.forID("@conceptID1"), Concept.forID("@conceptID2"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/concepts"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("addConcepts_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
  }

  @Test public void getConcept() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getConcept_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    @NotNull ClarifaiResponse<Concept> response = client.getConceptByID("@conceptID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/concepts/@conceptID"));
    assertEquals("GET", httpClient.requestMethod());

    assertTrue(response.isSuccessful());

    Concept concept = response.get();
    assertEquals("@conceptID", concept.id());
    assertEquals("@conceptName", concept.name());
    assertEquals("@appID", concept.appID());
  }

  @Test public void getConcepts() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getConcepts_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    @NotNull ClarifaiResponse<List<Concept>> response = client.getConcepts()
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/concepts?page=1"));
    assertEquals("GET", httpClient.requestMethod());

    assertTrue(response.isSuccessful());

    List<Concept> concepts = response.get();
    assertEquals("@conceptID1", concepts.get(0).id());
    assertEquals("@conceptName1", concepts.get(0).name());
    assertEquals("@appID", concepts.get(0).appID());

    assertEquals("@conceptID2", concepts.get(1).id());
    assertEquals("@conceptName2", concepts.get(1).name());
    assertEquals("@appID", concepts.get(1).appID());
  }

  @Test public void modifyConcepts() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("modifyConcepts_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<Concept>> response = client.modifyConcepts()
        .plus(Concept.forID("@conceptID").withName("@newConceptName"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/concepts"));
    assertEquals("PATCH", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("modifyConcepts_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    Concept concept = response.get().get(0);
    assertEquals("@conceptID", concept.id());
    assertEquals("@newConceptName", concept.name());
  }
}
