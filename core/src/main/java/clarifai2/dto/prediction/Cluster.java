package clarifai2.dto.prediction;

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

import static clarifai2.internal.InternalUtil.assertJsonIs;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Cluster.Adapter.class)
public abstract class Cluster extends Prediction implements HasClarifaiIDRequired {

  @NotNull public abstract int numClusters();

  Cluster() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<Cluster> {
    @Nullable @Override protected Deserializer<Cluster> deserializer() {
      return new Deserializer<Cluster>() {
        @Nullable @Override
        public Cluster deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Cluster> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          return new AutoValue_Cluster(
              root.get("id").getAsString(),
              root.get("num_clusters").getAsInt()
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<Cluster> typeToken() {
      return new TypeToken<Cluster>() {};
    }
  }
}