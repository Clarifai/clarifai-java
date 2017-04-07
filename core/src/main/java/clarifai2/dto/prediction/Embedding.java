package clarifai2.dto.prediction;

import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

  @SuppressWarnings("mutable") @NotNull abstract float[] _embedding();
  @NotNull public abstract int numDimensions();

  Embedding() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<Embedding> {
    @Nullable @Override protected Deserializer<Embedding> deserializer() {
      return new Deserializer<Embedding>() {
        @Nullable @Override
        public Embedding deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Embedding> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);

          final float[] embedding = new float[root.get("num_dimensions").getAsInt()];
          {
            final JsonArray embeddingJSON = root.getAsJsonArray("vector");
            for (int i = 0; i < embeddingJSON.size(); i++) {
              embedding[i] = embeddingJSON.get(i).getAsFloat();
            }
          }
          return new AutoValue_Embedding(
              embedding,
              root.get("num_dimensions").getAsInt()
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<Embedding> typeToken() {
      return new TypeToken<Embedding>() {};
    }
  }
}

