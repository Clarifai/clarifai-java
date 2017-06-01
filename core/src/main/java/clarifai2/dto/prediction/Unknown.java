package clarifai2.dto.prediction;

import com.google.auto.value.AutoValue;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class Unknown extends Prediction {

  Unknown() {}

  ; // make sure this is actually right (won't let anyone access this)
}
