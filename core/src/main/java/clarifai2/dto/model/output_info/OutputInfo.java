package clarifai2.dto.model.output_info;

import clarifai2.dto.model.ModelType;
import clarifai2.internal.JSONAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

import static clarifai2.internal.InternalUtil.fromJson;

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
      return fromJson(context, json, ModelType.determineFromOutputInfoRoot(root).infoType());
    }
  }
}

