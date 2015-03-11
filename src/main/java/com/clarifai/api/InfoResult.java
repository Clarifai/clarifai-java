package com.clarifai.api;

/**
 * API details and usage limits. These limits must be obeyed by the user when making API calls or
 * the API call will fail.
 */
public class InfoResult {
  private Integer minImageSize;
  private Integer maxImageSize;
  private Integer maxBatchSize;
  private Boolean embedAllowed;

  /**
   * Gets the minimum allowed image size (on the minimum dimension). Any images that have a
   * minimum dimension greater than this limit will not be processed.
   */
  public int getMinImageSize() {
    return (minImageSize == null) ? 0 : minImageSize;
  }

  /**
   * Gets the maximum allowed image size (on the minimum dimension). Any images that have a
   * minimum dimension greater than this limit will not be processed.
   */
  public int getMaxImageSize() {
    return (maxImageSize == null) ? Integer.MAX_VALUE : maxImageSize;
  }

  /** Gets the maximum number of images allowed in a single batch request. */
  public int getMaxBatchSize() {
    return (maxBatchSize == null) ? Integer.MAX_VALUE : maxBatchSize;
  }

  /** Returns whether the current user is allowed to use embed operations. */
  public boolean embedAllowed() {
    return (embedAllowed == null) ? false : embedAllowed;
  }

  @Override public String toString() {
    return "[minImageSize:" + getMinImageSize() + " maxImageSize:" + getMaxImageSize() +
        " maxBatchSize:" + getMaxBatchSize() + " embedAllowed:" + embedAllowed() + "]";
  }
}
