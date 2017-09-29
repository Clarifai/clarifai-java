package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.prediction.Concept;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConceptTests extends BaseClarifaiAPITest {

  private final double doubleComparisonDelta = 1e-7;

  @Test public void shouldUpdateConceptNameWhenModifyConceptRequest() {
    final String conceptId = "conceptId1";

    final String originalConceptName = "originalConceptName";
    final float originalConceptValue = 0.0f;

    try {
      final String newConceptName = "newConceptName";

      // Create a concept. It's fine if it already exists.
      client.addConcepts()
          .plus(Concept.forID(conceptId)
              .withName(originalConceptName)
          ).executeSync();

      // Update the concept and assert fields were updated.
      ClarifaiResponse<List<Concept>> setConceptsResponse = client.modifyConcepts()
          .plus(Concept.forID(conceptId)
              .withName(newConceptName)
          ).executeSync();
      assertEquals(newConceptName, setConceptsResponse.get().get(0).name());

      // Get the updated concept again to make sure fields were really updated.
      ClarifaiResponse<Concept> getResponse =
          client.getConceptByID(conceptId).executeSync();
      assertEquals(newConceptName, getResponse.get().name());
    } finally {
      // Revert the concept's name & value back to the original values.
      client.modifyConcepts()
          .plus(Concept.forID(conceptId)
              .withName(originalConceptName)
              .withValue(originalConceptValue)
          ).executeSync();
    }
  }
}
