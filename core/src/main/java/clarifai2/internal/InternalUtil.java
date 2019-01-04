package clarifai2.internal;

import clarifai2.internal.grpc.api.GeoOuterClass;
import clarifai2.Func1;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.PointF;
import clarifai2.exception.ClarifaiException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class InternalUtil {

  public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf8");

  private InternalUtil() {
    throw new UnsupportedOperationException("No instances");
  }

  public static boolean isRawType(@NotNull TypeToken<?> token) {
    return token.getType() instanceof Class;
  }

  @Contract("null -> fail") @NotNull public static <T> T assertNotNull(@Nullable T in) {
    if (in == null) {
      throw new NullPointerException();
    }
    return in;
  }

  @Contract("null -> true") public static boolean isJsonNull(@Nullable JsonElement in) {
    return in == null || in.isJsonNull();
  }

  @NotNull public static JsonElement coerceJsonNull(@Nullable JsonElement in) {
    return in == null ? JsonNull.INSTANCE : in;
  }

  /**
   * Asserts that the given JSON is of the type expected, and casts it to that type.
   *
   * @param json         the JSON in question. If {@code null} is passed, it is coerced to {@link JsonNull#INSTANCE}
   * @param expectedType the type that we are asserting this JSON is
   * @param <T>          the type that we are asserting this JSON is
   * @return this JSON, casted to the asserted type
   * @throws JsonParseException if the JSON was not of the asserted type
   */
  @NotNull
  public static <T extends JsonElement> T assertJsonIs(
      @Nullable JsonElement json,
      @NotNull Class<T> expectedType
  ) throws JsonParseException {
    if (json == null) {
      json = JsonNull.INSTANCE;
    }
    if (expectedType.isInstance(json)) {
      return expectedType.cast(json);
    }
    throw new JsonParseException(String.format("This JSON must be a %s, but it was a %s",
        expectedType.getSimpleName(),
        json.getClass().getSimpleName()
    ));
  }

  public static void assertMetadataHasNoNulls(@NotNull JsonObject json) {
    if (areNullsPresentInDictionaries(json)) {
      throw new IllegalArgumentException("You cannot use null as an entry's value in your metadata!");
    }
  }

  /**
   * @param json the JSON to check
   * @return {@code true} if this JSON is a {@link JsonNull} or if any of the elements it contains, recursively, are {@link JsonNull}s
   */
  public static boolean areNullsPresentInDictionaries(@NotNull JsonElement json) {
    if (!json.isJsonObject()) {
      return json.isJsonNull();
    }
    for (final Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
      if (areNullsPresentInDictionaries(entry.getValue())) {
        return true;
      }
    }
    return false;
  }

  @NotNull public static <T> List<T> copyArray(@NotNull JsonArray in, @NotNull Func1<JsonElement, T> mapper) {
    final List<T> out = new ArrayList<>(in.size());
    for (final JsonElement element : in) {
      out.add(mapper.call(element == null ? JsonNull.INSTANCE : element));
    }
    return out;
  }

  @SuppressWarnings("unchecked") @NotNull public static <E extends JsonElement> E jsonDeepCopy(@NotNull E in) {
    if (in instanceof JsonObject) {
      final JsonObject out = new JsonObject();
      for (final Map.Entry<String, JsonElement> entry : ((JsonObject) in).entrySet()) {
        out.add(entry.getKey(), jsonDeepCopy(entry.getValue()));
      }
      return (E) out;
    }
    if (in instanceof JsonArray) {
      final JsonArray out = new JsonArray();
      for (final JsonElement element : (JsonArray) in) {
        out.add(jsonDeepCopy(element));
      }
      return (E) out;
    }
    if (in instanceof JsonPrimitive || in instanceof JsonNull) {
      return in;
    }
    throw new IllegalArgumentException("Input JSON is of type " + in.getClass() + " and cannot be deep-copied");
  }

  @NotNull public static JsonObject asGeoPointJson(@NotNull PointF geoPoint) {
    return new JSONObjectBuilder()
        .add("latitude", geoPoint.x())
        .add("longitude", geoPoint.y())
        .build();
  }

  @NotNull public static GeoOuterClass.GeoPoint asGeoPointProto(@NotNull PointF geoPoint) {
    return GeoOuterClass.GeoPoint.newBuilder()
        .setLatitude(geoPoint.x())
        .setLongitude(geoPoint.y())
        .build();
  }

  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull public static ClarifaiClient clientInstance(@NotNull Gson gson) {
    return gson.fromJson(new JsonObject(), ClarifaiClient.class);
  }

  @Nullable public static <T> T fromJson(@NotNull Gson gson, @Nullable JsonElement element, @NotNull Class<T> type) {
    return gson.fromJson(element, type);
  }

  @NotNull public static <T> JsonElement toJson(@NotNull Gson gson, @Nullable T obj, @NotNull Class<T> type) {
    return coerceJsonNull(gson.toJsonTree(obj, type));
  }

  @Nullable
  public static <T> T fromJson(@NotNull Gson gson, @Nullable JsonElement element, @NotNull TypeToken<T> type) {
    return gson.fromJson(element, type.getType());
  }

  @NotNull public static <T> JsonElement toJson(@NotNull Gson gson, @Nullable T obj, @NotNull TypeToken<T> type) {
    return coerceJsonNull(gson.toJsonTree(obj, type.getType()));
  }

  @Contract("null, null -> true; null, !null -> false; !null, null -> false")
  public static <T> boolean nullSafeEquals(@Nullable T o1, @Nullable T o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
  }

  @NotNull
  public static byte[] read(File file) {
    if (!file.exists()) {
      throw new ClarifaiException("File " + file.getAbsolutePath() + " does not exist!");
    }
    ByteArrayOutputStream ous = null;
    InputStream ios = null;
    try {
      byte[] buffer = new byte[4096];
      ous = new ByteArrayOutputStream();
      ios = new FileInputStream(file);
      int read;
      while ((read = ios.read(buffer)) != -1) {
        ous.write(buffer, 0, read);
      }
    } catch (IOException e) {
      throw new ClarifaiException("Error while reading " + file.getName(), e);
    } finally {
      try {
        if (ous != null) {
          ous.close();
        }
      } catch (IOException ignored) {
      }
      try {
        if (ios != null) {
          ios.close();
        }
      } catch (IOException ignored) {
      }
    }
    return ous.toByteArray();
  }
}
