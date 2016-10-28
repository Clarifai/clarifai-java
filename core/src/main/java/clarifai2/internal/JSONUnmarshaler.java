package clarifai2.internal;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public interface JSONUnmarshaler<T> {
  @NotNull T fromJSON(@NotNull Gson gson, @NotNull JsonElement json);
}
