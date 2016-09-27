package clarifai2.dto.input;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

@JsonAdapter(ClarifaiInputUpdateAction.Adapter.class)
public enum ClarifaiInputUpdateAction {
  DELETE_INPUTS("delete_inputs"),
  DELETE_CONCEPTS("delete_concepts"),
  MERGE_CONCEPTS("merge_concepts"),;

  @NotNull private final String action;

  ClarifaiInputUpdateAction(@NotNull String action) {
    this.action = action;
  }

  static class Adapter
      implements JsonSerializer<ClarifaiInputUpdateAction>, JsonDeserializer<ClarifaiInputUpdateAction> {
    @Override
    public JsonElement serialize(ClarifaiInputUpdateAction src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.action);
    }

    @Override
    public ClarifaiInputUpdateAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final String string = json.getAsString();
      for (final ClarifaiInputUpdateAction action : ClarifaiInputUpdateAction.values()) {
        if (action.action.equals(string)) {
          return action;
        }
      }
      throw new IllegalArgumentException(
          "Cannot deserialize " + string + " to a " + ClarifaiInputUpdateAction.class.getName()
      );
    }
  }
}
