package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.internal.grpc.api.WorkflowOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.workflow.WorkflowPredictResult;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class WorkflowPredictRequest
    extends ClarifaiRequest.Builder<WorkflowPredictResult> {

  @NotNull private final String workflowID;
  @NotNull private final List<ClarifaiInput> inputData = new ArrayList<>();

  @Nullable private Double minValue = null;
  @Nullable private Integer maxConcepts = null;

  public WorkflowPredictRequest(@NotNull final BaseClarifaiClient client, @NotNull String workflowID) {
    super(client);
    this.workflowID = workflowID;
  }

  @NotNull public WorkflowPredictRequest withInputs(@NotNull ClarifaiInput... inputs) {
    return withInputs(Arrays.asList(inputs));
  }

  @NotNull public WorkflowPredictRequest withInputs(@NotNull Collection<ClarifaiInput> inputs) {
    this.inputData.addAll(inputs);
    return this;
  }

  @NotNull public final WorkflowPredictRequest withMinValue(@Nullable Double minValue) {
    this.minValue = minValue;
    return this;
  }

  @NotNull public final WorkflowPredictRequest withMaxConcepts(@Nullable Integer maxConcepts) {
    this.maxConcepts = maxConcepts;
    return this;
  }

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl() {
    return "/v2/workflows/" + workflowID + "/results";
  }

  @NotNull @Override protected DeserializedRequest<WorkflowPredictResult> request() {
    return new DeserializedRequest<WorkflowPredictResult>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        List<InputOuterClass.Input> inputsGrpc = new ArrayList<>();
        for (ClarifaiInput input: inputData) {
          inputsGrpc.add(input.serialize());
        }

        boolean anyOutputConfig = false;
        ModelOuterClass.OutputConfig.Builder outputConfigBuilder = ModelOuterClass.OutputConfig.newBuilder();
        if (minValue != null) {
          outputConfigBuilder.setMinValue(minValue.floatValue());
          anyOutputConfig = true;
        }
        if (maxConcepts != null) {
          outputConfigBuilder.setMaxConcepts(maxConcepts);
          anyOutputConfig = true;
        }

        WorkflowOuterClass.PostWorkflowResultsRequest.Builder requestBuilder =
            WorkflowOuterClass.PostWorkflowResultsRequest.newBuilder()
                .addAllInputs(inputsGrpc);

        if (anyOutputConfig) {
          requestBuilder.setOutputConfig(outputConfigBuilder);
        }

        return stub().postWorkflowResults(requestBuilder.build());
      }

      @NotNull @Override public WorkflowPredictResult unmarshalerGrpc(Object returnedObject) {
        WorkflowOuterClass.PostWorkflowResultsResponse workflowResultsResponse =
            (WorkflowOuterClass.PostWorkflowResultsResponse) returnedObject;
        return WorkflowPredictResult.deserialize(workflowResultsResponse, client);
      }
    };
  }
}
