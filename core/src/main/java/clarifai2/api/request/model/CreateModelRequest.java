package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CreateModelRequest extends ClarifaiRequest.Builder<ConceptModel> {

  @NotNull private final BaseClarifaiClient helper;

  @Nullable private ConceptOutputInfo outputInfo;
  @NotNull private final String id;
  @Nullable private String name;

  public CreateModelRequest(@NotNull final BaseClarifaiClient helper, @NotNull final String id) {
    super(helper);
    this.helper = helper;
    this.id = id;
  }

  @NotNull public CreateModelRequest withOutputInfo(@Nullable ConceptOutputInfo outputInfo) {
    this.outputInfo = outputInfo;
    return this;
  }

  @NotNull public CreateModelRequest withName(@Nullable String name) {
    this.name = name;
    return this;
  }

  @NotNull @Override protected JSONUnmarshaler<ConceptModel> unmarshaler() {
    return new JSONUnmarshaler<ConceptModel>() {
      @Nullable @Override public ConceptModel fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
        return gson.fromJson(json.getAsJsonObject().get("model"), new TypeToken<Model<Concept>>() {}.getType());
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    final JsonObject body = new JSONObjectBuilder()
        .add("model", gson.toJsonTree(
            Model._create(
                ModelType.CONCEPT,
                helper,
                id,
                name != null ? name : id,
                outputInfo
            ),
            new TypeToken<Model<Concept>>() {}.getType()
        )).build();
    return new Request.Builder()
        .url(buildURL("/v2/models"))
        .post(toRequestBody(body))
        .build();
  }
}
