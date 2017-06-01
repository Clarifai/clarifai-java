package clarifai2.dto.model;


import clarifai2.dto.model.output_info.UnknownOutputInfo;
import clarifai2.dto.prediction.Unknown;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class UnknownModel extends Model<Unknown> {

  UnknownModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return ModelType.UNKNOWN; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final UnknownOutputInfo outputInfo() {
    return (UnknownOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override UnknownModel build();
  }
}
