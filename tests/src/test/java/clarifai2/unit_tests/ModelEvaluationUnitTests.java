package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.model.ModelVersion;
import clarifai2.grpc.FkClarifaiHttpClient;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModelEvaluationUnitTests extends BaseUnitTest {

  @Test public void runModelEvaluation() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("runModelEvaluation_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    @NotNull ClarifaiResponse<ModelVersion> response = client.runModelEvaluation("@modelID", "@modelVersionID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/versions/@modelVersionID/metrics"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals("{}", httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ModelVersion modelVersion = response.get();

    assertEquals("@modelVersionID", modelVersion.id());
    assertEquals(21100, modelVersion.status().statusCode());
    assertEquals(21303, modelVersion.modelMetricsStatus().statusCode());
    assertEquals(2, modelVersion.activeConceptCount());
    assertEquals(30, modelVersion.totalInputCount());
  }
}
