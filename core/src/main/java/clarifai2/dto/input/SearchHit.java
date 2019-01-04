package clarifai2.dto.input;

import clarifai2.internal.grpc.api.Search;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class SearchHit {

  SearchHit() {} // AutoValue instances only

  @NotNull public abstract float score();

  @NotNull public abstract ClarifaiInput input();


  public static SearchHit deserialize(Search.Hit hit) {
    return new AutoValue_SearchHit(
        hit.getScore(),
        ClarifaiInput.deserialize(hit.getInput())
    );
  }
}
