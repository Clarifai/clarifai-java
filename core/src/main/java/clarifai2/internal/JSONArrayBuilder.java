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

  public JSONArrayBuilder(@NotNull final Iterable<JsonElement> startWith) {
    for (final JsonElement element : startWith) {
      add(element);
    }
  }

  @NotNull public JSONArrayBuilder add(@NotNull JsonElement element) {
    inner.add(element);
    return this;
  }

  @NotNull public JSONArrayBuilder add(@NotNull final JSONArrayBuilder other) {
    return add(other.build());
  }

  @NotNull public JSONArrayBuilder add(@NotNull final JSONObjectBuilder json) {
    return add(json.build());
  }

  @NotNull public JSONArrayBuilder add(@NotNull final String value) {
    inner.add(value);
    return this;
  }

  @NotNull public JSONArrayBuilder add(@NotNull final Boolean value) {
    inner.add(value);
    return this;
  }

  @NotNull public JSONArrayBuilder add(@NotNull final Character value) {
    inner.add(value);
    return this;
  }

  @NotNull public JSONArrayBuilder add(@NotNull final Number value) {
    inner.add(value);
    return this;
  }

  @NotNull
  public <T> JSONArrayBuilder addAll(@NotNull final Iterable<T> iterable, @NotNull final Func1<T, JsonElement> mapper) {
    for (final T element : iterable) {
      add(mapper.call(element));
    }
    return this;
  }

  @NotNull public JSONArrayBuilder addStrings(@NotNull final Iterable<String> values) {
    for (final String value : values) {
      add(value);
    }
    return this;
  }

  @NotNull public JSONArrayBuilder addBooleans(@NotNull final Iterable<Boolean> values) {
    for (final Boolean value : values) {
      add(value);
    }
    return this;
  }


  @NotNull public JSONArrayBuilder addCharacters(@NotNull final Iterable<Character> values) {
    for (final Character value : values) {
      add(value);
    }
    return this;
  }

  @NotNull public JSONArrayBuilder addNumbers(@NotNull final Iterable<Number> values) {
    for (final Number value : values) {
      add(value);
    }
    return this;
  }

  @NotNull public JsonArray build() {
    return inner;
  }

  @Override public int hashCode() {
    return inner.hashCode();
  }

  @Override public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof JSONArrayBuilder) {
      final JSONArrayBuilder that = (JSONArrayBuilder) obj;
      return this == that || this.inner.equals(that.inner);
    }
    if (obj instanceof JsonArray) {
      final JsonArray that = (JsonArray) obj;
      return this.inner.equals(that);
    }
    return false;
  }

  @Override public String toString() {
    return inner.toString();
  }
}
