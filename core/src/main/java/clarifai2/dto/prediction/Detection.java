package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.dto.input.Crop;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Detection extends Prediction {

  Detection() {} // AutoValue instances only

  @NotNull public abstract Crop crop();

  @NotNull public abstract List<Concept> concepts();


  @NotNull public static Detection deserialize(DataOuterClass.Region detection) {
    Crop crop = Crop.deserialize(detection.getRegionInfo().getBoundingBox());

    List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : detection.getData().getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }

    return new AutoValue_Detection(crop, concepts);
  }
}
