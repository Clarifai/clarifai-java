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
@JsonAdapter(FocusOutputInfo.Adapter.class)
public abstract class FocusOutputInfo extends OutputInfo {

  FocusOutputInfo() {} // AutoValue instances only

  @NotNull public abstract String type();

  @NotNull public abstract String typeExt();


  static class Adapter extends JSONAdapterFactory<FocusOutputInfo> {
    @Nullable @Override protected JSONAdapterFactory.Deserializer<FocusOutputInfo> deserializer() {
      return new Deserializer<FocusOutputInfo>() {
        @Nullable @Override
        public FocusOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<FocusOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_FocusOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<FocusOutputInfo> typeToken() {
      return new TypeToken<FocusOutputInfo>() {};
    }
  }
}
