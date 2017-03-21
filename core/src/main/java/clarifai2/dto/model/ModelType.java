package clarifai2.dto.model;

import clarifai2.dto.model.output_info.BlurOutputInfo;
import clarifai2.dto.model.output_info.ClusterOutputInfo;
import clarifai2.dto.model.output_info.ColorOutputInfo;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.model.output_info.EmbeddingOutputInfo;
import clarifai2.dto.model.output_info.FaceDetectionOutputInfo;
import clarifai2.dto.model.output_info.OutputInfo;
import clarifai2.dto.model.output_info.UnknownOutputInfo;
import clarifai2.dto.prediction.Blur;
import clarifai2.dto.prediction.Cluster;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Embedding;
import clarifai2.dto.prediction.FaceDetection;
import clarifai2.dto.prediction.Prediction;
import clarifai2.dto.prediction.Unknown;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public enum ModelType {
  CONCEPT(
      "concept",
      "concepts",
      ConceptOutputInfo.class,
      Concept.class
  ),
  EMBEDDING(
      "embed",
      "embeddings",
      EmbeddingOutputInfo.class,
      Embedding.class
  ),
  COLOR(
      "color",
      "colors",
      ColorOutputInfo.class,
      Color.class
  ),
  FACE_DETECTION(
      "facedetect",
      "regions",
      FaceDetectionOutputInfo.class,
      FaceDetection.class
  ),
  BLUR(
      "blur",
      "blurs",
      BlurOutputInfo.class,
      Blur.class
  ),
  CLUSTER(
      "cluster",
      "clusters",
      ClusterOutputInfo.class,
      Cluster.class
  ),
  UNKNOWN(
      "unknown",
      "unknowns",
      UnknownOutputInfo.class,
      Unknown.class
  );

  @NotNull private final String typeName;
  @NotNull private final String dataArrayName;
  @NotNull private final Class<? extends OutputInfo> infoType;
  @NotNull private final Class<? extends Prediction> tagType;

  ModelType(
      @NotNull String typeName,
      @NotNull String dataArrayName,
      @NotNull Class<? extends OutputInfo> infoType,
      @NotNull Class<? extends Prediction> tagType
  ) {
    this.typeName = typeName;
    this.dataArrayName = dataArrayName;
    this.infoType = infoType;
    this.tagType = tagType;
  }

  @NotNull public String typeName() {
    return typeName;
  }

  @NotNull public Class<? extends OutputInfo> infoType() {
    return infoType;
  }

  @NotNull public Class<? extends Prediction> predictionType() {
    return tagType;
  }

  @NotNull public static ModelType determineFromDataRoot(@NotNull JsonObject dataRoot) {
    for (final ModelType value : values()) {
      if (dataRoot.has(value.dataArrayName)) {
        return value;
      }
    }
    return UNKNOWN;
  }

  @NotNull public static ModelType determineFromOutputInfoRoot(@NotNull JsonElement outputInfoRoot) {
    final String type = outputInfoRoot.getAsJsonObject().get("type").getAsString();
    for (final ModelType value : values()) {
      if (value.typeName().equals(type)) {
        return value;
      }
    }
    return UNKNOWN;
  }
}
