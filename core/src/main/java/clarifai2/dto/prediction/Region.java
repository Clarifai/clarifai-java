package clarifai2.dto.prediction;

import clarifai2.dto.input.Crop;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Region.Adapter.class)
public abstract class Region extends Prediction {

  Region() {} // AutoValue instances only

  @NotNull public abstract String id();

  @NotNull public abstract Crop crop();

  @NotNull public abstract List<Concept> ageAppearances();

  @NotNull public abstract List<Concept> genderAppearances();

  @NotNull public abstract List<Concept> multiculturalAppearances();


  static class Adapter extends JSONAdapterFactory<Region> {
    @Nullable @Override protected Deserializer<Region> deserializer() {
      return new Deserializer<Region>() {
        @Nullable @Override
        public Region deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Region> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String id = root.get("id").getAsString();
          Crop crop = null;
          List<Concept> ageAppearances = new ArrayList<>();
          List<Concept> genderAppearances = new ArrayList<>();
          List<Concept> multiCulturalAppearances = new ArrayList<>();
          crop = fromJson(gson, root.getAsJsonObject()
              .getAsJsonObject("region_info")
              .getAsJsonObject("bounding_box"), Crop.class);
          JsonObject data = root.getAsJsonObject().getAsJsonObject("data");

          if (data != null) {
            JsonObject face = data.getAsJsonObject("face");
            JsonObject ageAppearance = face.getAsJsonObject("age_appearance");
            if (ageAppearance != null) {
              for (JsonElement ageElement : ageAppearance.getAsJsonArray("concepts")) {
                ageAppearances.add(fromJson(gson, ageElement, Concept.class));
              }
            }

            JsonObject genderAppearance = face.getAsJsonObject("gender_appearance");
            if (genderAppearance != null) {
              for (JsonElement ageElement : genderAppearance.getAsJsonArray("concepts")) {
                genderAppearances.add(fromJson(gson, ageElement, Concept.class));
              }
            }

            JsonObject multiculturalAppearance = face.getAsJsonObject("multicultural_appearance");
            if (multiculturalAppearance != null) {
              for (JsonElement ageElement : multiculturalAppearance.getAsJsonArray("concepts")) {
                multiCulturalAppearances.add(fromJson(gson, ageElement, Concept.class));
              }
            }

            if (crop == null) {
              throw new IllegalArgumentException("Crop cannot be null");
            }
          }
          return new AutoValue_Region(
              id,
              crop,
              ageAppearances,
              genderAppearances,
              multiCulturalAppearances
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<Region> typeToken() {
      return new TypeToken<Region>() {};
    }
  }
}
