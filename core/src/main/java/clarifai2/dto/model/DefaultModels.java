package clarifai2.dto.model;

import clarifai2.api.BaseClarifaiClient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public final class DefaultModels {

  @NotNull private final ExecutorService updater = Executors.newSingleThreadExecutor();

  @NotNull private final AtomicReference<ConceptModel> general;
  @NotNull private final AtomicReference<ConceptModel> food;
  @NotNull private final AtomicReference<ConceptModel> travel;
  @NotNull private final AtomicReference<ConceptModel> nsfw;
  @NotNull private final AtomicReference<ConceptModel> wedding;
  @NotNull private final AtomicReference<ConceptModel> apparel;
  @NotNull private final AtomicReference<LogoModel> logo;
  @NotNull private final AtomicReference<ColorModel> color;
  @NotNull private final AtomicReference<FocusModel> focus;
  @NotNull private final AtomicReference<FaceDetectionModel> face;
  @NotNull private final AtomicReference<DemographicsModel> demographics;
  @NotNull private final AtomicReference<EmbeddingModel> generalEmbed;

  public DefaultModels(@NotNull BaseClarifaiClient client) {
    general = create(ModelType.CONCEPT, client, "aaa03c23b3724a16a56b629203edc62c", "general-v1.3");
    food = create(ModelType.CONCEPT, client, "bd367be194cf45149e75f01d59f77ba7", "food-items-v1.0");
    travel = create(ModelType.CONCEPT, client, "eee28c313d69466f836ab83287a54ed9", "travel-v1.0");
    nsfw = create(ModelType.CONCEPT, client, "e9576d86d2004ed1a38ba0cf39ecb4b1", "nsfw-v1.0");
    wedding = create(ModelType.CONCEPT, client, "c386b7a870114f4a87477c0824499348", "weddings-v1.0");
    apparel = create(ModelType.CONCEPT, client, "e0be3b9d6a454f0493ac3a30784001ff", "apparel");
    logo = create(ModelType.LOGO, client, "c443119bf2ed4da98487520d01a0b1e3", "logo-v0.4");
    color = create(ModelType.COLOR, client, "eeed0b6733a644cea07cf4c60f87ebb7", "color");
    focus = create(ModelType.FOCUS, client, "c2cf7cecd8a6427da375b9f35fcd2381", "focus");
    face = create(ModelType.FACE_DETECTION, client, "a403429f2ddf4b49b307e318f00e528b", "face-v1.3");
    demographics = create(ModelType.DEMOGRAPHICS, client, "c0c0ac362b03416da06ab3fa36fb58e3", "age-gender-ethnicity");
    generalEmbed = create(ModelType.EMBEDDING, client, "bbb5f41425b8468d9b7a554ff10f8581", "general-v1.3");
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

  @NotNull public ConceptModel apparelModel() { return apparel.get(); }

  @NotNull public LogoModel logoModel() { return logo.get(); }

  @NotNull public ColorModel colorModel() { return color.get(); }

  @NotNull public FocusModel focusModel() { return focus.get(); }

  @NotNull public FaceDetectionModel faceDetectionModel() { return face.get(); }

  @NotNull public DemographicsModel demographicsModel() { return demographics.get(); }

  @NotNull public EmbeddingModel generalEmbeddingModel() { return generalEmbed.get(); }
}
