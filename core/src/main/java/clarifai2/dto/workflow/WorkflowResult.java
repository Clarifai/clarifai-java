package clarifai2.dto.workflow;

import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
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
public abstract class WorkflowResult {

  WorkflowResult() {} // AutoValue instances only

  @Nullable public abstract ClarifaiStatus status();
  @Nullable public abstract ClarifaiInput input();
  @Nullable public abstract List<ClarifaiOutput<Prediction>> predictions();


  static class Adapter extends JSONAdapterFactory<WorkflowResult> {
    @Nullable @Override protected Deserializer<WorkflowResult> deserializer() {
      return new Deserializer<WorkflowResult>() {
        @Nullable @Override
        public WorkflowResult deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<WorkflowResult> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          ClarifaiStatus status = fromJson(gson, root.get("status"), ClarifaiStatus.class);
          ClarifaiInput input = fromJson(gson, root.get("input"), ClarifaiInput.class);
          List<ClarifaiOutput<Prediction>> predictions =
              fromJson(gson, root.get("outputs"), new TypeToken<List<ClarifaiOutput<Prediction>>>() {});
          return new AutoValue_WorkflowResult(status, input, predictions);
        }
      };
    }

    @NotNull @Override protected TypeToken<WorkflowResult> typeToken() {
      return new TypeToken<WorkflowResult>() {};
    }
  }
}
