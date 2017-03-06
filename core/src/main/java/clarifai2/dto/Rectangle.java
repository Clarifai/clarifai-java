package clarifai2.dto;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Rectangle {

  @NotNull public static Rectangle of(@NotNull PointF topLeft, @NotNull PointF bottomRight) {
    return new AutoValue_Rectangle(topLeft, bottomRight);
  }

  @NotNull public abstract PointF topLeft();
  @NotNull public abstract PointF bottomRight();

  @NotNull public final PointF topRight() {
    return PointF.at(right(), top());
  }

  @NotNull public final PointF bottomLeft() {
    return PointF.at(left(), bottom());
  }

  public final float width() {
    return Math.abs(right() - left());
  }

  public final float height() {
    return Math.abs(bottom() - top());
  }

  public final float left() {
    return topLeft().x();
  }

  public final float top() {
    return topLeft().y();
  }

  public final float right() {
    return bottomRight().x();
  }

  public final float bottom() {
    return bottomRight().y();
  }

  Rectangle() {} // AutoValue instances only
}