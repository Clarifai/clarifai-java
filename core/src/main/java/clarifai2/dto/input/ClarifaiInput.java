package clarifai2.dto.input;

import clarifai2.dto.HasClarifaiID;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiInput.Adapter.class)
public abstract class ClarifaiInput implements HasClarifaiID {

  /**
   * @param image the image to represent
   * @return a {@link ClarifaiInput} that represents the given image
   */
  @NotNull public static ClarifaiInput forImage(@NotNull ClarifaiImage image) {
    return new AutoValue_ClarifaiInput(null, null, image, new JsonObject(), Collections.<Concept>emptyList());
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

  @NotNull abstract JsonObject _metadata();

  /**
   * @return the concepts present on this input
   */
  @NotNull public abstract List<Concept> concepts();

  /**
   * @param id the ID to assign to this input
   * @return a copy of this {@link ClarifaiInput} with its ID set to the specified value
   */
  @NotNull public final ClarifaiInput withID(@NotNull String id) {
    return withId(id);
  }

  /**
   * @param metadata the metadata to attach to this input
   * @return a copy of this {@link ClarifaiInput} with its metadata set to the specified value
   */
  @NotNull public final ClarifaiInput withMetadata(@NotNull JsonObject metadata) {
    InternalUtil.assertMetadataHasNoNulls(metadata);
    return new AutoValue_ClarifaiInput(id(), createdAt(), image(), metadata, concepts());
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


  static class Adapter implements JsonSerializer<ClarifaiInput>, JsonDeserializer<ClarifaiInput> {

    @Override
    public ClarifaiInput deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      final JsonObject data = root.getAsJsonObject("data");

      final List<Concept> concepts;
      {
        final JsonElement conceptsJSON = data.get("concepts");
        if (conceptsJSON != null) {
          concepts = fromJson(context, conceptsJSON, new TypeToken<List<Concept>>() {});
        } else {
          concepts = Collections.emptyList();
        }
      }

      final JsonObject metadata = data.has("metadata") ? data.getAsJsonObject("metadata") : new JsonObject();

      return new AutoValue_ClarifaiInput(
          InternalUtil.<String>nullSafeTraverse(root, "id"),
          fromJson(context, root.get("created_at"), Date.class),
          fromJson(context, data.get("image"), ClarifaiImage.class),
          metadata,
          concepts
      );
    }

    @Override public JsonElement serialize(ClarifaiInput src, Type typeOfSrc, JsonSerializationContext context) {
      return new JSONObjectBuilder()
          .add("id", src.id())
          .add("created_at", context.serialize(src.createdAt()))
          .add("data", new JSONObjectBuilder()
              .add("concepts", context.serialize(src.concepts(), new TypeToken<List<Concept>>() {}.getType()))
              .add("image", context.serialize(src.image()))
              .add("metadata", src.metadata())
          )
          .build();
    }
  }
}