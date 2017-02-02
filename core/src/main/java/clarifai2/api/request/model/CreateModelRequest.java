package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.*;

public final class CreateModelRequest extends ClarifaiRequest.Builder<ConceptModel> {

  @NotNull private final BaseClarifaiClient helper;

  @Nullable private ConceptOutputInfo outputInfo;
  @NotNull private final String id;

  @Nullable private String name = null;

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

  @NotNull public CreateModelRequest withLanguage(@NotNull String language) {
    this.language = language;
    return this;
  }

  @NotNull @Override protected DeserializedRequest<ConceptModel> request() {
    return new DeserializedRequest<ConceptModel>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder();
        bodyBuilder.add("model", buildJSONOfModel());
        final JsonObject body = bodyBuilder.build();
        return postRequest("/v2/models", body);
      }

      @NotNull @Override public JSONUnmarshaler<ConceptModel> unmarshaler() {
        return new JSONUnmarshaler<ConceptModel>() {
          @NotNull @Override public ConceptModel fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return assertNotNull(
                fromJson(gson, assertJsonIs(json, JsonObject.class).get("model"), new TypeToken<Model<?>>() {})
            ).asConceptModel();
          }
        };
      }
    };
  }

  @NotNull protected final JsonElement buildJSONOfModel() {
    return toJson(
        client.gson,
        Model._create(
            ModelType.CONCEPT,
            helper,
            id,
            name != null ? name : id,
            outputInfo
        ),
        new TypeToken<Model<?>>() {}
    );
  }
}
