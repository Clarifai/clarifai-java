package clarifai2.integration_tests;

import clarifai2.api.request.model.Action;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.ModelTrainingStatus;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.prediction.Concept;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static clarifai2.internal.InternalUtil.assertNotNull;

public class ModelEvaluationIntTests extends BaseIntTest {

  @Test public void shouldHaveModelVersionFieldsPopulatedAfterRunningEvaluation() throws InterruptedException {
    final String modelId = UUID.randomUUID().toString();
    List<ClarifaiInput> inputs = null;
    try {
      // Create inputs.
      inputs = client.addInputs()
          .plus(
              ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL)
                  .withConcepts(Concept.forID("metro")),
              ClarifaiInput.forImage(FAMILY_IMAGE_URL)
                  .withConcepts(Concept.forID("family"))
          )
          .allowDuplicateURLs(true)
          .executeSync()
          .get();

      // Create and train a model.
      ConceptModel model = client.createModel(modelId).executeSync().get();
      model = model.modify()
          .withConcepts(Action.MERGE, Concept.forID("metro"), Concept.forID("family"))
          .executeSync().get();
      model = model.train().executeSync().get().asConceptModel();

      // Wait for the training to complete.
      ModelVersion trainModelVersion;
      do {
        trainModelVersion = client.getModelVersionByID(modelId, model.modelVersion().id())
            .executeSync().get();
        Thread.sleep(1000);
      } while(trainModelVersion.status() == ModelTrainingStatus.TRAINING_QUEUED
          || trainModelVersion.status() == ModelTrainingStatus.TRAINING_IN_PROGRESS);

      // Run model evaluation and assert the response.
      final ModelVersion evalModelVersion = client.runModelEvaluation(model.id(), model.modelVersion().id())
          .executeSync().get();
      assertNotNull(evalModelVersion);
      assertNotNull(evalModelVersion.status());
      assertNotNull(evalModelVersion.modelMetricsStatus());

    } finally {
      // Clean up.
      client.deleteModel(modelId).executeSync();
      if (inputs != null) {
        for (ClarifaiInput input : inputs) {
          client.deleteInput(input.id()).executeSync();
        }
      }
    }
  }
}
