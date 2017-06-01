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
@JsonAdapter(VideoOutputInfo.Adapter.class)
public abstract class VideoOutputInfo extends OutputInfo {

  VideoOutputInfo() {} // AutoValue instances only

  @NotNull public abstract String type();

  @NotNull public abstract String typeExt();


  static class Adapter extends JSONAdapterFactory<VideoOutputInfo> {
    @Nullable @Override protected Deserializer<VideoOutputInfo> deserializer() {
      return new Deserializer<VideoOutputInfo>() {
        @Nullable @Override
        public VideoOutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<VideoOutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          String modelType = root.getAsJsonPrimitive("type").getAsString();
          String typeExt = root.getAsJsonPrimitive("type_ext").getAsString();
          return new AutoValue_VideoOutputInfo(modelType, typeExt);
        }
      };
    }

    @NotNull @Override protected TypeToken<VideoOutputInfo> typeToken() {
      return new TypeToken<VideoOutputInfo>() {};
    }
  }
}