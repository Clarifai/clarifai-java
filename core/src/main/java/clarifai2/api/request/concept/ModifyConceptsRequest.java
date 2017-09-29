package clarifai2.api.request.concept;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class ModifyConceptsRequest extends ClarifaiRequest.Builder<List<Concept>> {
  @NotNull private final String action;
  @NotNull private final List<Concept> concepts = new ArrayList<>();

  public ModifyConceptsRequest(@NotNull BaseClarifaiClient client, @NotNull String action) {
    super(client);
    this.action = action;
  }

  @NotNull public ModifyConceptsRequest plus(@NotNull Concept... concepts) {
    return plus(Arrays.asList(concepts));
  }

  @NotNull public ModifyConceptsRequest plus(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull @Override protected DeserializedRequest<List<Concept>> request() {
    return new DeserializedRequest<List<Concept>>() {
      @NotNull @Override public Request httpRequest() {
        final JsonObject body = new JSONObjectBuilder()
            .add("action", action)
            .add("concepts", new JSONArrayBuilder()
                .addAll(concepts, new Func1<Concept, JsonElement>() {
                  @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                    return new JSONObjectBuilder()
                        .add("id", concept.id())
                        .add("name", concept.name())
                        .build();
                  }
                })
            )
            .build();
        return patchRequest("/v2/concepts", body);
      }

      @NotNull @Override public JSONUnmarshaler<List<Concept>> unmarshaler() {
        return new JSONUnmarshaler<List<Concept>>() {
          @NotNull @Override
          public List<Concept> fromJSON(@NotNull final Gson gson, @NotNull final JsonElement json) {
            return InternalUtil.fromJson(
                gson,
                json.getAsJsonObject().get("concepts"),
                new TypeToken<List<Concept>>() {}
            );
          }
        };
      }
    };
  }
}
