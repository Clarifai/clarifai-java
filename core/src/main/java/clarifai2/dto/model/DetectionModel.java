package clarifai2.dto.model;

import clarifai2.dto.model.output_info.DetectionOutputInfo;
import clarifai2.dto.prediction.Detection;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class DetectionModel extends Model<Detection> {

  @NotNull @Override public final ModelType modelType() { return ModelType.Detection; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final DetectionOutputInfo outputInfo() {
    return (DetectionOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override DetectionModel build();
  }

  DetectionModel() {} // AutoValue instances only
}
