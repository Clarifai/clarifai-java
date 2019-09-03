package clarifai2.api.request.input;

import clarifai2.internal.grpc.api.InputOuterClass;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonNull;
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

  @NotNull @Override protected String method() {
    return "DELETE";
  }

  @NotNull @Override protected String subUrl() {
    return "v2/inputs";
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
      @NotNull @Override public ListenableFuture httpRequestGrpc() {
        return stub().deleteInputs(InputOuterClass.DeleteInputsRequest.newBuilder().addAllIds(inputIDs).build());
      }

      @NotNull @Override public JsonNull unmarshalerGrpc(Object returnedObject) {
        return JsonNull.INSTANCE;
      }
    };
  }
}
