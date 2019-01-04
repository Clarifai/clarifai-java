package clarifai2.api.request.feedback;

import clarifai2.internal.grpc.api.DataOuterClass;

public enum Feedback {
  ACCURATE,
  MISPLACED,
  NOT_DETECTED,
  FALSE_POSITIVE;

  public String toString() {
    return name().toLowerCase();
  }

  public DataOuterClass.RegionInfoFeedback serialize() {
    return DataOuterClass.RegionInfoFeedback.valueOf(toString());
  }
}
