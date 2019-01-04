package clarifai2.dto.prediction;

import clarifai2.dto.HasClarifaiIDRequired;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Cluster extends Prediction implements HasClarifaiIDRequired {

  Cluster() {} // AutoValue instances only

  @NotNull public abstract int numClusters();
}