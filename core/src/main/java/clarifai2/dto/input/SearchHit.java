package clarifai2.dto.input;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(SearchHit.Adapter.class)
public abstract class SearchHit {

  @NotNull public abstract float score();
  @NotNull public abstract ClarifaiInput input();

  SearchHit() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<SearchHit> {
    @Override public SearchHit deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      return new AutoValue_SearchHit(
          root.get("score").getAsFloat(),
          fromJson(context, root.get("input"), ClarifaiInput.class)
      );
    }
  }
}