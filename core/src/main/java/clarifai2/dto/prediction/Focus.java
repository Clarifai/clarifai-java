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

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Focus.Adapter.class)
public abstract class Focus extends Prediction {

  Focus() {} // Autovalue instances only

  @NotNull public abstract Crop crop();

  @NotNull public abstract double density();

  @NotNull public abstract double value();


  static class Adapter extends JSONAdapterFactory<Focus> {
    @Nullable @Override protected JSONAdapterFactory.Deserializer<Focus> deserializer() {
      return new Deserializer<Focus>() {
        @Nullable @Override
        public Focus deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Focus> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          Crop crop = null;
          crop = fromJson(gson, root.getAsJsonObject()
              .getAsJsonObject("region_info")
              .getAsJsonObject("bounding_box"), Crop.class);
          final double density = root.getAsJsonObject("data")
              .getAsJsonObject("focus")
              .getAsJsonPrimitive("density")
              .getAsDouble();
          final double value = root.getAsJsonPrimitive("value")
              .getAsDouble();
          return new AutoValue_Focus(
              crop,
              density,
              value
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<Focus> typeToken() {
      return new TypeToken<Focus>() {};
    }
  }
}
