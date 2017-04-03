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
@JsonAdapter(DemographicOutputInfo.Adapter.class)
public abstract class DemographicOutputInfo extends OutputInfo {

  @NotNull public abstract String type();
  @NotNull public abstract String typeExt();

  DemographicOutputInfo() {} // AutoValue instances only

  static class Adapter extends JSONAdapterFactory<DemographicOutputInfo> {
    @Nullable @Override protected Deserializer<DemographicOutputInfo> deserializer() {
      return new Deserializer<DemographicOutputInfo>() {
        @Nullable @Override
        public DemographicOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<DemographicOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_DemographicOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<DemographicOutputInfo> typeToken() {
      return new TypeToken<DemographicOutputInfo>() {};
    }
  }
}
