package clarifai2.solutions;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.solutions.moderation.Moderation;
import org.jetbrains.annotations.NotNull;

public class Solutions {
  @NotNull private final Moderation moderation;

  public Solutions(@NotNull String apiKey) {
    this.moderation = new Moderation(apiKey);
  }

  public Moderation moderation() {
    return this.moderation;
  }
}
