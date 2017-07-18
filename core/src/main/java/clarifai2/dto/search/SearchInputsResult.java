package clarifai2.dto.search;

import clarifai2.dto.input.SearchHit;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(SearchInputsResult.Adapter.class)
public abstract class SearchInputsResult {

  SearchInputsResult() {} // AutoValue instances only

  @NotNull public abstract String id();

  @NotNull public abstract List<SearchHit> searchHits();


  static class Adapter extends JSONAdapterFactory<SearchInputsResult> {
    @Nullable @Override protected Deserializer<SearchInputsResult> deserializer() {
      return new Deserializer<SearchInputsResult>() {
        @Nullable @Override
        public SearchInputsResult deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<SearchInputsResult> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          return new AutoValue_SearchInputsResult(
              root.get("id").getAsString(),
              fromJson(gson, root.get("hits"), new TypeToken<List<SearchHit>>() {})
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<SearchInputsResult> typeToken() {
      return new TypeToken<SearchInputsResult>() {};
    }
  }
}

