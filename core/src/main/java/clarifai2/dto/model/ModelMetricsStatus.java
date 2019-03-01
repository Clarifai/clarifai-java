package clarifai2.dto.model;

import clarifai2.internal.grpc.api.status.StatusOuterClass;

import java.util.HashMap;
import java.util.Map;

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
  MODEL_EVALUATION_NEED_LABELS(21315),

  /**
   * Model evaluation failed because there are not enough labeled inputs. Please ensure
   * there are at least 5 labeled inputs per concept before evaluating.
   */
  MODEL_EVALUATION_NEED_LABELED_INPUTS(21316),;

  private final int statusCode;

  ModelMetricsStatus(int statusCode) {
    this.statusCode = statusCode;
  }

  public int statusCode() {
    return statusCode;
  }

  // TODO(Rok) MEDIUM: Expose the description field.

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

  public static ModelMetricsStatus deserialize(StatusOuterClass.Status status) {
    final ModelMetricsStatus modelMetricsStatus = codeToStatus.get(status.getCodeValue());
    if (modelMetricsStatus == null) {
      System.err.printf(
          "Warning: This version of the API client does not recognize the model metrics status: %d. Consider upgrading "
              + "the client to the latest version.%n",
          status.getCodeValue()
      );
    }
    return modelMetricsStatus;
  }

  public boolean isError() {
    return 21310 <= statusCode && statusCode <= 21319;
  }
}
