package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.dto.input.Crop;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Focus extends Prediction {

  Focus() {} // Autovalue instances only

  @NotNull public abstract Crop crop();

  @NotNull public abstract double density();

  @NotNull public abstract double value();


  @NotNull public static Focus deserialize(DataOuterClass.Region region, float value) {
    return new AutoValue_Focus(
        Crop.deserialize(region.getRegionInfo().getBoundingBox()),
        region.getData().getFocus().getDensity(),
        value
    );
  }
}
