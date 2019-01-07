package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.prediction.Concept;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @deprecated use {@link ModifyModelRequest}
 */
@Deprecated
public final class PatchModelRequest extends ClarifaiRequest.Builder<ConceptModel> {

  @NotNull private final String modelID;
  @NotNull private final Action action;
  @NotNull private final List<Concept> concepts = new ArrayList<>();

  @Nullable private String language = null;

  public PatchModelRequest(
      @NotNull final BaseClarifaiClient helper,
      @NotNull String modelID,
      @NotNull Action action
  ) {
    super(helper);
    this.modelID = modelID;
    this.action = action;
  }

  @NotNull public PatchModelRequest plus(@NotNull Concept... concepts) {
    return plus(Arrays.asList(concepts));
  }

  @NotNull public PatchModelRequest plus(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull public PatchModelRequest withLanguage(@NotNull String language) {
    this.language = language;
    return this;
  }

  @NotNull @Override protected ClarifaiRequest<ConceptModel> build() {
    if (language != null) {
      return new ModifyModelRequest(client, modelID).withConcepts(action, concepts).withLanguage(language);
    } else {
      return new ModifyModelRequest(client, modelID).withConcepts(action, concepts);
    }
  }

  // Because this class is just a wrapper around ModifyModelRequest.
  @NotNull @Override protected String method() { return null; }
  @NotNull @Override protected String subUrl() { return null; }

  @NotNull @Override protected DeserializedRequest<ConceptModel> request() {
    throw new UnsupportedOperationException();
  }
}
