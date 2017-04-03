package clarifai2.dto.model;

import clarifai2.dto.model.output_info.DemographicOutputInfo;
import clarifai2.dto.prediction.Region;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class DemographicModel extends Model<Region> {
  @NotNull @Override public final ModelType modelType() { return ModelType.DEMOGRAPHIC_MODEL; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final DemographicOutputInfo outputInfo() {
    return (DemographicOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<DemographicModel.Builder> {
    @NotNull @Override DemographicModel build();
  }

  DemographicModel() {} // AutoValue instances only
}
