package clarifai2.dto;

import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Radius {

  Radius() {} // AutoValue instances only

  @NotNull public static Radius of(float value, @NotNull Unit unit) {
    return new AutoValue_Radius(value, unit);
  }

  public abstract float value();
  @NotNull public abstract Unit unit();

  public enum Unit {
    MILE("withinMiles"),
    KILOMETER("withinKilometers"),
    DEGREE("withinDegrees"),
    RADIAN("withinRadians"),;

    @NotNull private final String str;

    Unit(@NotNull String str) {
      this.str = str;
    }

    @Override public String toString() {
      return str;
    }
  }
}