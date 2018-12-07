package clarifai2.solutions.moderation.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.prediction.Prediction;
import clarifai2.internal.InternalUtil;
import clarifai2.solutions.moderation.dto.ModerationOutput;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;
import okhttp3.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class ModerationPredictRequest<PREDICTION extends Prediction>
    extends ClarifaiRequest.Builder<List<ModerationOutput<PREDICTION>>> {

  @NotNull private final String modelID;
  @NotNull private final List<ClarifaiInput> inputData = new ArrayList<>();

  public ModerationPredictRequest(@NotNull BaseClarifaiClient client, @NotNull String modelID) {
    // TODO(Rok) LOW: Once backend supports the utf-8 charset header, remove this MediaType argument.
    super(client, MediaType.parse("application/json"));
    this.modelID = modelID;
  }

  @NotNull public ModerationPredictRequest<PREDICTION> withInputs(@NotNull ClarifaiInput... inputData) {
    return withInputs(Arrays.asList(inputData));
  }

  @NotNull public ModerationPredictRequest<PREDICTION> withInputs(@NotNull Collection<ClarifaiInput> inputData) {
    this.inputData.addAll(inputData);
    return this;
  }

  @NotNull @Override protected DeserializedRequest<List<ModerationOutput<PREDICTION>>> request() {
    return new DeserializedRequest<List<ModerationOutput<PREDICTION>>>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder()
            .add("inputs", new JSONArrayBuilder()
                .addAll(inputData, new Func1<ClarifaiInput, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull ClarifaiInput model) {
                    return client.gson.toJsonTree(model);
                  }
                }));
        final JsonObject body = bodyBuilder.build();
        return postRequest("/v2/models/" + modelID + "/outputs", body);
      }

      @NotNull @Override public JSONUnmarshaler<List<ModerationOutput<PREDICTION>>> unmarshaler() {
        return new JSONUnmarshaler<List<ModerationOutput<PREDICTION>>>() {
          @NotNull @Override
          public List<ModerationOutput<PREDICTION>> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return InternalUtil.fromJson(
                gson,
                json.getAsJsonObject().get("outputs"),
                new TypeToken<List<ModerationOutput<PREDICTION>>>() {}
            );
          }
        };
      }
    };
  }
}
