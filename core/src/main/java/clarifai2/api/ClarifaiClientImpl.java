package clarifai2.api;

import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.api.request.concept.AddConceptsRequest;
import clarifai2.api.request.concept.DeleteConceptsFromInputRequest;
import clarifai2.api.request.concept.GetConceptByIDRequest;
import clarifai2.api.request.concept.GetConceptsRequest;
import clarifai2.api.request.concept.SearchConceptsRequest;
import clarifai2.api.request.input.AddConceptsToInputRequest;
import clarifai2.api.request.input.AddInputsRequest;
import clarifai2.api.request.input.DeleteAllInputsRequest;
import clarifai2.api.request.input.DeleteInputsRequest;
import clarifai2.api.request.input.GetInputRequest;
import clarifai2.api.request.input.GetInputsRequest;
import clarifai2.api.request.input.GetInputsStatusRequest;
import clarifai2.api.request.input.SearchClause;
import clarifai2.api.request.input.SearchInputsRequest;
import clarifai2.api.request.model.CreateModelRequest;
import clarifai2.api.request.model.DeleteAllModelsRequest;
import clarifai2.api.request.model.DeleteModelRequest;
import clarifai2.api.request.model.DeleteModelVersionRequest;
import clarifai2.api.request.model.FindModelRequest;
import clarifai2.api.request.model.GetModelInputsRequest;
import clarifai2.api.request.model.GetModelRequest;
import clarifai2.api.request.model.GetModelVersionRequest;
import clarifai2.api.request.model.GetModelVersionsRequest;
import clarifai2.api.request.model.GetModelsRequest;
import clarifai2.api.request.model.PatchModelRequest;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.api.request.model.TrainModelRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiInputUpdateAction;
import clarifai2.dto.input.ClarifaiInputsStatus;
import clarifai2.dto.model.DefaultModels;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class ClarifaiClientImpl extends BaseClarifaiClient implements ClarifaiClient {

  private final DefaultModels builtInModels = new DefaultModels(this);

  ClarifaiClientImpl(@NotNull ClarifaiBuilder builder) {
    super(builder);
  }

  @NotNull @Override public AddInputsRequest addInputs() {
    return new AddInputsRequest(this);
  }

//  @NotNull @Override public ClarifaiRequest<List<ClarifaiInput>> addInputsFromCSV(@NotNull final File csvFile) {
//    return new ClarifaiAddInputsFromCSVRequest(this, csvFile).build();
//  }

  @NotNull @Override
  public AddConceptsToInputRequest addConceptsToInput(@NotNull final String inputID) {
    return new AddConceptsToInputRequest(this, inputID);
  }

  @NotNull @Override
  public DeleteConceptsFromInputRequest deleteConceptsFromInput(@NotNull final String inputID) {
    return new DeleteConceptsFromInputRequest(this, inputID);
  }

  @NotNull @Override public ClarifaiPaginatedRequest.Builder<List<ClarifaiInput>, ?> getInputs() {
    return new GetInputsRequest(this);
  }

  @NotNull @Override public ClarifaiRequest<ClarifaiInput> getInputByID(@NotNull final String inputID) {
    return new GetInputRequest(this, inputID).build();
  }

  @NotNull @Override public DeleteInputsRequest deleteInputs() {
    return new DeleteInputsRequest(this);
  }

  @NotNull @Override public ClarifaiRequest<List<ClarifaiInput>> deleteAllInputs() {
    return new DeleteAllInputsRequest(this).build();
  }

  @NotNull @Override public ClarifaiRequest<ClarifaiInputsStatus> getInputsStatus() {
    return new GetInputsStatusRequest(this).build();
  }

  @NotNull @Override public SearchInputsRequest searchInputs(@NotNull SearchClause searchClause) {
    return searchInputs(Collections.singletonList(searchClause));
  }

  @NotNull @Override public SearchInputsRequest searchInputs(@NotNull SearchClause... searchClauses) {
    return searchInputs(Arrays.asList(searchClauses));
  }

  @NotNull @Override public SearchInputsRequest searchInputs(@NotNull List<SearchClause> searchClauses) {
    return new SearchInputsRequest(this, searchClauses);
  }

  @NotNull @Override public AddConceptsRequest addConcepts() {
    return new AddConceptsRequest(this);
  }

  @NotNull @Override public ClarifaiPaginatedRequest.Builder<List<Concept>, ?> getConcepts() {
    return new GetConceptsRequest(this);
  }

  @NotNull @Override public ClarifaiRequest<Concept> getConceptByID(@NotNull final String conceptID) {
    return new GetConceptByIDRequest(this, conceptID).build();
  }

  @NotNull @Override
  public ClarifaiPaginatedRequest.Builder<List<Concept>, ?> searchConcepts(@NotNull final String conceptSearchQuery) {
    return new SearchConceptsRequest(this, conceptSearchQuery);
  }

  @NotNull @Override public CreateModelRequest createModel(@NotNull final String id) {
    return new CreateModelRequest(this, id);
  }

  @NotNull @Override public DefaultModels getDefaultModels() {
    return builtInModels;
  }

  @NotNull @Override public ClarifaiPaginatedRequest.Builder<List<Model<?>>, ?> getModels() {
    return new GetModelsRequest(this);
  }


  @NotNull @Override public ClarifaiRequest<Model<?>> getModelByID(@NotNull String modelID) {
    return new GetModelRequest(this, modelID).build();
  }

  @NotNull @Override public ClarifaiRequest<List<Model<?>>> deleteModel(@NotNull final String modelID) {
    return new DeleteModelRequest(this, modelID).build();
  }

  @NotNull @Override
  public ClarifaiRequest<List<ModelVersion>> deleteModelVersion(@NotNull String modelID, @NotNull String versionID) {
    return new DeleteModelVersionRequest(this, modelID, versionID).build();
  }

  @NotNull @Override public ClarifaiRequest<List<Model<?>>> deleteAllModels() {
    return new DeleteAllModelsRequest(this).build();
  }

  @NotNull @Override
  public ClarifaiRequest<ModelVersion> getModelVersionByID(@NotNull String modelID, @NotNull String versionID) {
    return new GetModelVersionRequest(this, modelID, versionID);
  }

  @NotNull @Override
  public ClarifaiPaginatedRequest.Builder<List<ModelVersion>, ?> getModelVersions(@NotNull final String modelID) {
    return new GetModelVersionsRequest(this, modelID);
  }

  @NotNull @Override public GetModelInputsRequest getModelInputs(@NotNull final String modelID) {
    return new GetModelInputsRequest(this, modelID);
  }

  @NotNull @Override public FindModelRequest findModel() {
    return new FindModelRequest(this);
  }

  @NotNull @Override public PatchModelRequest addConceptsToModel(@NotNull final String modelID) {
    return new PatchModelRequest(this, modelID, ClarifaiInputUpdateAction.MERGE_CONCEPTS);
  }

  @NotNull @Override public PatchModelRequest deleteConceptsFromModel(@NotNull final String modelID) {
    return new PatchModelRequest(this, modelID, ClarifaiInputUpdateAction.DELETE_CONCEPTS);
  }

  @NotNull @Override public ClarifaiRequest<Model<?>> trainModel(@NotNull final String modelID) {
    return new TrainModelRequest(this, modelID).build();
  }

  @NotNull @Override public PredictRequest<Prediction> predict(@NotNull String modelID) {
    return new PredictRequest<>(this, modelID);
  }

}
