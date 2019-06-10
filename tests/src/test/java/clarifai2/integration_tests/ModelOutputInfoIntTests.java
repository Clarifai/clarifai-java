package clarifai2.integration_tests;

import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.exception.ClarifaiException;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ModelOutputInfoIntTests extends BaseIntTest {
  @Rule public final ExpectedException exception = ExpectedException.none();

  /**
   * Default models (not the ones received using GetModelRequest) shouldn't have outputInfo populated.
   */
  @Test public void defaultModelShouldNotHaveOutputInfo() {
    ConceptModel model = client.getDefaultModels().apparelModel();

    exception.expect(ClarifaiException.class);
    model.outputInfo();
  }

  /**
   * Models received using GetModelsRequest should have outputInfo populated.
   */
  @Test public void shouldNotHaveOutputInfoAfterGetModelsRequest() {
    Model<?> model = client.getModels().getPage(1).executeSync().get().get(0);

    assertNotNull(model.outputInfo());
  }

  /**
   * When using the getModelRequest helper, outputInfo should be populated.
   */
  @Test public void shouldHaveOutputInfoWhenUsingHelper() {
    ConceptModel model = client.getDefaultModels().apparelModel()
        .getModelRequest().executeSync().get().asConceptModel();
    assertNotNull(model.outputInfo());
    assertNotNull(model.outputInfo().concepts());
  }

  @Test public void shouldGetModelWithVersion() {
    String modelID = client.getDefaultModels().generalModel().id();

    // This is the first version of the general model.
    String modelVersionID = "aa9ca48295b37401f8af92ad1af0d91d";

    ClarifaiResponse<Model<?>> response = client.getModelByID(modelID)
        .withVersion(modelVersionID)
        .executeSync();

    ConceptModel model = response.get().asConceptModel();
    assertTrue(model.outputInfo().concepts().size() > 0);
    assertEquals(modelVersionID, model.modelVersion().id());
  }

}
