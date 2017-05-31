package clarifai2.dto.model;

import clarifai2.dto.HasClarifaiIDRequired;
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

  ModelVersion() {} // AutoValue instances only

  @NotNull public abstract Date createdAt();

  @NotNull public abstract ModelTrainingStatus status();


  static class Adapter extends JSONAdapterFactory<ModelVersion> {
    @Nullable @Override protected Deserializer<ModelVersion> deserializer() {
      return new Deserializer<ModelVersion>() {
        @Nullable @Override
        public ModelVersion deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ModelVersion> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          return new AutoValue_ModelVersion(
              root.get("id").getAsString(),
              fromJson(gson, root.get("created_at"), Date.class),
              fromJson(gson, root.get("status"), ModelTrainingStatus.class)
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ModelVersion> typeToken() {
      return new TypeToken<ModelVersion>() {};
    }
  }
}