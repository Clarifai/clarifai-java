package clarifai2.solutions.moderation.dto;

import clarifai2.dto.prediction.Concept;
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

@AutoValue
@JsonAdapter(ModerationDetail.Adapter.class)
public abstract class ModerationDetail {
  ModerationDetail() {} // AutoValue instances only

  @NotNull public abstract Concept concept();
  public abstract int code();
  @NotNull public abstract String description();
  public abstract float thresholdMin();
  public abstract float thresholdMax();

  static class Adapter extends JSONAdapterFactory<ModerationDetail> {
    @Nullable @Override protected Deserializer<ModerationDetail>  deserializer() {
      return new Deserializer<ModerationDetail>() {
        @Nullable @Override
        public ModerationDetail deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ModerationDetail> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          return new AutoValue_ModerationDetail(
            gson.fromJson(root.get("concept"), Concept.class),
              root.get("code").getAsInt(),
              root.get("description").getAsString(),
              root.get("threshold_min").getAsFloat(),
              root.get("threshold_max").getAsFloat()
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ModerationDetail> typeToken() {
      return new TypeToken<ModerationDetail>() {};
    }
  }
}
