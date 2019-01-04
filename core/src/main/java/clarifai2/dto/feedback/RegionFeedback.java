package clarifai2.dto.feedback;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.Func1;
import clarifai2.api.request.feedback.Feedback;
import clarifai2.dto.input.Crop;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class RegionFeedback {
  private String id = null;
  private Collection<ConceptFeedback> conceptFeedbacks = new ArrayList<>();
  private FaceFeedback faceFeedback = null;

  RegionFeedback() {} // AutoValue instances only

  @NotNull public static RegionFeedback make() {
    return make(null, null);
  }

  @NotNull public static RegionFeedback make(@Nullable Crop crop, @Nullable Feedback feedback) {
    return new AutoValue_RegionFeedback(crop, feedback);
  }

  @Nullable public abstract Crop crop();
  @Nullable public abstract Feedback feedback();

  @NotNull public RegionFeedback withID(@NotNull String regionID) {
    this.id = regionID;
    return this;
  }

  @NotNull public RegionFeedback withConceptFeedbacks(@NotNull ConceptFeedback... inputData) {
    return withConceptFeedbacks(Arrays.asList(inputData));
  }

  @NotNull public RegionFeedback withConceptFeedbacks(@NotNull Collection<ConceptFeedback> conceptFeedbacks) {
    this.conceptFeedbacks.addAll(conceptFeedbacks);
    return this;
  }

  @NotNull public RegionFeedback withFaceFeedback(@NotNull FaceFeedback faceFeedback) {
    this.faceFeedback = faceFeedback;
    return this;
  }

  public DataOuterClass.Region serialize() {
    DataOuterClass.Region.Builder builder = DataOuterClass.Region.newBuilder();
    if (id != null) {
      builder.setId(id);
    }
    if (crop() != null) {
      builder.setRegionInfo(
          DataOuterClass.RegionInfo.newBuilder()
              .setBoundingBox(crop().serializeBoundingBox())
              .setFeedback(feedback().serialize())
      );
    }
    DataOuterClass.Data.Builder dataBuilder = DataOuterClass.Data.newBuilder();
    if (!conceptFeedbacks.isEmpty()) {
      List<ConceptOuterClass.Concept> conceptsGrpc = new ArrayList<>();
      for (ConceptFeedback conceptFeedback : conceptFeedbacks) {
        conceptsGrpc.add(conceptFeedback.serialize());
      }
      dataBuilder.addAllConcepts(conceptsGrpc);
    }
    if (faceFeedback != null) {
      dataBuilder.setFace(faceFeedback.serialize());
    }
    return builder.setData(dataBuilder).build();
  }
}
