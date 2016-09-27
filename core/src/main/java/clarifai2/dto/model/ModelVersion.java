package clarifai2.dto.model;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Date;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ModelVersion.Adapter.class)
public abstract class ModelVersion {

  @NotNull public abstract String id();
  @NotNull public abstract Date createdAt();
  @NotNull public abstract ModelTrainingStatus status();

  ModelVersion() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<ModelVersion> {
    @Override public ModelVersion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      return new AutoValue_ModelVersion(
          root.get("id").getAsString(),
          context.<Date>deserialize(root.get("created_at"), Date.class),
          context.<ModelTrainingStatus>deserialize(root.get("status"), ModelTrainingStatus.class)
      );
    }
  }
}