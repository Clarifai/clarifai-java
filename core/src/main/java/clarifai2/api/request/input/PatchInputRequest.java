package clarifai2.api.request.input;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.feedback.RegionFeedback;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Region;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class PatchInputRequest extends ClarifaiRequest.Builder<ClarifaiInput> {
  @NotNull private final String action;
  @NotNull private final String inputID;

  @NotNull private final List<Concept> concepts = new ArrayList<>();
  @NotNull private final List<RegionFeedback> regionFeedbacks = new ArrayList<>();

  public PatchInputRequest(@NotNull BaseClarifaiClient client, @NotNull String inputID, @NotNull String action) {
    super(client);
    this.inputID = inputID;
    this.action = action;
  }

  @NotNull public PatchInputRequest plus(@NotNull Concept... concepts) {
    return plus(Arrays.asList(concepts));
  }

  @NotNull public PatchInputRequest plus(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull public PatchInputRequest plusRegionFeedbacks(@NotNull RegionFeedback... regionFeedbacks) {
    return plusRegionFeedbacks(Arrays.asList(regionFeedbacks));
  }

  @NotNull public PatchInputRequest plusRegionFeedbacks(@NotNull Collection<RegionFeedback> regionFeedbacks) {
    this.regionFeedbacks.addAll(regionFeedbacks);
    return this;
  }

  @NotNull @Override protected DeserializedRequest<ClarifaiInput> request() {
    return new DeserializedRequest<ClarifaiInput>() {
      @NotNull @Override public Request httpRequest() {
        JSONObjectBuilder data = new JSONObjectBuilder();
        if (!concepts.isEmpty()) {
          data = data.add("concepts", new JSONArrayBuilder()
              .addAll(concepts, new Func1<Concept, JsonElement>() {
                @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                  return client.gson.toJsonTree(concept);
                }
              }));
        }
        if (!regionFeedbacks.isEmpty()) {
          data = data.add("regions", new JSONArrayBuilder()
              .addAll(regionFeedbacks, new Func1<RegionFeedback, JsonElement>() {
                @NotNull @Override public JsonElement call(@NotNull RegionFeedback region) {
                  return client.gson.toJsonTree(region);
                }
              }));
        }

        final JsonObject body = new JSONObjectBuilder()
            .add("action", action)
            .add("inputs", new JSONArrayBuilder()
                .add(new JSONObjectBuilder()
                  .add("id", inputID)
                  .add("data", data)))
            .build();
        return patchRequest("/v2/inputs", body);
      }

      @NotNull @Override public JSONUnmarshaler<ClarifaiInput> unmarshaler() {
        return new JSONUnmarshaler<ClarifaiInput>() {
          @NotNull @Override public ClarifaiInput fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            final JsonElement firstInput = json.getAsJsonObject().getAsJsonArray("inputs").get(0);
            return InternalUtil.fromJson(gson, firstInput, new TypeToken<ClarifaiInput>() {});
          }
        };
      }
    };
  }
}
