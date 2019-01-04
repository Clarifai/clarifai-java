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
public abstract class Demographics extends Prediction {

  Demographics() {} // AutoValue instances only

  @NotNull public abstract Crop boundingBox();
  @NotNull public abstract List<Concept> ageAppearanceConcepts();
  @NotNull public abstract List<Concept> genderAppearanceConcepts();
  @NotNull public abstract List<Concept> multiculturalAppearanceConcepts();


  @NotNull public static Demographics deserialize(DataOuterClass.Region demographics) {
    FaceOuterClass.Face face = demographics.getData().getFace();
    List<Concept> ageAppearanceConcepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : face.getAgeAppearance().getConceptsList()) {
      ageAppearanceConcepts.add(Concept.deserialize(concept));
    }
    List<Concept> genderAppearanceConcepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : face.getGenderAppearance().getConceptsList()) {
      genderAppearanceConcepts.add(Concept.deserialize(concept));
    }
    List<Concept> multiculturalAppearanceConcepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : face.getMulticulturalAppearance().getConceptsList()) {
      multiculturalAppearanceConcepts.add(Concept.deserialize(concept));
    }
    return new AutoValue_Demographics(
        Crop.deserialize(demographics.getRegionInfo().getBoundingBox()),
        ageAppearanceConcepts,
        genderAppearanceConcepts,
        multiculturalAppearanceConcepts
    );
  }
}

