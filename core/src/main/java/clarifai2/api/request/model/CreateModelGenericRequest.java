package clarifai2.api.request.model;

import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output_info.OutputInfo;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CreateModelGenericRequest<T extends Model<?>> extends ClarifaiRequest.Builder<T> {

  @NotNull private final String id;
  @Nullable private OutputInfo outputInfo;
  @Nullable private String name = null;

  public CreateModelGenericRequest(@NotNull final BaseClarifaiClient helper, @NotNull final String id) {
    super(helper);
    this.id = id;
  }

  @NotNull public CreateModelGenericRequest<T> withOutputInfo(@Nullable OutputInfo outputInfo) {
    this.outputInfo = outputInfo;
    return this;
  }

  @NotNull public CreateModelGenericRequest<T> withName(@Nullable String name) {
    this.name = name;
    return this;
  }

  @NotNull @Override protected String method() {
    return "POST";
  }

  @NotNull @Override protected String subUrl() {
    return "v2/models";
  }

  @NotNull @Override protected DeserializedRequest<T> request() {
    return new DeserializedRequest<T>() {
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        ModelOuterClass.Model.Builder modelBuilder = ModelOuterClass.Model.newBuilder()
            .setId(id);
        if (name != null) {
          modelBuilder.setName(name);
        }
        if (outputInfo != null) {
          modelBuilder.setOutputInfo(outputInfo.serialize());
        }
        return stub().postModels(ModelOuterClass.PostModelsRequest.newBuilder().addModels(modelBuilder).build());
      }

      @NotNull @Override public T unmarshalerGrpc(Object returnedObject) {
        ModelOuterClass.SingleModelResponse modelResponse = (ModelOuterClass.SingleModelResponse) returnedObject;
        return (T) Model.deserialize(modelResponse.getModel(), client);
      }
    };
  }
}
