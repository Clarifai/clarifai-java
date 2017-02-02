package clarifai2.api;

import clarifai2.Func0;
import clarifai2.Func1;
import clarifai2.dto.ClarifaiStatus;
import clarifai2.exception.ClarifaiException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;

public abstract class ClarifaiResponse<T> {

  public static final class Successful<T> extends ClarifaiResponse<T> {

    @NotNull private final String rawBody;
    @NotNull private final T deserialized;

    public Successful(
        @NotNull ClarifaiStatus status,
        int httpCode,
        @NotNull String rawBody,
        @NotNull T deserialized
    ) {
      super(status, httpCode);
      this.deserialized = deserialized;
      this.rawBody = rawBody;
    }

    @NotNull @Override public String rawBody() {
      return rawBody;
    }

    @NotNull @Override public <R> ClarifaiResponse<R> map(@NotNull Func1<T, R> mapper) {
      return new Successful<>(status, httpCode, rawBody, mapper.call(deserialized));
    }

    @Nullable @Override public T getOrNull() {
      return deserialized;
    }

    @Override public boolean isSuccessful() {
      return true;
    }

    @Override public boolean isMixedSuccess() {
      return false;
    }
  }

  public static final class Failure<T> extends ClarifaiResponse<T> {

    @NotNull private String rawBody;

    public Failure(@NotNull ClarifaiStatus status, int httpCode, @NotNull String rawBody) {
      super(status, httpCode);
      this.rawBody = rawBody;
    }

    @NotNull @Override public String rawBody() {
      return rawBody;
    }

    @NotNull @Override public <R> ClarifaiResponse<R> map(@NotNull Func1<T, R> mapper) {
      return new Failure<>(status, httpCode, rawBody);
    }

    @Nullable @Override public T getOrNull() {
      return null;
    }

    @Override public boolean isSuccessful() {
      return false;
    }

    @Override public boolean isMixedSuccess() {
      return false;
    }
  }


  public static final class NetworkError<T> extends ClarifaiResponse<T> {

    public NetworkError(@NotNull ClarifaiStatus status) {
      super(status, 0);
    }

    @NotNull @Override public <R> ClarifaiResponse<R> map(@NotNull Func1<T, R> mapper) {
      return new NetworkError<>(status);
    }

    @NotNull @Override public String rawBody() throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Cannot get rawBody() on a ClarifaiResponse that ended in network error");
    }

    @Nullable @Override public T getOrNull() {
      return null;
    }

    @Override public boolean isSuccessful() {
      return false;
    }

    @Override public boolean isMixedSuccess() {
      return false;
    }
  }


  @NotNull final ClarifaiStatus status;
  final int httpCode;

  protected ClarifaiResponse(@NotNull ClarifaiStatus status, int httpCode) {
    this.status = status;
    this.httpCode = httpCode;
  }

  /**
   * @return The value returned by this HTTP request, or throws a {@link NoSuchElementException}
   * if there is no value. You likely want to guard any invocations create this method with a check against
   * {@link #isSuccessful()} to ensure that an exception won't be thrown.
   * @throws NoSuchElementException if {@link #isSuccessful()} is false.
   */
  @NotNull
  public final T get() {
    final T value = getOrNull();
    if (value == null) {
      throw new NoSuchElementException("This API call was not successful. Details about this error: " + getStatus());
    }
    return value;
  }

  /**
   * @param ifResponseWasNull The default value to return if we got no value from the HTTP request
   * @return The HTTP response value if it was valid, otherwise {@code ifResponseWasNull}
   */
  @NotNull
  public final T getOr(@NotNull T ifResponseWasNull) {
    final T value = getOrNull();
    return value != null ? value : ifResponseWasNull;
  }

  /**
   * @param supplier the function to invoke and return a value from if the value from the HTTP request was null
   * @return The HTTP response value if it was valid, otherwise the return value of {@code supplier.call()}
   */
  @NotNull public final T getOr(@NotNull Func0<T> supplier) {
    final T value = getOrNull();
    return value != null ? value : supplier.call();
  }

  /**
   * @param mapper the mapping function to apply to the HTTP response (if it was non-null)
   * @param <R>    the type of the value returned from the mapper
   * @return a {@link ClarifaiResponse} that represents the object represented by {@code this} object, but with
   * the mapping function applied.
   */
  @NotNull public abstract <R> ClarifaiResponse<R> map(@NotNull Func1<T, R> mapper);

  /**
   * @return The HTTP response value if it was present, otherwise {@code null}
   */
  @Nullable
  public abstract T getOrNull();

  /**
   * @param throwable The throwable to throw if there was no valid HTTP response
   * @return The HTTP response value
   * @throws ClarifaiException wraps your provided throwable, if no valid value returned via HTTP
   */
  @NotNull
  public T getOrThrow(@NotNull Throwable throwable) throws ClarifaiException {
    final T value = getOrNull();
    if (value == null) {
      throw new ClarifaiException("No value present", throwable);
    }
    return value;
  }

  /**
   * @return information about the status of this API call, or {@code null} if the API could not return a value
   * (usually due to network connectivity errors).
   */
  @NotNull public final ClarifaiStatus getStatus() {
    return status;
  }

  /**
   * @return true if you can receive a valid value from {@link #get()} or related methods.
   */
  public abstract boolean isSuccessful();

  /**
   * @return true if there are valid and invalid values from {@link #get()} or related methods.
   */
  public abstract boolean isMixedSuccess();

  /**
   * @return The HTTP response code
   */
  public final int responseCode() {
    return httpCode;
  }

  @NotNull public abstract String rawBody() throws UnsupportedOperationException;
}
