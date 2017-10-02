package clarifai2.dto.prediction;

import clarifai2.dto.input.Crop;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(FaceEmbedding.Adapter.class)
public abstract class FaceEmbedding extends Prediction {

  FaceEmbedding() {} // AutoValue instances only

  @NotNull public abstract Crop crop();

  @NotNull public abstract List<Embedding> embeddings();


  static class Adapter extends JSONAdapterFactory<FaceEmbedding> {
    @Nullable @Override protected Deserializer<FaceEmbedding> deserializer() {
      return new Deserializer<FaceEmbedding>() {
        @Nullable @Override
        public FaceEmbedding deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<FaceEmbedding> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          Crop crop = fromJson(gson, root.getAsJsonObject()
              .getAsJsonObject("region_info")
              .getAsJsonObject("bounding_box"), Crop.class);
          List<Embedding> embeddings = new ArrayList<>();
          for (JsonElement embeddingElement
              : root.getAsJsonObject().getAsJsonObject("data").getAsJsonArray("embeddings")) {
            embeddings.add(fromJson(gson, embeddingElement, Embedding.class));
          }
          if (crop == null) {
            throw new IllegalArgumentException("Crop cannot be null");
          }
          return new AutoValue_FaceEmbedding(crop, embeddings);
        }
      };
    }

    @NotNull @Override protected TypeToken<FaceEmbedding> typeToken() {
      return new TypeToken<FaceEmbedding>() {};
    }
  }
}
