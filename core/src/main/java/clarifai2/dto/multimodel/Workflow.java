package clarifai2.dto.multimodel;

import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Workflow.Adapter.class)
public abstract class Workflow {

  Workflow() {} // AutoValue instances only

  @Nullable public abstract String id();
  @Nullable public abstract String appId();
  @Nullable public abstract Date createdAt();


  static class Adapter extends JSONAdapterFactory<Workflow> {
    @Nullable @Override protected Deserializer<Workflow> deserializer() {
      return new Deserializer<Workflow>() {
        @Nullable @Override
        public Workflow deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Workflow> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          return new AutoValue_Workflow(
              root.get("id").getAsString(),
              root.get("app_id").getAsString(),
              fromJson(gson, root.get("created_at"), Date.class)
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<Workflow> typeToken() {
      return new TypeToken<Workflow>() {};
    }
  }
}
