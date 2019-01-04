package clarifai2.dto.input;

import clarifai2.internal.grpc.api.InputOuterClass;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class ClarifaiInputsStatus {

  ClarifaiInputsStatus() {} // AutoValue instances only

  @NotNull public abstract int processed();

  @NotNull public abstract int toProcess();

  @NotNull public abstract int errors();

  @NotNull public abstract int processing();

  @AutoValue.Builder
  public interface Builder {
    @NotNull Builder processed(int processed);
    @NotNull Builder toProcess(int toProcess);
    @NotNull Builder errors(int errors);
    @NotNull Builder processing(int processing);
    @NotNull ClarifaiInputsStatus build();
  }

  public static ClarifaiInputsStatus deserialize(InputOuterClass.InputCount inputCount) {
    return new AutoValue_ClarifaiInputsStatus.Builder()
        .processed(inputCount.getProcessed())
        .toProcess(inputCount.getToProcess())
        .errors(inputCount.getErrors())
        .processing(inputCount.getProcessing())
        .build();
  }
}
