package clarifai2.dto.model.output_info;

import clarifai2.Func1;
import clarifai2.dto.input.image.Crop;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Region;
import clarifai2.internal.JSONAdapterFactory;
import clarifai2.internal.JSONArrayBuilder;
import clarifai2.internal.JSONObjectBuilder;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static clarifai2.internal.InternalUtil.fromJson;
import static clarifai2.internal.InternalUtil.toJson;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceDetectionOutputInfo extends OutputInfo {

  FaceDetectionOutputInfo() {} // AutoValue instances only
}