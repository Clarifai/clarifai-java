package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static clarifai2.internal.InternalUtil.*;

public final class AddInputsRequest extends ClarifaiRequest.Builder<List<ClarifaiInput>> {

  private static final int MAX_NUM_INPUTS = 128;

  @NotNull private final List<ClarifaiInput> inputs = new ArrayList<>();

  private boolean allowDuplicateURLs = false;

  public AddInputsRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull public AddInputsRequest plus(@NotNull ClarifaiInput... inputs) {
    return plus(Arrays.asList(inputs));
  }

  @NotNull public AddInputsRequest plus(@NotNull Collection<ClarifaiInput> inputs) {
    if (inputs.size() > MAX_NUM_INPUTS) {
      throw new IllegalArgumentException(String.format("Can't add more than %d inputs in one request", MAX_NUM_INPUTS));
    }
    this.inputs.addAll(inputs);
    return this;
  }

  /**
   * @param allowDuplicateURLs whether the API should allow uploading inputs with URLs that are already part of other
   *                           inputs in this app.
   * @return this request builder
   */
  @NotNull public AddInputsRequest allowDuplicateURLs(boolean allowDuplicateURLs) {
    this.allowDuplicateURLs = allowDuplicateURLs;
    return this;
  }

  @NotNull @Override protected DeserializedRequest<List<ClarifaiInput>> request() {
    return new DeserializedRequest<List<ClarifaiInput>>() {

      @NotNull @Override public Request httpRequest() {
        final TypeToken<List<ClarifaiInput>> type = new TypeToken<List<ClarifaiInput>>() {};
        final JsonArray inputs = toJson(client.gson, AddInputsRequest.this.inputs, type).getAsJsonArray();
        if (allowDuplicateURLs) {
          for (final JsonElement input : inputs) {
            final JsonObject image = input.getAsJsonObject().getAsJsonObject("data").getAsJsonObject("image");
            if (image.has("url")) {
              image.addProperty("allow_duplicate_url", allowDuplicateURLs);
            }
          }
        }

        final JsonObject body = new JSONObjectBuilder()
            .add("inputs", inputs)
            .build();
        return postRequest("/v2/inputs", body);
      }

      @NotNull @Override public JSONUnmarshaler<List<ClarifaiInput>> unmarshaler() {
        return new JSONUnmarshaler<List<ClarifaiInput>>() {
          @NotNull @Override public List<ClarifaiInput> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return assertNotNull(fromJson(
                gson,
                json.getAsJsonObject().getAsJsonArray("inputs"),
                new TypeToken<List<ClarifaiInput>>() {}
            ));
          }
        };
      }
    };
  }
}
