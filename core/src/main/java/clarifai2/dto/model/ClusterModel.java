package clarifai2.dto.model;

import clarifai2.dto.model.output_info.ClusterOutputInfo;
import clarifai2.dto.prediction.Cluster;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class ClusterModel extends Model<Cluster> {

  ClusterModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return modelTypeStatic(); }
  public static ModelType modelTypeStatic() { return ModelType.CLUSTER; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final ClusterOutputInfo outputInfo() {
    return (ClusterOutputInfo) super.outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override ClusterModel build();
  }
}