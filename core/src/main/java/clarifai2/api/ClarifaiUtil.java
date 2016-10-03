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

  public static ClarifaiRequest<Model<?>> trainAndAwaitCompletion(
      @NotNull final ClarifaiClient client,
      @NotNull final String modelID
  ) {
    final ClarifaiRequest<Model<?>> trainRequest = client.trainModel(modelID);
    return new ClarifaiRequest.Adapter<Model<?>>() {
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
          InternalUtil.sleep(2000);
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
          InternalUtil.sleep(2000);
        }
      }

    };
  }

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
