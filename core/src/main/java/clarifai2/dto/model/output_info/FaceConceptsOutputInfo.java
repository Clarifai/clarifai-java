package clarifai2.dto.model.output_info;

import clarifai2.internal.grpc.api.ConceptOuterClass;
import clarifai2.internal.grpc.api.DataOuterClass;
import clarifai2.internal.grpc.api.ModelOuterClass;
import clarifai2.dto.prediction.Concept;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("NullableProblems")
@AutoValue
public abstract class FaceConceptsOutputInfo extends OutputInfo {

  FaceConceptsOutputInfo() {} // AutoValue instances only

  @NotNull public static FaceConceptsOutputInfo forConcepts(@NotNull Concept... concepts) {
    return forConcepts(Arrays.asList(concepts));
  }

  @NotNull public static FaceConceptsOutputInfo forConcepts(@NotNull List<Concept> concepts) {
    return new AutoValue_FaceConceptsOutputInfo(concepts, false, false, null, null, null);
  }

  @Nullable public abstract List<Concept> concepts();

  @NotNull public abstract boolean areConceptsMutuallyExclusive();

  @NotNull public abstract boolean isEnvironmentClosed();

  @Nullable public abstract String language();

  @Nullable public abstract String type();

  @Nullable public abstract String typeExt();

  @NotNull public final FaceConceptsOutputInfo withLanguage(@NotNull String language) {
    return new AutoValue_FaceConceptsOutputInfo(
        concepts(), areConceptsMutuallyExclusive(), isEnvironmentClosed(), language, null, null
    );
  }

  @NotNull public final FaceConceptsOutputInfo areConceptsMutuallyExclusive(boolean areConceptsMutuallyExclusive) {
    return withAreConceptsMutuallyExclusive(areConceptsMutuallyExclusive);
  }

  @NotNull public final FaceConceptsOutputInfo isEnvironmentClosed(boolean isEnvironmentClosed) {
    return withIsEnvironmentClosed(isEnvironmentClosed);
  }

  // These are awful method names, but auto-value-with needs these particular names, so we'll expose better ones publicly
  @NotNull abstract FaceConceptsOutputInfo withAreConceptsMutuallyExclusive(boolean areConceptsMutuallyExclusive);

  @NotNull abstract FaceConceptsOutputInfo withIsEnvironmentClosed(boolean isEnvironmentClosed);

  @Override @NotNull public ModelOuterClass.OutputInfo serialize() {
    List<ConceptOuterClass.Concept> conceptsGrpc = new ArrayList<>();
    for (Concept concept : concepts()) {
      conceptsGrpc.add(concept.serialize());
    }

    ModelOuterClass.OutputConfig.Builder outputConfigBuilder = ModelOuterClass.OutputConfig.newBuilder()
        .setConceptsMutuallyExclusive(areConceptsMutuallyExclusive())
        .setClosedEnvironment(isEnvironmentClosed());
    if (language() != null) {
      outputConfigBuilder.setLanguage(language());
    }
    return ModelOuterClass.OutputInfo.newBuilder()
        .setData(DataOuterClass.Data.newBuilder().addAllConcepts(conceptsGrpc))
        .setOutputConfig(outputConfigBuilder)
        .build();
  }

  @NotNull public static FaceConceptsOutputInfo deserializeInner(ModelOuterClass.OutputInfo outputInfo) {
    List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : outputInfo.getData().getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }
    ModelOuterClass.OutputConfig outputConfig = outputInfo.getOutputConfig();
    return new AutoValue_FaceConceptsOutputInfo(
        concepts,
        outputConfig.getConceptsMutuallyExclusive(),
        outputConfig.getClosedEnvironment(),
        outputConfig.getLanguage(),
        outputInfo.getType(),
        outputInfo.getTypeExt()
    );
  }
}
