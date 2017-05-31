package clarifai2.dto.prediction;

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

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Color.Adapter.class)
public abstract class Color extends Prediction {

  Color() {} // AutoValue instances only

  @NotNull public abstract String hex();

  @NotNull public abstract String webSafeHex();

  @NotNull public abstract String webSafeColorName();

  @NotNull public abstract float value();


  static class Adapter extends JSONAdapterFactory<Color> {
    @Nullable @Override protected Deserializer<Color> deserializer() {
      return new Deserializer<Color>() {
        @Nullable @Override
        public Color deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Color> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          final JsonObject w3c = root.get("w3c").getAsJsonObject();
          return new AutoValue_Color(
              root.get("raw_hex").getAsString(),
              w3c.get("hex").getAsString(),
              w3c.get("name").getAsString(),
              root.get("value").getAsFloat()
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<Color> typeToken() {
      return new TypeToken<Color>() {};
    }
  }
}