package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.prediction.Concept;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ModifyModelRequest extends ClarifaiRequest.Builder<ConceptModel> {

  @NotNull private final String modelID;

  @Nullable private Action action = Action.MERGE;
  @Nullable private List<Concept> concepts = null;
  @Nullable private String name = null;
  @Nullable private Boolean conceptsMutuallyExclusive = null;
  @Nullable private Boolean closedEnvironment = null;
  @Nullable private String language = null;

  public ModifyModelRequest(@NotNull BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  /**
   * Sets the concepts to be used to modify this model.
   * <p>
   * Calling this method twice will result in the old list being overwritten with the contents of this list.
   *
   * @param action   the action to use when modifying these concepts
   * @param concepts the concepts to modify on this model
   * @return this request-builder
   * @see #withConcepts(Action, List)
   * @see Action#MERGE
   * @see Action#OVERWRITE
   * @see Action#REMOVE
   */
  @NotNull public ModifyModelRequest withConcepts(@NotNull Action action, @NotNull Concept... concepts) {
    return withConcepts(action, Arrays.asList(concepts));
  }

  /**
   * Sets the concepts to be used to modify this model.
   * <p>
   * Calling this method twice will result in the old list being overwritten with the contents of this list.
   *
   * @param action   the action to use when modifying these concepts
   * @param concepts the concepts to modify on this model
   * @return this request-builder
   * @see #withConcepts(Action, List)
   * @see Action#MERGE
   * @see Action#OVERWRITE
   * @see Action#REMOVE
   */
  @NotNull public ModifyModelRequest withConcepts(@NotNull Action action, @NotNull List<Concept> concepts) {
    this.concepts = concepts;
    this.action = action;
    return this;
  }

  /**
   * Sets this request to modify the name of this model.
   *
   * @param name the new name of this model
   * @return this request-builder
   */
  @NotNull public ModifyModelRequest withName(@NotNull String name) {
    this.name = name;
    return this;
  }

  @NotNull public ModifyModelRequest withConceptsMutuallyExclusive(boolean conceptsMutuallyExclusive) {
    this.conceptsMutuallyExclusive = conceptsMutuallyExclusive;
    return this;
  }

  @NotNull public ModifyModelRequest withClosedEnvironment(boolean closedEnvironment) {
    this.closedEnvironment = closedEnvironment;
    return this;
  }

  @NotNull public ModifyModelRequest withLanguage(@NotNull String language) {
    this.language = language;
    return this;
  }

  @NotNull @Override protected String method() {
    return "PATCH";
  }

  @NotNull @Override protected String subUrl() {
    return "/v2/models";
  }

  @NotNull @Override protected DeserializedRequest<ConceptModel> request() {
    return new DeserializedRequest<ConceptModel>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        ModelOuterClass.Model.Builder model = ModelOuterClass.Model.newBuilder()
            .setId(modelID);
        if (name != null) {
          model.setName(name);
        }

        ModelOuterClass.OutputInfo.Builder outputInfo = ModelOuterClass.OutputInfo.newBuilder();
        if (concepts != null) {
          List<ConceptOuterClass.Concept> conceptsGrpc = new ArrayList<>();
          for (Concept concept : concepts) {
            conceptsGrpc.add(concept.serialize());
          }
          outputInfo.setData(DataOuterClass.Data.newBuilder().addAllConcepts(conceptsGrpc));
        }


        ModelOuterClass.OutputConfig.Builder outputConfig = ModelOuterClass.OutputConfig.newBuilder();
        if (language != null) {
          outputConfig.setLanguage(language);
        }
        if (conceptsMutuallyExclusive != null) {
          outputConfig.setConceptsMutuallyExclusive(conceptsMutuallyExclusive);
        }
        if (closedEnvironment != null) {
          outputConfig.setClosedEnvironment(closedEnvironment);
        }
        outputInfo.setOutputConfig(outputConfig);

        model.setOutputInfo(outputInfo);
        return stub().patchModels(
            ModelOuterClass.PatchModelsRequest.newBuilder().addModels(model).setAction(action.serialize()).build()
        );
      }

      @NotNull @Override public ConceptModel unmarshalerGrpc(Object returnedObject) {
        ModelOuterClass.MultiModelResponse modelsResponse = (ModelOuterClass.MultiModelResponse) returnedObject;
        return (ConceptModel) Model.deserialize(modelsResponse.getModels(0), client);
      }
    };
  }
}
