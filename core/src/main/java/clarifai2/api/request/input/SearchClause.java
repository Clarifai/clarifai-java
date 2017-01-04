package clarifai2.api.request.input;

import clarifai2.dto.PointF;
import clarifai2.dto.Radius;
import clarifai2.dto.Rectangle;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.input.image.ClarifaiURLImage;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.toJson;

public abstract class SearchClause {

  /**
   * A search clause that will match inputs that had metadata that matches the metadata on the given
   * {@link ClarifaiInput}
   *
   * @param input the input whose metadata to search by
   * @return a {@link SearchClause} to be given to a search endpoint
   * @see #matchMetadata(JsonObject)
   */
  @NotNull public static SearchClause matchMetadata(@NotNull ClarifaiInput input) {
    return matchMetadata(input.metadata());
  }

  /**
   * A search clause that will match inputs that had metadata that matches the given metadata
   *
   * @param metadata the metadata to search by
   * @return a {@link SearchClause} to be given to a search endpoint
   */
  @NotNull public static SearchClause matchMetadata(@NotNull JsonObject metadata) {
    return new Metadata(metadata);
  }

  /**
   * A search clause that will match inputs that had images with the given URL
   * <p>
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
   * <p>
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
   * <p>
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
   * <p>
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

  @NotNull public static SearchClause matchGeo(@NotNull final PointF center, @NotNull final Radius radius) {
    return new GeoCircle(center, radius);
  }

  @NotNull public static SearchClause matchGeo(@NotNull final PointF topLeft, @NotNull final PointF bottomRight) {
    return new GeoRect(topLeft, bottomRight);
  }

  private SearchClause() {}

  @JsonAdapter(Metadata.Adapter.class)
  static class Metadata extends SearchClause {
    @NotNull private final JsonObject metadata;

    private Metadata(@NotNull JsonObject metadata) {
      this.metadata = metadata;
    }

    static class Adapter extends JSONAdapterFactory<Metadata> {
      @Nullable @Override protected Serializer<Metadata> serializer() {
        return new Serializer<Metadata>() {
          @NotNull @Override public JsonElement serialize(@Nullable Metadata value, @NotNull Gson gson) {
            if (value == null) {
              return JsonNull.INSTANCE;
            }
            return new JSONObjectBuilder()
                .add("input", new JSONObjectBuilder()
                    .add("data", new JSONObjectBuilder()
                        .add("metadata", value.metadata)
                    )
                )
                .build();
          }
        };
      }

      @NotNull @Override protected TypeToken<Metadata> typeToken() {
        return new TypeToken<Metadata>() {};
      }
    }
  }


  @JsonAdapter(InputImage.Adapter.class)
  static class InputImage extends SearchClause {
    @NotNull private final ClarifaiImage image;

    private InputImage(@NotNull ClarifaiImage image) {
      this.image = image;
    }

    static class Adapter extends JSONAdapterFactory<InputImage> {
      @Nullable @Override protected Serializer<InputImage> serializer() {
        return new Serializer<InputImage>() {
          @NotNull @Override public JsonElement serialize(@Nullable InputImage value, @NotNull Gson gson) {
            if (value == null) {
              return JsonNull.INSTANCE;
            }
            return new JSONObjectBuilder()
                .add("input", new JSONObjectBuilder()
                    .add("data", new JSONObjectBuilder()
                        .add("image", toJson(gson, value.image, ClarifaiImage.class))
                    )
                )
                .build();
          }
        };
      }

      @NotNull @Override protected TypeToken<InputImage> typeToken() {
        return new TypeToken<InputImage>() {};
      }
    }
  }


  @JsonAdapter(OutputImage.Adapter.class)
  static class OutputImage extends SearchClause {

    @NotNull private final ClarifaiImage image;

    private OutputImage(@NotNull ClarifaiImage image) {
      this.image = image;
    }

    static class Adapter extends JSONAdapterFactory<OutputImage> {
      @Nullable @Override protected Serializer<OutputImage> serializer() {
        return new Serializer<OutputImage>() {
          @NotNull @Override public JsonElement serialize(OutputImage value, @NotNull Gson gson) {
            return new JSONObjectBuilder()
                .add("output", new JSONObjectBuilder()
                    .add("input", new JSONObjectBuilder()
                        .add("data", new JSONObjectBuilder()
                            .add("image", toJson(gson, value.image, ClarifaiImage.class))
                        )
                    )
                )
                .build();
          }
        };
      }

      @NotNull @Override protected TypeToken<OutputImage> typeToken() {
        return new TypeToken<OutputImage>() {};
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

    static class Adapter extends JSONAdapterFactory<SearchConcept> {
      @Nullable @Override protected Serializer<SearchConcept> serializer() {
        return new Serializer<SearchConcept>() {
          @NotNull @Override public JsonElement serialize(@Nullable SearchConcept value, @NotNull Gson gson) {
            if (value == null) {
              return JsonNull.INSTANCE;
            }
            return new JSONObjectBuilder()
                .add(value.owningObjectName, new JSONObjectBuilder()
                    .add("data", new JSONObjectBuilder()
                        .add("concepts", new JSONArrayBuilder().add(toJson(gson, value.concept, Concept.class)))
                    )
                )
                .build();
          }
        };
      }

      @NotNull @Override protected TypeToken<SearchConcept> typeToken() {
        return new TypeToken<SearchConcept>() {};
      }
    }
  }
}
