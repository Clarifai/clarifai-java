package clarifai2.dto.model;

import clarifai2.dto.model.output_info.LogoOutputInfo;
import clarifai2.dto.prediction.Logo;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class LogoModel extends Model<Logo> {
  LogoModel() {}

  @NotNull @Override public final ModelType modelType() { return ModelType.LOGO; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final LogoOutputInfo outputInfo() {
    return (LogoOutputInfo) super.outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<LogoModel.Builder> {
    @NotNull @Override LogoModel build();
  }
}
