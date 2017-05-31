package clarifai2.dto.model;

import clarifai2.dto.model.output_info.EmbeddingOutputInfo;
import clarifai2.dto.prediction.Embedding;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class EmbeddingModel extends Model<Embedding> {

  EmbeddingModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return ModelType.EMBEDDING; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final EmbeddingOutputInfo outputInfo() {
    return (EmbeddingOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override EmbeddingModel build();
  }
}