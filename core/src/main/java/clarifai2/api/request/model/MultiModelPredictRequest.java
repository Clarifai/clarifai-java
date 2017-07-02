package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.multimodel.WorkflowResult;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.prediction.Prediction;
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

/*
 * NOTE: This class is a part of a future api.  Details of both the interface and
 * implementation may change!
 */

public final class MultiModelPredictRequest<PREDICTION extends Prediction>
    extends ClarifaiRequest.Builder<WorkflowResult<PREDICTION>> {

  @NotNull private final String workflowID;
  @NotNull private final List<ClarifaiURLImage> urlImages = new ArrayList<>();

  public MultiModelPredictRequest(@NotNull final BaseClarifaiClient client, @NotNull String workflowID) {
    super(client);
    this.workflowID = workflowID;
  }

  @NotNull public MultiModelPredictRequest<PREDICTION> withUrlImages(@NotNull ClarifaiURLImage... urlImages) {
    return withUrlImages(Arrays.asList(urlImages));
  }

  @NotNull public MultiModelPredictRequest<PREDICTION> withUrlImages(@NotNull Collection<ClarifaiURLImage> urlImages) {
    this.urlImages.addAll(urlImages);
    return this;
  }

  @NotNull @Override protected DeserializedRequest<WorkflowResult<PREDICTION>> request() {
    return new DeserializedRequest<WorkflowResult<PREDICTION>>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder()
            .add("inputs", new JSONArrayBuilder()
                .addAll(urlImages, new Func1<ClarifaiURLImage, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull ClarifaiURLImage model) {
                    JSONObjectBuilder imageJsonObject = new JSONObjectBuilder()
                        .add("url", model.url().toString());
                    if (model.allowDuplicateUrl() != null) {
                      imageJsonObject.add("allow_duplicate_url", model.allowDuplicateUrl());
                    }
                    return new JSONObjectBuilder()
                        .add("data", new JSONObjectBuilder()
                            .add("image", imageJsonObject)).build();
                  }
                }));
        final JsonObject body = bodyBuilder.build();
        return postRequest("/v2/workflows/" + workflowID + "/results", body);
      }

      @NotNull @Override public JSONUnmarshaler<WorkflowResult<PREDICTION>> unmarshaler() {
        return new JSONUnmarshaler<WorkflowResult<PREDICTION>>() {
          @NotNull @Override
          public WorkflowResult<PREDICTION> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            JsonObject rootObject = json.getAsJsonObject();
            WorkflowResult<PREDICTION> workflowResults = InternalUtil.fromJson(
                gson, rootObject, new TypeToken<WorkflowResult<PREDICTION>>() {});
            return workflowResults;
          }
        };
      }
    };
  }
}
