package clarifai2.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JSONObjectBuilder {

  private final JsonObject inner;

  public JSONObjectBuilder() {
    this(new JsonObject());
  }

  public JSONObjectBuilder(final JsonObject startWith) {
    this.inner = startWith;
  }

  @NotNull public JSONObjectBuilder add(@NotNull String name, @NotNull JSONObjectBuilder json) {
    return add(name, json.build());
  }

  @NotNull public JSONObjectBuilder add(@NotNull String name, @NotNull JSONArrayBuilder json) {
    return add(name, json.build());
  }

  @NotNull public JSONObjectBuilder add(@NotNull String name, @Nullable JsonElement json) {
    inner.add(name, json);
    return this;
  }

  @NotNull public JSONObjectBuilder add(@NotNull String name, @Nullable String value) {
    inner.addProperty(name, value);
    return this;
  }

  @NotNull public JSONObjectBuilder add(@NotNull String name, @Nullable Number value) {
    inner.addProperty(name, value);
    return this;
  }

  @NotNull public JSONObjectBuilder add(@NotNull String name, @Nullable Boolean value) {
    inner.addProperty(name, value);
    return this;
  }

  @NotNull public JSONObjectBuilder add(@NotNull String name, @Nullable Character value) {
    inner.addProperty(name, value);
    return this;
  }

  @NotNull public JSONObjectBuilder addIfNotNull(@NotNull String name, @Nullable JsonElement json) {
    if (json != null) {
      return add(name, json);
    }
    return this;
  }

  @NotNull public JSONObjectBuilder addIfNotNull(@NotNull String name, @Nullable String value) {
    if (value != null) {
      return add(name, value);
    }
    return this;
  }

  @NotNull public JSONObjectBuilder addIfNotNull(@NotNull String name, @Nullable Number value) {
    if (value != null) {
      return add(name, value);
    }
    return this;
  }

  @NotNull public JSONObjectBuilder addIfNotNull(@NotNull String name, @Nullable Boolean value) {
    if (value != null) {
      return add(name, value);
    }
    return this;
  }

  @NotNull public JSONObjectBuilder addIfNotNull(@NotNull String name, @Nullable Character value) {
    if (value != null) {
      return add(name, value);
    }
    return this;
  }

  public int size() {
    return inner.size();
  }

  @NotNull public JsonObject build() {
    return inner;
  }

  @Override public int hashCode() {
    return inner.hashCode();
  }

  @Override public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof JSONObjectBuilder) {
      final JSONObjectBuilder that = (JSONObjectBuilder) obj;
      return this == that || this.inner.equals(that.inner);
    }
    if (obj instanceof JsonObject) {
      final JsonObject that = (JsonObject) obj;
      return this.inner.equals(that);
    }
    return false;
  }

  @Override public String toString() {
    return inner.toString();
  }
}
