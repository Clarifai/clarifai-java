package clarifai2.dto.feedback;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.dto.HasClarifaiID;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class ConceptFeedback implements HasClarifaiID {
  ConceptFeedback() {} // AutoValue instances only

  @NotNull public static ConceptFeedback forIdAndValue(@NotNull String id, boolean value) {
    return new AutoValue_ConceptFeedback(id, value);
  }

  /**
   * @return whether this concept is present
   */
  @NotNull public abstract boolean value();

  @NotNull public ConceptOuterClass.Concept serialize() {
    return ConceptOuterClass.Concept.newBuilder()
        .setId(id())
        .setValue(value() ? 1.0f : 0.0f)
        .build();
  }
}
