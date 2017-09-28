package clarifai2.dto.model;

import clarifai2.internal.JSONAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static clarifai2.internal.InternalUtil.assertJsonIs;

@JsonAdapter(ModelMetricsStatus.Adapter.class)
public enum ModelMetricsStatus {

  /**
   * Model was successfully evaluated.
   */
  MODEL_EVALUATED(21300),

  /**
   * Model is evaluating.
   */
  MODEL_EVALUATING(21301),

  /**
   * Model is not yet evaluated.
   */
  MODEL_NOT_EVALUATED(21302),

  /**
   * Model is queued for evaluation.
   */
  MODEL_QUEUED_FOR_EVALUATION(21303),

  /**
   * Model evaluation timed out.
   */
  MODEL_EVALUATION_TIMED_OUT(21310),

  /**
   * Model evaluation timed out waiting on inputs to process.
   */
  MODEL_EVALUATION_WAITING_ERROR(21311),

  /**
   * Model evaluation unknown internal error.
   */
  MODEL_EVALUATION_UNKNOWN_ERROR(21312),

  /**
   * Model evaluation failed because there are not enough annotated inputs. Please
   * have at least 2 concepts in your model with 5 labelled inputs each before evaluating.
   */
  MODEL_EVALUATION_NEED_LABELS(21315),;

  private final int statusCode;

  ModelMetricsStatus(int statusCode) {
    this.statusCode = statusCode;
  }

  public boolean isError() {
    return 21310 <= statusCode && statusCode <= 21319;
  }

  static class Adapter extends JSONAdapterFactory<ModelMetricsStatus> {

    static final Map<Integer, ModelMetricsStatus> codeToStatus;

    static {
      final ModelMetricsStatus[] values = ModelMetricsStatus.values();
      codeToStatus = new HashMap<>(values.length);
      for (ModelMetricsStatus modelMetricsStatus : values) {
        if (codeToStatus.containsKey(modelMetricsStatus.statusCode)) {
          throw new IllegalStateException(
              codeToStatus.get(modelMetricsStatus.statusCode) + " and " + modelMetricsStatus
                  + " have the same statusCode of " + modelMetricsStatus.statusCode
          );
        }
        codeToStatus.put(modelMetricsStatus.statusCode, modelMetricsStatus);
      }
    }

    @Nullable @Override protected Deserializer<ModelMetricsStatus> deserializer() {
      return new Deserializer<ModelMetricsStatus>() {
        @Nullable @Override
        public ModelMetricsStatus deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ModelMetricsStatus> type,
            @NotNull Gson gson
        ) {
          final int statusCode = assertJsonIs(json, JsonObject.class).get("code").getAsInt();
          final ModelMetricsStatus model = codeToStatus.get(statusCode);
          if (model == null) {
            throw new IllegalArgumentException(
                "This version of the API client does not recognize the model metrics status: " + json
            );
          }
          return model;
        }
      };
    }

    @NotNull @Override protected TypeToken<ModelMetricsStatus> typeToken() {
      return new TypeToken<ModelMetricsStatus>() {};
    }
  }
}
