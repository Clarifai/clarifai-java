package clarifai2.dto.model;

import clarifai2.dto.model.output_info.FaceEmbeddingOutputInfo;
import clarifai2.dto.prediction.FaceEmbedding;
import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.internal.grpc.api.OutputOuterClass;
import clarifai2.dto.model.output_info.ClusterOutputInfo;
import clarifai2.dto.model.output_info.ColorOutputInfo;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.model.output_info.EmbeddingOutputInfo;
import clarifai2.dto.model.output_info.LogoOutputInfo;
import clarifai2.dto.model.output_info.OutputInfo;
import clarifai2.dto.model.output_info.UnknownOutputInfo;
import clarifai2.dto.model.output_info.VideoOutputInfo;
import clarifai2.dto.prediction.Cluster;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Embedding;
import clarifai2.dto.prediction.Frame;
import clarifai2.dto.prediction.Logo;
import clarifai2.dto.prediction.Prediction;
import clarifai2.dto.prediction.Unknown;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public enum ModelType {
  CONCEPT(
      "concept",
      "concept",
      ConceptOutputInfo.class,
      Concept.class
  ),
  EMBEDDING(
      "embed",
      "embed",
      EmbeddingOutputInfo.class,
      Embedding.class
  ),
  FACE_EMBEDDING(
      "embed",
      "detect-embed",
      FaceEmbeddingOutputInfo.class,
      FaceEmbedding.class
  ),
  COLOR(
      "color",
      "color",
      ColorOutputInfo.class,
      Color.class
  ),
  CLUSTER(
      "cluster",
      "cluster",
      ClusterOutputInfo.class,
      Cluster.class
  ),
  LOGO(
      "logo",
      "detection",
      LogoOutputInfo.class,
      Logo.class
  ),
  VIDEO(
      "video",
      "video",
      VideoOutputInfo.class,
      Frame.class
  ),
  UNKNOWN(
      "unknown",
      "unknown",
      UnknownOutputInfo.class,
      Unknown.class
  );

  @NotNull private final String type;
  @NotNull private final String typeExt;
  @NotNull private final Class<? extends OutputInfo> infoType;
  @NotNull private final Class<? extends Prediction> tagType;

  ModelType(
      @NotNull String type,
      @NotNull String typeExt,
      @NotNull Class<? extends OutputInfo> infoType,
      @NotNull Class<? extends Prediction> tagType
  ) {
    this.type = type;
    this.typeExt = typeExt;
    this.infoType = infoType;
    this.tagType = tagType;
  }

  @NotNull public static ModelType determineModelType(OutputOuterClass.Output output) {
    String typeExt = output.getModel().getOutputInfo().getTypeExt();

    ModelType determinedModelType = determineModelType(typeExt);

    if (determinedModelType == CONCEPT && output.getData().getFramesCount() > 0) {
      determinedModelType = VIDEO;
    }

    return determinedModelType;
  }

  @NotNull public static ModelType determineModelType(ModelOuterClass.OutputInfo outputInfo) {
    String typeExt = outputInfo.getTypeExt();
    return determineModelType(typeExt);
  }

  @NotNull public static ModelType determineModelType(@NotNull JsonElement outputInfoRoot) {
    String typeExt = outputInfoRoot.getAsJsonObject().get("type_ext").getAsString();
    return determineModelType(typeExt);
  }

  @NotNull public static ModelType determineModelType(@NotNull String typeExt) {
    for (final ModelType value : values()) {
      if (value.typeExt().equals(typeExt)) {
        return value;
      }
    }
    return UNKNOWN;
  }

  @NotNull public String typeName() {
    return type;
  }

  @NotNull public String typeExt() {
    return typeExt;
  }

  @NotNull public Class<? extends OutputInfo> infoType() {
    return infoType;
  }

  @NotNull public Class<? extends Prediction> predictionType() {
    return tagType;
  }
}
