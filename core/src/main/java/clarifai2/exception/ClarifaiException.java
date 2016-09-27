package clarifai2.exception;

public final class ClarifaiException extends RuntimeException {

  public ClarifaiException(Throwable cause) {
    super(cause);
  }

  public ClarifaiException(String message) {
    super(message);
  }

  public ClarifaiException(String message, Throwable cause) {
    super(message, cause);
  }
}
