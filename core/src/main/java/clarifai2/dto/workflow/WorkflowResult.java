package clarifai2.dto.workflow;

import clarifai2.internal.grpc.api.OutputOuterClass;
import clarifai2.internal.grpc.api.WorkflowOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Prediction;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class WorkflowResult {

  WorkflowResult() {} // AutoValue instances only

  @NotNull public abstract ClarifaiStatus status();
  @NotNull public abstract ClarifaiInput input();
  @NotNull public abstract List<ClarifaiOutput<Prediction>> predictions();


  public static WorkflowResult deserialize(
      WorkflowOuterClass.WorkflowResult workflowResult, BaseClarifaiClient client
  ) {
    List<ClarifaiOutput<Prediction>> predictions = new ArrayList<>();
    for (OutputOuterClass.Output output : workflowResult.getOutputsList()) {
      predictions.add(ClarifaiOutput.deserialize(output, client));
    }
    return new AutoValue_WorkflowResult(
        ClarifaiStatus.deserialize(workflowResult.getStatus()),
        ClarifaiInput.deserialize(workflowResult.getInput()),
        predictions
    );
  }
}
