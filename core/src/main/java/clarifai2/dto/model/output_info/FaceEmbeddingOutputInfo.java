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
@JsonAdapter(FaceEmbeddingOutputInfo.Adapter.class)
public abstract class FaceEmbeddingOutputInfo extends OutputInfo {

  FaceEmbeddingOutputInfo() {} // AutoValue instances only

  @NotNull public abstract String type();

  @NotNull public abstract String typeExt();


  static class Adapter extends JSONAdapterFactory<FaceEmbeddingOutputInfo> {
    @Nullable @Override protected JSONAdapterFactory.Deserializer<FaceEmbeddingOutputInfo> deserializer() {
      return new Deserializer<FaceEmbeddingOutputInfo>() {
        @Nullable @Override
        public FaceEmbeddingOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<FaceEmbeddingOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_FaceEmbeddingOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<FaceEmbeddingOutputInfo> typeToken() {
      return new TypeToken<FaceEmbeddingOutputInfo>() {};
    }
  }
}
