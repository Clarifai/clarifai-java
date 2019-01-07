package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.internal.grpc.api.EmbeddingOuterClass;
import clarifai2.dto.input.Crop;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceEmbedding extends Prediction {

  FaceEmbedding() {} // AutoValue instances only

  @NotNull public abstract Crop crop();

  @NotNull public abstract List<Embedding> embeddings();


  @NotNull public static FaceEmbedding deserialize(DataOuterClass.Region region) {
    List<Embedding> embeddings = new ArrayList<>();
    for (EmbeddingOuterClass.Embedding embedding : region.getData().getEmbeddingsList()) {
      embeddings.add(Embedding.deserialize(embedding));
    }
    return new AutoValue_FaceEmbedding(Crop.deserialize(region.getRegionInfo().getBoundingBox()), embeddings);
  }
}
