package clarifai2.internal;

import clarifai2.exception.ClarifaiException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class InternalUtil {

  public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf8");

  private InternalUtil() {
    throw new UnsupportedOperationException("No instances");
  }

  @NotNull
  public static <T> T fromJson(@NotNull Gson gson, @NotNull JsonElement element, @NotNull TypeToken<T> token) {
    final T result = gson.fromJson(element, token.getType());
    if (result == null) {
      throw new ClarifaiException("fromJson returned null. Json: " + element);
    }
    return result;
  }

  @Nullable
  public static <T> T nullSafeTraverse(@NotNull JsonObject root, @NotNull String... traversalKeys) {
    JsonElement current = root;
    for (final String traversalKey : traversalKeys) {
      if (current == null || !current.isJsonObject()) {
        return null;
      }
      current = current.getAsJsonObject().get(traversalKey);
    }
    if (current == null || current.isJsonNull()) {
      return null;
    }
    final Object returnValue;
    if (current.isJsonPrimitive()) {
      returnValue = getJsonPrimitiveUnsafe(current.getAsJsonPrimitive());
    } else {
      returnValue = current;
    }
    //noinspection unchecked
    return ((T) returnValue);
  }

  @Nullable
  private static Object getJsonPrimitiveUnsafe(@NotNull JsonPrimitive root) {
    if (root.isBoolean()) {
      return root.getAsBoolean();
    }
    if (root.isString()) {
      return root.getAsString();
    }
    if (root.isNumber()) {
      // THIS IS NOT GREAT
      final Number num = root.getAsNumber();
      try {
        return num.longValue();
      } catch(NumberFormatException e) {
        return num.doubleValue();
      }
    }
    return null;
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
