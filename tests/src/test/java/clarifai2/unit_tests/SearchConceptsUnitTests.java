package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.prediction.Concept;
import clarifai2.grpc.FkClarifaiHttpClient;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchConceptsUnitTests extends BaseUnitTest {

  @Test public void searchConcepts() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("searchConcepts_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<Concept>> response = client.searchConcepts("conc*")
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/concepts/searches?page=1"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("searchConcepts_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    List<Concept> concepts = response.get();

    assertEquals("@conceptID1", concepts.get(0).id());
    assertEquals("concealer", concepts.get(0).name());

    assertEquals("@conceptID2", concepts.get(1).id());
    assertEquals("concentrate", concepts.get(1).name());
  }

  @Test public void searchConceptsWithLanguage() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("searchConceptsWithLanguage_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<Concept>> response = client.searchConcepts("狗*")
        .withLanguage("zh")
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/concepts/searches?page=1"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("searchConceptsWithLanguage_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    List<Concept> concepts = response.get();

    assertEquals("@conceptID1", concepts.get(0).id());
    assertEquals("狗", concepts.get(0).name());

    assertEquals("@conceptID2", concepts.get(1).id());
    assertEquals("狗仔队", concepts.get(1).name());

    assertEquals("@conceptID3", concepts.get(2).id());
    assertEquals("狗窝", concepts.get(2).name());
  }
}
