package clarifai2.dto.model;

import clarifai2.dto.model.output_info.VideoOutputInfo;
import clarifai2.dto.prediction.Frame;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class VideoModel extends Model<Frame> {

  VideoModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return ModelType.VIDEO; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final VideoOutputInfo outputInfo() {
    return (VideoOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override VideoModel build();
  }
}