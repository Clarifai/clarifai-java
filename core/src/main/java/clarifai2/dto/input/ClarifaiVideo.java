package clarifai2.dto.input;

import clarifai2.internal.grpc.api.VideoOuterClass;
import clarifai2.exception.ClarifaiException;
import clarifai2.internal.InternalUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("NullableProblems")
public abstract class ClarifaiVideo implements ClarifaiInputValue {

  @NotNull public static ClarifaiURLVideo of(@NotNull URL videoURL) {
    return new AutoValue_ClarifaiURLVideo(videoURL);
  }

  @NotNull public static ClarifaiURLVideo of(@NotNull String videoURL) {
    final URL result;
    try {
      result = new URL(videoURL);
    } catch (MalformedURLException e) {
      throw new ClarifaiException("Could not parse URL " + videoURL, e);
    }
    return of(result);
  }

  @NotNull public static ClarifaiFileVideo of(@NotNull byte[] videoBytes) {
    return new AutoValue_ClarifaiFileVideo(videoBytes);
  }

  @NotNull public static ClarifaiFileVideo of(@NotNull File videoFile) {
    return of(InternalUtil.read(videoFile));
  }

  public static ClarifaiVideo deserialize(VideoOuterClass.Video video) {
    return !video.getUrl().equals("") ?
        ClarifaiURLVideo.deserializeInner(video) :
        ClarifaiFileVideo.deserializeInner(video);
  }

  @NotNull public abstract VideoOuterClass.Video serialize(boolean allowDuplicateURLs);
}
