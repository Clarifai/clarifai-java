package clarifai2.api.request;

import clarifai2.internal.grpc.api.V2Grpc;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.ClarifaiStatus;
import clarifai2.exception.ClarifaiException;
import clarifai2.exception.ClarifaiClientClosedException;
import clarifai2.exception.NetworkException;
import clarifai2.grpc.JsonChannel;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonElement;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static clarifai2.internal.InternalUtil.MEDIA_TYPE_JSON;
import static clarifai2.internal.InternalUtil.fromJson;

/**
 * An interface returned by the {@link ClarifaiClient} used to execute an API request.
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


  /**
   * A request and the deserialization that goes along with it, if the request was successful
   *
   * @param <T> the type to deserialize to
   */
  interface DeserializedRequest<T> {
    @NotNull ListenableFuture httpRequestGrpc();
    @NotNull T unmarshalerGrpc(Object returnedObject);
  }


  abstract class Adapter<T> implements ClarifaiRequest<T> {
    @NotNull protected final BaseClarifaiClient client;

    protected Adapter(@NotNull BaseClarifaiClient client) {
      this.client = client;
    }

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
            throw new ClarifaiException("API failure occurred and was not handled. HTTP code: " + errorCode);
          }
          onFailure.onClarifaiResponseUnsuccessful(errorCode);
        }

        @Override public void onClarifaiResponseNetworkError(@NotNull IOException e) {
          if (onNetworkError == null) {
            throw new ClarifaiException("Network error occurred while making an API call. Error: " + e.getMessage());
          }
          onNetworkError.onClarifaiResponseNetworkError(e);
        }
      });
    }
  }


  abstract class Builder<T> extends Adapter<T> {

    @Nullable private final MediaType requestContentType;

    protected Builder(@NotNull BaseClarifaiClient client) {
      this(client, MEDIA_TYPE_JSON);
    }

    protected Builder(@NotNull BaseClarifaiClient client, @Nullable MediaType contentType) {
      super(client);
      requestContentType = contentType;
    }

    @NotNull public V2Grpc.V2FutureStub stub() {
      return V2Grpc.newFutureStub(new JsonChannel(client.clarifaiHttpClient))
          .withOption(JsonChannel.CLARIFAI_METHOD_OPTION, method())
          .withOption(JsonChannel.CLARIFAI_BASE_URL_OPTION, client.baseURL.toString())
          .withOption(JsonChannel.CLARIFAI_SUB_URL_OPTION, subUrl());
    }

    @NotNull @Override public final ClarifaiResponse<T> executeSync() {
      return build().executeSync();
    }

    @Override public final void executeAsync(@Nullable Callback<T> callback) {
      build().executeAsync(callback);
    }

    @NotNull protected ClarifaiRequest<T> build() {
      return new Impl<>(client, request());
    }

    @NotNull protected abstract String method();
    @NotNull protected abstract String subUrl();

    @NotNull protected abstract DeserializedRequest<T> request();

    @NotNull protected final Request getRequest(@NotNull String endpoint) {
      return builder(endpoint).get().build();
    }

    @NotNull protected final Request deleteRequest(@NotNull String endpoint, @NotNull JsonElement deleteBody) {
      return builder(endpoint).delete(toRequestBody(deleteBody)).build();
    }

    @NotNull protected final Request postRequest(@NotNull String endpoint, @NotNull JsonElement postBody) {
      return builder(endpoint).post(toRequestBody(postBody)).build();
    }

    @NotNull protected final Request patchRequest(@NotNull String endpoint, @NotNull JsonElement patchBody) {
      return builder(endpoint).patch(toRequestBody(patchBody)).build();
    }

    @NotNull private Request.Builder builder(@NotNull String endpoint) {
      return new Request.Builder().url(toHTTPUrl(endpoint));
    }

    @NotNull private HttpUrl toHTTPUrl(@NotNull String endpoint) {
      if (endpoint.charAt(0) == '/') {
        endpoint = endpoint.substring(1);
      }
      return client.baseURL.newBuilder().addPathSegments(endpoint).build();
    }

    @NotNull private RequestBody toRequestBody(@NotNull JsonElement json) {
      return RequestBody.create(requestContentType, client.gson.toJson(json));
    }

  }


  class Impl<T> extends Adapter<T> {

    @NotNull private final DeserializedRequest<T> request;

    Impl(
        @NotNull BaseClarifaiClient client,
        @NotNull DeserializedRequest<T> request
    ) {
      super(client);
      this.request = request;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public ClarifaiResponse<T> executeSync() {
      try {
        Object o;
        try {
          ListenableFuture listenableFuture = request.httpRequestGrpc();
          o = listenableFuture.get();
        } catch (IllegalArgumentException|ClarifaiClientClosedException e) {
          throw e;
        } catch (RuntimeException e) {
          throw new NetworkException(e);
        } catch (Exception e) {
          throw new NetworkException(e);
        }

        JsonFormat.Printer printer = JsonFormat.printer();
        String rawJSON = printer.print((MessageOrBuilder) o);
        // TODO(Rok) HIGH: Code is not always 200.
        int code = 200;

        Object statusObj;
        try {
          Method getStatus = o.getClass().getMethod("getStatus");
          statusObj = getStatus.invoke(o);
        } catch (Exception e) {
          return new ClarifaiResponse.Failure<>(ClarifaiStatus.unknown(), code, rawJSON);
        }
        final ClarifaiStatus status = ClarifaiStatus.deserialize(statusObj);

        final boolean successfulHTTPCode = 200 <= code && code < 300;
        if (successfulHTTPCode && (status == null || status.equals(ClarifaiStatus.success()))) {
          return new ClarifaiResponse.Successful<>(
              status,
              code,
              rawJSON,
              request.unmarshalerGrpc(o)
          );
        } else if (successfulHTTPCode && status.equals(ClarifaiStatus.mixedSuccess())) {
          return new ClarifaiResponse.MixedSuccess<>(
              status,
              code,
              rawJSON,
              request.unmarshalerGrpc(o)
          );
        }
        return new ClarifaiResponse.Failure<>(status, code, rawJSON);
      } catch (ClarifaiClientClosedException e) {
        throw e;
      } catch (NetworkException|IOException e) {
        return new ClarifaiResponse.NetworkError<>(ClarifaiStatus.networkError(e));
      }
    }

    @Override public void executeAsync(@Nullable final Callback<T> callback) {
      client.httpClient.dispatcher().executorService().submit(new Callable<Void>() {
        @Override public Void call() throws Exception {
          final ClarifaiResponse<T> response = executeSync();
          if (callback != null) {
            if (response.isSuccessful()) {
              callback.onClarifaiResponseSuccess(response.get());
            } else {
              if (response.getStatus().networkErrorOccurred()) {
                callback.onClarifaiResponseNetworkError(new IOException(response.getStatus().errorDetails()));
              } else {
                callback.onClarifaiResponseUnsuccessful(response.responseCode());
              }
            }
          }
          return null;
        }
      });
    }
  }
}
