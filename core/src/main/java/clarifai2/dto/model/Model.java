package clarifai2.dto.model;

import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.api.request.model.GetModelInputsRequest;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.api.request.model.TrainModelRequest;
import clarifai2.dto.HasClarifaiIDRequired;
import clarifai2.dto.model.output_info.OutputInfo;
import clarifai2.dto.prediction.Prediction;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONObjectBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.kevinmost.auto.value.custom_hashcode_equals.adapter.IgnoreForHashCodeEquals;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

import static clarifai2.internal.InternalUtil.clientInstance;
import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.isJsonNull;
import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@JsonAdapter(Model.Adapter.class)
public abstract class Model<PREDICTION extends Prediction> implements HasClarifaiIDRequired {

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

  public final boolean isEmbeddingModel() {
    return this instanceof EmbeddingModel;
  }

  @NotNull public final EmbeddingModel asEmbeddingModel() {
    return ((EmbeddingModel) this);
  }

  public final boolean isFocusModel() {
    return this instanceof FocusModel;
  }

  @NotNull public final FocusModel asFocusModel() {
    return ((FocusModel) this);
  }

  public final boolean isClusterModel() {
    return this instanceof ClusterModel;
  }

  @NotNull public final ClusterModel asClusterModel() {
    return ((ClusterModel) this);
  }

  @Nullable public abstract String name();
  @Nullable public abstract Date createdAt();
  @Nullable public abstract String appID();
  @Nullable public abstract ModelVersion modelVersion();
  @NotNull public abstract ModelType modelType();
  @Nullable public abstract OutputInfo outputInfo();
  @IgnoreForHashCodeEquals @Nullable abstract OutputInfo _outputInfo();

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
      case FOCUS:
        return new AutoValue_FocusModel.Builder();
      case CLUSTER:
        return new AutoValue_ClusterModel.Builder();
      case COLOR:
        return new AutoValue_ColorModel.Builder();
      case EMBEDDING:
        return new AutoValue_EmbeddingModel.Builder();
      case LOGO:
        return new AutoValue_LogoModel.Builder();
      case FACE_DETECTION:
        return new AutoValue_FaceDetectionModel.Builder();
      case DEMOGRAPHICS:
        return new AutoValue_DemographicsModel.Builder();
      case VIDEO:
        return new AutoValue_VideoModel.Builder();
      default:
        return new AutoValue_UnknownModel.Builder();
    }
  }


  @SuppressWarnings("rawtypes")
  static class Adapter extends JSONAdapterFactory<Model> {
    @Nullable @Override protected Serializer<Model> serializer() {
      return new Serializer<Model>() {
        @NotNull @Override public JsonElement serialize(@Nullable Model value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("id", value.id())
              .add("name", value.name())
              .add("app_id", value.appID())
              .add("created_at", toJson(gson, value.createdAt(), Date.class))
              .add("model_version", toJson(gson, value.modelVersion(), ModelVersion.class))
              .add("output_info", toJson(gson, value._outputInfo(), OutputInfo.class))
              .build();
        }
      };
    }

    @Nullable @Override protected Deserializer<Model> deserializer() {
      return new Deserializer<Model>() {
        @Nullable @Override
        public Model deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Model> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = json.getAsJsonObject();
          ModelType modelType = ModelType.determineModelType(root.get("output_info"));
          // hacky solution needed because of model type ambiguity.
          if (modelType == ModelType.DEMOGRAPHICS|| modelType == ModelType.FACE_DETECTION) {
            if (root.getAsJsonPrimitive("name").getAsString().equals("demographics")) {
              modelType = ModelType.DEMOGRAPHICS;
            } else {
              modelType = ModelType.FACE_DETECTION;
            }
          }
          return getBuilder(modelType)
              .id(root.get("id").getAsString())
              .name(root.get("name").getAsString())
              .createdAt(fromJson(gson, root.get("created_at"), Date.class))
              .appID(root.get("app_id") == null ? null : root.get("app_id").getAsString())
              .modelVersion(fromJson(gson, root.get("model_version"), ModelVersion.class))
              ._outputInfo(fromJson(gson, root.get("output_info"), OutputInfo.class))
              .client(clientInstance(gson))
              .build()
              ;
        }
      };
    }

    @NotNull @Override protected TypeToken<Model> typeToken() {
      return new TypeToken<Model>() {};
    }
  }
}
