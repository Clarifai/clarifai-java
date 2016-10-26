package clarifai2.dto.model;

import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.api.request.model.GetModelInputsRequest;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.api.request.model.TrainModelRequest;
import clarifai2.dto.model.output_info.OutputInfo;
import clarifai2.dto.prediction.Prediction;
import clarifai2.exception.ClarifaiException;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONObjectBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.kevinmost.auto.value.custom_hashcode_equals.adapter.IgnoreForHashCodeEquals;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@SuppressWarnings("NullableProblems")
@JsonAdapter(Model.Adapter.class)
public abstract class Model<PREDICTION extends Prediction> {

  @SuppressWarnings("unchecked")
  @NotNull
  public static <T extends Model<?>> T _create(
      @NotNull ModelType modelType,
      @NotNull ClarifaiClient helper,
      @NotNull String id,
      @NotNull String name,
      @Nullable OutputInfo outputInfo
  ) {
    return ((T) getBuilder(modelType)
        .client(helper)
        .id(id)
        .name(name)
        ._outputInfo(outputInfo)
        .build());
  }

  public final boolean isConceptModel() {
    return this instanceof ConceptModel;
  }

  @NotNull public final ConceptModel asConceptModel() {
    return (ConceptModel) this;
  }

  public final boolean isColorModel() {
    return this instanceof ColorModel;
  }

  @NotNull public final ColorModel asColorModel() {
    return ((ColorModel) this);
  }

  public final boolean isFaceDetectionModel() {
    return this instanceof FaceDetectionModel;
  }

  @NotNull public final FaceDetectionModel asFaceDetectionModel() {
    return ((FaceDetectionModel) this);
  }

  public final boolean isBlurModel() {
    return this instanceof BlurModel;
  }

  @NotNull public final BlurModel asBlurModel() {
    return ((BlurModel) this);
  }

  public final boolean isEmbeddingModel() {
    return this instanceof EmbeddingModel;
  }

  @NotNull public final EmbeddingModel asEmbeddingModel() {
    return ((EmbeddingModel) this);
  }

  public final boolean isClusterModel() {
    return this instanceof ClusterModel;
  }

  @NotNull public final ClusterModel asClusterModel() {
    return ((ClusterModel) this);
  }

  @NotNull public abstract String id();
  @Nullable public abstract String name();
  @Nullable public abstract Date createdAt();
  @Nullable public abstract Date updatedAt();
  @Nullable public abstract String appID();
  @Nullable public abstract ModelVersion modelVersion();
  @NotNull public abstract ModelType modelType();
  @Nullable public abstract OutputInfo outputInfo();
  @Nullable abstract OutputInfo _outputInfo();

  @IgnoreForHashCodeEquals @NotNull abstract ClarifaiClient client();

  @NotNull public final GetModelInputsRequest getInputs() {
    return client().getModelInputs(id());
  }

  @NotNull public final ClarifaiPaginatedRequest<List<ModelVersion>> getVersions() {
    return client().getModelVersions(id());
  }

  @NotNull public final PredictRequest<PREDICTION> predict() {
    //noinspection unchecked
    return (PredictRequest<PREDICTION>) client().predict(id());
  }

  @NotNull public final ClarifaiRequest<ModelVersion> getVersionByID(@NotNull String versionID) {
    return client().getModelVersionByID(id(), versionID);
  }

  @NotNull public final ClarifaiRequest<List<ModelVersion>> deleteVersion(@NotNull ModelVersion version) {
    return deleteVersion(version.id());
  }

  @NotNull public final ClarifaiRequest<List<ModelVersion>> deleteVersion(@NotNull String versionID) {
    return client().deleteModelVersion(id(), versionID);
  }

  @NotNull public final TrainModelRequest train() {
    return client().trainModel(id());
  }

  Model() {} // AutoValue instances only

  protected interface Builder<B extends Builder<B>> {
    @NotNull B id(@NotNull String id);
    @NotNull B name(@NotNull String name);
    @NotNull B createdAt(@Nullable Date createdAt);
    @NotNull B updatedAt(@Nullable Date updatedAt);
    @NotNull B appID(@Nullable String appID);
    @NotNull B modelVersion(@Nullable ModelVersion modelVersion);
    @NotNull B _outputInfo(@Nullable OutputInfo _outputInfo);
    @NotNull B client(@Nullable ClarifaiClient client);
    @NotNull Model<?> build();
  }

  @NotNull private static Builder<?> getBuilder(@NotNull ModelType modelType) {
    switch (modelType) {
      case CONCEPT:
        return new AutoValue_ConceptModel.Builder();
      case BLUR:
        return new AutoValue_BlurModel.Builder();
      case CLUSTER:
        return new AutoValue_ClusterModel.Builder();
      case COLOR:
        return new AutoValue_ColorModel.Builder();
      case EMBEDDING:
        return new AutoValue_EmbeddingModel.Builder();
      case FACE_DETECTION:
        return new AutoValue_FaceDetectionModel.Builder();
      default:
        throw new ClarifaiException(
            "This version of the Clarifai API client does not support models of type " + modelType
        );
    }
  }


  static class Adapter implements JsonSerializer<Model<?>>, JsonDeserializer<Model<?>> {
    @Override
    public Model<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      final ModelType modelType = ModelType.determineFromOutputInfoRoot(root.get("output_info"));
      return getBuilder(modelType)
          .id(root.get("id").getAsString())
          .name(root.get("name").getAsString())
          .createdAt(context.<Date>deserialize(root.get("created_at"), Date.class))
          .updatedAt(context.<Date>deserialize(root.get("updated_at"), Date.class))
          .appID(InternalUtil.<String>nullSafeTraverse(root, "app_id"))
          .modelVersion(context.<ModelVersion>deserialize(root.get("model_version"), ModelVersion.class))
          ._outputInfo(context.<OutputInfo>deserialize(root.get("output_info"), OutputInfo.class))
          .client(context.<ClarifaiClient>deserialize(new JsonObject(), ClarifaiClient.class))
          .build()
          ;
    }

    @Override public JsonElement serialize(Model<?> src, Type typeOfSrc, JsonSerializationContext context) {
      return new JSONObjectBuilder()
          .add("id", src.id())
          .add("name", src.name())
          .add("app_id", src.appID())
          .add("created_at", context.serialize(src.createdAt()))
          .add("updated_at", context.serialize(src.updatedAt()))
          .add("model_version", context.serialize(src.modelVersion()))
          .add("output_info", context.serialize(src._outputInfo()))
          .build();
    }
  }
}
