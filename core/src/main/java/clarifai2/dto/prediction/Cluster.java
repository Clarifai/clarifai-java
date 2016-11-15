package clarifai2.dto.prediction;

import clarifai2.dto.HasClarifaiIDRequired;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Cluster.Adapter.class)
public abstract class Cluster extends Prediction implements HasClarifaiIDRequired {

  @NotNull public abstract int numClusters();

  Cluster() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<Cluster> {
    @Override public Cluster deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      return new AutoValue_Cluster(
          root.get("id").getAsString(),
          root.get("num_clusters").getAsInt()
      );
    }
  }
}