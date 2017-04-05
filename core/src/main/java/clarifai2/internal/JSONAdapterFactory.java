package clarifai2.internal;

import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import static clarifai2.internal.InternalUtil.isRawType;

/**
 * A type-safe {@link TypeAdapterFactory}. This is preferable to using just a simple {@link TypeAdapter} because you
 * can access the underlying {@link Gson} instance, so nested objects can be handled by other adapters that are
 * registered wit that Gson instance.
 *
 * @param <T> the type of data that will be serialized and/or deserialized
 */
public abstract class JSONAdapterFactory<T> implements TypeAdapterFactory {

  @NotNull private final TypeToken<T> typeToken;
  @Nullable private final Serializer<T> serializer;
  @Nullable private final Deserializer<T> deserializer;

  public JSONAdapterFactory() {
    this.typeToken = typeToken();
    this.serializer = serializer();
    this.deserializer = deserializer();
  }

  /**
   * @return a {@link Serializer} that defines how to convert this object to a JSON representation, or {@code null}
   * if this factory doesn't want to define how to perform this serialization.
   */
  @Nullable protected Serializer<T> serializer() {
    return null;
  }

  /**
   * @return a {@link Deserializer} that defines how to reify this object from a JSON representation, or {@code null}
   * if this factory doesn't want to define how to perform this deserialization.
   */
  @Nullable protected Deserializer<T> deserializer() {
    return null;
  }

  @NotNull protected abstract TypeToken<T> typeToken();

  @Override public final <T1> TypeAdapter<T1> create(final Gson gson, final TypeToken<T1> typeToken) {
    final boolean shouldHandleThisType;
    if (isRawType(this.typeToken)) {
      // If this adapter specifies that it supports a raw type, we will treat the candidate type as a raw type as well
      shouldHandleThisType = this.typeToken.getType().equals(typeToken.getRawType());
    } else {
      shouldHandleThisType = this.typeToken.equals(typeToken);
    }
    // Return null if the type is not what this factory is meant to produce, so that Gson knows we can't handle that type
    if (!shouldHandleThisType) {
      return null;
    }
    //noinspection unchecked
    return (TypeAdapter<T1>) buildAdapter(gson);
  }

  @NotNull private TypeAdapter<T> buildAdapter(@NotNull Gson gson) {
    return new Adapter<>(gson, typeToken, serializer, deserializer, this);
  }

  @NotNull private TypeAdapter<T> passthroughAdapter(@NotNull Gson gson) {
    return gson.getDelegateAdapter(this, typeToken);
  }

  /**
   * An interface that defines how to convert a given object to JSON. Null objects must be handled!
   *
   * @param <T> the data-type that is being converted to JSON
   */
  protected interface Serializer<T> {
    @NotNull JsonElement serialize(
        @Nullable final T value,
        @NotNull final Gson gson
    );
  }


  /**
   * An interface that defines how to convert a given JSON element to an object. {@link JsonNull} can be present as
   * the serialized value as well, and must be handled!
   *
   * @param <T> the data-type that is being reified from the JSON
   */
  protected interface Deserializer<T> {
    @Nullable T deserialize(
        @NotNull final JsonElement json,
        @NotNull final TypeToken<T> type,
        @NotNull final Gson gson
    );
  }


  private static class Adapter<T> extends TypeAdapter<T> {

    @NotNull private final Gson gson;

    /**
     * The type that should be handled by this adapter
     */
    @NotNull private final TypeToken<T> token;

    /**
     * The interface that defines how to serialize this type, or {@code null} if this adapter should not handle
     * serialization of this type.
     */
    @Nullable private final Serializer<T> serializer;

    /**
     * The interface that defines how to deserialize this type, or {@code null} if this adapter should not handle
     * deserialization of this type.
     */
    @Nullable private final Deserializer<T> deserializer;

    /**
     * used to build a delegate adapter that is stored into {@link #passthroughAdapter}
     */
    @NotNull private final TypeAdapterFactory toBePassedThrough;
    /**
     * Should not be used directly; only a lazy backing field that should be grabbed by calling {@link #getPassthroughAdapter()}
     */
    @Nullable private TypeAdapter<T> passthroughAdapter;

    private Adapter(
        @NotNull Gson gson,
        @NotNull TypeToken<T> token,
        @Nullable Serializer<T> serializer,
        @Nullable Deserializer<T> deserializer,
        @NotNull TypeAdapterFactory toBePassedThrough
    ) {
      this.gson = gson;
      this.token = token;
      this.serializer = serializer;
      this.deserializer = deserializer;
      this.toBePassedThrough = toBePassedThrough;
    }

    @NotNull private TypeAdapter<T> getPassthroughAdapter() {
      if (passthroughAdapter == null) {
        passthroughAdapter = gson.getDelegateAdapter(toBePassedThrough, token);
      }
      return passthroughAdapter;
    }

    @Override public void write(JsonWriter jsonWriter, T value) throws IOException {
      if (serializer == null) {
        getPassthroughAdapter().write(jsonWriter, value);
      } else {
        final JsonElement serialized = serializer.serialize(value, gson);
        Streams.write(serialized, jsonWriter);
      }
    }

    @Override public T read(JsonReader jsonReader) throws IOException {
      if (deserializer == null) {
        return getPassthroughAdapter().read(jsonReader);
      } else {
        final JsonElement json = Streams.parse(jsonReader);
        return deserializer.deserialize(json, token, gson);
      }
    }
  }
}
