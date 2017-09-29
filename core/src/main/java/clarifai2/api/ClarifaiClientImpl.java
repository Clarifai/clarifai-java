package clarifai2.api;

import clarifai2.api.request.concept.AddConceptsRequest;
import clarifai2.api.request.concept.GetConceptByIDRequest;
import clarifai2.api.request.concept.GetConceptsRequest;
import clarifai2.api.request.concept.ModifyConceptsRequest;
import clarifai2.api.request.concept.SearchConceptsRequest;
import clarifai2.api.request.feedback.ModelFeedbackRequest;
import clarifai2.api.request.feedback.SearchesFeedbackRequest;
import clarifai2.api.request.input.AddInputsRequest;
import clarifai2.api.request.input.DeleteAllInputsRequest;
import clarifai2.api.request.input.DeleteInputRequest;
import clarifai2.api.request.input.DeleteInputsBatchRequest;
import clarifai2.api.request.input.GetInputRequest;
import clarifai2.api.request.input.GetInputsRequest;
import clarifai2.api.request.input.GetInputsStatusRequest;
import clarifai2.api.request.input.PatchInputMetadataRequest;
import clarifai2.api.request.input.PatchInputRequest;
import clarifai2.api.request.input.SearchClause;
import clarifai2.api.request.input.SearchInputsRequest;
import clarifai2.api.request.model.Action;
import clarifai2.api.request.model.CreateModelRequest;
import clarifai2.api.request.model.DeleteAllModelsRequest;
import clarifai2.api.request.model.DeleteModelRequest;
import clarifai2.api.request.model.DeleteModelVersionRequest;
import clarifai2.api.request.model.DeleteModelsBatchRequest;
import clarifai2.api.request.model.FindModelRequest;
import clarifai2.api.request.model.GetModelInputsRequest;
import clarifai2.api.request.model.GetModelRequest;
import clarifai2.api.request.model.GetModelVersionRequest;
import clarifai2.api.request.model.GetModelVersionsRequest;
import clarifai2.api.request.model.GetModelsRequest;
import clarifai2.api.request.model.ModifyModelRequest;
import clarifai2.api.request.model.RunModelEvaluationRequest;
import clarifai2.api.request.model.WorkflowPredictRequest;
import clarifai2.api.request.model.PatchModelRequest;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.api.request.model.TrainModelRequest;
import clarifai2.dto.model.DefaultModels;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.prediction.Prediction;
import com.google.gson.JsonObject;
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

  @NotNull @Override public PatchInputRequest mergeConceptsForInput(@NotNull String inputID) {
    return new PatchInputRequest(this, inputID, "merge");
  }

  @NotNull @Override public PatchInputRequest setConceptsForInput(@NotNull String inputID) {
    return new PatchInputRequest(this, inputID, "overwrite");
  }

  @NotNull @Override public PatchInputRequest removeConceptsForInput(@NotNull String inputID) {
    return new PatchInputRequest(this, inputID, "remove");
  }

  @NotNull @Override
  public PatchInputMetadataRequest addMetadataForInput(@NotNull String inputID, @NotNull JsonObject metadata) {
    return new PatchInputMetadataRequest(this, inputID, metadata);
  }

  @NotNull @Override public GetInputsRequest getInputs() {
    return new GetInputsRequest(this);
  }

  @NotNull @Override public GetInputRequest getInputByID(@NotNull final String inputID) {
    return new GetInputRequest(this, inputID);
  }

  @NotNull @Override public DeleteInputRequest deleteInput(@NotNull String inputID) {
    return new DeleteInputRequest(this, inputID);
  }

  @NotNull @Override public DeleteInputsBatchRequest deleteInputsBatch() {
    return new DeleteInputsBatchRequest(this);
  }

  @NotNull @Override
  public DeleteAllInputsRequest deleteAllInputs() {
    return new DeleteAllInputsRequest(this);
  }

  @NotNull @Override public GetInputsStatusRequest getInputsStatus() {
    return new GetInputsStatusRequest(this);
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

  @NotNull @Override public GetConceptsRequest getConcepts() {
    return new GetConceptsRequest(this);
  }

  @NotNull @Override public GetConceptByIDRequest getConceptByID(@NotNull final String conceptID) {
    return new GetConceptByIDRequest(this, conceptID);
  }

  @NotNull @Override public ModifyConceptsRequest modifyConcepts() {
    return new ModifyConceptsRequest(this, "overwrite");
  }

  @NotNull @Override
  public SearchConceptsRequest searchConcepts(@NotNull final String conceptSearchQuery) {
    return new SearchConceptsRequest(this, conceptSearchQuery);
  }

  @NotNull @Override public CreateModelRequest createModel(@NotNull final String id) {
    return new CreateModelRequest(this, id);
  }

  @NotNull @Override public DefaultModels getDefaultModels() {
    return builtInModels;
  }

  @NotNull @Override public GetModelsRequest getModels() {
    return new GetModelsRequest(this);
  }


  @NotNull @Override public GetModelRequest getModelByID(@NotNull String modelID) {
    return new GetModelRequest(this, modelID);
  }

  @NotNull @Override public DeleteModelRequest deleteModel(@NotNull final String modelID) {
    return new DeleteModelRequest(this, modelID);
  }

  @NotNull @Override public DeleteModelsBatchRequest deleteModelsBatch() {
    return new DeleteModelsBatchRequest(this);
  }

  @NotNull @Override
  public DeleteModelVersionRequest deleteModelVersion(@NotNull String modelID, @NotNull String versionID) {
    return new DeleteModelVersionRequest(this, modelID, versionID);
  }

  @NotNull @Override public DeleteAllModelsRequest deleteAllModels() {
    return new DeleteAllModelsRequest(this);
  }

  @NotNull @Override
  public GetModelVersionRequest getModelVersionByID(@NotNull String modelID, @NotNull String versionID) {
    return new GetModelVersionRequest(this, modelID, versionID);
  }

  @NotNull @Override
  public GetModelVersionsRequest getModelVersions(@NotNull final String modelID) {
    return new GetModelVersionsRequest(this, modelID);
  }

  @NotNull @Override public GetModelInputsRequest getModelInputs(@NotNull final String modelID) {
    return new GetModelInputsRequest(this, modelID);
  }

  @NotNull @Override public FindModelRequest findModel() {
    return new FindModelRequest(this);
  }

  @NotNull @Override public PatchModelRequest mergeConceptsForModel(@NotNull String modelID) {
    return new PatchModelRequest(this, modelID, Action.MERGE);
  }

  @NotNull @Override public PatchModelRequest setConceptsForModel(@NotNull String modelID) {
    return new PatchModelRequest(this, modelID, Action.OVERWRITE);
  }

  @NotNull @Override public PatchModelRequest removeConceptsForModel(@NotNull String modelID) {
    return new PatchModelRequest(this, modelID, Action.REMOVE);
  }

  @NotNull @Override public ModifyModelRequest modifyModel(@NotNull String modelID) {
    return new ModifyModelRequest(this, modelID);
  }

  @NotNull @Override public TrainModelRequest trainModel(@NotNull final String modelID) {
    return new TrainModelRequest(this, modelID);
  }

  @NotNull @Override public PredictRequest<Prediction> predict(@NotNull String modelID) {
    return new PredictRequest<>(this, modelID);
  }

  @NotNull @Override public SearchesFeedbackRequest searchesFeedback() {
    return new SearchesFeedbackRequest(this);
  }

  @NotNull @Override public ModelFeedbackRequest modelFeedback(@NotNull String modelID) {
    return new ModelFeedbackRequest(this, modelID);
  }

  @NotNull @Override public WorkflowPredictRequest workflowPredict(@NotNull String workflowID) {
    return new WorkflowPredictRequest(this, workflowID);
  }

  @NotNull @Override public RunModelEvaluationRequest runModelEvaluation(@NotNull String modelID,
      @NotNull String versionID) {
    return new RunModelEvaluationRequest(this, modelID, versionID);
  }
}
