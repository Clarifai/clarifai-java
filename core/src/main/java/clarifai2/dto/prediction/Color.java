package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.ColorOuterClass;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Color extends Prediction {

  Color() {} // AutoValue instances only

  @NotNull public abstract String hex();

  @NotNull public abstract String webSafeHex();

  @NotNull public abstract String webSafeColorName();

  @NotNull public abstract float value();


  @NotNull public static Color deserialize(ColorOuterClass.Color color) {
    return new AutoValue_Color(
        color.getRawHex(),
        color.getW3C().getHex(),
        color.getW3C().getName(),
        color.getValue()
    );
  }
}