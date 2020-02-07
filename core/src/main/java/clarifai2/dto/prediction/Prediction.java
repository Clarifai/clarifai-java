package clarifai2.dto.prediction;

import org.jetbrains.annotations.NotNull;

public abstract class Prediction {
  Prediction() {}

  public final boolean isConcept() {
    return this instanceof Concept;
  }

  @NotNull public final Concept asConcept() {
    return (Concept) this;
  }

  public final boolean isColor() {
    return this instanceof Color;
  }

  @NotNull public final Color asColor() {
    return (Color) this;
  }

  @NotNull public final FaceEmbedding asFaceEmbedding() {
    return (FaceEmbedding) this;
  }

  @NotNull public final Detection asDetection() {
    return (Detection) this;
  }

  public final boolean isBlur() {
    return this instanceof Blur;
  }

  @NotNull public final Blur asBlur() {
    return (Blur) this;
  }

  public final boolean isEmbedding() {
    return this instanceof Embedding;
  }

  @NotNull public final Embedding asEmbedding() {
    return (Embedding) this;
  }

  public final boolean isCluster() {
    return this instanceof Cluster;
  }

  @NotNull public final Cluster asCluster() {
    return (Cluster) this;
  }

  @NotNull public final Region asRegion() {
    return (Region) this;
  }

  public final boolean isFrame() {
    return this instanceof Frame;
  }

  @NotNull public final Frame asFrame() {
    return (Frame) this;
  }
}
