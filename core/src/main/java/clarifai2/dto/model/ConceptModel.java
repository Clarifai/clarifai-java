package clarifai2.dto.model;

import clarifai2.api.request.model.PatchModelRequest;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class ConceptModel extends Model<Concept> {

  @NotNull
  public final PatchModelRequest addConcepts() {
    return client().addConceptsToModel(id());
  }

  @NotNull
  public final PatchModelRequest deleteConcepts() {
    return client().deleteConceptsFromModel(id());
  }

  @NotNull @Override public final ModelType modelType() { return ModelType.CONCEPT; }

  @SuppressWarnings("ConstantConditions")
  @NotNull
  @Override
  public final ConceptOutputInfo outputInfo() {
    return (ConceptOutputInfo) _outputInfo();
  }

  @AutoValue.Builder
  public interface Builder extends Model.Builder<Builder> {
    @NotNull @Override ConceptModel build();
  }

  ConceptModel() {} // AutoValue instances only
}