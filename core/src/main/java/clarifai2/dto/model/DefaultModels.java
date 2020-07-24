package clarifai2.dto.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.model.GetModelRequest;
import org.jetbrains.annotations.NotNull;

/**
 * A collection of default models. NOTE: These models are not complete, which means
 * that createdAt, appID, modelVersion, and outputInfo are not populated. If you require
 * this data, execute {@link GetModelRequest} to acquire a complete model.
 */
public final class DefaultModels {

  @NotNull private final ConceptModel general;
  @NotNull private final ConceptModel food;
  @NotNull private final ConceptModel travel;
  @NotNull private final ConceptModel nsfw;
  @NotNull private final ConceptModel wedding;
  @NotNull private final ConceptModel apparel;
  @NotNull private final ConceptModel moderation;
  @NotNull private final ConceptModel landscapeQuality;
  @NotNull private final ConceptModel portraitQuality;
  @NotNull private final ConceptModel texturesAndPatterns;
  @NotNull private final DetectionModel logo;
  @NotNull private final DetectionModel face;
  @NotNull private final DetectionModel demographics;
  @NotNull private final DetectionModel celebrity;
  @NotNull private final ColorModel color;
  @NotNull private final EmbeddingModel generalEmbed;
  @NotNull private final FaceEmbeddingModel faceEmbed;
  @NotNull private final VideoModel generalVideo;
  @NotNull private final VideoModel foodVideo;
  @NotNull private final VideoModel nsfwVideo;
  @NotNull private final VideoModel travelVideo;
  @NotNull private final VideoModel weddingVideo;
  @NotNull private final VideoModel apparelVideo;

  public DefaultModels(@NotNull BaseClarifaiClient client) {
    general = create(ModelType.CONCEPT, client, "aaa03c23b3724a16a56b629203edc62c", "general-v1.3");
    food = create(ModelType.CONCEPT, client, "bd367be194cf45149e75f01d59f77ba7", "food-items-v1.0");
    travel = create(ModelType.CONCEPT, client, "eee28c313d69466f836ab83287a54ed9", "travel-v1.0");
    nsfw = create(ModelType.CONCEPT, client, "e9576d86d2004ed1a38ba0cf39ecb4b1", "nsfw-v1.0");
    wedding = create(ModelType.CONCEPT, client, "c386b7a870114f4a87477c0824499348", "weddings-v1.0");
    apparel = create(ModelType.CONCEPT, client, "e0be3b9d6a454f0493ac3a30784001ff", "apparel");
    moderation = create(ModelType.CONCEPT, client, "d16f390eb32cad478c7ae150069bd2c6", "moderation");
    landscapeQuality = create(ModelType.CONCEPT, client, "bec14810deb94c40a05f1f0eb3c91403", "landscape-quality");
    portraitQuality = create(ModelType.CONCEPT, client, "de9bd05cfdbf4534af151beb2a5d0953", "portrait-quality");
    texturesAndPatterns = create(ModelType.CONCEPT, client, "fbefb47f9fdb410e8ce14f24f54b47ff", "textures-patterns");
    logo = create(ModelType.DETECT_CONCEPT, client, "c443119bf2ed4da98487520d01a0b1e3", "logo-v0.4");
    color = create(ModelType.COLOR, client, "eeed0b6733a644cea07cf4c60f87ebb7", "color");
    face = create(ModelType.DETECT_CONCEPT, client, "53e1df302c079b3db8a0a36033ed2d15", "face-v3.1-cluster");
    celebrity = create(ModelType.DETECT_CONCEPT, client, "e466caa0619f444ab97497640cefc4dc", "celeb-v1.3");
    demographics = create(ModelType.DETECT_CONCEPT, client, "c0c0ac362b03416da06ab3fa36fb58e3", "age-gender-ethnicity");
    generalEmbed = create(ModelType.EMBEDDING, client, "bbb5f41425b8468d9b7a554ff10f8581", "general-v1.3");
    faceEmbed = create(ModelType.FACE_EMBEDDING, client, "e15d0f873e66047e579f90cf82c9882z", "face-v3.1");

    // This is very poor practice, the user should just be able to enter either an image or a video, and the client
    // should just handle it as needed. However, the client will need to be refactored before this is possible.
    generalVideo = create(ModelType.VIDEO, client, "aaa03c23b3724a16a56b629203edc62c", "general-v1.3");
    foodVideo = create(ModelType.VIDEO, client, "bd367be194cf45149e75f01d59f77ba7", "food-items-v1.0");
    travelVideo = create(ModelType.VIDEO, client, "eee28c313d69466f836ab83287a54ed9", "travel-v1.0");
    nsfwVideo = create(ModelType.VIDEO, client, "e9576d86d2004ed1a38ba0cf39ecb4b1", "nsfw-v1.0");
    weddingVideo = create(ModelType.VIDEO, client, "c386b7a870114f4a87477c0824499348", "weddings-v1.0");
    apparelVideo = create(ModelType.VIDEO, client, "e0be3b9d6a454f0493ac3a30784001ff", "apparel");
  }

  @NotNull private <M extends Model<?>> M create(
      @NotNull ModelType type,
      @NotNull final BaseClarifaiClient client,
      @NotNull String id,
      @NotNull String name
  ) {
    return Model.<M>_create(type, client, id, name, null);
  }

  @NotNull public ConceptModel generalModel() { return general; }

  @NotNull public ConceptModel foodModel() { return food; }

  @NotNull public ConceptModel travelModel() { return travel; }

  @NotNull public ConceptModel nsfwModel() { return nsfw; }

  @NotNull public ConceptModel weddingModel() { return wedding; }

  @NotNull public ConceptModel apparelModel() { return apparel; }

  @NotNull public ConceptModel moderationModel() { return moderation; }

  @NotNull public ConceptModel landscapeQualityModel() { return landscapeQuality; }

  @NotNull public ConceptModel portraitQualityModel() { return portraitQuality; }

  @NotNull public ConceptModel texturesAndPatternsModel() { return texturesAndPatterns; }

  @NotNull public DetectionModel logoModel() { return logo; }

  @NotNull public ColorModel colorModel() { return color; }
  @NotNull public DetectionModel faceDetectionModel() { return face; }
  @NotNull public DetectionModel demographicsModel() { return demographics; }
  @NotNull public DetectionModel celebrityModel() { return celebrity; }

  @NotNull public EmbeddingModel generalEmbeddingModel() { return generalEmbed; }
  @NotNull public FaceEmbeddingModel faceEmbeddingModel() { return faceEmbed; }

  @NotNull public VideoModel generalVideoModel() { return generalVideo; }

  @NotNull public VideoModel foodVideoModel() { return foodVideo; }

  @NotNull public VideoModel travelVideoModel() { return travelVideo; }

  @NotNull public VideoModel nsfwVideoModel() { return nsfwVideo; }

  @NotNull public VideoModel weddingVideoModel() { return weddingVideo; }

  @NotNull public VideoModel apparelVideoModel() { return apparelVideo; }
}
