package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.EmbeddingOuterClass;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Embedding extends Prediction {

  Embedding() {} // AutoValue instances only

  @NotNull public final float[] embedding() {
    // Return a defensive copy so that they can't modify the original byte-array
    final float[] embedding = _embedding();
    return Arrays.copyOf(embedding, embedding.length);
  }

  @SuppressWarnings("PMD.MethodNamingConventions")
  @NotNull abstract float[] _embedding();

  @NotNull public abstract int numDimensions();


  @NotNull public static Embedding deserialize(EmbeddingOuterClass.Embedding embeddingResponse) {
    List<Float> vectorList = embeddingResponse.getVectorList();
    float[] embedding = new float[vectorList.size()];
    for (int i = 0; i < vectorList.size(); i++) {
      embedding[i] = vectorList.get(i);
    }
    return new AutoValue_Embedding(
        embedding,
        embeddingResponse.getNumDimensions()
    );
  }
}

