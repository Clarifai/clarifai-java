package clarifai2.dto.multimodel;

import clarifai2.dto.prediction.Prediction;
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
@JsonAdapter(WorkflowResult.Adapter.class)
public abstract class WorkflowResult<PREDICTION extends Prediction> {

  WorkflowResult() {} // AutoValue instances only

  @Nullable public abstract Workflow workflow();
  @Nullable public abstract List<MultiModelResult<PREDICTION>> multiModelResults();


  static class Adapter extends JSONAdapterFactory<WorkflowResult> {
    @Nullable @Override protected Deserializer<WorkflowResult> deserializer() {
      return new Deserializer<WorkflowResult>() {
        @Nullable @Override
        public WorkflowResult deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<WorkflowResult> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          Workflow workflow = fromJson(gson, root.get("workflow"), Workflow.class);
          List<MultiModelResult<Prediction>> predictions = fromJson(
              gson, root.get("results"), new TypeToken<List<MultiModelResult<Prediction>>>() {});
          return new AutoValue_WorkflowResult(workflow, predictions);
        }
      };
    }

    @NotNull @Override protected TypeToken<WorkflowResult> typeToken() {
      return new TypeToken<WorkflowResult>() {};
    }
  }
}
