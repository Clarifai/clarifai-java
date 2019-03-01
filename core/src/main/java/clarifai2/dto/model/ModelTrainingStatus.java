package clarifai2.dto.model;

import clarifai2.internal.grpc.api.status.StatusOuterClass;
import clarifai2.api.ClarifaiClient;
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

@JsonAdapter(ModelTrainingStatus.Adapter.class)
public enum ModelTrainingStatus {
  /**
   * This model has been trained.
   */
  TRAINED(21100),
  /**
   * This model is currently being trained by the server.
   */
  TRAINING_IN_PROGRESS(21101),
  /**
   * This model hasn't been trained. Use {@link ClarifaiClient#trainModel(String)} or {@link Model#train()}
   * to train it.
   */
  NOT_YET_TRAINED(21102),
  /**
   * This model is in the queue to be trained by the server.
   */
  TRAINING_QUEUED(21103),
  /**
   * Model training had no data
   */
  MODEL_TRAINING_NO_DATA(21110),
  /**
   * There are no positive examples for this model, so it cannot be trained. Please add at least one positive example
   * for each of the model's concepts before trying to train it.
   */
  NO_POSITIVE_EXAMPLES(21111),
  /**
   * Custom model training was ONE_VS_N but with a single class
   */
  MODEL_TRAINING_ONE_VS_N_SINGLE_CLASS(21112),
  /**
   * Training took longer than the server allows
   */
  MODEL_TRAINING_TIMED_OUT(21113),
  /**
   * Training got error waiting on asset pipeline to finish
   */
  MODEL_TRAINING_WAITING_ERROR(21114),
  /**
   * Training threw an unknown error
   */
  MODEL_TRAINING_UNKNOWN_ERROR(21115),
  /**
   * Training request was unexpectedly redelivered, contact support@clarifai.com if this continues to happen.
   */
  MODEL_TRAINING_UNEXPECTED_REDELIVERY_ERROR(21116),;

  private final int statusCode;

  ModelTrainingStatus(int statusCode) {
    this.statusCode = statusCode;
  }

  static final Map<Integer, ModelTrainingStatus> codeToStatus;

  static {
    final ModelTrainingStatus[] values = ModelTrainingStatus.values();
    codeToStatus = new HashMap<>(values.length);
    for (ModelTrainingStatus modelTrainingStatus : values) {
      if (codeToStatus.containsKey(modelTrainingStatus.statusCode)) {
        throw new IllegalStateException(
            codeToStatus.get(modelTrainingStatus.statusCode) + " and " + modelTrainingStatus
                + " have the same statusCode of " + modelTrainingStatus.statusCode
        );
      }
      codeToStatus.put(modelTrainingStatus.statusCode, modelTrainingStatus);
    }
  }

  public boolean isError() {
    return 21110 <= statusCode && statusCode <= 21119;
  }

  public boolean isTerminalEvent() {
    return this.isError() || this == TRAINED;
  }

  public int statusCode() {
    return statusCode;
  }

  // TODO(Rok) MEDIUM: Expose the description field.

  public static ModelTrainingStatus deserialize(StatusOuterClass.Status status) {
    ModelTrainingStatus model = codeToStatus.get(status.getCodeValue());
    if (model == null) {
      System.err.printf(
          "Warning This version of the API client does not recognize the model training status: %d. Consider upgrading "
              + "the client to the latest version.%n",
          status.getCodeValue()
      );
    }
    return model;
  }

  static class Adapter extends JSONAdapterFactory<ModelTrainingStatus> {
    @Nullable @Override protected Deserializer<ModelTrainingStatus> deserializer() {
      return new Deserializer<ModelTrainingStatus>() {
        @Nullable @Override
        public ModelTrainingStatus deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ModelTrainingStatus> type,
            @NotNull Gson gson
        ) {
          final int statusCode = assertJsonIs(json, JsonObject.class).get("code").getAsInt();
          final ModelTrainingStatus model = codeToStatus.get(statusCode);
          if (model == null) {
            throw new IllegalArgumentException(
                "This version of the API client does not recognize the model training status: " + json
            );
          }
          return model;
        }
      };
    }

    @NotNull @Override protected TypeToken<ModelTrainingStatus> typeToken() {
      return new TypeToken<ModelTrainingStatus>() {};
    }
  }
}
