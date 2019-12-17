package clarifai2.dto.input;

import clarifai2.internal.grpc.api.VideoOuterClass;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class ClarifaiURLVideo extends ClarifaiVideo {

  @NotNull public abstract URL url();

  @NotNull @Override public VideoOuterClass.Video serialize(boolean allowDuplicateUrl) {
    VideoOuterClass.Video video = VideoOuterClass.Video.newBuilder()
        .setUrl(url().toString())
        .setAllowDuplicateUrl(allowDuplicateUrl)
        .build();
    return video;
  }

  @NotNull public static ClarifaiURLVideo deserializeInner(VideoOuterClass.Video videoResponse) {
    ClarifaiURLVideo video = ClarifaiURLVideo.of(videoResponse.getUrl());
    return video;
  }
}
