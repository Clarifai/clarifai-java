package clarifai2.dto.model.output_info;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.JsonAdapter;

@SuppressWarnings("NullableProblems")
@AutoValue
@JsonAdapter(FocusOutputInfo.Adapter.class)
public abstract class FocusOutputInfo extends OutputInfo {

  FocusOutputInfo() {} // AutoValue instances only
}
