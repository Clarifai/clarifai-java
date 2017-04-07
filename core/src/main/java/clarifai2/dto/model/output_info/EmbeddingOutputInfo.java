package clarifai2.dto.model.output_info;

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

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(EmbeddingOutputInfo.Adapter.class  )
public abstract class EmbeddingOutputInfo extends OutputInfo {

  @NotNull public abstract String type();
  @NotNull public abstract String typeExt();

  EmbeddingOutputInfo() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<EmbeddingOutputInfo> {
    @Nullable @Override protected JSONAdapterFactory.Deserializer<EmbeddingOutputInfo> deserializer() {
      return new Deserializer<EmbeddingOutputInfo>() {
        @Nullable @Override
        public EmbeddingOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<EmbeddingOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_EmbeddingOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<EmbeddingOutputInfo> typeToken() {
      return new TypeToken<EmbeddingOutputInfo>() {};
    }
  }
}