package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.SearchClause;
import clarifai2.dto.PointF;
import clarifai2.dto.Radius;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.input.SearchHit;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.search.SearchInputsResult;
import clarifai2.grpc.FkClarifaiHttpClient;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchInputsUnitTests extends BaseUnitTest {

  @Test public void searchInputsByConceptID() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("searchInputsByConceptID_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<SearchInputsResult> response = client.searchInputs(
        SearchClause.matchConcept(Concept.forID("@conceptID"))
    )
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/searches?page=1"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("searchInputsByConceptID_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    SearchInputsResult results = response.get();
    List<SearchHit> searchHits = results.searchHits();
    assertEquals(1, searchHits.size());

    ClarifaiInput input = searchHits.get(0).input();
    assertEquals("@inputID", input.id());
    assertEquals("https://some-image-url", ((ClarifaiURLImage) input.inputValue()).url().toString());
  }

  @Test public void searchInputsByConceptName() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("searchInputsByConceptName_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<SearchInputsResult> response = client.searchInputs(
        SearchClause.matchConcept(Concept.forName("@conceptName"))
    )
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/searches?page=1"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("searchInputsByConceptName_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    SearchInputsResult results = response.get();
    List<SearchHit> searchHits = results.searchHits();
    assertEquals(1, searchHits.size());

    ClarifaiInput input = searchHits.get(0).input();
    assertEquals("@inputID", input.id());
    assertEquals("https://some-image-url", ((ClarifaiURLImage) input.inputValue()).url().toString());
  }

  @Test public void searchInputsByGeo() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("searchInputsByGeo_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<SearchInputsResult> response = client.searchInputs(
        SearchClause.matchGeo(PointF.at(-1, 1.5f), Radius.of(1, Radius.Unit.KILOMETER))
    )
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/searches?page=1"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("searchInputsByGeo_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    SearchInputsResult results = response.get();
    List<SearchHit> searchHits = results.searchHits();
    assertEquals(1, searchHits.size());

    ClarifaiInput input = searchHits.get(0).input();
    assertEquals("@inputID", input.id());
    assertEquals("https://some-image-url", ((ClarifaiURLImage) input.inputValue()).url().toString());
  }
}
