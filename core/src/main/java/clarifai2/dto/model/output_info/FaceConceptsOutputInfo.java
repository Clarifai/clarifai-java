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
@JsonAdapter(FaceConceptsOutputInfo.Adapter.class)
public abstract class FaceConceptsOutputInfo extends OutputInfo {

  FaceConceptsOutputInfo() {} // AutoValue instances only

  @NotNull public abstract String type();

  @NotNull public abstract String typeExt();


  static class Adapter extends JSONAdapterFactory<FaceConceptsOutputInfo> {
    @Nullable @Override protected Deserializer<FaceConceptsOutputInfo> deserializer() {
      return new Deserializer<FaceConceptsOutputInfo>() {
        @Nullable @Override
        public FaceConceptsOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<FaceConceptsOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_FaceConceptsOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<FaceConceptsOutputInfo> typeToken() {
      return new TypeToken<FaceConceptsOutputInfo>() {};
    }
  }
}
