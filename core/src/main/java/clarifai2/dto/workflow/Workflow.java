package clarifai2.dto.workflow;

import clarifai2.internal.grpc.api.WorkflowOuterClass;
import clarifai2.grpc.DateTimeConverter;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Workflow {

  Workflow() {} // AutoValue instances only

  @Nullable public abstract String id();
  @Nullable public abstract String appId();
  @Nullable public abstract Date createdAt();


  public static Workflow deserialize(WorkflowOuterClass.Workflow workflow) {
    return new AutoValue_Workflow(
        workflow.getId(),
        workflow.getAppId(),
        DateTimeConverter.timestampToDate(workflow.getCreatedAt())
    );
  }
}
