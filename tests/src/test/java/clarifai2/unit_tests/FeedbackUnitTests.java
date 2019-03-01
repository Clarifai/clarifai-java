package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.feedback.Feedback;
import clarifai2.dto.feedback.ConceptFeedback;
import clarifai2.dto.feedback.RegionFeedback;
import clarifai2.dto.input.Crop;
import clarifai2.grpc.FkClarifaiHttpClient;
import com.google.gson.JsonNull;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FeedbackUnitTests extends BaseUnitTest {

  @Test public void modelFeedback() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("modelFeedback_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.modelFeedback("@modelID")
        .withImageUrl("@imageURL")
        .withInputId("@inputID")
        .withOutputId("@outputID")
        .withEndUserId("@endUserID")
        .withSessionId("@sessionID")
        .withConcepts(ConceptFeedback.forIdAndValue("dog", true), ConceptFeedback.forIdAndValue("cat", false))
        .withEventType("annotation")
        .withRegions(
            RegionFeedback.make(
                Crop.create().top(0.1f).left(0.1f).bottom(0.2f).right(0.2f),
                Feedback.NOT_DETECTED
            )
            .withConceptFeedbacks(
                ConceptFeedback.forIdAndValue("freeman", true),
                ConceptFeedback.forIdAndValue("eminem", false)
            )
        )
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/feedback"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("modelFeedback_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
  }

  @Test public void searchesFeedback() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("searchesFeedback_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.searchesFeedback()
        .withId("@inputID")
        .withSearchId("@searchID")
        .withEndUserId("@endUserID")
        .withEventType("search_click")
        .withSessionId("@sessionID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/searches/feedback/"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("searchesFeedback_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
  }
}
