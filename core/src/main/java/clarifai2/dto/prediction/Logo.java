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
public abstract class Logo extends Prediction {

  Logo() {} // AutoValue instances only

  @NotNull public abstract Crop boundingBox();

  @NotNull public abstract List<Concept> concepts();


  @NotNull public static Logo deserialize(DataOuterClass.Region logo) {
    Crop crop = Crop.deserialize(logo.getRegionInfo().getBoundingBox());

    List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : logo.getData().getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }

    return new AutoValue_Logo(crop, concepts);
  }
}
