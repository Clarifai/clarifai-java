package clarifai2.dto;

import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONAdapterFactory;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import static clarifai2.internal.InternalUtil.isJsonNull;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(ClarifaiStatus.Adapter.class)
public abstract class ClarifaiStatus {

//  @Nullable private static ClarifaiStatus success;
//  @Nullable private static ClarifaiStatus mixedSuccess;
//  @Nullable private static ClarifaiStatus unknown;

  ClarifaiStatus() {} // AutoValue instances only

  @NotNull public static ClarifaiStatus success() {
    return new AutoValue_ClarifaiStatus(
        false,
        10000,
        "Ok",
        null
    );
  }

  @NotNull public static ClarifaiStatus mixedSuccess() {
    return new AutoValue_ClarifaiStatus(
        false,
        10010,
        "Mixed Success",
        null
    );
  }

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


  static class Adapter extends JSONAdapterFactory<ClarifaiStatus> {
    @Nullable @Override protected Deserializer<ClarifaiStatus> deserializer() {
      return new Deserializer<ClarifaiStatus>() {
        @Nullable @Override
        public ClarifaiStatus deserialize(
            @NotNull JsonElement json,
            @NotNull TypeToken<ClarifaiStatus> type,
            @NotNull Gson gson) {
          final JsonObject root = InternalUtil.assertJsonIs(json, JsonObject.class);
          return new AutoValue_ClarifaiStatus(
              false,
              root.get("code").getAsInt(),
              root.get("description").getAsString(),
              isJsonNull(root.get("details")) ? null : root.get("details").getAsString()
          );
        }
      };
    }

    @NotNull @Override protected TypeToken<ClarifaiStatus> typeToken() {
      return new TypeToken<ClarifaiStatus>() {};
    }
  }
}
