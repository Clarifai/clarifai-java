package clarifai2.dto;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class PointF {

  @NotNull public static PointF at(float x, float y) {
    return new AutoValue_PointF(x, y);
  }

  public abstract float x();
  public abstract float y();

  @NotNull public final PointF translated(float translateX, float translateY) {
    return new AutoValue_PointF(x() + translateX, y() + translateY);
  }

  PointF() {} // AutoValue instances only
}