package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.Action;
import clarifai2.dto.ClarifaiStatus;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class InvalidResponseUnitTests extends BaseUnitTest {

  @Test public void invalidJson() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("invalidJson_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.getModelByID("")
        .executeSync();

    assertFalse(response.isSuccessful());
  }

  @Test public void predictWithInvalidURL() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predictWithInvalidURL_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    @NotNull ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-invalid-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictWithInvalidURL_request.json"), httpClient.requestBody());

    assertFalse(response.isSuccessful());

    assertEquals(10020, response.getStatus().statusCode());
    assertEquals("Failure", response.getStatus().description());

    // TODO(Rok) HIGH: It should be possible to get the output and inspect the status despite of the error response.
    //                 The lines below should be valid. Fix implementation.
//    ClarifaiOutput<Prediction> output = response.get().get(0);
//
//    assertEquals(30002, output.status().statusCode());
//    assertEquals("Download failed; check URL", output.status().description());
//    assertEquals("404 Client Error: Not Found for url: https://some-invalid-url", output.status().errorDetails());
  }

  @Test public void batchPredictWithOneInvalidURL() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("batchPredictWithOneInvalidURL_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    @NotNull ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(
            ClarifaiInput.forImage("https://some-valid-url"),
            ClarifaiInput.forImage("https://some-invalid-url")
        )
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("batchPredictWithOneInvalidURL_request.json"), httpClient.requestBody());

    assertFalse(response.isSuccessful());

    assertEquals(10010, response.getStatus().statusCode());
    assertEquals("Mixed Success", response.getStatus().description());

    ClarifaiOutput<Prediction> output1 = response.get().get(0);

    assertEquals(10000, output1.status().statusCode());
    assertEquals("Ok", output1.status().description());
    assertEquals("@inputID1", output1.input().id());
    assertEquals("https://some-valid-url", ((ClarifaiURLImage) output1.input().inputValue()).url().toString());

    ClarifaiOutput<Prediction> output2 = response.get().get(1);

    assertEquals(30002, output2.status().statusCode());
    assertEquals("Download failed; check URL", output2.status().description());
    assertEquals("404 Client Error: Not Found for url: @invalidURL", output2.status().errorDetails());
    assertEquals("@inputID2", output2.input().id());
    assertEquals("https://some-invalid-url", ((ClarifaiURLImage) output2.input().inputValue()).url().toString());
  }
}
