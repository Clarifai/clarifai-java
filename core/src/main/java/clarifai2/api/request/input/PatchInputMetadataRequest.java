package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
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

public final class PatchInputMetadataRequest extends ClarifaiRequest.Builder<ClarifaiInput> {

  @NotNull private final String inputID;
  @NotNull private final JsonObject metadata;

  public PatchInputMetadataRequest(
      @NotNull BaseClarifaiClient client,
      @NotNull String inputID,
      @NotNull JsonObject metadata
  ) {
    super(client);
    this.inputID = inputID;
    this.metadata = metadata;
    InternalUtil.assertMetadataHasNoNulls(metadata);
  }

  @NotNull @Override protected DeserializedRequest<ClarifaiInput> request() {
    return new DeserializedRequest<ClarifaiInput>() {
      @NotNull @Override public Request httpRequest() {
        final JsonObject body = new JSONObjectBuilder()
            .add("action", "overwrite")
            .add("inputs", new JSONArrayBuilder()
                .add(new JSONObjectBuilder()
                    .add("id", inputID)
                    .add("data", new JSONObjectBuilder()
                        .add("metadata", metadata)
                    )
                )
            )
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
