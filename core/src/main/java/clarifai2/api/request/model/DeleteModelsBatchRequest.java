package clarifai2.api.request.model;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.*;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class DeleteModelsBatchRequest extends ClarifaiRequest.Builder<JsonNull> {

  @NotNull private final List<String> modelIDs = new ArrayList<>();

  public DeleteModelsBatchRequest(@NotNull BaseClarifaiClient client) {
    super(client);
  }

  @NotNull public DeleteModelsBatchRequest plus(@NotNull String... modelIDs) {
    return plus(Arrays.asList(modelIDs));
  }

  @NotNull public DeleteModelsBatchRequest plus(@NotNull Collection<String> modelIDs) {
    this.modelIDs.addAll(modelIDs);
    return this;
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public Request httpRequest() {
        final JsonObject body = new JSONObjectBuilder()
            .add("ids", new JSONArrayBuilder()
                .addAll(modelIDs, new Func1<String, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull String modelID) {
                    return new JsonPrimitive(modelID);
                  }
                })
            )
            .build();
        return postRequest("/v2/models", body);
      }

      @NotNull @Override public JSONUnmarshaler<JsonNull> unmarshaler() {
        return new JSONUnmarshaler<JsonNull>() {
          @NotNull @Override public JsonNull fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return JsonNull.INSTANCE;
          }
        };
      }
    };
  }
}
