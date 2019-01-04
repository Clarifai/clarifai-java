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
public abstract class ConceptOutputInfo extends OutputInfo {

  ConceptOutputInfo() {} // AutoValue instances only

  @NotNull public static ConceptOutputInfo forConcepts(@NotNull Concept... concepts) {
    return forConcepts(Arrays.asList(concepts));
  }

  @NotNull public static ConceptOutputInfo forConcepts(@NotNull List<Concept> concepts) {
    return new AutoValue_ConceptOutputInfo(concepts, false, false, null);
  }

  @Nullable public abstract List<Concept> concepts();

  @NotNull public abstract boolean areConceptsMutuallyExclusive();

  @NotNull public abstract boolean isEnvironmentClosed();

  @Nullable public abstract String language();

  @NotNull public final ConceptOutputInfo withLanguage(@NotNull String language) {
    return new AutoValue_ConceptOutputInfo(concepts(), areConceptsMutuallyExclusive(), isEnvironmentClosed(), language);
  }

  @NotNull public final ConceptOutputInfo areConceptsMutuallyExclusive(boolean areConceptsMutuallyExclusive) {
    return withAreConceptsMutuallyExclusive(areConceptsMutuallyExclusive);
  }

  @NotNull public final ConceptOutputInfo isEnvironmentClosed(boolean isEnvironmentClosed) {
    return withIsEnvironmentClosed(isEnvironmentClosed);
  }

  // These are awful method names, but auto-value-with needs these particular names, so we'll expose better ones publicly
  @NotNull abstract ConceptOutputInfo withAreConceptsMutuallyExclusive(boolean areConceptsMutuallyExclusive);

  @NotNull abstract ConceptOutputInfo withIsEnvironmentClosed(boolean isEnvironmentClosed);

  @Override @NotNull public ModelOuterClass.OutputInfo serialize() {
    List<ConceptOuterClass.Concept> conceptsGrpc = new ArrayList<>();
    if (concepts() != null) {
      for (Concept concept : concepts()) {
        conceptsGrpc.add(concept.serialize());
      }
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

  @NotNull public static ConceptOutputInfo deserializeInner(ModelOuterClass.OutputInfo outputInfo) {
    List<Concept> concepts = new ArrayList<>();
    for (ConceptOuterClass.Concept concept : outputInfo.getData().getConceptsList()) {
      concepts.add(Concept.deserialize(concept));
    }

    boolean areConceptsMutuallyExclusive = outputInfo.getOutputConfig().getConceptsMutuallyExclusive();
    boolean isEnvironmentClosed = outputInfo.getOutputConfig().getClosedEnvironment();
    String language = outputInfo.getOutputConfig().getLanguage();

    return new AutoValue_ConceptOutputInfo(concepts, areConceptsMutuallyExclusive, isEnvironmentClosed, language);
  }
}
