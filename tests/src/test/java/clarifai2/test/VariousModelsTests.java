package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VariousModelsTests extends BaseClarifaiAPITest {

  @Test public void shouldBeSuccessfulForModerationModel() {
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = client.getDefaultModels().moderationModel().predict()
        .withInputs(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL)).executeSync();
    assertTrue(response.isSuccessful());
    List<Concept> concepts = response.get().get(0).data();
    assertNotNull(concepts);
    assertTrue(concepts.size() > 0);
  }
}

