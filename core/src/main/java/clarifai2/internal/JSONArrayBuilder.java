package clarifai2.internal;

import clarifai2.Func1;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public final class JSONArrayBuilder {

  private final JsonArray inner = new JsonArray();

  public JSONArrayBuilder() {
    this(Collections.<JsonElement>emptyList());
  }

  public JSONArrayBuilder(@NotNull Iterable<JsonElement> startWith) {
    for (final JsonElement element : startWith) {
      add(element);
    }
  }

  @NotNull public JSONArrayBuilder add(@NotNull JsonElement element) {
    inner.add(element);
    return this;
  }

  @NotNull public JSONArrayBuilder add(@NotNull JSONArrayBuilder other) {
    return add(other.build());
  }

  @NotNull public JSONArrayBuilder add(@NotNull JSONObjectBuilder json) {
    return add(json.build());
  }

  @NotNull public JSONArrayBuilder add(@NotNull String value) {
    inner.add(value);
    return this;
  }

  @NotNull public JSONArrayBuilder add(@NotNull Boolean value) {
    inner.add(value);
    return this;
  }

  @NotNull public JSONArrayBuilder add(@NotNull Character value) {
    inner.add(value);
    return this;
  }

  @NotNull public JSONArrayBuilder add(@NotNull Number value) {
    inner.add(value);
    return this;
  }

  @NotNull
  public <T> JSONArrayBuilder addAll(@NotNull Iterable<T> iterable, @NotNull Func1<T, JsonElement> mapper) {
    for (final T element : iterable) {
      add(mapper.call(element));
    }
    return this;
  }

  @NotNull public JSONArrayBuilder addStrings(@NotNull Iterable<String> values) {
    for (final String value : values) {
      add(value);
    }
    return this;
  }

  @NotNull public JSONArrayBuilder addBooleans(@NotNull Iterable<Boolean> values) {
    for (final Boolean value : values) {
      add(value);
    }
    return this;
  }


  @NotNull public JSONArrayBuilder addCharacters(@NotNull Iterable<Character> values) {
    for (final Character value : values) {
      add(value);
    }
    return this;
  }

  @NotNull public JSONArrayBuilder addNumbers(@NotNull Iterable<Number> values) {
    for (final Number value : values) {
      add(value);
    }
    return this;
  }

  @NotNull public JsonArray build() {
    return inner;
  }
}
