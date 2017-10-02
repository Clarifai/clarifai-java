package clarifai2.dto.model;

import clarifai2.dto.model.output_info.FaceConceptsOutputInfo;
import clarifai2.dto.model.output_info.ClusterOutputInfo;
import clarifai2.dto.model.output_info.ColorOutputInfo;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.model.output_info.DemographicsOutputInfo;
import clarifai2.dto.model.output_info.EmbeddingOutputInfo;
import clarifai2.dto.model.output_info.FaceDetectionOutputInfo;
import clarifai2.dto.model.output_info.FaceEmbeddingOutputInfo;
import clarifai2.dto.model.output_info.FocusOutputInfo;
import clarifai2.dto.model.output_info.LogoOutputInfo;
import clarifai2.dto.model.output_info.OutputInfo;
import clarifai2.dto.model.output_info.UnknownOutputInfo;
import clarifai2.dto.model.output_info.VideoOutputInfo;
import clarifai2.dto.prediction.FaceConcepts;
import clarifai2.dto.prediction.Cluster;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Embedding;
import clarifai2.dto.prediction.FaceDetection;
import clarifai2.dto.prediction.FaceEmbedding;
import clarifai2.dto.prediction.Focus;
import clarifai2.dto.prediction.Frame;
import clarifai2.dto.prediction.Logo;
import clarifai2.dto.prediction.Prediction;
import clarifai2.dto.prediction.Region;
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
  DEMOGRAPHICS(
      "facedetect",
      "facedetect-demographics",
      DemographicsOutputInfo.class,
      Region.class
  ),
  FACE_DETECTION(
      "facedetect",
      "facedetect",
      FaceDetectionOutputInfo.class,
      FaceDetection.class
  ),
  FACE_CONCEPTS(
      "facedetect",
      "facedetect-identity",
      FaceConceptsOutputInfo.class,
      FaceConcepts.class
  ),
  FOCUS(
      "blur",
      "focus",
      FocusOutputInfo.class,
      Focus.class
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

  @NotNull public static ModelType determineModelType(@NotNull JsonElement outputInfoRoot) {
    String typeExt = outputInfoRoot.getAsJsonObject().get("type_ext").getAsString();
    if (typeExt.equals("facedetect-celebrity")) {
      typeExt = "facedetect-identity";
    }
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
