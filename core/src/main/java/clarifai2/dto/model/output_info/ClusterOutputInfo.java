package clarifai2.dto.model.output_info;

import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.exception.ClarifaiException;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class ClusterOutputInfo extends OutputInfo {

  ClusterOutputInfo() {} // AutoValue instances only

  @Override @NotNull public ModelOuterClass.OutputInfo serialize() {
    throw new ClarifaiException(this.getClass().getSimpleName() + " is not serializable");
  }

  @NotNull public static ClusterOutputInfo deserializeInner(ModelOuterClass.OutputInfo outputInfo) {
    return new AutoValue_ClusterOutputInfo();
  }
}