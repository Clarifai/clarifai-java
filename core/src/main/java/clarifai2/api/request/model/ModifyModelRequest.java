package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static clarifai2.internal.InternalUtil.assertNotNull;
import static clarifai2.internal.InternalUtil.fromJson;

public final class ModifyModelRequest extends ClarifaiRequest.Builder<ConceptModel> {

  @NotNull private final String modelID;

  @Nullable private Action action = null;
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

  @NotNull @Override protected DeserializedRequest<ConceptModel> request() {
    return new DeserializedRequest<ConceptModel>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder model = new JSONObjectBuilder().add("id", modelID);
        if (name != null) {
          model.add("name", name);
        }

        final JSONObjectBuilder outputInfo = new JSONObjectBuilder();
        if (concepts != null) {
          outputInfo
              .add("data", new JSONObjectBuilder()
                  .add("concepts", new JSONArrayBuilder()
                      .addAll(concepts, new Func1<Concept, JsonElement>() {
                        @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                          return client.gson.toJsonTree(concept);
                        }
                      })
                  )
              );
        }
        final JSONObjectBuilder outputConfig = new JSONObjectBuilder();
        if (language != null) {
          outputConfig.add("language", language);
        }
        if (conceptsMutuallyExclusive != null || closedEnvironment != null) {
          outputConfig
              .add("concepts_mutually_exclusive", conceptsMutuallyExclusive)
              .add("closed_environment", closedEnvironment);
        }
        outputInfo.add("output_config", outputConfig.build());
        model.add("output_info", outputInfo);

        final JsonObject body = new JSONObjectBuilder()
            .add("models", new JSONArrayBuilder().add(model))
            .add("action", client.gson.toJsonTree(action))
            .build();
        return patchRequest("/v2/models", body);
      }

      @NotNull @Override public JSONUnmarshaler<ConceptModel> unmarshaler() {
        return new JSONUnmarshaler<ConceptModel>() {
          @NotNull @Override public ConceptModel fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return assertNotNull(fromJson(
                gson,
                json.getAsJsonObject().getAsJsonArray("models").get(0),
                new TypeToken<Model<?>>() {}
            )).asConceptModel();
          }
        };
      }
    };
  }
}
