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
public abstract class Region extends Prediction {

  Region() {} // AutoValue instances only

  @NotNull public abstract String id();

  @NotNull public abstract Crop crop();

  @NotNull public abstract List<Concept> ageAppearances();

  @NotNull public abstract List<Concept> genderAppearances();

  @NotNull public abstract List<Concept> multiculturalAppearances();


  // Unfortunately, the demographics model returns a list of Region instances instead of Demographics instances.
  // In order to remain backwards compatible we cannot change that return type. So we are forced to have this
  // demographics deserialization here instead of in Demographics.
  @NotNull public static Region deserialize(DataOuterClass.Region region) {
    FaceOuterClass.Face face = region.getData().getFace();
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
    return new AutoValue_Region(
        region.getId(),
        Crop.deserialize(region.getRegionInfo().getBoundingBox()),
        ageAppearanceConcepts,
        genderAppearanceConcepts,
        multiculturalAppearanceConcepts
    );
  }
}
