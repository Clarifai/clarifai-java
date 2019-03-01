package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.Action;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.FaceDetectionModel;
import clarifai2.dto.model.FaceEmbeddingModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import clarifai2.grpc.FkClarifaiHttpClient;
import com.google.gson.JsonNull;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PredictUnitTests extends BaseUnitTest {

  @Test public void predict() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predict_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predict_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());

    assertEquals("@outputID", output.id());

    Concept concept = (Concept) output.data().get(0);
    assertEquals("@conceptID1", concept.id());

    Model<Prediction> model = output.model();
    assertEquals("@modelID", model.id());
    assertEquals("@modelName", model.name());
    assertEquals("concept", model.modelType().typeExt());
  }

  @Test public void predictWithArguments() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predictWithArguments_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .withMinValue(0.98)
        .withMaxConcepts(3)
        .withLanguage("de")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictWithArguments_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());

    assertEquals("@outputID", output.id());

    Concept concept = (Concept) output.data().get(0);
    assertEquals("@conceptID1", concept.id());

    Model<Prediction> model = output.model();
    assertEquals("@modelID", model.id());
    assertEquals("@modelName", model.name());
    assertEquals("concept", model.modelType().typeExt());
  }

  @Test public void batchPredict() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("batchPredict_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(
            ClarifaiInput.forImage("https://some-image-url1"),
            ClarifaiInput.forImage("https://some-image-url2")
        )
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("batchPredict_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    ClarifaiOutput<Prediction> output1 = response.get().get(0);
    assertEquals("@outputID1", output1.id());
    assertEquals("@inputID1", output1.input().id());
    Concept concept11 = (Concept) output1.data().get(0);
    assertEquals("@conceptID11", concept11.id());
    Concept concept12 = (Concept) output1.data().get(1);
    assertEquals("@conceptID12", concept12.id());

    ClarifaiOutput<Prediction> output2 = response.get().get(1);
    assertEquals("@outputID2", output2.id());
    assertEquals("@inputID2", output2.input().id());
    Concept concept21 = (Concept) output2.data().get(0);
    assertEquals("@conceptID21", concept21.id());
    Concept concept22 = (Concept) output2.data().get(1);
    assertEquals("@conceptID22", concept22.id());
  }

  @Test public void batchPredictWithArguments() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("batchPredictWithArguments_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(
            ClarifaiInput.forImage("https://some-image-url1"),
            ClarifaiInput.forImage("https://some-image-url2")
        )
        .withMinValue(0.98)
        .withMaxConcepts(2)
        .withLanguage("de")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("batchPredictWithArguments_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    ClarifaiOutput<Prediction> output1 = response.get().get(0);
    assertEquals("@outputID1", output1.id());
    assertEquals("@inputID1", output1.input().id());
    Concept concept11 = (Concept) output1.data().get(0);
    assertEquals("@conceptID11", concept11.id());
    Concept concept12 = (Concept) output1.data().get(1);
    assertEquals("@conceptID12", concept12.id());

    ClarifaiOutput<Prediction> output2 = response.get().get(1);
    assertEquals("@outputID2", output2.id());
    assertEquals("@inputID2", output2.input().id());
    Concept concept21 = (Concept) output2.data().get(0);
    assertEquals("@conceptID21", concept21.id());
    Concept concept22 = (Concept) output2.data().get(1);
    assertEquals("@conceptID22", concept22.id());
  }

  // To be future-proof against expansion, response objects with unknown fields should be
  // parsed correctly and unknown fields ignored.
  @Test public void predictWithUnknownFields() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("predictWithUnknownFields_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictWithUnknownFields_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());

    assertEquals("@outputID", output.id());

    Concept concept = (Concept) output.data().get(0);
    assertEquals("@conceptID1", concept.id());

    Model<Prediction> model = output.model();
    assertEquals("@modelID", model.id());
    assertEquals("@modelName", model.name());
    assertEquals("concept", model.modelType().typeExt());
  }
}
