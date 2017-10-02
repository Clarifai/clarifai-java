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
@JsonAdapter(FaceConcepts.Adapter.class)
public abstract class FaceConcepts extends Prediction {

  FaceConcepts() {} // AutoValue instances only

  @NotNull public abstract Crop boundingBox();

  @NotNull public abstract List<Concept> concepts();

  static class Adapter extends JSONAdapterFactory<FaceConcepts> {
    @Nullable @Override protected Deserializer<FaceConcepts> deserializer() {
      return new Deserializer<FaceConcepts>() {
        @Nullable @Override
        public FaceConcepts deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<FaceConcepts> type,
            @NotNull Gson gson
        ) {
          JsonObject root = assertJsonIs(json, JsonObject.class);
          Crop crop = fromJson(gson, root
              .getAsJsonObject("region_info")
              .getAsJsonObject("bounding_box"), Crop.class);
          JsonObject face = root.getAsJsonObject().getAsJsonObject("data").getAsJsonObject("face");
          List<Concept> concepts = new ArrayList<>();
          for (JsonElement identityElement : face.getAsJsonObject("identity").getAsJsonArray("concepts")) {
            concepts.add(fromJson(gson, identityElement, Concept.class));
          }
          return new AutoValue_FaceConcepts(
              crop,
              concepts
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<FaceConcepts> typeToken() {
      return new TypeToken<FaceConcepts>() {};
    }
  }
}
