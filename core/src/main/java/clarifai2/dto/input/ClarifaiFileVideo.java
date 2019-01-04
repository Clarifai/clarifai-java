package clarifai2.dto.input;

import clarifai2.internal.grpc.api.VideoOuterClass;
import clarifai2.exception.ClarifaiException;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class ClarifaiFileVideo extends ClarifaiVideo {

  ClarifaiFileVideo() {} // AutoValue instances only

  @NotNull public final byte[] videoBytes() {
    // Return a defensive copy so that the underlying data can't be modified
    final byte[] bytes = bytes();
    return Arrays.copyOf(bytes, bytes.length);
  }

  @SuppressWarnings("mutable") @NotNull abstract byte[] bytes();

  @NotNull @Override public VideoOuterClass.Video serialize(boolean allowDuplicateUrl) {
    VideoOuterClass.Video video = VideoOuterClass.Video.newBuilder()
        .setBase64(com.google.protobuf.ByteString.copyFrom(bytes()))
        .build();
    return video;
  }

  @NotNull public static ClarifaiFileVideo deserializeInner(VideoOuterClass.Video video) {
    throw new ClarifaiException(
        "Deserialization of file videos is not supported by the backend, so this should never occur"
    );
  }
}

