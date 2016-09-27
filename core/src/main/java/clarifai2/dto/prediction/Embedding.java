package clarifai2.dto.prediction;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Arrays;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Embedding.Adapter.class)
public abstract class Embedding extends Prediction {

  @NotNull public final float[] embedding() {
    // Return a defensive copy so that they can't modify the original byte-array
    final float[] embedding = _embedding();
    return Arrays.copyOf(embedding, embedding.length);
  }

  @SuppressWarnings("mutable") @NotNull public abstract float[] _embedding();
  @NotNull public abstract int numDimensions();

  Embedding() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<Embedding> {
    @Override public Embedding deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();

      final float[] embedding = new float[1024];
      {
        final JsonArray embeddingJSON = root.getAsJsonArray("embedding");
        for (int i = 0; i < embeddingJSON.size(); i++) {
          embedding[i] = embeddingJSON.get(i).getAsFloat();
        }
      }
      return new AutoValue_Embedding(
          embedding,
          root.get("num_dimensions").getAsInt()
      );
    }
  }
}

