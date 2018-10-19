package clarifai2.solutions.moderation.dto;

import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.isJsonNull;

@AutoValue
@JsonAdapter(ModerationStatus.Adapter.class)
public abstract class ModerationStatus {

  public abstract int statusCode();

  @NotNull public abstract String description();

  @Nullable public abstract String inputID();

  @NotNull public abstract List<ModerationDetail> details();


  static class Adapter extends JSONAdapterFactory<ModerationStatus> {
    @Nullable @Override protected Deserializer<ModerationStatus> deserializer() {
      return new Deserializer<ModerationStatus>() {
        @Nullable @Override
        public ModerationStatus deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ModerationStatus> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);

          List<ModerationDetail> moderationDetails = new ArrayList<>();

          JsonArray details = root.getAsJsonArray("details");
          if (details != null) {
            for (JsonElement detailElement: details) {
              moderationDetails.add(fromJson(gson, detailElement, ModerationDetail.class));
            }
          }

          return new AutoValue_ModerationStatus(
              root.get("code").getAsInt(),
              root.get("description").getAsString(),
              isJsonNull(root.get("input_id")) ? null : root.get("input_id").getAsString(),
              moderationDetails
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ModerationStatus> typeToken() {
      return new TypeToken<ModerationStatus>() {};
    }
  }
}
