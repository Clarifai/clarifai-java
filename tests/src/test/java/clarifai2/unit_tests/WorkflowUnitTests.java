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
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import clarifai2.dto.search.SearchInputsResult;
import clarifai2.dto.workflow.Workflow;
import clarifai2.dto.workflow.WorkflowPredictResult;
import clarifai2.dto.workflow.WorkflowResult;
import clarifai2.grpc.FkClarifaiHttpClient;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorkflowUnitTests extends BaseUnitTest {

  private String TINY_PNG_IMAGE_BASE64 =
      "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z/C/HgAGgwJ/lK3Q6wAAAABJRU5ErkJggg==";

  @Test public void workflowPredict() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("workflowPredict_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<WorkflowPredictResult> response = client.workflowPredict("@workflowID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/workflows/@workflowID/results"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("workflowPredict_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    WorkflowPredictResult workflowPredictResult = response.get();
    assertEquals("@workflowID", workflowPredictResult.workflow().id());
    assertEquals("@appID", workflowPredictResult.workflow().appId());

    WorkflowResult workflowResult = workflowPredictResult.workflowResults().get(0);
    assertEquals("@inputID", workflowResult.input().id());

    ClarifaiOutput<Prediction> output1 = workflowResult.predictions().get(0);
    assertEquals("@outputID1", output1.id());
    assertEquals("@conceptID11", ((Concept) output1.data().get(0)).id());

    ClarifaiOutput<Prediction> output2 = workflowResult.predictions().get(1);
    assertEquals("@outputID2", output2.id());
    assertEquals("@conceptID21", ((Concept) output2.data().get(0)).id());
    assertEquals("@conceptID22", ((Concept) output2.data().get(1)).id());
  }

  @Test public void workflowBatchPredict() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("workflowBatchPredict_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<WorkflowPredictResult> response = client.workflowPredict("@workflowID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url1"))
        .withInputs(ClarifaiInput.forImage("https://some-image-url2"))
        .withMinValue(0.98)
        .withMaxConcepts(3)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/workflows/@workflowID/results"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("workflowBatchPredict_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    WorkflowPredictResult workflowPredictResult = response.get();
    assertEquals("@workflowID", workflowPredictResult.workflow().id());
    assertEquals("@appID", workflowPredictResult.workflow().appId());

    WorkflowResult workflowResult1 = workflowPredictResult.workflowResults().get(0);
    assertEquals("@inputID1", workflowResult1.input().id());

    ClarifaiOutput<Prediction> output1 = workflowResult1.predictions().get(0);
    assertEquals("@outputID11", output1.id());

    ClarifaiOutput<Prediction> output2 = workflowResult1.predictions().get(1);
    assertEquals("@outputID12", output2.id());
    assertEquals("@conceptID11", ((Concept) output2.data().get(0)).id());
    assertEquals("@conceptID12", ((Concept) output2.data().get(1)).id());

    WorkflowResult workflowResult2 = workflowPredictResult.workflowResults().get(1);
    assertEquals("@inputID2", workflowResult2.input().id());

    ClarifaiOutput<Prediction> output3 = workflowResult2.predictions().get(0);
    assertEquals("@outputID21", output3.id());
    assertEquals("@conceptID21", ((Concept) output3.data().get(0)).id());

    ClarifaiOutput<Prediction> output4 = workflowResult2.predictions().get(1);
    assertEquals("@outputID22", output4.id());
    assertEquals("@conceptID31", ((Concept) output4.data().get(0)).id());
    assertEquals("@conceptID32", ((Concept) output4.data().get(1)).id());
  }

  @Test public void workflowPredictWithBase64() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("workflowPredictWithBase64_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<WorkflowPredictResult> response = client.workflowPredict("@workflowID")
        .withInputs(ClarifaiInput.forImage(Base64.getDecoder().decode(TINY_PNG_IMAGE_BASE64)))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/workflows/@workflowID/results"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("workflowPredictWithBase64_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    WorkflowPredictResult workflowPredictResult = response.get();
    assertEquals("@workflowID", workflowPredictResult.workflow().id());
    assertEquals("@appID", workflowPredictResult.workflow().appId());

    WorkflowResult workflowResult = workflowPredictResult.workflowResults().get(0);
    assertEquals("@inputID", workflowResult.input().id());

    ClarifaiOutput<Prediction> output1 = workflowResult.predictions().get(0);
    assertEquals("@outputID1", output1.id());
    assertEquals("@conceptID11", ((Concept) output1.data().get(0)).id());

    ClarifaiOutput<Prediction> output2 = workflowResult.predictions().get(1);
    assertEquals("@outputID2", output2.id());
    assertEquals("@conceptID21", ((Concept) output2.data().get(0)).id());
    assertEquals("@conceptID22", ((Concept) output2.data().get(1)).id());
  }

}
