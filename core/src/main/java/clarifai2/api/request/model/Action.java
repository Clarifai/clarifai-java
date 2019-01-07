package clarifai2.api.request.model;

import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

  @NotNull public String serialize() {
    return this.value;
  }

  @NotNull public static Action deserialize(String value) {
    for (final Action action : Action.values()) {
      if (action.value.equals(value)) {
        return action;
      }
    }
    throw new IllegalStateException("Unknown Action: " + value);
  }

  static class Adapter extends JSONAdapterFactory<Action> {

    @Nullable @Override protected Serializer<Action> serializer() {
      return new Serializer<Action>() {
        @NotNull @Override public JsonElement serialize(@Nullable Action value, @NotNull Gson gson) {
          if (value == null) {
            value = MERGE;
          }
          return new JsonPrimitive(value.value);
        }
      };
    }

    @Nullable @Override protected Deserializer<Action> deserializer() {
      return new Deserializer<Action>() {
        @Nullable @Override
        public Action deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Action> type,
            @NotNull Gson gson) {
          final String str = InternalUtil.assertJsonIs(json, JsonPrimitive.class).getAsString();
          for (final Action action : Action.values()) {
            if (action.value.equals(str)) {
              return action;
            }
          }
          throw new IllegalStateException("Unknown Action: " + str);
        }
      };
    }

    @NotNull @Override protected TypeToken<Action> typeToken() {
      return new TypeToken<Action>() {};
    }
  }
}
