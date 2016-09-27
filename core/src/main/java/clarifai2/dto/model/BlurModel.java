package clarifai2.dto.model;

import clarifai2.dto.model.output_info.BlurOutputInfo;
import clarifai2.dto.prediction.Blur;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class BlurModel extends Model<Blur> {

  @NotNull @Override public final ModelType modelType() { return ModelType.BLUR; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final BlurOutputInfo outputInfo() {
    return (BlurOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override BlurModel build();
  }

  BlurModel() {} // AutoValue instances only
}