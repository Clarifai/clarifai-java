package clarifai2.dto.prediction;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Frame extends Prediction {

  Frame() {} // AutoValue instances only

  public abstract int index();

  public abstract long time();

  @NotNull public abstract List<Concept> concepts();


  @NotNull public static Frame deserialize(DataOuterClass.Frame frame) {
    List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : frame.getData().getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }
    return new AutoValue_Frame(
        frame.getFrameInfo().getIndex(),
        frame.getFrameInfo().getTime(),
        concepts
    );
  }
}
