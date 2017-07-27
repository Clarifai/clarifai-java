package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.workflow.WorkflowPredictResult;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
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

  @NotNull @Override protected DeserializedRequest<WorkflowPredictResult> request() {
    return new DeserializedRequest<WorkflowPredictResult>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder()
            .add("inputs", new JSONArrayBuilder()
                .addAll(inputData, new Func1<ClarifaiInput, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull ClarifaiInput model) {
                    return client.gson.toJsonTree(model);
                  }
                }));
        if (minValue != null || maxConcepts != null) {
          bodyBuilder.add("output_config", new JSONObjectBuilder()
              .addIfNotNull("min_value", minValue)
              .addIfNotNull("max_concepts", maxConcepts));
        }
        final JsonObject body = bodyBuilder.build();
        return postRequest("/v2/workflows/" + workflowID + "/results", body);
      }

      @NotNull @Override public JSONUnmarshaler<WorkflowPredictResult> unmarshaler() {
        return new JSONUnmarshaler<WorkflowPredictResult>() {
          @NotNull @Override
          public WorkflowPredictResult fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            JsonObject rootObject = json.getAsJsonObject();
            WorkflowPredictResult workflowResults = InternalUtil.fromJson(
                gson, rootObject, new TypeToken<WorkflowPredictResult>() {});
            return workflowResults;
          }
        };
      }
    };
  }
}
