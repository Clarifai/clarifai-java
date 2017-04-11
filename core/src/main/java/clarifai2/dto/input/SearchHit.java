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

  static class Adapter extends JSONAdapterFactory<SearchHit> {
    @Nullable @Override protected Deserializer<SearchHit> deserializer() {
      return new Deserializer<SearchHit>() {
        @Nullable @Override
        public SearchHit deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<SearchHit> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          return new AutoValue_SearchHit(
              root.get("score").getAsFloat(),
              gson.fromJson(root.get("input"), ClarifaiInput.class)
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<SearchHit> typeToken() {
      return new TypeToken<SearchHit>() {};
    }
  }
}