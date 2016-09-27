package clarifai2.internal;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JSONUnmarshaler<T> {
  @Nullable T fromJSON(@NotNull Gson gson, @NotNull JsonElement json);
}
