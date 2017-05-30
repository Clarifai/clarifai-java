package clarifai2.dto.prediction;

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

import java.util.ArrayList;
import java.util.List;

import static clarifai2.internal.InternalUtil.fromJson;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(Frame.Adapter.class)
public abstract class Frame extends Prediction {

  public abstract int index();
  public abstract long time();
  @NotNull public abstract List<Concept> concepts();

  Frame() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<Frame> {
    @Nullable @Override protected JSONAdapterFactory.Deserializer<Frame> deserializer() {
      return new Deserializer<Frame>() {
        @Nullable @Override
        public Frame deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<Frame> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);

          List<Concept> concepts = new ArrayList<>();
          for (JsonElement element : root.getAsJsonObject("data").getAsJsonArray("concepts")) {
            concepts.add(fromJson(gson, element, Concept.class));
          }
          return new AutoValue_Frame(
              root.getAsJsonObject("frame_info").getAsJsonPrimitive("index").getAsInt(),
              root.getAsJsonObject("frame_info").getAsJsonPrimitive("time").getAsLong(),
              concepts);
        }
      };
    }

    @NotNull @Override protected TypeToken<Frame> typeToken () {
      return new TypeToken<Frame>() {};
    }
  }
}
