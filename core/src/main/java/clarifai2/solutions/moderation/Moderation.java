package clarifai2.solutions.moderation;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.dto.prediction.Concept;
import clarifai2.solutions.moderation.api.request.input.GetModerationStatusRequest;
import clarifai2.solutions.moderation.api.request.model.ModerationPredictRequest;
import org.jetbrains.annotations.NotNull;

public class Moderation {

  @NotNull private final BaseClarifaiClient client;

  public Moderation(@NotNull String apiKey) {
    this.client = (BaseClarifaiClient) new ClarifaiBuilder(apiKey)
        .baseURL("https://api.clarifai-moderation.com")
        .buildSync();
  }

  @NotNull public ModerationPredictRequest<Concept> predict(@NotNull String modelID) {
    return new ModerationPredictRequest<>(this.client, modelID);
  }

  @NotNull public GetModerationStatusRequest getModerationStatus(@NotNull String inputID) {
    return new GetModerationStatusRequest(this.client, inputID);
  }
}
