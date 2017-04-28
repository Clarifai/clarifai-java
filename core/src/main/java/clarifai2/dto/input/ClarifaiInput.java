package clarifai2.dto.input;

import clarifai2.dto.HasClarifaiID;
import clarifai2.dto.PointF;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.prediction.Concept;
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

  /**
   * @param image the image to represent
   * @return a {@link ClarifaiInput} that represents the given image
   */
  @NotNull public static ClarifaiInput forImage(@NotNull ClarifaiImage image) {
    return new AutoValue_ClarifaiInput(null, null, image, new JsonObject(), Collections.<Concept>emptyList(), null);
  }

  @Nullable public abstract Date createdAt();

  /**
   * @return the image represented by this concept
   */
  @NotNull public abstract ClarifaiImage image();

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
    return new AutoValue_ClarifaiInput(id(), createdAt(), image(), metadata(), concepts(), geo);
  }

  /**
   * @param metadata the metadata to attach to this input
   * @return a copy of this {@link ClarifaiInput} with its metadata set to the specified value
   */
  @NotNull public final ClarifaiInput withMetadata(@NotNull JsonObject metadata) {
    InternalUtil.assertMetadataHasNoNulls(metadata);
    return new AutoValue_ClarifaiInput(id(), createdAt(), image(), metadata, concepts(), geo());
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

  ClarifaiInput() {} // AutoValue instances only

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
              .add("image", toJson(gson, value.image(), ClarifaiImage.class))
              .add("metadata", value.metadata());
          if (value.geo() != null) {
            data.add("geo", new JSONObjectBuilder()
                      .add("geo_point", new JSONObjectBuilder()
                      .add("latitude", value.geo().x())
                      .add("longitude", value.geo().y())));
          }
          if (value.createdAt() != null) {
            builder.add("created_at", toJson(gson, value.createdAt(), Date.class));
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
          final JsonObject geo = data.has("geo") ? data.getAsJsonObject("geo").getAsJsonObject("geo_point") : new JsonObject();
          final PointF geoPoint = geo.has("latitude") ? PointF.at(geo.get("latitude").getAsFloat(), geo.get("longitude").getAsFloat()) : null;

          return new AutoValue_ClarifaiInput(
              isJsonNull(root.get("id")) ? null : root.get("id").getAsString(),
              fromJson(gson, root.get("created_at"), Date.class),
              fromJson(gson, data.get("image"), ClarifaiImage.class),
              metadata,
              concepts,
              geoPoint
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiInput> typeToken() {
      return new TypeToken<ClarifaiInput>() {};
    }
  }
}
