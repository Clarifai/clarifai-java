package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.model.output_info.FaceConceptsOutputInfo;
import clarifai2.dto.model.output_info.OutputInfo;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.assertNotNull;
import static clarifai2.internal.InternalUtil.fromJson;

public final class CreateModelGenericRequest<T extends Model<?>> extends ClarifaiRequest.Builder<T> {

  @NotNull private final BaseClarifaiClient helper;
  @NotNull private final String id;
  @Nullable private OutputInfo outputInfo;
  @Nullable private String name = null;

  public CreateModelGenericRequest(@NotNull final BaseClarifaiClient helper, @NotNull final String id) {
    super(helper);
    this.helper = helper;
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

  @NotNull @Override protected DeserializedRequest<T> request() {
    return new DeserializedRequest<T>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder();

        bodyBuilder.add("model", buildJSONOfModel());
        final JsonObject body = bodyBuilder.build();
        return postRequest("/v2/models", body);
      }

      @NotNull @Override public JSONUnmarshaler<T> unmarshaler() {
        return new JSONUnmarshaler<T>() {
          @NotNull @Override public T fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            JsonElement model = assertJsonIs(json, JsonObject.class).get("model");

            return assertNotNull((T) fromJson(gson, model, new TypeToken<Model<?>>() {}));
          }
        };
      }
    };
  }

  @NotNull protected JsonElement buildJSONOfModel() {

    return new JSONObjectBuilder()
        .add("id", id)
        .addIfNotNull("name", name)
        .addIfNotNull("output_info", client.gson.toJsonTree(outputInfo, OutputInfo.class))
        .build();
  }
}
