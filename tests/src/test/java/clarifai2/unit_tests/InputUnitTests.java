package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.feedback.Feedback;
import clarifai2.dto.PointF;
import clarifai2.dto.feedback.ConceptFeedback;
import clarifai2.dto.feedback.FaceFeedback;
import clarifai2.dto.feedback.RegionFeedback;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiInputsStatus;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.input.Crop;
import clarifai2.dto.prediction.Concept;
import clarifai2.grpc.FkClarifaiHttpClient;
import clarifai2.internal.JSONObjectBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class InputUnitTests extends BaseUnitTest {

  public static final double ERROR_MARGIN = 10e-9;

  @Test public void addInputs() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("addInputs_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiInput>> response = client.addInputs()
        .plus(
            ClarifaiInput.forImage("https://some.image.url1").withID("@inputID1").withGeo(PointF.at(66, 55)),
            ClarifaiInput.forImage("https://some.image.url2").withID("@inputID2")
        )
        .allowDuplicateURLs(true)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("addInputs_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    List<ClarifaiInput> inputs = response.get();
    ClarifaiInput input1 = inputs.get(0);
    ClarifaiInput input2 = inputs.get(1);


    assertEquals("@inputID1", input1.id());
    assertEquals("https://some.image.url1", ((ClarifaiURLImage)input1.inputValue()).url().toString());
    assertEquals(PointF.at(66, 55), input1.geo());

    assertEquals("@inputID2", input2.id());
    assertEquals("https://some.image.url2", ((ClarifaiURLImage)input2.inputValue()).url().toString());
    assertNull(input2.geo());
  }

  @Test public void getInputs() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getInputs_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiInput>> response = client.getInputs()
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs?page=1"));
    assertEquals("GET", httpClient.requestMethod());

    assertTrue(response.isSuccessful());

    List<ClarifaiInput> inputs = response.get();
    ClarifaiInput input1 = inputs.get(0);
    ClarifaiInput input2 = inputs.get(1);


    assertEquals("@inputID1", input1.id());
    assertEquals("https://some.image.url1", ((ClarifaiURLImage)input1.inputValue()).url().toString());
    assertEquals(PointF.at(66, 55), input1.geo());

    assertEquals("@inputID2", input2.id());
    assertEquals("https://some.image.url2", ((ClarifaiURLImage)input2.inputValue()).url().toString());
    assertNull(input2.geo());
  }

  @Test public void getInput() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getInput_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<ClarifaiInput> response = client.getInputByID("@inputID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs/@inputID"));
    assertEquals("GET", httpClient.requestMethod());

    assertTrue(response.isSuccessful());

    ClarifaiInput input = response.get();

    assertEquals("@inputID", input.id());
    assertEquals("https://some.image.url", ((ClarifaiURLImage)input.inputValue()).url().toString());
    assertEquals(PointF.at(66, 55), input.geo());
  }

  @Test public void getInputsStatus() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getInputsStatus_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<ClarifaiInputsStatus> response = client.getInputsStatus()
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs/status"));
    assertEquals("GET", httpClient.requestMethod());

    assertTrue(response.isSuccessful());

    ClarifaiInputsStatus inputsStatus = response.get();

    assertEquals(1, inputsStatus.processed());
    assertEquals(2, inputsStatus.toProcess());
    assertEquals(3, inputsStatus.errors());
    assertEquals(4, inputsStatus.processing());
    // TODO(Rok) MEDIUM: Expose the other fields.
  }

  @Test public void mergeConceptsForInputs() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("mergeConceptsForInput_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<ClarifaiInput> response = client.mergeConceptsForInput("@inputID")
        .plus(
            Concept.forID("@positiveConcept1")
                .withName("@positiveConceptName1")
                .withValue(true),
            Concept.forID("@positiveConcept2")
                .withValue(true),
            Concept.forID("@negativeConcept1")
                .withName("@negativeConceptName1")
                .withValue(false),
            Concept.forID("@negativeConcept2")
                .withValue(false)
        )
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs"));
    assertEquals("PATCH", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("mergeConceptsForInput_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    ClarifaiInput input = response.get();

    assertEquals("@inputID", input.id());

    Concept positiveConcept1 = input.concepts().get(0);
    assertEquals("@positiveConcept1", positiveConcept1.id());
    assertEquals("@positiveConceptName1", positiveConcept1.name());
    assertEquals(1, positiveConcept1.value(), ERROR_MARGIN);

    Concept positiveConcept2 = input.concepts().get(1);
    assertEquals("@positiveConcept2", positiveConcept2.id());
    assertEquals(1, positiveConcept2.value(), ERROR_MARGIN);

    Concept negativeConcept1 = input.concepts().get(2);
    assertEquals("@negativeConcept1", negativeConcept1.id());
    assertEquals("@negativeConceptName1", negativeConcept1.name());
    assertEquals(0, negativeConcept1.value(), ERROR_MARGIN);

    Concept negativeConcept2 = input.concepts().get(3);
    assertEquals("@negativeConcept2", negativeConcept2.id());
    assertEquals(0, negativeConcept2.value(), ERROR_MARGIN);
  }

  @Test public void setConceptsForInputsWithRegions() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("setConceptsForInputWithRegions_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<ClarifaiInput> response = client.setConceptsForInput("@inputID")
        .plusRegionFeedbacks(
            RegionFeedback.make(Crop.create().top(0.1f).left(0.2f).bottom(0.3f).right(0.4f), Feedback.MISPLACED)
            .withConceptFeedbacks(
                ConceptFeedback.forIdAndValue("@concept1", true),
                ConceptFeedback.forIdAndValue("@concept2", false)
            )
            .withID("@regionID")
            .withFaceFeedback(FaceFeedback.make(
                ConceptFeedback.forIdAndValue("@faceConcept1", true),
                ConceptFeedback.forIdAndValue("@faceConcept2", false)
            ))
        )
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs"));
    assertEquals("PATCH", httpClient.requestMethod());

    assertJsonEquals(readResourceFile("setConceptsForInputWithRegions_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    ClarifaiInput input = response.get();

    assertEquals("@inputID", input.id());

    Concept positiveConcept1 = input.concepts().get(0);
    assertEquals("@concept1", positiveConcept1.id());
    assertEquals(1, positiveConcept1.value(), ERROR_MARGIN);

    Concept positiveConcept2 = input.concepts().get(1);
    assertEquals("@concept2", positiveConcept2.id());
    assertEquals(0, positiveConcept2.value(), ERROR_MARGIN);
  }

  @Test public void removeConceptsForInputs() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("removeConceptsForInputs_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<ClarifaiInput> response = client.removeConceptsForInput("@inputID")
        .plus(
            Concept.forID("@concept2")
                .withValue(false))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs"));
    assertEquals("PATCH", httpClient.requestMethod());

    assertJsonEquals(readResourceFile("removeConceptsForInputs_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    ClarifaiInput input = response.get();

    assertEquals("@inputID", input.id());
    assertEquals(2, input.concepts().size());

    Concept positiveConcept1 = input.concepts().get(0);
    assertEquals("@concept1", positiveConcept1.id());
    assertEquals(1, positiveConcept1.value(), ERROR_MARGIN);

    Concept positiveConcept2 = input.concepts().get(1);
    assertEquals("@concept2", positiveConcept2.id());
    assertEquals(0, positiveConcept2.value(), ERROR_MARGIN);
  }

  @Test public void addMetadataForInput() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("addMetadataForInput_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    JsonObject jsonObject = new JSONObjectBuilder()
        .add("@key1", "@value1")
        .add("@key2", "@value2")
        .build();

    ClarifaiResponse<ClarifaiInput> response = client.addMetadataForInput("@inputID", jsonObject)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs"));
    assertEquals("PATCH", httpClient.requestMethod());

    assertJsonEquals(readResourceFile("addMetadataForInput_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    ClarifaiInput input = response.get();

    assertEquals("@inputID", input.id());

    JsonObject metadata = input.metadata();
    assertEquals("@value1", metadata.get("@key1").getAsString());
    assertEquals("@value2", metadata.get("@key2").getAsString());
  }

  @Test public void deleteAllInputs() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("deleteAllInputs_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.deleteAllInputs()
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs"));
    assertEquals("DELETE", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("deleteAllInputs_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
  }

  @Test public void deleteInput() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("deleteInput_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.deleteInput("@inputID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs/@inputID"));
    assertEquals("DELETE", httpClient.requestMethod());
    assertJsonEquals("{}", httpClient.requestBody());

    assertTrue(response.isSuccessful());
  }

  @Test public void deleteInputsBatch() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("deleteInputsBatch_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.deleteInputsBatch()
        .plus("@inputID1", "@inputID2")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/inputs"));
    assertEquals("DELETE", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("deleteInputsBatch_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
  }
}
