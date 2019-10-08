package clarifai2.dto.input;

import clarifai2.dto.ClarifaiStatus;
import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.internal.grpc.api.GeoOuterClass;
import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.dto.HasClarifaiID;
import clarifai2.dto.PointF;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Region;
import clarifai2.grpc.DateTimeConverter;
import clarifai2.grpc.MetadataConverter;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.isJsonNull;
import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiInput.Adapter.class)
public abstract class ClarifaiInput implements HasClarifaiID {
  ClarifaiInput() {} // AutoValue instances only

  /**
   * This is just a convenience function.
   * It's equivalent to {@code ClarifaiInput.forInputValue(ClarifaiImage.of(imageBytes))}.
   * @param imageBytes the image to represent
   * @return a {@link ClarifaiInput} that represents the given image
   */
  @NotNull public static ClarifaiInput forImage(@NotNull byte[] imageBytes) {
    return ClarifaiInput.forInputValue(ClarifaiImage.of(imageBytes));
  }

  /**
   * This is just a convenience function.
   * It's equivalent to {@code ClarifaiInput.forInputValue(ClarifaiImage.of(imageFile))}.
   * @param imageFile the image to represent
   * @return a {@link ClarifaiInput} that represents the given image
   */
  @NotNull public static ClarifaiInput forImage(@NotNull File imageFile) {
    return ClarifaiInput.forInputValue(ClarifaiImage.of(imageFile));
  }

  /**
   * This is just a convenience function.
   * It's equivalent to {@code ClarifaiInput.forInputValue(ClarifaiImage.of(imageURL))}.
   * @param imageURL the image to represent
   * @return a {@link ClarifaiInput} that represents the given image
   */
  @NotNull public static ClarifaiInput forImage(@NotNull String imageURL) {
    return ClarifaiInput.forInputValue(ClarifaiImage.of(imageURL));
  }

  /**
   * This is just a convenience function.
   * It's equivalent to {@code ClarifaiInput.forInputValue(ClarifaiImage.of(imageURL))}.
   * @param imageURL the image to represent
   * @return a {@link ClarifaiInput} that represents the given image
   */
  @NotNull public static ClarifaiInput forImage(@NotNull URL imageURL) {
    return ClarifaiInput.forInputValue(ClarifaiImage.of(imageURL));
  }

  /**
   * This is just a convenience function.
   * It's equivalent to {@code forInputValue(ClarifaiVideo.of(videoBytes))}.
   * @param videoBytes the video to represent
   * @return a {@link ClarifaiInput} that represents the given video
   */
  @NotNull public static ClarifaiInput forVideo(@NotNull byte[] videoBytes) {
    return forInputValue(ClarifaiVideo.of(videoBytes));
  }

  /**
   * This is just a convenience function.
   * It's equivalent to {@code forInputValue(ClarifaiVideo.of(videoFile))}.
   * @param videoFile the video to represent
   * @return a {@link ClarifaiInput} that represents the given video
   */
  @NotNull public static ClarifaiInput forVideo(@NotNull File videoFile) {
    return forInputValue(ClarifaiVideo.of(videoFile));
  }

  /**
   * This is just a convenience function.
   * It's equivalent to {@code forInputValue(ClarifaiVideo.of(videoURL))}.
   * @param videoURL the video to represent
   * @return a {@link ClarifaiInput} that represents the given video
   */
  @NotNull public static ClarifaiInput forVideo(@NotNull String videoURL) {
    return forInputValue(ClarifaiVideo.of(videoURL));
  }

  /**
   * This is just a convenience function.
   * It's equivalent to {@code forInputValue(ClarifaiVideo.of(videoURL))}.
   * @param videoURL the video to represent
   * @return a {@link ClarifaiInput} that represents the given video
   */
  @NotNull public static ClarifaiInput forVideo(@NotNull URL videoURL) {
    return forInputValue(ClarifaiVideo.of(videoURL));
  }

  /**
   * Deprecated. Use {@link #forInputValue(ClarifaiInputValue)} instead.
   * @param image the image to represent
   * @return a {@link ClarifaiInput} that represents the given image
   */
  @Deprecated
  @NotNull public static ClarifaiInput forImage(@NotNull ClarifaiImage image) {
    return forInputValue(image);
  }

  /**
   * Deprecated. Use {@link #forInputValue(ClarifaiInputValue)} instead.
   * @param video
   * @return
   */
  @Deprecated
  @NotNull public static ClarifaiInput forVideo(@NotNull ClarifaiVideo video) {
    return forInputValue(video);
  }

  @NotNull public static ClarifaiInput forInputValue(@NotNull ClarifaiInputValue inputValue) {
    return new AutoValue_ClarifaiInput(null, null, inputValue, new JsonObject(), Collections.<Concept>emptyList(), null,
        null, null);
  }

  @Nullable public abstract Date createdAt();

  /**
   * @return the input value represented by this concept
   */
  @NotNull public abstract ClarifaiInputValue inputValue();

  /**
   * @return the metadata on this image
   */
  @NotNull public final JsonObject metadata() {
    // return a defensive copy so that the immutable object can't be modified
    return InternalUtil.jsonDeepCopy(_metadata());
  }

  @SuppressWarnings("PMD.MethodNamingConventions")
  @NotNull abstract JsonObject _metadata();

  /**
   * @return the concepts present on this input
   */
  @NotNull public abstract List<Concept> concepts();

  /**
   * @return the geo-point that this input is linked to
   */
  @Nullable public abstract PointF geo();

  @Nullable public abstract List<Region> regions();

  @Nullable public abstract ClarifaiStatus status();

  /**
   * @param id the ID to assign to this input
   * @return a copy of this {@link ClarifaiInput} with its ID set to the specified value
   */
  @NotNull public final ClarifaiInput withID(@NotNull String id) {
    return withId(id);
  }

  /**
   * @param geo a geographic coordinate to assign to this input
   * @return a copy of this {@link ClarifaiInput} with its geographic coordinate set to the specified value
   */
  @NotNull public final ClarifaiInput withGeo(@Nullable PointF geo) {
    return new AutoValue_ClarifaiInput(id(), createdAt(), inputValue(), metadata(), concepts(), geo, regions(), status());
  }

  /**
   * @param metadata the metadata to attach to this input
   * @return a copy of this {@link ClarifaiInput} with its metadata set to the specified value
   */
  @NotNull public final ClarifaiInput withMetadata(@NotNull JsonObject metadata) {
    InternalUtil.assertMetadataHasNoNulls(metadata);
    return new AutoValue_ClarifaiInput(id(), createdAt(), inputValue(), metadata, concepts(), geo(), regions(), status());
  }

  // Hide the ugly casing that auto-value-with requires
  @NotNull abstract ClarifaiInput withId(@NotNull String id);

  /**
   * @param concepts the concepts to assign to this input
   * @return a copy of this {@link ClarifaiInput} with these concepts marked present
   */
  @NotNull public abstract ClarifaiInput withConcepts(@NotNull List<Concept> concepts);

  @NotNull public final ClarifaiInput withConcepts(@NotNull Concept... concepts) {
    return withConcepts(Arrays.asList(concepts));
  }

  public static ClarifaiInput deserialize(InputOuterClass.Input input) {
    final ClarifaiStatus status = ClarifaiStatus.deserialize(input.getStatus());

    final DataOuterClass.Data data = input.getData();

    final List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept: data.getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }

    JsonObject metadata = MetadataConverter.structToJsonObject(data.getMetadata());

    PointF geoPoint = null;
    if (data.hasGeo()) {
      GeoOuterClass.GeoPoint gp = data.getGeo().getGeoPoint();
      geoPoint = PointF.at(gp.getLatitude(), gp.getLongitude());
    }

    final List<Region> regions = new ArrayList<>();
    for (DataOuterClass.Region region : data.getRegionsList()) {
      regions.add(Region.deserialize(region));
    }

    return new AutoValue_ClarifaiInput(
        input.getId(),
        DateTimeConverter.timestampToDate(input.getCreatedAt()),
        data.hasVideo() ? ClarifaiVideo.deserialize(data.getVideo()) : ClarifaiImage.deserialize(data.getImage()),
        metadata,
        concepts,
        geoPoint,
        regions,
        status
    );
  }

  public InputOuterClass.Input serialize() {
    return serialize(false);
  }

  public InputOuterClass.Input serialize(boolean allowDuplicateURLs) {
    InputOuterClass.Input.Builder builder = InputOuterClass.Input.newBuilder();
    if (id() != null) {
      builder.setId(id());
    }

    DataOuterClass.Data.Builder dataBuilder = DataOuterClass.Data.newBuilder();

    if (!concepts().isEmpty()) {
      List<ConceptOuterClass.Concept> conceptsGrpc = new ArrayList<>();
      for (Concept concept : concepts()) {
        conceptsGrpc.add(concept.serialize());
      }
      dataBuilder.addAllConcepts(conceptsGrpc);
    }

    if (metadata().size() > 0) {
      dataBuilder.setMetadata(MetadataConverter.jsonObjectToStruct(metadata()));
    }

    if (inputValue() instanceof ClarifaiVideo) {
      ClarifaiVideo video = (ClarifaiVideo) inputValue();
      dataBuilder.setVideo(video.serialize(allowDuplicateURLs));
    } else {
      ClarifaiImage image = (ClarifaiImage) inputValue();
      dataBuilder.setImage(image.serialize(allowDuplicateURLs));
    }
    if (geo() != null) {
      dataBuilder.setGeo(
          GeoOuterClass.Geo.newBuilder().setGeoPoint(
              GeoOuterClass.GeoPoint.newBuilder().setLatitude(geo().x()).setLongitude(geo().y())
          )
      );
    }
    if (createdAt() != null) {
      builder.setCreatedAt(DateTimeConverter.dateToTimestamp(createdAt()));
    }
    return builder
        .setData(dataBuilder)
        .build();
  }

  static class Adapter extends JSONAdapterFactory<ClarifaiInput> {
    @Nullable @Override protected Serializer<ClarifaiInput> serializer() {
      return new Serializer<ClarifaiInput>() {
        @NotNull @Override public JsonElement serialize(@Nullable ClarifaiInput value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          final JSONObjectBuilder builder = new JSONObjectBuilder()
              .add("id", value.id());
          final JSONObjectBuilder data = new JSONObjectBuilder()
              .add("concepts", toJson(gson, value.concepts(), new TypeToken<List<Concept>>() {}))
              .add("metadata", value.metadata());
          if (value.inputValue() instanceof ClarifaiVideo) {
            data.add("video", toJson(gson, (ClarifaiVideo) value.inputValue(), ClarifaiVideo.class));
          } else {
            data.add("image", toJson(gson, (ClarifaiImage) value.inputValue(), ClarifaiImage.class));
          }
          if (value.geo() != null) {
            data.add("geo", new JSONObjectBuilder()
                .add("geo_point", new JSONObjectBuilder()
                    .add("latitude", value.geo().x())
                    .add("longitude", value.geo().y())));
          }
          if (value.createdAt() != null) {
            builder.addIfNotNull("created_at", toJson(gson, value.createdAt(), Date.class));
          }
          builder.add("data", data);
          return builder.build();
        }
      };
    }

    @Nullable @Override protected Deserializer<ClarifaiInput> deserializer() {
      return new Deserializer<ClarifaiInput>() {
        @Nullable @Override
        public ClarifaiInput deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiInput> type,
            @NotNull Gson gson) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          final JsonObject data = root.getAsJsonObject("data");

          final List<Concept> concepts;
          {
            final JsonElement conceptsJSON = data.get("concepts");
            if (conceptsJSON != null) {
              concepts = fromJson(gson, conceptsJSON, new TypeToken<List<Concept>>() {});
            } else {
              concepts = Collections.emptyList();
            }
          }

          final JsonObject metadata = data.has("metadata") ? data.getAsJsonObject("metadata") : new JsonObject();
          final JsonObject geo =
              data.has("geo") ? data.getAsJsonObject("geo").getAsJsonObject("geo_point") : new JsonObject();
          final PointF geoPoint = geo.has("latitude")
              ? PointF.at(geo.get("latitude").getAsFloat(), geo.get("longitude").getAsFloat())
              : null;

          return new AutoValue_ClarifaiInput(
              isJsonNull(root.get("id")) ? null : root.get("id").getAsString(),
              fromJson(gson, root.get("created_at"), Date.class),
              data.has("video")
                  ? fromJson(gson, data.get("video"), ClarifaiVideo.class)
                  : fromJson(gson, data.get("image"), ClarifaiImage.class),
              metadata,
              concepts,
              geoPoint,
              data.has("regions") ? InternalUtil.fromJson(
                  gson,
                  data.get("regions"),
                  new TypeToken<List<Region>>() {}
              ) : Collections.<Region>emptyList(),
              gson.fromJson(root.getAsJsonObject("status"), ClarifaiStatus.class)
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiInput> typeToken() {
      return new TypeToken<ClarifaiInput>() {};
    }
  }
}
