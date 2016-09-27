package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.internal.ClarifaiUtil;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class AddInputsRequest extends ClarifaiRequest.Builder<List<ClarifaiInput>> {

  @NotNull private final List<ClarifaiInput> inputs = new ArrayList<>();

  public AddInputsRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull public AddInputsRequest plus(@NotNull ClarifaiInput... inputs) {
    Collections.addAll(this.inputs, inputs);
    return this;
  }

  @NotNull public AddInputsRequest plus(@NotNull Collection<ClarifaiInput> inputs) {
    this.inputs.addAll(inputs);
    return this;
  }

  @NotNull @Override protected JSONUnmarshaler<List<ClarifaiInput>> unmarshaler() {
    return new JSONUnmarshaler<List<ClarifaiInput>>() {
      @Nullable @Override
      public List<ClarifaiInput> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return ClarifaiUtil.fromJson(
            gson,
            json.getAsJsonObject().getAsJsonArray("inputs"),
            new TypeToken<List<ClarifaiInput>>() {}
        );
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    final JsonObject requestBody = new JSONObjectBuilder()
        .add("inputs", ClarifaiUtil.toJson(gson, inputs, new TypeToken<List<ClarifaiInput>>() {}))
        .build();
    return new Request.Builder()
        .url(buildURL("/v2/inputs"))
        .post(toRequestBody(requestBody))
        .build();
  }
}
