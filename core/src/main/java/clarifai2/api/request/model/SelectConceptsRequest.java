package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import clarifai2.internal.InternalUtil;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/*
 * NOTE: This class is a part of a future api.  Details of both the interface and
 * implementation may change!
 */

public final class SelectConceptsRequest<PREDICTION extends Prediction>
    extends ClarifaiRequest.Builder<List<ClarifaiOutput<PREDICTION>>> {

  @NotNull private final String modelID;
  @NotNull private final List<ClarifaiURLImage> images = new ArrayList<>();
  @NotNull private final List<Concept> concepts = new ArrayList<>();
  @Nullable private ModelVersion version = null;

  public SelectConceptsRequest(@NotNull final BaseClarifaiClient client, @NotNull String modelID) {
    super(client);
    this.modelID = modelID;
  }

  @NotNull public SelectConceptsRequest withImages(@NotNull ClarifaiURLImage... images) {
    return withImages(Arrays.asList(images));
  }

  @NotNull public SelectConceptsRequest withImages(@NotNull Collection<ClarifaiURLImage> images) {
    this.images.addAll(images);
    return this;
  }

  @NotNull public SelectConceptsRequest withConcepts(@NotNull Concept... concepts) {
    return withConcepts(Arrays.asList(concepts));
  }

  @NotNull public SelectConceptsRequest withConcepts(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull public SelectConceptsRequest withVersion(@NotNull ModelVersion version) {
    this.version = version;
    return this;
  }

  @NotNull @Override protected DeserializedRequest<List<ClarifaiOutput<PREDICTION>>> request() {
    return new DeserializedRequest<List<ClarifaiOutput<PREDICTION>>>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder()
            .add("inputs", new JSONArrayBuilder()
                .addAll(images, new Func1<ClarifaiURLImage, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull ClarifaiURLImage image) {
                    return new JSONObjectBuilder()
                        .add("data", new JSONObjectBuilder()
                            .add("image", client.gson.toJsonTree(image)))
                        .build();
                  }
                }));
        bodyBuilder.add("model", new JSONObjectBuilder()
            .add("output_info", new JSONObjectBuilder()
                .add("output_config", new JSONObjectBuilder()
                    .add("select_concepts", new JSONArrayBuilder()
                        .addAll(concepts, new Func1<Concept, JsonElement>() {
                          @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                            return client.gson.toJsonTree(concept);
                          }
                        })))));
        final JsonObject body = bodyBuilder.build();
        if (version == null) {
          return postRequest("/v2/models/" + modelID + "/outputs", body);
        }
        return postRequest("/v2/models/" + modelID + "/versions/" + version.id() + "/output", body);
      }

      @NotNull @Override public JSONUnmarshaler<List<ClarifaiOutput<PREDICTION>>> unmarshaler() {
        return new JSONUnmarshaler<List<ClarifaiOutput<PREDICTION>>>() {
          @NotNull @Override
          public List<ClarifaiOutput<PREDICTION>> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return InternalUtil.fromJson(
                gson,
                json.getAsJsonObject().get("outputs"),
                new TypeToken<List<ClarifaiOutput<PREDICTION>>>() {}
            );
          }
        };
      }
    };
  }
}

