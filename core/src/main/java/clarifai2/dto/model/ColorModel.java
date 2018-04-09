package clarifai2.dto.model;

import clarifai2.dto.model.output_info.ColorOutputInfo;
import clarifai2.dto.prediction.Color;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class ColorModel extends Model<Color> {

  ColorModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return modelTypeStatic(); }
  public static ModelType modelTypeStatic() { return ModelType.COLOR; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final ColorOutputInfo outputInfo() {
    return (ColorOutputInfo) super.outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override ColorModel build();
  }
}