package clarifai2;

import org.jetbrains.annotations.NotNull;

public interface Func1<T, R> {
  @NotNull R call(@NotNull T t);
}
