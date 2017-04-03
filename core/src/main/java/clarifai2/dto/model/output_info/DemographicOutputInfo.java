package clarifai2.dto.model.output_info;

import clarifai2.Func1;
import clarifai2.dto.input.image.Crop;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Region;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONArrayBuilder;
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

import java.util.ArrayList;
import java.util.List;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(DemographicOutputInfo.Adapter.class)
public abstract class DemographicOutputInfo extends OutputInfo {

  @NotNull public abstract List<Region> concepts();
  @Nullable public abstract String language();

  DemographicOutputInfo() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<DemographicOutputInfo> {
    @Nullable @Override protected Serializer<DemographicOutputInfo> serializer() {
      return new Serializer<DemographicOutputInfo>() {
        @NotNull @Override public JsonElement serialize(
            @Nullable DemographicOutputInfo value,
            @NotNull final Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return JsonNull.INSTANCE;
        }
      };
    }

    @Nullable @Override protected Deserializer<DemographicOutputInfo> deserializer() {
      return new Deserializer<DemographicOutputInfo>() {
        @Nullable @Override
        public DemographicOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<DemographicOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = json.getAsJsonObject();

          List<Region> regions = new ArrayList<>();
          for (JsonElement element : root.getAsJsonObject("data").getAsJsonArray("regions")) {
            Crop crop = null;
            List<Concept> ageAppearances = new ArrayList<>();
            List<Concept> genderAppearances= new ArrayList<>();
            List<Concept> multiCulturalAppearances = new ArrayList<>();
            if (element.getAsJsonObject().has("region_info")) {
              crop = fromJson(gson, element.getAsJsonObject()
                  .getAsJsonObject("region_info")
                  .getAsJsonObject("bounding_box"), Crop.class); // only when the crop object
            } else if (element.getAsJsonObject().has("data")) {
              JsonObject face = element.getAsJsonObject().getAsJsonObject("data").getAsJsonObject("face");
              for (JsonElement ageElement : face.getAsJsonObject("age_appearance").getAsJsonArray("concepts")) {
                ageAppearances.add(fromJson(gson, ageElement, Concept.class));
              }
              for (JsonElement ageElement : face.getAsJsonObject("gender_appearance").getAsJsonArray("concepts")) {
                genderAppearances.add(fromJson(gson, ageElement, Concept.class));
              }
              for (JsonElement ageElement : face.getAsJsonObject("multicultural_appearance").getAsJsonArray("concepts")) {
                multiCulturalAppearances.add(fromJson(gson, ageElement, Concept.class));
              }
            }
            regions.add(new AutoValue_Region(crop, ageAppearances, genderAppearances, multiCulturalAppearances));
          }
          return new AutoValue_DemographicOutputInfo(regions, null);
        }
      };
    }

    @NotNull @Override protected TypeToken<DemographicOutputInfo> typeToken() {
      return new TypeToken<DemographicOutputInfo>() {};
    }
  }
}
