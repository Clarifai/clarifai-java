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
@JsonAdapter(LogoOutputInfo.Adapter.class)
public abstract class LogoOutputInfo extends OutputInfo {

  LogoOutputInfo() {} // AutoValue instances only

  @NotNull public abstract String type();

  @NotNull public abstract String typeExt();


  static class Adapter extends JSONAdapterFactory<LogoOutputInfo> {
    @Nullable @Override protected JSONAdapterFactory.Deserializer<LogoOutputInfo> deserializer() {
      return new Deserializer<LogoOutputInfo>() {
        @Nullable @Override
        public LogoOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<LogoOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_LogoOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<LogoOutputInfo> typeToken() {
      return new TypeToken<LogoOutputInfo>() {};
    }
  }
}
