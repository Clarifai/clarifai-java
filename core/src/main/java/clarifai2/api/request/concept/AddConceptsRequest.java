package clarifai2.api.request.concept;

import clarifai2.Func1;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.prediction.Concept;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class AddConceptsRequest extends ClarifaiRequest.Builder<List<Concept>> {

  @NotNull private final List<Concept> concepts = new ArrayList<>();

  public AddConceptsRequest(@NotNull BaseClarifaiClient client) {
    super(client);
  }

  @NotNull public AddConceptsRequest plus(@NotNull Collection<Concept> concepts) {
    this.concepts.addAll(concepts);
    return this;
  }

  @NotNull public AddConceptsRequest plus(@NotNull Concept... concepts) {
    Collections.addAll(this.concepts, concepts);
    return this;
  }

  @NotNull @Override protected JSONUnmarshaler<List<Concept>> unmarshaler() {
    return new JSONUnmarshaler<List<Concept>>() {
      @Nullable @Override public List<Concept> fromJSON(@NotNull Gson gson, @NotNull JsonElement json) {
        return Collections.emptyList();
      }
    };
  }

  @NotNull @Override protected Request buildRequest() {
    final JsonObject body = new JSONObjectBuilder()
        .add("concepts", new JSONArrayBuilder()
            .addAll(concepts, new Func1<Concept, JsonElement>() {
              @NotNull @Override public JsonElement call(@NotNull Concept concept) {
                return new JSONObjectBuilder()
                    .add("id", concept.id())
                    .add("name", concept.name())
                    .build();
              }
            })
        ).build();
    return new Request.Builder()
        .url(buildURL("v2/concepts"))
        .post(toRequestBody(body))
        .build();
  }
}
