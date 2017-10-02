package clarifai2.dto.model;

import clarifai2.dto.model.output_info.FaceEmbeddingOutputInfo;
import clarifai2.dto.prediction.FaceEmbedding;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceEmbeddingModel extends Model<FaceEmbedding> {

  FaceEmbeddingModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return ModelType.FACE_EMBEDDING; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final FaceEmbeddingOutputInfo outputInfo() {
    return (FaceEmbeddingOutputInfo) super.outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override FaceEmbeddingModel build();
  }
}
