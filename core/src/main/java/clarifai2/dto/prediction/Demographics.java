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
@JsonAdapter(Demographics.Adapter.class)
public abstract class Demographics extends Prediction {

  Demographics() {} // AutoValue instances only

  @NotNull public abstract Crop boundingBox();
  @NotNull public abstract List<Concept> ageAppearanceConcepts();
  @NotNull public abstract List<Concept> genderAppearanceConcepts();
  @NotNull public abstract List<Concept> multiculturalAppearanceConcepts();


  static class Adapter extends JSONAdapterFactory<Demographics> {
    @Nullable @Override protected Deserializer<Demographics> deserializer() {
      return new Deserializer<Demographics>() {
        @Nullable @Override
        public Demographics deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Demographics> type,
            @NotNull Gson gson
        ) {
          JsonObject root = assertJsonIs(json, JsonObject.class);
          final JsonObject box = root
              .getAsJsonObject("region_info")
              .getAsJsonObject("bounding_box");
          JsonObject face = root.getAsJsonObject("data").getAsJsonObject("face");
          List<Concept> ageAppearanceConcepts = new ArrayList<>();
          for (JsonElement element : face.getAsJsonObject("age_appearance").getAsJsonArray("concepts")) {
            ageAppearanceConcepts.add(fromJson(gson, element, Concept.class));
          }
          List<Concept> genderAppearanceConcepts = new ArrayList<>();
          for (JsonElement element : face.getAsJsonObject("gender_appearance").getAsJsonArray("concepts")) {
            genderAppearanceConcepts.add(fromJson(gson, element, Concept.class));
          }
          List<Concept> multiculturalAppearanceConcepts = new ArrayList<>();
          for (JsonElement element : face.getAsJsonObject("multicultural_appearance").getAsJsonArray("concepts")) {
            multiculturalAppearanceConcepts.add(fromJson(gson, element, Concept.class));
          }
          return new AutoValue_Demographics(
              fromJson(gson, box, Crop.class),
              ageAppearanceConcepts,
              genderAppearanceConcepts,
              multiculturalAppearanceConcepts
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<Demographics> typeToken() {
      return new TypeToken<Demographics>() {};
    }
  }
}

