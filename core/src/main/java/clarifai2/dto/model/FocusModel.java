package clarifai2.dto.model;

import clarifai2.dto.model.output_info.FocusOutputInfo;
import clarifai2.dto.prediction.Focus;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FocusModel extends Model<Focus> {
  FocusModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return modelTypeStatic(); }
  public static ModelType modelTypeStatic() { return ModelType.FOCUS; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final FocusOutputInfo outputInfo() {
    return (FocusOutputInfo) super.outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<FocusModel.Builder> {
    @NotNull @Override FocusModel build();
  }
}
