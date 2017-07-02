package clarifai2.api.request.feedback;

public enum Feedback {
  ACCURATE,
  MISPLACED,
  NOT_DETECTED,
  FALSE_POSITIVE;

  public String toString() {
    return name().toLowerCase();
  }
}
