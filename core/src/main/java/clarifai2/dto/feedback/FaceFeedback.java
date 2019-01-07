package clarifai2.dto.feedback;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.FaceOuterClass;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceFeedback {
  FaceFeedback() {} // AutoValue instances only

  @NotNull public static FaceFeedback make(@NotNull ConceptFeedback... identityConceptFeedbacks) {
    return make(Arrays.asList(identityConceptFeedbacks));
  }

  @NotNull public static FaceFeedback make(@NotNull Collection<ConceptFeedback> identityConceptFeedbacks) {
    return make(identityConceptFeedbacks, Collections.<ConceptFeedback>emptyList());
  }

  @NotNull public static FaceFeedback make(@NotNull Collection<ConceptFeedback> identityConceptFeedbacks,
      @Nullable Collection<ConceptFeedback> ageConceptFeedbacks) {
    return new AutoValue_FaceFeedback(identityConceptFeedbacks, ageConceptFeedbacks);
  }

  @NotNull public abstract Collection<ConceptFeedback> identityConceptFeedback();
  @NotNull public abstract Collection<ConceptFeedback> ageConceptFeedback();

  public FaceOuterClass.Face serialize() {
    FaceOuterClass.Face.Builder builder = FaceOuterClass.Face.newBuilder();

    if (!identityConceptFeedback().isEmpty()) {
      List<ConceptOuterClass.Concept> faceIdentityConceptsGrpc = new ArrayList<>();
      for (ConceptFeedback conceptFeedback : identityConceptFeedback()) {
        faceIdentityConceptsGrpc.add(conceptFeedback.serialize());
      }
      builder.setIdentity(FaceOuterClass.FaceIdentity.newBuilder().addAllConcepts(faceIdentityConceptsGrpc));
    }

    if (!ageConceptFeedback().isEmpty()) {
      List<ConceptOuterClass.Concept> ageAppearanceConcepts = new ArrayList<>();
      for (ConceptFeedback conceptFeedback : ageConceptFeedback()) {
        ageAppearanceConcepts.add(conceptFeedback.serialize());
      }
      builder.setAgeAppearance(FaceOuterClass.FaceAge.newBuilder().addAllConcepts(ageAppearanceConcepts));
    }

    return builder.build();
  }
}
