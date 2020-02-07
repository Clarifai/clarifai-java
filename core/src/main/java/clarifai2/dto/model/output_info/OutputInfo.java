package clarifai2.dto.model.output_info;

import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.dto.model.ModelType;
import clarifai2.internal.JSONAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static clarifai2.internal.InternalUtil.assertJsonIs;
import static clarifai2.internal.InternalUtil.fromJson;

@JsonAdapter(OutputInfo.Adapter.class)
public abstract class OutputInfo {

  OutputInfo() {}

  @NotNull public abstract ModelOuterClass.OutputInfo serialize();

  @Nullable public static OutputInfo deserialize(ModelOuterClass.OutputInfo outputInfo) {
    Class<? extends OutputInfo> aClass = ModelType.determineModelType(outputInfo).infoType();

    Method m;
    try {
      m = aClass.getMethod("deserializeInner", ModelOuterClass.OutputInfo.class);
    } catch (NoSuchMethodException e) {
      return null;
    }
    Object result = null;
    try {
      result = m.invoke(null, outputInfo);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return (OutputInfo) result;
  }

  static class Adapter extends JSONAdapterFactory<OutputInfo> {

    @Nullable @Override protected Serializer<OutputInfo> serializer() {
      return new Serializer<OutputInfo>() {
        @NotNull @Override public JsonElement serialize(@Nullable OutputInfo value, @NotNull final Gson gson) {

          JsonElement jsonOutputInfo;
          if (value instanceof ConceptOutputInfo) {
            jsonOutputInfo = gson.toJsonTree(value, ConceptOutputInfo.class);
          } else {
            throw new RuntimeException("Unsupported serialization for this OutputInfo object.");
          }

          return jsonOutputInfo;
        }
      };
    }

    @Nullable @Override protected Deserializer<OutputInfo> deserializer() {
      return new Deserializer<OutputInfo>() {
        @Nullable @Override
        public OutputInfo deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<OutputInfo> type,
            @NotNull Gson gson
        ) {
          final JsonObject root = assertJsonIs(json, JsonObject.class);
          if (root.has("message")) {
            return null;
          }
          if (root.has("type") && root.size() == 1) {
            return null;
          }
          return fromJson(gson, json, ModelType.determineModelType(root).infoType());
        }
      };
    }

    @NotNull @Override protected TypeToken<OutputInfo> typeToken() {
      return new TypeToken<OutputInfo>() {};
    }
  }
}
