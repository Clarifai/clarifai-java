package clarifai2.dto.input;

import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiInputsStatus.Adapter.class)
public abstract class ClarifaiInputsStatus {

  @NotNull public abstract int processed();
  @NotNull public abstract int toProcess();
  @NotNull public abstract int errors();

  ClarifaiInputsStatus() {} // AutoValue instances only

  static class Adapter implements JsonSerializer<ClarifaiInputsStatus>, JsonDeserializer<ClarifaiInputsStatus> {

    @Override public JsonElement serialize(ClarifaiInputsStatus src, Type typeOfSrc, JsonSerializationContext context) {
      return new JSONObjectBuilder()
          .add("processed", src.processed())
          .add("to_process", src.toProcess())
          .add("errors", src.errors())
          .build();
    }

    @Override
    public ClarifaiInputsStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      return new AutoValue_ClarifaiInputsStatus(
          root.get("errors").getAsInt(),
          root.get("to_process").getAsInt(),
          root.get("processed").getAsInt()
      );
    }
  }
}