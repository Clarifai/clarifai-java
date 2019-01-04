package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonNull;
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

  @NotNull @Override protected String method() {
    return "DELETE";
  }

  @NotNull @Override protected String subUrl() {
    return "/v2/models";
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
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().deleteModels(ModelOuterClass.DeleteModelsRequest.newBuilder().addAllIds(modelIDs).build());
      }

      @NotNull @Override public JsonNull unmarshalerGrpc(Object returnedObject) {
        return JsonNull.INSTANCE;
      }
    };
  }
}
