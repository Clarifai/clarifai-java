package clarifai2.dto.model;

import clarifai2.dto.model.output_info.FaceDetectionOutputInfo;
import clarifai2.dto.prediction.FaceDetection;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceDetectionModel extends Model<FaceDetection> {

  @NotNull @Override public final ModelType modelType() { return ModelType.FACE_DETECTION; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final FaceDetectionOutputInfo outputInfo() {
    return (FaceDetectionOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override FaceDetectionModel build();
  }

  FaceDetectionModel() {} // AutoValue instances only
}