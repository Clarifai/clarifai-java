package clarifai2.dto.model;

import clarifai2.internal.grpc.api.ModelVersionOuterClass;
import clarifai2.dto.HasClarifaiIDRequired;
import clarifai2.grpc.DateTimeConverter;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ModelVersion.Adapter.class)
public abstract class ModelVersion implements HasClarifaiIDRequired {

  public static int DEFAULT_COUNT = 0;

  ModelVersion() {} // AutoValue instances only

  @NotNull public abstract Date createdAt();

  @NotNull public abstract ModelTrainingStatus status();

  /*
   * The number of active concepts. 0 if not available.
   */
  @NotNull public abstract int activeConceptCount();

  /*
   * Model evaluation metrics status. null if not available.
   */
  @Nullable public abstract ModelMetricsStatus modelMetricsStatus();

  /*
   * The number of total inputs. 0 if not available.
   */
  @NotNull public abstract int totalInputCount();

  public static ModelVersion deserialize(ModelVersionOuterClass.ModelVersion modelVersion) {
    return new AutoValue_ModelVersion(
        modelVersion.getId(),
        DateTimeConverter.timestampToDate(modelVersion.getCreatedAt()),
        ModelTrainingStatus.deserialize(modelVersion.getStatus()),
        modelVersion.getActiveConceptCount(),
        modelVersion.hasMetrics() ? ModelMetricsStatus.deserialize(modelVersion.getMetrics().getStatus()) : null,
        modelVersion.getTotalInputCount()
    );
  }

  static class Adapter extends JSONAdapterFactory<ModelVersion> {
    @Nullable @Override protected Deserializer<ModelVersion> deserializer() {
      return new Deserializer<ModelVersion>() {
        @Nullable @Override
        public ModelVersion deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ModelVersion> type,
            @NotNull Gson gson
        ) {
          final String activeConceptCountField = "active_concept_count";
          final String totalInputCountField = "total_input_count";
          final String metricsField = "metrics";

          final JsonObject root = assertJsonIs(json, JsonObject.class);
          return new AutoValue_ModelVersion(
              root.get("id").getAsString(),
              fromJson(gson, root.get("created_at"), Date.class),
              fromJson(gson, root.get("status"), ModelTrainingStatus.class),
              root.has(activeConceptCountField) ? root.get(activeConceptCountField).getAsInt() : DEFAULT_COUNT,
              root.has(metricsField) ?
                  fromJson(gson, root.get(metricsField).getAsJsonObject().get("status"), ModelMetricsStatus.class)
                  : null,
              root.has(totalInputCountField) ? root.get(totalInputCountField).getAsInt() : DEFAULT_COUNT
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ModelVersion> typeToken() {
      return new TypeToken<ModelVersion>() {};
    }
  }
}