package clarifai2.api.request.input;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiInputUpdateAction;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class DeleteInputsRequest extends ClarifaiRequest.Builder<List<ClarifaiInput>> {

  @NotNull private final List<String> inputIDs = new ArrayList<>();

  public DeleteInputsRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull public DeleteInputsRequest delete(@NotNull String... inputIDsToDelete) {
    Collections.addAll(inputIDs, inputIDsToDelete);
    return this;
  }

  @NotNull public DeleteInputsRequest delete(@NotNull Collection<String> inputIDsToDelete) {
    this.inputIDs.addAll(inputIDsToDelete);
    return this;
  }

  @NotNull @Override protected JSONUnmarshaler<List<ClarifaiInput>> unmarshaler() {
    return new JSONUnmarshaler<List<ClarifaiInput>>() {
      @Nullable @Override
      public List<ClarifaiInput> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return Collections.emptyList();
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    final JsonObject body = new JSONObjectBuilder()
        .add("inputs", new JSONArrayBuilder()
            .addAll(inputIDs, new Func1<String, JsonElement>() {
              @NotNull @Override public JsonElement call(@NotNull String model) {
                return new JSONObjectBuilder()
                    .add("id", model)
                    .build();
              }
            })
            .build()
        )
        .add("action", gson.toJsonTree(ClarifaiInputUpdateAction.DELETE_INPUTS))
        .build();
    return new Request.Builder()
        .url(buildURL("/v2/inputs"))
        .patch(toRequestBody(body))
        .build();
  }
}
