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
   * This model hasn't been trained. Use {@link ClarifaiClient#trainModel(String)} or {@link Model#train()}
   * to train it.
   */
  NOT_YET_TRAINED(21102),
  /**
   * This model is in the queue to be trained by the server.
   */
  TRAINING_QUEUED(21103),
  /**
   * This model is currently being trained by the server.
   */
  TRAINING_IN_PROGRESS(21101),
  /**
   * This model has been trained.
   */
  TRAINED(21100),
  /**
   * There are no positive examples for this model, so it cannot be trained. Please add at least one positive example
   * for each of the model's concepts before trying to train it.
   */
  NO_POSITIVE_EXAMPLES(21111),;

  private final int statusCode;

  ModelTrainingStatus(int statusCode) {
    this.statusCode = statusCode;
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
