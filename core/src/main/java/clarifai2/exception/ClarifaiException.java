package clarifai2.exception;

public final class ClarifaiException extends RuntimeException {

  public ClarifaiException(final Throwable cause) {
    super(cause);
  }

  public ClarifaiException(final String message) {
    super(message);
  }

  public ClarifaiException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
