package clarifai2.api;

import org.jetbrains.annotations.NotNull;

public final class ClarifaiToken {
  @NotNull private final String accessToken;
  private final long expiresAt;

  ClarifaiToken(@NotNull String accessToken, long expiresInSeconds) {
    this.accessToken = accessToken;
    this.expiresAt = System.currentTimeMillis() + (expiresInSeconds * 1000);
  }

  /**
   * @return the access token
   */
  @NotNull public String getAccessToken() {
    return accessToken;
  }

  /**
   * @return the amount of milliseconds after the start of the Unix epoch when this token is considered expired
   */
  public long getExpiresAt() {
    return expiresAt;
  }
}
