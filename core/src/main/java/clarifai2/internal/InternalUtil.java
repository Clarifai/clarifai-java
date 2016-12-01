package clarifai2.internal;

import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.exception.ClarifaiException;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
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

  @Contract("null -> fail") @NotNull public static <T> T assertNotNull(@Nullable T in) {
    if (in == null) {
      throw new NullPointerException();
    }
    return in;
  }

  @Contract("null -> false") public static boolean isJsonNull(@Nullable JsonElement in) {
    return in != null && (!in.isJsonNull());
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

  @NotNull public static ClarifaiClient clientInstance(@NotNull JsonDeserializationContext context) {
    return context.deserialize(new JsonObject(), ClarifaiClient.class);
  }

  @Nullable public static <T> T fromJson(
      @NotNull JsonDeserializationContext context,
      @Nullable JsonElement element,
      @NotNull Class<T> type
  ) {
    return context.deserialize(element, type);
  }

  @Nullable public static <T> T fromJson(
      @NotNull JsonDeserializationContext context,
      @Nullable JsonElement element,
      @NotNull TypeToken<T> type
  ) {
    return context.deserialize(element, type.getType());
  }

  @Nullable public static <T> T fromJson(@NotNull Gson gson, @Nullable JsonElement element, @NotNull Class<T> type) {
    return gson.fromJson(element, type);
  }

  @Nullable public static <T> T fromJson(@NotNull Gson gson, @Nullable JsonElement element, @NotNull TypeToken<T> type) {
    return gson.fromJson(element, type.getType());
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
