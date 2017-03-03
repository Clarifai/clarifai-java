package clarifai2.dto.model;

import clarifai2.dto.model.output_info.ClusterOutputInfo;
import clarifai2.dto.model.output_info.ColorOutputInfo;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.model.output_info.DemographicsOutputInfo;
import clarifai2.dto.model.output_info.EmbeddingOutputInfo;
import clarifai2.dto.model.output_info.FaceDetectionOutputInfo;
import clarifai2.dto.model.output_info.OutputInfo;
import clarifai2.dto.model.output_info.UnknownOutputInfo;
import clarifai2.dto.prediction.Cluster;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Embedding;
import clarifai2.dto.prediction.FaceDetection;
import clarifai2.dto.prediction.Focus;
import clarifai2.dto.prediction.Logo;
import clarifai2.dto.prediction.Prediction;
import clarifai2.dto.prediction.Region;
import clarifai2.dto.prediction.Unknown;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public enum ModelType {
  CONCEPT(
      "concept",
      ConceptOutputInfo.class,
      Concept.class
  ),
  EMBEDDING(
      "embed",
      EmbeddingOutputInfo.class,
      Embedding.class
  ),
  COLOR(
      "color",
      ColorOutputInfo.class,
      Color.class
  ),
  DEMOGRAPHICS(
      "facedetect",
      DemographicsOutputInfo.class,
      Region.class
  ),
  FACE_DETECTION(
      "facedetect",
      FaceDetectionOutputInfo.class,
      FaceDetection.class
  ),
  FOCUS(
      "blur",
      FocusOutputInfo.class,
      Focus.class
  ),
  CLUSTER(
      "cluster",
      ClusterOutputInfo.class,
      Cluster.class
  ),
  LOGO(
      "logo",
      DemographicsOutputInfo.class,
      Logo.class
  ),
  UNKNOWN(
      "unknown",
      UnknownOutputInfo.class,
      Unknown.class
  );

  @NotNull private final String typeName;
  @NotNull private final Class<? extends OutputInfo> infoType;
  @NotNull private final Class<? extends Prediction> tagType;

  ModelType(
      @NotNull String typeName,
      @NotNull Class<? extends OutputInfo> infoType,
      @NotNull Class<? extends Prediction> tagType
  ) {
    this.typeName = typeName;
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

  @NotNull public static ModelType determineModelType(@NotNull JsonElement outputInfoRoot) {
    final String type = outputInfoRoot.getAsJsonObject().get("type").getAsString();
    final String typeExt = outputInfoRoot.getAsJsonObject().get("type_ext").getAsString();
    for (final ModelType value : values()) {
      if (value.typeName().equals(type)) {
        // Logo model is ambiguous.
        if (value == CONCEPT) {
          return "detection".equals(typeExt) ? LOGO : value;
        }
        return value;
      }
    }
    return UNKNOWN;
  }
}