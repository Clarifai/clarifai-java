package clarifai2.dto.prediction;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Color.Adapter.class)
public abstract class Color extends Prediction {

  @NotNull public abstract String hex();
  @NotNull public abstract String webSafeHex();
  @NotNull public abstract String webSafeColorName();
  @NotNull public abstract float value();

  Color() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<Color> {
    @Override public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      final JsonObject w3c = root.get("w3c").getAsJsonObject();
      return new AutoValue_Color(
          root.get("raw_hex").getAsString(),
          w3c.get("hex").getAsString(),
          w3c.get("name").getAsString(),
          root.get("value").getAsFloat()
      );
    }
  }
}