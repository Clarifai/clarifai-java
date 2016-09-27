package clarifai2.api.request.input;

import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.input.image.ClarifaiURLImage;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public abstract class SearchClause {

  /**
   * A search clause that will match inputs that had images with the given URL
   *
   * This is NOT a visual similarity search. This is a simple string-search for the given image's URL. For visual
   * similarity, please use {@link #matchImageVisually(ClarifaiImage)}.
   *
   * @param image the URL of the image to search by
   * @return a {@link SearchClause} to be given to a search endpoint
   */
  @NotNull public static SearchClause matchImageURL(@NotNull ClarifaiURLImage image) {
    return new InputImage(image);
  }

  /**
   * A search clause that will match inputs that are visually similar to the image with the given URL.
   *
   * This method is different from {@link #matchImageURL(ClarifaiURLImage)}, which also takes an image,
   * but matches inputs that share that image's URL.
   *
   * @param image the URL of the image to search by
   * @return a {@link SearchClause} to be given to a search endpoint
   */
  @NotNull public static SearchClause matchImageVisually(@NotNull ClarifaiImage image) {
    return new OutputImage(image);
  }

  /**
   * A search clause that will match inputs that the user explicitly tagged with the given concept
   *
   * Both this method and the related {@link #matchConcept(Concept)} will match inputs that
   * the user explicitly tagged with these concepts when uploading the input, but
   * {@link #matchConcept(Concept)} will also match inputs that the API predicted to
   * contain these concepts.
   *
   * @param concept the concept to search by
   * @return a {@link SearchClause} to be given to a search endpoint
   */
  @NotNull public static SearchClause matchUserTaggedConcept(@NotNull Concept concept) {
    return new SearchConcept("input", concept);
  }

  /**
   * A search clause that will match inputs both by concepts that the user tagged them with explicitly, and by concepts
   * that the API predicted upon the inputs.
   *
   * This is similar to {@link #matchUserTaggedConcept(Concept)} in that both will match
   * images that the user explicitly tagged with these concepts when uploading the input, but this method will also
   * match inputs that the API predicted to contain these concepts.
   *
   * @param concept the concept to search by
   * @return a {@link SearchClause} to be given to a search endpoint
   */
  @NotNull public static SearchClause matchConcept(@NotNull Concept concept) {
    return new SearchConcept("output", concept);
  }

  private SearchClause() {}

  @JsonAdapter(InputImage.Adapter.class)
  static class InputImage extends SearchClause {
    @NotNull private final ClarifaiImage image;

    private InputImage(@NotNull ClarifaiImage image) {
      this.image = image;
    }

    static class Adapter implements JsonSerializer<InputImage> {
      @Override public JsonElement serialize(InputImage src, Type typeOfSrc, JsonSerializationContext context) {
        return new JSONObjectBuilder()
            .add("input", new JSONObjectBuilder()
                .add("data", new JSONObjectBuilder()
                    .add("image", context.serialize(src.image))
                )
            )
            .build();
      }
    }
  }


  @JsonAdapter(OutputImage.Adapter.class)
  static class OutputImage extends SearchClause {

    @NotNull private final ClarifaiImage image;

    private OutputImage(@NotNull ClarifaiImage image) {
      this.image = image;
    }

    static class Adapter implements JsonSerializer<OutputImage> {
      @Override public JsonElement serialize(OutputImage src, Type typeOfSrc, JsonSerializationContext context) {
        return new JSONObjectBuilder()
            .add("output", new JSONObjectBuilder()
                .add("input", new JSONObjectBuilder()
                    .add("data", new JSONObjectBuilder()
                        .add("image", context.serialize(src.image))
                    )
                )
            )
            .build();
      }
    }
  }


  @JsonAdapter(SearchConcept.Adapter.class)
  static class SearchConcept extends SearchClause {
    @NotNull private final String owningObjectName;
    @NotNull private final Concept concept;

    private SearchConcept(@NotNull String owningObjectName, @NotNull Concept concept) {
      this.owningObjectName = owningObjectName;
      this.concept = concept;
    }

    static class Adapter implements JsonSerializer<SearchConcept> {
      @Override
      public JsonElement serialize(SearchConcept src, Type typeOfSrc, JsonSerializationContext context) {
        return new JSONObjectBuilder()
            .add(src.owningObjectName, new JSONObjectBuilder()
                .add("data", new JSONObjectBuilder()
                    .add("concepts", new JSONArrayBuilder().add(context.serialize(src.concept)))
                )
            )
            .build();
      }
    }
  }
}
