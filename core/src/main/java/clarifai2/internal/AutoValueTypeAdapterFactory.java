package clarifai2.internal;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * A {@link TypeAdapterFactory} that attempts to delegate any serialization/deserialization of an AutoValue class to the
 * adapter that is registered for the base class.
 * <p>
 * For example, this allows use of {@link JsonAdapter} on the base AutoValue class. Proper serialization will still
 * occur according to that {@link JsonAdapter} when the class is passed through Gson. Otherwise, Gson would always use
 * default serialization on AutoValue classes, because technically, the generated class has no custom serializer.
 */
public final class AutoValueTypeAdapterFactory implements TypeAdapterFactory {

  private static final String AUTO_VALUE_CLASS_PREFIX = "AutoValue_";

  @Override public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
    final Type type = typeToken.getType();
    if (!(type instanceof Class)) {
      return null;
    }
    final String typeName = ((Class<?>) type).getName();
    if (!typeName.contains(AUTO_VALUE_CLASS_PREFIX)) {
      return null;
    }
    try {
      final Class<?> nonAutoValueType = Class.forName(typeName.replace(AUTO_VALUE_CLASS_PREFIX, ""));
      //noinspection unchecked
      return ((TypeAdapter<T>) gson.getAdapter(nonAutoValueType));
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("Tried to get the non-AutoValue version of " + typeName, e);
    }
  }

}
