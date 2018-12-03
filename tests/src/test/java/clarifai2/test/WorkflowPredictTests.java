package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.WorkflowPredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Prediction;
import clarifai2.dto.workflow.WorkflowPredictResult;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WorkflowPredictTests extends BaseClarifaiAPITest {

  @NotNull static final private String FOOD_AND_GENERAL_WORKFLOW_ID = "food-and-general";

  @Test public void shouldReturnCorrectModels() {
    WorkflowPredictRequest request = makeWorkflowPredictRequestForFoodAndGeneral();
    ClarifaiResponse<WorkflowPredictResult> response = request.executeSync();
    assertEquals("food-and-general", response.get().workflow().id());
    List<ClarifaiOutput<Prediction>> modelOutputs = response.get().workflowResults().get(0).predictions();
    assertTrue(modelOutputs.stream().anyMatch(
        modelOutput -> modelOutput.model().name().toLowerCase().contains("food")
    ));
    assertTrue(modelOutputs.stream().anyMatch(
        modelOutput -> modelOutput.model().name().toLowerCase().contains("general")
    ));
  }

  @Test public void shouldApplyMinValue() {
    WorkflowPredictRequest request = makeWorkflowPredictRequestForFoodAndGeneral()
        .withMinValue(0.9);
    ClarifaiResponse<WorkflowPredictResult> response = request.executeSync();
    List<ClarifaiOutput<Prediction>> modelOutputs = response.get().workflowResults().get(0).predictions();
    // Make sure no model returns concepts with value less than minValue.
    assertTrue(modelOutputs.get(0).data().stream()
        .allMatch(prediction -> prediction.asConcept().value() >= 0.9));
    assertTrue(modelOutputs.get(1).data().stream()
        .allMatch(prediction -> prediction.asConcept().value() >= 0.9));
  }

  @Test public void shouldApplyMaxConcepts() {
    WorkflowPredictRequest request = makeWorkflowPredictRequestForFoodAndGeneral()
        .withMaxConcepts(3);
    ClarifaiResponse<WorkflowPredictResult> response = request.executeSync();
    List<ClarifaiOutput<Prediction>> modelOutputs = response.get().workflowResults().get(0).predictions();
    // Make sure no model returns more than maxConcepts number of concepts.
    assertTrue(modelOutputs.get(0).data().size() <= 3);
    assertTrue(modelOutputs.get(1).data().size() <= 3);
  }

  @Test public void shouldApplyMinValueAndMaxConcepts() {
    WorkflowPredictRequest request = makeWorkflowPredictRequestForFoodAndGeneral()
        .withMinValue(0.9)
        .withMaxConcepts(3);
    ClarifaiResponse<WorkflowPredictResult> response = request.executeSync();
    List<ClarifaiOutput<Prediction>> modelOutputs = response.get().workflowResults().get(0).predictions();
    assertTrue(modelOutputs.get(0).data().stream()
        .allMatch(prediction -> prediction.asConcept().value() >= 0.9));
    assertTrue(modelOutputs.get(1).data().stream()
        .allMatch(prediction -> prediction.asConcept().value() >= 0.9));
    assertTrue(modelOutputs.get(0).data().size() <= 3);
    assertTrue(modelOutputs.get(1).data().size() <= 3);
  }

  @Test public void shouldWorkflowPredictFileImage() {
    ClarifaiInput clarifaiInput = ClarifaiInput.forImage(METRO_NORTH_IMAGE_FILE);

    final WorkflowPredictRequest wf1 = client.workflowPredict("food-and-general");
    ClarifaiResponse<WorkflowPredictResult> response = wf1
            .withInputs(clarifaiInput)
            .executeSync();
    assertTrue(response.isSuccessful());
  }

  @NotNull private WorkflowPredictRequest makeWorkflowPredictRequestForFoodAndGeneral() {
    return client.workflowPredict(FOOD_AND_GENERAL_WORKFLOW_ID)
        .withInputs(
            ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL));
  }
}
