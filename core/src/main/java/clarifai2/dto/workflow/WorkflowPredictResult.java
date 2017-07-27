package clarifai2.dto.workflow;

import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(WorkflowPredictResult.Adapter.class)
public abstract class WorkflowPredictResult {

  WorkflowPredictResult() {} // AutoValue instances only

  @Nullable public abstract Workflow workflow();
  @Nullable public abstract List<WorkflowResult> workflowResults();


  static class Adapter extends JSONAdapterFactory<WorkflowPredictResult> {
    @Nullable @Override protected Deserializer<WorkflowPredictResult> deserializer() {
      return new Deserializer<WorkflowPredictResult>() {
        @Nullable @Override
        public WorkflowPredictResult deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<WorkflowPredictResult> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          Workflow workflow = fromJson(gson, root.get("workflow"), Workflow.class);
          List<WorkflowResult> predictions = fromJson(
              gson, root.get("results"), new TypeToken<List<WorkflowResult>>() {});
          return new AutoValue_WorkflowPredictResult(workflow, predictions);
        }
      };
    }

    @NotNull @Override protected TypeToken<WorkflowPredictResult> typeToken() {
      return new TypeToken<WorkflowPredictResult>() {};
    }
  }
}
