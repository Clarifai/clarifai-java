package clarifai2.api.request.input;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DeleteInputsBatchRequest extends ClarifaiRequest.Builder<JsonNull> {

  @NotNull private final List<String> inputIDs = new ArrayList<>();

  public DeleteInputsBatchRequest(@NotNull BaseClarifaiClient client) {
    super(client);
  }

  @NotNull public DeleteInputsBatchRequest plus(@NotNull String... inputIDs) {
    return plus(Arrays.asList(inputIDs));
  }

  @NotNull public DeleteInputsBatchRequest plus(@NotNull Collection<String> inputIDs) {
    this.inputIDs.addAll(inputIDs);
    return this;
  }

  @NotNull @Override protected DeserializedRequest<JsonNull> request() {
    return new DeserializedRequest<JsonNull>() {
      @NotNull @Override public Request httpRequest() {
        final JsonObject body = new JSONObjectBuilder().add("ids", new JSONArrayBuilder()
            .addAll(inputIDs, new Func1<String, JsonElement>() {
              @NotNull @Override public JsonElement call(@NotNull String s) {
                return new JsonPrimitive(s);
              }
            })
        ).build();
        return deleteRequest("v2/inputs", body);
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
