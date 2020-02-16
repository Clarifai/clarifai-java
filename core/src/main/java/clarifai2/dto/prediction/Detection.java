package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.dto.input.Crop;
import clarifai2.internal.grpc.api.FaceOuterClass;
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

  @NotNull public abstract List<Concept> ageAppearances();

  @NotNull public abstract List<Concept> genderAppearances();

  @NotNull public abstract List<Concept> multiculturalAppearances();


  @NotNull public static Detection deserialize(DataOuterClass.Region detection) {
    Crop crop = Crop.deserialize(detection.getRegionInfo().getBoundingBox());

    List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : detection.getData().getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }

    List<Concept> ageAppearanceConcepts = new ArrayList<>();
    List<Concept> genderAppearanceConcepts = new ArrayList<>();
    List<Concept> multiculturalAppearanceConcepts = new ArrayList<>();

    FaceOuterClass.Face face = detection.getData().getFace();
    if (face != null) {
      for (ConceptOuterClass.Concept concept : face.getAgeAppearance().getConceptsList()) {
        ageAppearanceConcepts.add(Concept.deserialize(concept));
      }
      for (ConceptOuterClass.Concept concept : face.getGenderAppearance().getConceptsList()) {
        genderAppearanceConcepts.add(Concept.deserialize(concept));
      }
      for (ConceptOuterClass.Concept concept : face.getMulticulturalAppearance().getConceptsList()) {
        multiculturalAppearanceConcepts.add(Concept.deserialize(concept));
      }
    }

    return new AutoValue_Detection(
        crop, concepts, ageAppearanceConcepts, genderAppearanceConcepts, multiculturalAppearanceConcepts
    );
  }
}
