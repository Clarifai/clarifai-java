package clarifai2.dto.model;

import clarifai2.dto.model.output_info.DetectionOutputInfo;
import clarifai2.dto.prediction.Detection;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class DetectionModel extends Model<Detection> {
  DetectionModel() {}

  @NotNull @Override public final ModelType modelType() { return modelTypeStatic(); }
  public static ModelType modelTypeStatic() { return ModelType.DETECT_CONCEPT; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final DetectionOutputInfo outputInfo() {
    return (DetectionOutputInfo) super.outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<DetectionModel.Builder> {
    @NotNull @Override DetectionModel build();
  }
}
