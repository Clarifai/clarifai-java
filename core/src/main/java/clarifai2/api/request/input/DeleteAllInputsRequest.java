package clarifai2.api.request.input;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class DeleteAllInputsRequest extends ClarifaiRequest.Builder<List<ClarifaiInput>> {

  public DeleteAllInputsRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
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
    return new Request.Builder()
        .url(buildURL("/v2/inputs"))
        .delete()
        .build();
  }
}
