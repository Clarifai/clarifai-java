package clarifai2.dto;

import org.jetbrains.annotations.NotNull;

public interface HasClarifaiIDRequired extends HasClarifaiID {
  @NotNull @Override String id();
}
