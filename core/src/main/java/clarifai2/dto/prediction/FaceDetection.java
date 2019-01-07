package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.dto.input.Crop;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceDetection extends Prediction {

  FaceDetection() {} // AutoValue instances only

  @NotNull public abstract Crop boundingBox();


  @NotNull public static FaceDetection deserialize(DataOuterClass.Region region) {
    return new AutoValue_FaceDetection(Crop.deserialize(region.getRegionInfo().getBoundingBox()));
  }
}