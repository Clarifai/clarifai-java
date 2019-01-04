package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.model.Model;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class GetModelsRequest extends ClarifaiPaginatedRequest.Builder<List<Model<?>>, GetModelsRequest> {

  public GetModelsRequest(@NotNull final BaseClarifaiClient client) {
    super(client);
  }

  @NotNull @Override protected String method() {
    return "GET";
  }

  @NotNull @Override protected String subUrl(int page) {
    return buildURL("/v2/models", page);
  }

  @NotNull @Override protected List<Model<?>> unmarshalerGrpc(Object returnedObject) {
    ModelOuterClass.MultiModelResponse modelsResponse = (ModelOuterClass.MultiModelResponse) returnedObject;
    List<Model<?>> models = new ArrayList<>();
    for (ModelOuterClass.Model model : modelsResponse.getModelsList()) {
      models.add(Model.deserialize(model, client));
    }
    return models;
  }

  @NotNull @Override protected ListenableFuture buildRequestGrpc(int page) {
    return stub(page).listModels(ModelOuterClass.ListModelsRequest.newBuilder().build());
  }
}
