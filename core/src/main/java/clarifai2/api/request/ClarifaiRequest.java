package clarifai2.api.request;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.exception.ClarifaiException;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import static clarifai2.internal.InternalUtil.MEDIA_TYPE_JSON;

/**
 * An interface returned by the {@link ClarifaiClient} used to execute an API request.
 * <p>
 * API requests can be executed synchronously using {{@link #executeSync()}}, or can be queued up
 * using {{@link #executeAsync(Callback)}}
 *
 * @param <RESULT> the data-type returned by a successful API call
 */
public interface ClarifaiRequest<RESULT> {
  /**
   * Blocks this thread until a response is received, successful or not.
   *
   * @return the response retrieved by invoking this request
   * @throws ClarifaiException if an error occurs while executing
   */
  @NotNull ClarifaiResponse<RESULT> executeSync();

  /**
   * Executes the given request in a background process, and then returns the result to this one
   * via the callback.
   *
   * @param callback the object that will be notified with the results create the request, successful or not.
   */
  void executeAsync(@Nullable Callback<RESULT> callback);

  /**
   * Executes the given request in a background process, and then returns the result to this one via the onSuccess
   * callback. If an API failure or a network error occurs, a {@link ClarifaiException} will be thrown.
   *
   * @param onSuccess the callback to be notified with the results of the API call if it was successful
   * @throws ClarifaiException if the request is not successful
   */
  void executeAsync(@Nullable OnSuccess<RESULT> onSuccess) throws ClarifaiException;

  /**
   * Executes the given request in a background process, and then returns the result to this one via the onSuccess
   * callback if it was successful, or via the onFailure callback if it was unsuccessful. If a network error occurs,
   * a {@link ClarifaiException} will be thrown.
   *
   * @param onSuccess the callback to be notified with the results of the API call if it was successful
   * @param onFailure the callback to be notified with the results of the API call if it was not successful
   * @throws ClarifaiException if the request is not successful due to a network error
   */
  void executeAsync(@Nullable OnSuccess<RESULT> onSuccess, @Nullable OnFailure onFailure);

  /**
   * Executes the given request in a background process, and then returns the result to this one via onSuccess,
   * onFailure, or onNetworkError based on the response.
   *
   * @param onSuccess      the callback that is notified if the API call was successful
   * @param onFailure      the callback that is notified if the API call is not successful
   * @param onNetworkError the callback that is notified if there is a network error while making this API call
   */
  void executeAsync(
      @Nullable OnSuccess<RESULT> onSuccess,
      @Nullable OnFailure onFailure,
      @Nullable OnNetworkError onNetworkError
  );

  interface OnSuccess<RESULT> {
    void onClarifaiResponseSuccess(@NotNull RESULT result);
  }


  interface OnFailure {
    void onClarifaiResponseUnsuccessful(int errorCode);
  }


  interface OnNetworkError {
    void onClarifaiResponseNetworkError(@NotNull IOException e);
  }


  /**
   * One of the three methods in this callback is executed when
   * {@link ClarifaiRequest#executeAsync(Callback)} is called on a {@link ClarifaiRequest}
   *
   * @param <RESULT> The type create data that will be returned if this API call was successful
   */
  interface Callback<RESULT> {
    /**
     * Invoked when the API call was successful.
     *
     * @param result The result create the API call, which was successful.
     */
    void onClarifaiResponseSuccess(@NotNull RESULT result);

    /**
     * Invoked when the API call reached the server, but a 4xx/5xx error was returned.
     *
     * @param errorCode The (unsuccessful) error code
     */
    void onClarifaiResponseUnsuccessful(int errorCode);

    /**
     * Invoked when the request did not successfully reach the server
     *
     * @param e The IOException associated with this connectivity error
     */
    void onClarifaiResponseNetworkError(@NotNull IOException e);
  }

  public abstract class Adapter<T> implements ClarifaiRequest<T> {

    @Override public final void executeAsync(@Nullable OnSuccess<T> onSuccess) throws ClarifaiException {
      executeAsync(onSuccess, null);
    }

    @Override
    public final void executeAsync(@Nullable OnSuccess<T> onSuccess,
        @Nullable OnFailure onFailure) {
      executeAsync(onSuccess, onFailure, null);
    }

    @Override
    public final void executeAsync(@Nullable final OnSuccess<T> onSuccess,
        @Nullable final OnFailure onFailure,
        @Nullable final OnNetworkError onNetworkError) {
      executeAsync(new Callback<T>() {
        @Override public void onClarifaiResponseSuccess(@NotNull T t) {
          if (onSuccess != null) {
            onSuccess.onClarifaiResponseSuccess(t);
          }
        }

        @Override public void onClarifaiResponseUnsuccessful(int errorCode) {
          if (onFailure == null) {
            throw new ClarifaiException(
                "Unsuccessful response from Clarifai API was not handled. Error code: " + errorCode
            );
          }
          onFailure.onClarifaiResponseUnsuccessful(errorCode);
        }

        @Override public void onClarifaiResponseNetworkError(@NotNull IOException e) {
          if (onNetworkError == null) {
            throw new ClarifaiException("Network error while contacting Clarifai API was not handled.", e);
          } else {
            onNetworkError.onClarifaiResponseNetworkError(e);
          }
        }
      });
    }
  }

  abstract class Builder<T> extends ClarifaiRequest.Adapter<T> {

    @NotNull protected final Gson gson;
    @NotNull protected final OkHttpClient client;
    @NotNull protected final HttpUrl baseURL;

    protected Builder(@NotNull BaseClarifaiClient client) {
      this.gson = client.gson;
      this.client = client.client;
      this.baseURL = client.baseURL;
    }

    @NotNull public final ClarifaiRequest<T> build() {
      return new ClarifaiRequestImpl<>(gson, client, buildRequest(), unmarshaler());
    }

    @NotNull protected abstract JSONUnmarshaler<T> unmarshaler();

    @NotNull protected abstract Request buildRequest();

    @NotNull @Override public final ClarifaiResponse<T> executeSync() {
      return build().executeSync();
    }

    @Override public final void executeAsync(@Nullable Callback<T> callback) {
      build().executeAsync(callback);
    }

    @NotNull protected final RequestBody toRequestBody(@NotNull JsonElement json) {
      return RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(json));
    }

    @NotNull protected final HttpUrl buildURL(@NotNull String path) {
      if (path.charAt(0) == '/') {
        path = path.substring(1);
      }
      return baseURL.newBuilder().addPathSegments(path).build();
    }
  }
}
