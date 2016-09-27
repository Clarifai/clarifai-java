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

  @NotNull public JsonObject build() {
    return inner;
  }
}
