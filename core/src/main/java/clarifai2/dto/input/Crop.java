package clarifai2.dto.input;

import clarifai2.internal.grpc.api.DataOuterClass;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Crop {

  Crop() {} // AutoValue instances only

  @NotNull public static Crop create() {
    return new AutoValue_Crop(0.0F, 0.0F, 1.0F, 1.0F);
  }

  @NotNull public abstract float top();

  @NotNull public abstract float left();

  @NotNull public abstract float bottom();

  @NotNull public abstract float right();

  @NotNull public final Crop top(@NotNull float top) {
    return withTop(top);
  }

  @NotNull public final Crop left(@NotNull float left) {
    return withLeft(left);
  }

  @NotNull public final Crop bottom(@NotNull float bottom) {
    return withBottom(bottom);
  }

  @NotNull public final Crop right(@NotNull float right) {
    return withRight(right);
  }

  // These are not great method names, so we'll alias them above
  @NotNull abstract Crop withTop(@NotNull float top);

  @NotNull abstract Crop withLeft(@NotNull float left);

  @NotNull abstract Crop withBottom(@NotNull float bottom);

  @NotNull abstract Crop withRight(@NotNull float right);

  @NotNull public static Crop deserialize(List<Float> cropList) {
    return new AutoValue_Crop(
        cropList.get(0),
        cropList.get(1),
        cropList.get(2),
        cropList.get(3)
    );
  }

  @NotNull public static Crop deserialize(DataOuterClass.BoundingBox boundingBox) {
    return new AutoValue_Crop(
        boundingBox.getTopRow(),
        boundingBox.getLeftCol(),
        boundingBox.getBottomRow(),
        boundingBox.getRightCol()
    );
  }

  public DataOuterClass.BoundingBox serializeBoundingBox() {
    return DataOuterClass.BoundingBox.newBuilder()
        .setTopRow(top())
        .setLeftCol(left())
        .setBottomRow(bottom())
        .setRightCol(right())
        .build();
  }
}
