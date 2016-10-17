package clarifai2.api;

import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.api.request.concept.AddConceptsRequest;
import clarifai2.api.request.concept.DeleteConceptsFromInputRequest;
import clarifai2.api.request.input.AddConceptsToInputRequest;
import clarifai2.api.request.input.AddInputsRequest;
import clarifai2.api.request.input.DeleteInputsRequest;
import clarifai2.api.request.input.SearchClause;
import clarifai2.api.request.input.SearchInputsRequest;
import clarifai2.api.request.model.CreateModelRequest;
import clarifai2.api.request.model.FindModelRequest;
import clarifai2.api.request.model.GetModelInputsRequest;
import clarifai2.api.request.model.PatchModelRequest;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiInputsStatus;
import clarifai2.dto.model.DefaultModels;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
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
   * Adds the given concepts to the input with the given ID.
   *
   * @param inputID the input to modify
   * @return a builder to construct a request that will, when executed, return the newly-modified input
   */
  @NotNull AddConceptsToInputRequest addConceptsToInput(@NotNull String inputID);

  /**
   * Deletes the concepts with the given IDs from the input with the given ID.
   *
   * @param inputID the input to modify
   * @return a request that will, when executed, return the newly-modified input
   */
  @NotNull DeleteConceptsFromInputRequest deleteConceptsFromInput(@NotNull String inputID);

  /**
   * Get all of the inputs associated with this app
   *
   * @return a paginated request to look through all of the {@link ClarifaiInput}s in this app
   */
  @NotNull ClarifaiPaginatedRequest.Builder<List<ClarifaiInput>, ?> getInputs();

  /**
   * Get the input with the given ID
   *
   * @param inputID the ID of the input to get
   * @return a request that will, when executed, return the input that was retrieved
   */
  @NotNull ClarifaiRequest<ClarifaiInput> getInputByID(@NotNull String inputID);

  /**
   * Deletes the given inputs from your app.
   *
   * @return a builder to construct a request that will, when executed, return no inputs (but delete the given inputs)
   */
  @NotNull DeleteInputsRequest deleteInputs();

  /**
   * Deletes all inputs associated with this account
   *
   * @return a request that will, when executed, return no inputs (but delete all of your inputs)
   */
  @NotNull ClarifaiRequest<List<ClarifaiInput>> deleteAllInputs();

  /**
   * @return the current status of your Clarifai inputs (how many have been processed, how many are yet to be
   * processed, and how many errors occurred during processing)
   */
  @NotNull ClarifaiRequest<ClarifaiInputsStatus> getInputsStatus();

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
  @NotNull ClarifaiPaginatedRequest.Builder<List<Concept>, ?> getConcepts();

  /**
   * Get the concept associated with this concept ID
   *
   * @param conceptID the ID of the concept to get
   * @return a request that will, when executed, return the concept that was retrieved
   */
  @NotNull ClarifaiRequest<Concept> getConceptByID(@NotNull String conceptID);

  /**
   * Get the concepts that match this search string
   *
   * @param conceptSearchQuery the query-string to use to search concepts. Can contain '*' for wildcard searches; eg:
   *                           "l*" to search all concepts beginning with the letter 'l'.
   * @return a paginated request that, when executed, will return all of the concepts that match this search query
   */
  @NotNull ClarifaiPaginatedRequest.Builder<List<Concept>, ?> searchConcepts(@NotNull String conceptSearchQuery);

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
  @NotNull ClarifaiPaginatedRequest.Builder<List<Model<?>>, ?> getModels();

  @NotNull ClarifaiRequest<Model<?>> getModelByID(@NotNull String modelID);

  @NotNull ClarifaiRequest<List<Model<?>>> deleteModel(@NotNull String modelID);

  @NotNull ClarifaiRequest<List<ModelVersion>> deleteModelVersion(@NotNull String modelID, @NotNull String versionID);

  @NotNull ClarifaiRequest<List<Model<?>>> deleteAllModels();

  @NotNull ClarifaiRequest<ModelVersion> getModelVersionByID(@NotNull String modelID, @NotNull String versionID);

  /**
   * Returns all of the {@link ModelVersion}s for this {@link Model}
   *
   * @param modelID the id of the model
   * @return a request that, when executed, will return all of the versions associated with this model.
   */
  @NotNull ClarifaiPaginatedRequest.Builder<List<ModelVersion>, ?> getModelVersions(@NotNull String modelID);

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
   * @param modelID the ID of the model to modify
   * @return a request builder that allows you to link {@link Concept}s to a model
   */
  @NotNull PatchModelRequest addConceptsToModel(@NotNull String modelID);

  /**
   * @param modelID the ID of the model to modify
   * @return a request builder that allows you to unlink {@link Concept}s from a model
   */
  @NotNull PatchModelRequest deleteConceptsFromModel(@NotNull String modelID);

  /**
   * @param modelID the ID of the model to train
   * @return a request that, when executed, trains a new version of the {@link Model} with the given ID
   */
  @NotNull ClarifaiRequest<Model<?>> trainModel(@NotNull String modelID);

  /**
   * Predict using the model with the given ID.
   *
   * @param modelID the ID of the model to use
   * @return a request builder that, when executed, will predict upon your model
   */
  @NotNull PredictRequest<Prediction> predict(@NotNull String modelID);
}


