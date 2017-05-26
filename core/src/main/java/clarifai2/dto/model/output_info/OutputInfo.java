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

@JsonAdapter(OutputInfo.Adapter.class)
public abstract class OutputInfo {

  OutputInfo() {}

  static class Adapter extends JSONAdapterFactory<OutputInfo> {
    @Nullable @Override protected Deserializer<OutputInfo> deserializer() {
      return new Deserializer<OutputInfo>() {
        @Nullable @Override
        public OutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<OutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          if (root.has("message")) {
            return null;
          }
          if (root.has("type") && root.size() == 1) {
            return null;
          }
          return fromJson(gson, json, ModelType.determineModelType(root).infoType());
        }
      };
    }

    @NotNull @Override protected TypeToken<OutputInfo> typeToken() {
      return new TypeToken<OutputInfo>() {};
    }
  }
}

