package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.internal.grpc.api.FaceOuterClass;
import clarifai2.dto.input.Crop;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceConcepts extends Prediction {

  FaceConcepts() {} // AutoValue instances only

  @NotNull public abstract Crop boundingBox();

  @NotNull public abstract List<Concept> concepts();

  @NotNull public abstract String id();


  @NotNull public static FaceConcepts deserialize(DataOuterClass.Region regionResponse) {
    List<Concept> concepts = new ArrayList<>();
    FaceOuterClass.Face face = regionResponse.getData().getFace();
    for (ConceptOuterClass.Concept concept : face.getIdentity().getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }
    return new AutoValue_FaceConcepts(
        Crop.deserialize(regionResponse.getRegionInfo().getBoundingBox()),
        concepts,
        regionResponse.getId()
    );
  }
}
