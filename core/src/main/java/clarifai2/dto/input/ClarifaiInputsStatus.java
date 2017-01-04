package clarifai2.dto.input;

import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiInputsStatus.Adapter.class)
public abstract class ClarifaiInputsStatus {

  @NotNull public abstract int processed();
  @NotNull public abstract int toProcess();
  @NotNull public abstract int errors();

  @AutoValue.Builder
  public interface Builder {
    @NotNull Builder processed(int processed);
    @NotNull Builder toProcess(int toProcess);
    @NotNull Builder errors(int errors);
    @NotNull ClarifaiInputsStatus build();
  }

  ClarifaiInputsStatus() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<ClarifaiInputsStatus> {
    @Nullable @Override protected Serializer<ClarifaiInputsStatus> serializer() {
      return new Serializer<ClarifaiInputsStatus>() {
        @NotNull @Override
        public JsonElement serialize(@Nullable ClarifaiInputsStatus value, @NotNull Gson gson) {
          if (value == null) {
            return JsonNull.INSTANCE;
          }
          return new JSONObjectBuilder()
              .add("processed", value.processed())
              .add("to_process", value.toProcess())
              .add("errors", value.errors())
              .build();
        }
      };
    }

    @Nullable @Override protected Deserializer<ClarifaiInputsStatus> deserializer() {
      return new Deserializer<ClarifaiInputsStatus>() {
        @Nullable @Override
        public ClarifaiInputsStatus deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiInputsStatus> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          return new AutoValue_ClarifaiInputsStatus.Builder()
              .processed(root.get("processed").getAsInt())
              .toProcess(root.get("to_process").getAsInt())
              .errors(root.get("errors").getAsInt())
              .build();
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiInputsStatus> typeToken() {
      return new TypeToken<ClarifaiInputsStatus>() {};
    }
  }
}