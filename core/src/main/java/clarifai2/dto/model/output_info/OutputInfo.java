package clarifai2.dto.model.output_info;

import clarifai2.dto.model.ModelType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;

@JsonAdapter(OutputInfo.Adapter.class)
public abstract class OutputInfo {

  OutputInfo() {}

  static class Adapter implements JsonDeserializer<OutputInfo> {
    @Override public OutputInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      if (root.has("message")) {
        return null;
      }
      if (root.has("type") && root.size() == 1) {
        return null;
      }
      return context.deserialize(json, ModelType.determineFromOutputInfoRoot(root).infoType());
    }
  }
}

