package clarifai2.dto.prediction;

import clarifai2.dto.input.image.Crop;
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

  @NotNull public abstract Crop crop();
  @NotNull public abstract List<Concept> ageAppearances();
  @NotNull public abstract List<Concept> genderAppearances();
  @NotNull public abstract List<Concept> multiculturalAppearances();

  Region() {} // AutoValue instances only

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
          Crop crop = null;
          List<Concept> ageAppearances = new ArrayList<>();
          List<Concept> genderAppearances= new ArrayList<>();
          List<Concept> multiCulturalAppearances = new ArrayList<>();
          crop = fromJson(gson, root.getAsJsonObject()
              .getAsJsonObject("region_info")
              .getAsJsonObject("bounding_box"), Crop.class);
          JsonObject face = root.getAsJsonObject().getAsJsonObject("data").getAsJsonObject("face");
          for (JsonElement ageElement : face.getAsJsonObject("age_appearance").getAsJsonArray("concepts")) {
            ageAppearances.add(fromJson(gson, ageElement, Concept.class));
          }
          for (JsonElement ageElement : face.getAsJsonObject("gender_appearance").getAsJsonArray("concepts")) {
            genderAppearances.add(fromJson(gson, ageElement, Concept.class));
          }
          for (JsonElement ageElement : face.getAsJsonObject("multicultural_appearance").getAsJsonArray("concepts")) {
            multiCulturalAppearances.add(fromJson(gson, ageElement, Concept.class));
          }
          if (crop == null) throw new IllegalArgumentException("Crop cannot be null");
          return new AutoValue_Region(
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
