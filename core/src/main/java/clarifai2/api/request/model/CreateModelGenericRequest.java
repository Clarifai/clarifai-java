package clarifai2.api.request.model;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.model.Model;
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
import static clarifai2.internal.InternalUtil.toJson;

public final class CreateModelGenericRequest<T extends Model> extends ClarifaiRequest.Builder<Model<?>> {

  @NotNull private final BaseClarifaiClient helper;
  @NotNull private final String id;
  @Nullable private OutputInfo outputInfo;
  @Nullable private String name = null;

  public CreateModelGenericRequest(@NotNull final BaseClarifaiClient helper, @NotNull final String id) {
    super(helper);
    this.helper = helper;
    this.id = id;
  }

  @NotNull public CreateModelGenericRequest withOutputInfo(@Nullable OutputInfo outputInfo) {
    this.outputInfo = outputInfo;
    return this;
  }

  @NotNull public CreateModelGenericRequest withName(@Nullable String name) {
    this.name = name;
    return this;
  }

  @NotNull @Override protected DeserializedRequest<Model<?>> request() {
    return new DeserializedRequest<Model<?>>() {
      @NotNull @Override public Request httpRequest() {
        final JSONObjectBuilder bodyBuilder = new JSONObjectBuilder();

        bodyBuilder.add("model", buildJSONOfModel());
        final JsonObject body = bodyBuilder.build();
        return postRequest("/v2/models", body);
      }

      @NotNull @Override public JSONUnmarshaler<Model<?>> unmarshaler() {
        return new JSONUnmarshaler<Model<?>>() {
          @NotNull @Override public Model<?> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
            return assertNotNull(
                fromJson(gson, assertJsonIs(json, JsonObject.class).get("model"), new TypeToken<Model<?>>() {})
            );
          }
        };
      }
    };
  }

  @NotNull protected JsonElement buildJSONOfModel() {
    return toJson(
        client.gson,
        Model._create(
            T.modelTypeStatic(),
            helper,
            id,
            name != null ? name : id,
            outputInfo
        ),
        new TypeToken<Model<?>>() {}
    );
  }
}
