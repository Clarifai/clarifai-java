package clarifai2.api;

import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelTrainingStatus;
import clarifai2.dto.model.ModelVersion;
import clarifai2.exception.ClarifaiException;
import org.jetbrains.annotations.NotNull;

/**
 * Convenience methods using the Clarifai API that are more complicated than a single network request.
 */
public final class ClarifaiUtil {
  private ClarifaiUtil() { throw new UnsupportedOperationException("No instances"); }

  /**
   * Trains the model with the given ID, blocking until the model has finished being trained
   *
   * @param client  a {@link ClarifaiClient} instance
   * @param modelID the ID of the model to train
   * @return {@code true} if successful, {@code false} if not
   */
  public static boolean trainAndAwaitCompletion(@NotNull ClarifaiClient client, @NotNull String modelID) {
    final ClarifaiResponse<Model<?>> trainingResult = client.trainModel(modelID).executeSync();
    if (!trainingResult.isSuccessful()) {
      final String errorDetails = trainingResult.getStatus().errorDetails();
      throw new ClarifaiException(errorDetails);
    }
    while (true) {
      final ClarifaiResponse<Model<?>> modelResponse = client.getModelByID(modelID).executeSync();
      if (!modelResponse.isSuccessful()) {
        return false;
      }
      final ModelVersion version = modelResponse.get().modelVersion();
      if (version == null) {
        return false;
      }
      if (version.status() == ModelTrainingStatus.TRAINED) {
        return true;
      }
      try {
        Thread.sleep(2_000);
      } catch (InterruptedException ignored) {
        return false;
      }
    }
  }
}
