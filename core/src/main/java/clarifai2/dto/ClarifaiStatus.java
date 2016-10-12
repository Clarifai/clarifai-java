package clarifai2.dto;

import clarifai2.internal.InternalUtil;
import com.google.auto.value.AutoValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiStatus.Adapter.class)
public abstract class ClarifaiStatus {

  @NotNull public static ClarifaiStatus networkError(@NotNull IOException networkException) {
    return new AutoValue_ClarifaiStatus(
        true,
        0,
        "Network error occurred",
        networkException.getMessage()
    );
  }

  @NotNull public static ClarifaiStatus unknown() {
    return new AutoValue_ClarifaiStatus(
        false,
        0,
        "Unknown response",
        null
    );
  }

  /**
   * @return whether this {@link ClarifaiStatus} represents a request that was unsuccessful due to a network error
   */
  @NotNull public abstract boolean networkErrorOccurred();

  /**
   * @return the Clarifai status code for this response. This is different from the HTTP status code!
   */
  @NotNull public abstract int statusCode();

  /**
   * @return a description of the request and response
   */
  @NotNull public abstract String description();

  /**
   * @return details about the error that occurred, or {@code null} if no error occurred
   */
  @Nullable public abstract String errorDetails();

  ClarifaiStatus() {} // AutoValue instances only

  static class Adapter implements JsonDeserializer<ClarifaiStatus> {
    @Override
    public ClarifaiStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      final JsonObject root = json.getAsJsonObject();
      return new AutoValue_ClarifaiStatus(
          false,
          root.get("code").getAsInt(),
          root.get("description").getAsString(),
          InternalUtil.<String>nullSafeTraverse(root, "details")
      );
    }
  }
}