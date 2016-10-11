package clarifai2.api;

import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelTrainingStatus;
import clarifai2.dto.model.ModelVersion;
import clarifai2.internal.InternalUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * Convenience methods using the Clarifai API that are more complicated than a single network request.
 */
public final class ClarifaiUtil {
  private ClarifaiUtil() { throw new UnsupportedOperationException("No instances"); }

  /**
   * Because training is an async process that could take a long time, you can't count on your model being successfully
   * trained as soon as you get a response from {@link ClarifaiClient#trainModel(String)} or {@link Model#train()}.
   * <p>
   * The {@link ClarifaiRequest} built by this method takes in a model ID
   * and executes {@link ClarifaiClient#trainModel(String)} upon it, and periodically checks the model's status.
   * <p>
   * If this request is invoked with {@link ClarifaiRequest#executeSync()}, it will block and make multiple API calls
   * until the model's status shows as {@link ModelTrainingStatus#TRAINED}.
   * <p>
   * If this request is invoked with {@link ClarifaiRequest#executeAsync(ClarifaiRequest.Callback)}, it will notify the
   * callback when the model's status shows as {@link ModelTrainingStatus#TRAINED}.
   *
   * @param client  the {@link ClarifaiClient} to use to make this request
   * @param modelID the ID of the model to use
   * @return a request that, when executed, will request training upon the given model ID and make requests periodically
   * to check its training status.
   */
  public static ClarifaiRequest<Model<?>> trainAndAwaitCompletion(
      @NotNull final ClarifaiClient client,
      @NotNull final String modelID
  ) {
    final ClarifaiRequest<Model<?>> trainRequest = client.trainModel(modelID);

    return new ClarifaiRequest.Adapter<Model<?>>() {
      int numChecks = 0;

      @NotNull @Override public ClarifaiResponse<Model<?>> executeSync() {
        final ClarifaiResponse<Model<?>> trainingResponse = trainRequest.executeSync();
        if (!trainingResponse.isSuccessful()) {
          return trainingResponse;
        }
        while (true) {
          final ClarifaiResponse<Model<?>> modelResponse = client.getModelByID(modelID).executeSync();
          if (!modelResponse.isSuccessful()) {
            return modelResponse;
          }
          final ModelVersion version = modelResponse.get().modelVersion();
          if (version != null && version.status() == ModelTrainingStatus.TRAINED) {
            return modelResponse;
          }
          sleep();
        }
      }

      @Override public void executeAsync(@Nullable Callback<Model<?>> callback) {
        final ClarifaiResponse<Model<?>> trainingResponse = trainRequest.executeSync();
        if (callback == null || !notifyCallbackIfUnsuccessful(callback, trainingResponse)) {
          return;
        }
        while (true) {
          final ClarifaiResponse<Model<?>> modelResponse = client.getModelByID(modelID).executeSync();
          if (!notifyCallbackIfUnsuccessful(callback, modelResponse)) {
            return;
          }
          final Model<?> model = modelResponse.get();
          final ModelVersion version = model.modelVersion();
          if (version != null && version.status() == ModelTrainingStatus.TRAINED) {
            callback.onClarifaiResponseSuccess(model);
            return;
          }
          sleep();
        }
      }

      private void sleep() {
        InternalUtil.sleep((numChecks < 5) ? 2000 : 5000);
        numChecks++;
      }

    };
  }

  /**
   * @param callback callback to notify
   * @param response the response that you got
   * @return {@code true} if successful, else {@code false}
   */
  private static boolean notifyCallbackIfUnsuccessful(
      @NotNull ClarifaiRequest.Callback<?> callback,
      @NotNull ClarifaiResponse<?> response
  ) {
    if (response.isSuccessful()) {
      return true;
    }
    final ClarifaiStatus status = response.getStatus();
    if (status.networkErrorOccurred()) {
      callback.onClarifaiResponseNetworkError(new IOException(status.errorDetails()));
    } else {
      callback.onClarifaiResponseUnsuccessful(status.statusCode());
    }
    return false;
  }
}
