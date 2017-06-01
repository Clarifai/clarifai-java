package clarifai2.dto.model;

import clarifai2.dto.model.output_info.DemographicsOutputInfo;
import clarifai2.dto.prediction.Region;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class DemographicsModel extends Model<Region> {
  DemographicsModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return ModelType.DEMOGRAPHICS; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final DemographicsOutputInfo outputInfo() {
    return (DemographicsOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<DemographicsModel.Builder> {
    @NotNull @Override DemographicsModel build();
  }
}
