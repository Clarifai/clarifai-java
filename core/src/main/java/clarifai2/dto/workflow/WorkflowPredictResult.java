package clarifai2.dto.workflow;

import clarifai2.internal.grpc.api.WorkflowOuterClass;
import clarifai2.api.BaseClarifaiClient;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class WorkflowPredictResult {

  WorkflowPredictResult() {} // AutoValue instances only

  @NotNull public abstract Workflow workflow();
  @NotNull public abstract List<WorkflowResult> workflowResults();


  public static WorkflowPredictResult deserialize(
      WorkflowOuterClass.PostWorkflowResultsResponse workflowResultsResponse, BaseClarifaiClient client
  ) {
    Workflow workflow = Workflow.deserialize(workflowResultsResponse.getWorkflow());
    List<WorkflowResult> predictions = new ArrayList<>();
    for (WorkflowOuterClass.WorkflowResult workflowResult : workflowResultsResponse.getResultsList()) {
      predictions.add(WorkflowResult.deserialize(workflowResult, client));
    }

    return new AutoValue_WorkflowPredictResult(workflow, predictions);
  }
}
