package clarifai2.api;

import clarifai2.api.request.concept.AddConceptsRequest;
import clarifai2.api.request.concept.GetConceptByIDRequest;
import clarifai2.api.request.concept.GetConceptsRequest;
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
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.DefaultModels;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * An interface containing all of the methods that are used to make requests to the Clarifai API.
 * <p>
 * Create an instance of this interface using the {@link ClarifaiBuilder}
 */
public interface ClarifaiClient {

  /**
   * @return {@code true} if the user has a token and it isn't expired; else, {@code false}
   */
  boolean hasValidToken();

  /**
   * @return the current token that we're using
   * @throws IllegalStateException if the current token is either invalid or expired. Check {@link #hasValidToken()}
   *                               before calling this method to ensure that this exception is not thrown
   */
  @NotNull ClarifaiToken getToken() throws IllegalStateException;

  /**
   * Adds inputs to your Clarifai app.
   *
   * @return a builder to construct a request that will, when executed, return the inputs that were just added
   */
  @NotNull AddInputsRequest addInputs();

  /**
   * Merges any specified concepts into the list of concepts that are associated with this input.
   * <p>
   * If the IDs on any of the given concepts already exist on this input, the new concept will overwrite the old one.
   *
   * @param inputID the input to modify
   * @return a builder to construct a request that will, when executed, return the newly-modified input
   */
  @NotNull PatchInputRequest mergeConceptsForInput(@NotNull String inputID);

  /**
   * Overwrites the list of concepts on this input with the user's provided values.
   *
   * @param inputID the input to modify
   * @return a builder to construct a request that will, when executed, return the newly-modified input
   */
  @NotNull PatchInputRequest setConceptsForInput(@NotNull String inputID);

  /**
   * Removes the concepts with the given IDs from this input.
   *
   * @param inputID the input to modify
   * @return a builder to construct a request that will, when executed, return the newly-modified input
   */
  @NotNull PatchInputRequest removeConceptsForInput(@NotNull String inputID);

  /**
   * Adds the given metadata to this input's metadata.
   * <p>
   * The keys in the new metadata are parsed depth-first, and the existing metadata is checked for a conflicting key
   * at that location.
   * <p>
   * If the existing metadata does not have a key that conflicts with an entry in the new metadata, that new entry is
   * added to the existing metadata.
   * <p>
   * If the existing metadata DOES have a key that conflicts with an entry in the new metadata:
   * - If the existing and new values are of different types (primitive vs list vs dictionary), the new value will overwrite the existing value;
   * - Otherwise, if the existing and new values are both primitives or both lists, the new value will overwrite the existing value;
   * - Otherwise, both the existing and new value must be dictionaries, and the new dictionary will be merged
   * into the existing dictionary, with conflicts being resolved in the same manner described above
   *
   * @param inputID  the input to merge this metadata into
   * @param metadata the metadata to merge
   * @return a builder to construct a request that will, when executed, return the newly-modified input
   */
  @NotNull PatchInputMetadataRequest addMetadataForInput(@NotNull String inputID, @NotNull JsonObject metadata);

  /**
   * Get all of the inputs associated with this app
   *
   * @return a paginated request to look through all of the {@link ClarifaiInput}s in this app
   */
  @NotNull GetInputsRequest getInputs();

  /**
   * Get the input with the given ID
   *
   * @param inputID the ID of the input to get
   * @return a request that will, when executed, return the input that was retrieved
   */
  @NotNull GetInputRequest getInputByID(@NotNull String inputID);

  /**
   * Deletes the input with the given ID
   *
   * @param inputID the ID of the input to delete
   * @return a request that will, when executed, delete the given input
   */
  @NotNull DeleteInputRequest deleteInput(@NotNull String inputID);

  /**
   * @return a request builder to specify inputs to delete
   * <p>
   * Note that this is an asynchronous operation on the server. Until the follow-up request is invoked and
   * returns successfully, there is no guarantee that the inputs that you have specified have been deleted from the
   * server
   */
  @NotNull DeleteInputsBatchRequest deleteInputsBatch();

  /**
   * @return a request that, when executed, will delete all inputs in your account
   * <p>
   * Note that this is an asynchronous operation on the server. Until the follow-up request is invoked and
   * returns successfully, there is no guarantee that the inputs that you have specified have been deleted from the
   * server
   */
  @NotNull DeleteAllInputsRequest deleteAllInputs();

  /**
   * @return the current status of your Clarifai inputs (how many have been processed, how many are yet to be
   * processed, and how many errors occurred during processing)
   */
  @NotNull GetInputsStatusRequest getInputsStatus();

  /**
   * @param searchClause the clause to begin this search with
   * @return a builder to construct a request that will, when executed, search over your {@link ClarifaiInput}s
   */
  @NotNull SearchInputsRequest searchInputs(@NotNull SearchClause searchClause);

  /**
   * @param searchClauses the clauses to begin this search with
   * @return a builder to construct a request that will, when executed, search over your {@link ClarifaiInput}s
   */
  @NotNull SearchInputsRequest searchInputs(@NotNull SearchClause... searchClauses);

  /**
   * @param searchClauses the clauses to begin this search with
   * @return a builder to construct a request that will, when executed, search over your {@link ClarifaiInput}s
   */
  @NotNull SearchInputsRequest searchInputs(@NotNull List<SearchClause> searchClauses);

  /**
   * @return a builder to construct a request that will, when executed, add concepts to your app
   */
  @NotNull AddConceptsRequest addConcepts();

  /**
   * @return a paginated request to look through all of the {@link Concept}s in this app
   */
  @NotNull GetConceptsRequest getConcepts();

  /**
   * Get the concept associated with this concept ID
   *
   * @param conceptID the ID of the concept to get
   * @return a request that will, when executed, return the concept that was retrieved
   */
  @NotNull GetConceptByIDRequest getConceptByID(@NotNull String conceptID);

  /**
   * Get the concepts that match this search string
   *
   * @param conceptSearchQuery the query-string to use to search concepts. Can contain '*' for wildcard searches; eg:
   *                           "l*" to search all concepts beginning with the letter 'l'.
   * @return a paginated request that, when executed, will return all of the concepts that match this search query
   */
  @NotNull SearchConceptsRequest searchConcepts(@NotNull String conceptSearchQuery);

  /**
   * Create a new {@link Model}.
   *
   * @param id the id of the new model to create
   * @return a request that, when executed, will return a newly-created model
   */
  @NotNull CreateModelRequest createModel(@NotNull String id);

  /**
   * Get models that all Clarifai accounts automatically have access to.
   *
   * @return models that all Clarifai accounts automatically have access to.
   */
  @NotNull DefaultModels getDefaultModels();

  /**
   * Get all of the models associated with this app.
   *
   * @return a request that, when executed, will return all of the models associated with this app.
   */
  @NotNull GetModelsRequest getModels();

  @NotNull GetModelRequest getModelByID(@NotNull String modelID);

  @NotNull DeleteModelRequest deleteModel(@NotNull String modelID);

  @NotNull DeleteModelsBatchRequest deleteModelsBatch();

  @NotNull DeleteAllModelsRequest deleteAllModels();

  @NotNull DeleteModelVersionRequest deleteModelVersion(@NotNull String modelID, @NotNull String versionID);

  @NotNull GetModelVersionRequest getModelVersionByID(@NotNull String modelID, @NotNull String versionID);

  /**
   * Returns all of the {@link ModelVersion}s for this {@link Model}
   *
   * @param modelID the id of the model
   * @return a request that, when executed, will return all of the versions associated with this model.
   */
  @NotNull GetModelVersionsRequest getModelVersions(@NotNull String modelID);

  /**
   * Returns all of the {@link ClarifaiInput}s for this {@link Model}
   *
   * @param modelID the ID of the model
   * @return a request that, when executed, will return all of the inputs associated with this model.
   */
  @NotNull GetModelInputsRequest getModelInputs(@NotNull String modelID);

  /**
   * Query your app for models.
   *
   * @return a request builder that allows you to specify a type and name to search by.
   */
  @NotNull FindModelRequest findModel();

  /**
   * Merges any specified concepts into the list of concepts that are associated with this model.
   * <p>
   * If the IDs on any of the given concepts already exist on this model, the new concept will overwrite the old one.
   *
   * @param modelID the model to modify
   * @return a builder to construct a request that will, when executed, return the newly-modified model
   * @deprecated use {@link #modifyModel(String)}
   */
  @Deprecated
  @NotNull PatchModelRequest mergeConceptsForModel(@NotNull String modelID);

  /**
   * Overwrites the list of concepts on this model with the user's provided values.
   *
   * @param modelID the model to modify
   * @return a builder to construct a request that will, when executed, return the newly-modified model
   * @deprecated use {@link #modifyModel(String)}
   */
  @Deprecated
  @NotNull PatchModelRequest setConceptsForModel(@NotNull String modelID);

  /**
   * Removes the concepts with the given IDs from this model.
   *
   * @param modelID the model to modify
   * @return a builder to construct a request that will, when executed, return the newly-modified model
   * @deprecated use {@link #modifyModel(String)}
   */
  @Deprecated
  @NotNull PatchModelRequest removeConceptsForModel(@NotNull String modelID);

  /**
   * Allows the user to change the name output configuration, or concepts of their model.
   *
   * @param modelID the model to modify
   * @return a builder to construct a request that will, when executed, return the newly-modified model
   */
  @NotNull ModifyModelRequest modifyModel(@NotNull String modelID);

  /**
   * @param modelID the ID of the model to train
   * @return a request that, when executed, trains a new version of the {@link Model} with the given ID
   */
  @NotNull TrainModelRequest trainModel(@NotNull String modelID);

  /**
   * Predict using the model with the given ID.
   *
   * @param modelID the ID of the model to use
   * @return a request builder that, when executed, will predict upon your model
   */
  @NotNull PredictRequest<Prediction> predict(@NotNull String modelID);

  /**
   * Adds feedback for the searches in order to improve its future performance.
   *
   * @return a request builder that, when executed, adds feedback for the searches
   */
  @NotNull SearchesFeedbackRequest searchesFeedback();

  /**
   * Adds feedback to the model in order to improve its future performance.
   *
   * @param modelID the ID of the model to use
   * @return a request builder that, when executed, adds feedback to the model
   */
  @NotNull ModelFeedbackRequest modelFeedback(@NotNull String modelID);

  /**
   * Predicts using multiple models under the given workflow ID.
   *
   * @param workflowID the ID of the workflow to use
   * @return a request builder that, when executed, will predict upon the models in the workflow
   */
  @NotNull WorkflowPredictRequest workflowPredict(@NotNull String workflowID);

  /**
   * Enqueues the model for evaluation.
   *
   * @param modelID the ID of the model to use
   * @return a request builder that, when executed, runs model evaluation
   */
  @NotNull public RunModelEvaluationRequest runModelEvaluation(@NotNull String modelID, @NotNull String versionID);

  /**
   * Closes the {@link OkHttpClient} instances that this client uses to make HTTP requests.
   * <p>
   * Note that most users will not need to use this method. According to the OkHttp documentation, clients will
   * automatically relinquish resources that are unused over time. This method is only required if aggressive
   * relinquishment of resources is needed.
   * <p>
   * Using this {@link ClarifaiClient} instance after this method has been called is an error.
   */
  void close();
}


