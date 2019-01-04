package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import com.google.common.util.concurrent.ListenableFuture;
import clarifai2.internal.grpc.api.ModelOuterClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class FindModelRequest extends ClarifaiPaginatedRequest.Builder<List<Model<?>>, FindModelRequest> {

  @Nullable private String name;
  @Nullable private ModelType modelType;

  public FindModelRequest(@NotNull final BaseClarifaiClient client) {
    super(client);
  }

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl(final int page) {
    return buildURL("/v2/models/searches", page);
  }

  @NotNull public FindModelRequest withName(@Nullable String name) {
    this.name = name;
    return this;
  }

  @NotNull public FindModelRequest withModelType(@NotNull ModelType modelType) {
    this.modelType = modelType;
    return this;
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
    ModelOuterClass.ModelQuery.Builder modelQuery = ModelOuterClass.ModelQuery.newBuilder();
    if (name != null) {
      modelQuery.setName(name);
    }
    if (modelType != null) {
      modelQuery.setType(modelType.typeName());
    }
    return stub(page).postModelsSearches(
        ModelOuterClass.PostModelsSearchesRequest.newBuilder().setModelQuery(modelQuery).build()
    );
  }
}
