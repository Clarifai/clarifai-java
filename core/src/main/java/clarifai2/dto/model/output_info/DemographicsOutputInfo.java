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
@JsonAdapter(DemographicsOutputInfo.Adapter.class)
public abstract class DemographicsOutputInfo extends OutputInfo {

  DemographicsOutputInfo() {} // AutoValue instances only

  @NotNull public abstract String type();

  @NotNull public abstract String typeExt();


  static class Adapter extends JSONAdapterFactory<DemographicsOutputInfo> {
    @Nullable @Override protected Deserializer<DemographicsOutputInfo> deserializer() {
      return new Deserializer<DemographicsOutputInfo>() {
        @Nullable @Override
        public DemographicsOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<DemographicsOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_DemographicsOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<DemographicsOutputInfo> typeToken() {
      return new TypeToken<DemographicsOutputInfo>() {};
    }
  }
}
