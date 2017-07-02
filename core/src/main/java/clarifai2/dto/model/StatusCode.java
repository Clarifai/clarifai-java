package clarifai2.dto.model;

public enum StatusCode {
  /**
   * API key has insufficient scope.s
   */
  INSUFFICIENT_SCOPES(11007);

  private final int statusCode;

  StatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  /**
   * Returns the numeric value of the status enum.
   * @return numeric value
   */
  public int getValue() {
    return this.statusCode;
  }
}
