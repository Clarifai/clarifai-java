package clarifai2.dto.model;

import clarifai2.api.BaseClarifaiClient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public final class DefaultModels {

  @NotNull private final ExecutorService updater = Executors.newSingleThreadExecutor();

  @NotNull private AtomicReference<ConceptModel> general;
  @NotNull private AtomicReference<ConceptModel> food;
  @NotNull private AtomicReference<ConceptModel> travel;
  @NotNull private AtomicReference<ConceptModel> nsfw;
  @NotNull private AtomicReference<ConceptModel> wedding;
  @NotNull private AtomicReference<ColorModel> color;
  @NotNull private AtomicReference<FaceDetectionModel> faceModel;
  @NotNull private AtomicReference<FaceDetectionModel> demographicsModel;

  public DefaultModels(@NotNull BaseClarifaiClient client) {
    general = create(ModelType.CONCEPT, client, "aaa03c23b3724a16a56b629203edc62c", "general-v1.3");
    food = create(ModelType.CONCEPT, client, "bd367be194cf45149e75f01d59f77ba7", "food-items-v1.0");
    travel = create(ModelType.CONCEPT, client, "eee28c313d69466f836ab83287a54ed9", "travel-v1.0");
    nsfw = create(ModelType.CONCEPT, client, "e9576d86d2004ed1a38ba0cf39ecb4b1", "nsfw-v1.0");
    wedding = create(ModelType.CONCEPT, client, "c386b7a870114f4a87477c0824499348", "weddings-v1.0");
    color = create(ModelType.COLOR, client, "eeed0b6733a644cea07cf4c60f87ebb7", "color");
    faceModel = create(ModelType.FACE_DETECTION, client, "a403429f2ddf4b49b307e318f00e528b", "face-v1.3");
    demographicsModel = create(ModelType.FACE_DETECTION, client, "c0c0ac362b03416da06ab3fa36fb58e3", "age-gender-ethnicity");
  }

  @NotNull private <M extends Model<?>> AtomicReference<M> create(
      @NotNull ModelType type,
      @NotNull final BaseClarifaiClient client,
      @NotNull String id,
      @NotNull String name
  ) {
    final AtomicReference<M> model =
        new AtomicReference<>(Model.<M>_create(type, client, id, name, null));
    updater.execute(new Runnable() {
      @Override public void run() {
        final M updated = update(client, model.get());
        model.set(updated);
      }
    });
    return model;
  }

  @NotNull
  private <M extends Model<?>> M update(@NotNull final BaseClarifaiClient client, @NotNull final M unupdatedModel) {
    @SuppressWarnings("unchecked") final M model =
        (M) client.getModelByID(unupdatedModel.id()).executeSync().getOrNull();
    if (model == null) {
      return update(client, unupdatedModel);
    }
    return model;
  }

  @NotNull public ConceptModel generalModel() { return general.get(); }

  @NotNull public ConceptModel foodModel() { return food.get(); }

  @NotNull public ConceptModel travelModel() { return travel.get(); }

  @NotNull public ConceptModel nsfwModel() { return nsfw.get(); }

  @NotNull public ConceptModel weddingModel() { return wedding.get(); }

  @NotNull public ColorModel colorModel() { return color.get(); }

  @NotNull public FaceDetectionModel faceDetectionModel() { return faceModel.get(); }

  @NotNull public FaceDetectionModel demographicsModel() { return demographicsModel.get(); }
}
