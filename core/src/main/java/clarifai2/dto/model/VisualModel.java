package clarifai2.dto.model;

import clarifai2.dto.model.output_info.VisualOutputInfo;
import clarifai2.dto.prediction.Visual;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class VisualModel extends Model<Visual> {

  @NotNull @Override public final ModelType modelType() { return ModelType.VISUAL; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final VisualOutputInfo outputInfo() {
    return (VisualOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override VisualModel build();
  }

  VisualModel() {} // AutoValue instances only
}
