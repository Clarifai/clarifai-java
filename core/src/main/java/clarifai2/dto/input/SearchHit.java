package clarifai2.dto.input;

import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.assertJsonIs;

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