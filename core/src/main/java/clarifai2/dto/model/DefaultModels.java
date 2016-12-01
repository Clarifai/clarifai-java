package clarifai2.dto.model;

import clarifai2.api.BaseClarifaiClient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public final class DefaultModels {

  @NotNull private final ConceptModel general;
  @NotNull private final ConceptModel food;
  @NotNull private final ConceptModel travel;
  @NotNull private final ConceptModel nsfw;
  @NotNull private final ConceptModel wedding;
  @NotNull private final ColorModel color;
  @NotNull private final FaceDetectionModel faceModel;

  public DefaultModels(@NotNull BaseClarifaiClient client) {
    general = Model._create(ModelType.CONCEPT, client, "aaa03c23b3724a16a56b629203edc62c", "general-v1.3", null);
    food = Model._create(ModelType.CONCEPT, client, "bd367be194cf45149e75f01d59f77ba7", "food-items-v1.0", null);
    travel = Model._create(ModelType.CONCEPT, client, "eee28c313d69466f836ab83287a54ed9", "travel-v1.0", null);
    nsfw = Model._create(ModelType.CONCEPT, client, "e9576d86d2004ed1a38ba0cf39ecb4b1", "nsfw-v1.0", null);
    wedding = Model._create(ModelType.CONCEPT, client, "c386b7a870114f4a87477c0824499348", "weddings-v1.0", null);
    color = Model._create(ModelType.COLOR, client, "eeed0b6733a644cea07cf4c60f87ebb7", "color", null);
    faceModel = Model._create(ModelType.FACE_DETECTION, client, "a403429f2ddf4b49b307e318f00e528b", "face-v1.3", null);
    // TODO(Kevin): Eventually refresh these models from the API in the background so we have their output info
  }

  @NotNull public ConceptModel generalModel() { return general; }

  @NotNull public ConceptModel foodModel() { return food; }

  @NotNull public ConceptModel travelModel() { return travel; }

  @NotNull public ConceptModel nsfwModel() { return nsfw; }

  @NotNull public ConceptModel weddingModel() { return wedding; }

  @NotNull public ColorModel colorModel() { return color; }

  @NotNull public FaceDetectionModel faceDetectionModel() { return faceModel; }
}
