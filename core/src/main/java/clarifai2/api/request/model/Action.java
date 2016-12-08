package clarifai2.api.request.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

@JsonAdapter(Action.Adapter.class)
public enum Action {
  /**
   * Merges any specified concepts into the list of concepts that are associated with this model.
   * <p>
   * If the IDs on any of the given concepts already exist on this model, the new concept will overwrite the old one.
   */
  MERGE("merge"),
  /**
   * Overwrites the list of concepts on this model with the user's provided values.
   */
  OVERWRITE("overwrite"),
  /**
   * Removes the concepts with the given IDs from this model.
   */
  REMOVE("remove"),;

  @NotNull private String value;

  Action(@NotNull String value) {
    this.value = value;
  }

  static class Adapter implements JsonSerializer<Action>, JsonDeserializer<Action> {

    @Override public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      if (json == null || !json.isJsonPrimitive()) {
        return null;
      }
      final String str = json.getAsJsonPrimitive().getAsString();
      for (final Action action : Action.values()) {
        if (action.value.equals(str)) {
          return action;
        }
      }
      throw new IllegalStateException("Unknown Action: " + str);
    }

    @Override public JsonElement serialize(Action src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.value);
    }
  }
}
