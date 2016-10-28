package clarifai2.dto.model;

import clarifai2.api.ClarifaiClient;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
  ;

  private final int statusCode;

  ModelTrainingStatus(int statusCode) {
    this.statusCode = statusCode;
  }

  public boolean isError() {
    return 21110 <= statusCode && statusCode <= 21119;
  }

  public boolean isTerminalEvent() {
    return this.isError() || this == TRAINED;
  }

  static class Adapter implements JsonDeserializer<ModelTrainingStatus> {

    final Map<Integer, ModelTrainingStatus> codeToStatus;

    Adapter() {
      final ModelTrainingStatus[] values = ModelTrainingStatus.values();
      this.codeToStatus = new HashMap<>(values.length);
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

    @Override
    public ModelTrainingStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final int statusCode = json.getAsJsonObject().get("code").getAsInt();
      final ModelTrainingStatus model = codeToStatus.get(statusCode);
      if (model == null) {
        throw new IllegalArgumentException(
            "This version of the API client does not recognize the model training status: " + json
        );
      }
      return model;
    }
  }
}
