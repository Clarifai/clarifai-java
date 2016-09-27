package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.Model;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class DeleteAllModelsRequest extends ClarifaiRequest.Builder<List<Model<?>>> {

  public DeleteAllModelsRequest(@NotNull final BaseClarifaiClient helper) {
    super(helper);
  }

  @NotNull @Override protected JSONUnmarshaler<List<Model<?>>> unmarshaler() {
    return new JSONUnmarshaler<List<Model<?>>>() {
      @Nullable @Override
      public List<Model<?>> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return Collections.emptyList();
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    return new Request.Builder()
        .url(buildURL("/v2/models"))
        .delete()
        .build();
  }
}
