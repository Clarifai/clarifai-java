package clarifai2.dto.prediction;

import clarifai2.dto.input.Crop;
import clarifai2.internal.InternalUtil;
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

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Logo.Adapter.class)
public abstract class Logo extends Prediction {

  Logo() {} // AutoValue instances only

  @NotNull public abstract Crop boundingBox();

  @NotNull public abstract List<Concept> concepts();


  static class Adapter extends JSONAdapterFactory<Logo> {
    @Nullable @Override protected JSONAdapterFactory.Deserializer<Logo> deserializer() {
      return new Deserializer<Logo>() {
        @Nullable @Override
        public Logo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Logo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          List<Concept> concepts = new ArrayList<>();
          Crop crop = fromJson(gson, root.getAsJsonObject()
              .getAsJsonObject("region_info")
              .getAsJsonObject("bounding_box"), Crop.class);
          for (JsonElement element : root.getAsJsonObject("data").getAsJsonArray("concepts")) {
            concepts.add(fromJson(gson, element, Concept.class));
          }
          return new AutoValue_Logo(crop, concepts);
        }
      };
    }

    @NotNull @Override protected TypeToken<Logo> typeToken() {
      return new TypeToken<Logo>() {};
    }
  }
}
