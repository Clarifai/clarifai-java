package clarifai2.dto.model;

import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.ModifyModelRequest;
import clarifai2.api.request.model.PatchModelRequest;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import com.google.auto.value.AutoValue;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Model.Adapter.class)
public abstract class ConceptModel extends Model<Concept> {

  /**
   * @see ClarifaiClient#mergeConceptsForModel(String)
   * @deprecated use {@link #modify()}
   */
  @Deprecated
  @NotNull
  public final PatchModelRequest mergeConcepts() {
    return client().mergeConceptsForModel(id());
  }

  /**
   * @see ClarifaiClient#setConceptsForModel(String)
   * @deprecated use {@link #modify()}
   */
  @Deprecated
  @NotNull
  public final PatchModelRequest setConcepts() {
    return client().setConceptsForModel(id());
  }

  /**
   * @see ClarifaiClient#removeConceptsForModel(String)
   * @deprecated use {@link #modify()}
   */
  @Deprecated
  @NotNull
  public final PatchModelRequest removeConcepts() {
    return client().removeConceptsForModel(id());
  }

  @NotNull public final ModifyModelRequest modify() {
    return client().modifyModel(id());
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