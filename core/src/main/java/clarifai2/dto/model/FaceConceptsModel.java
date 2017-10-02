package clarifai2.dto.model;

import clarifai2.dto.model.output_info.FaceConceptsOutputInfo;
import clarifai2.dto.prediction.FaceConcepts;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceConceptsModel extends Model<FaceConcepts> {

  FaceConceptsModel() {} // AutoValue instances only

  @NotNull @Override public final ModelType modelType() { return ModelType.FACE_CONCEPTS; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final FaceConceptsOutputInfo outputInfo() {
    return (FaceConceptsOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override FaceConceptsModel build();
  }
}
