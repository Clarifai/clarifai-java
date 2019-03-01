package clarifai2.integration_tests;

import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelVersion;
import org.junit.Test;

import java.util.List;

import static clarifai2.internal.InternalUtil.assertNotNull;

public class ModelVersionIntTests extends BaseIntTest {

  @Test public void shouldHaveFieldsPopulatedAfterGetModelById() {
    ConceptModel model = client.getModelByID(client.getDefaultModels().nsfwModel().id())
        .executeSync().get().asConceptModel();
    ModelVersion modelVersion = model.modelVersion();
    assertNotNull(modelVersion.status());
    assertNotNull(modelVersion.createdAt());
    assertNotNull(modelVersion.totalInputCount());
    assertNotNull(modelVersion.activeConceptCount());
  }

  @Test public void shouldHaveFieldsPopulatedAfterGetModels() {
    ClarifaiResponse<List<Model<?>>> response = client.getModels().build().getPage(1).executeSync();
    Model<?> model = response.get().get(0);
    ModelVersion modelVersion = model.modelVersion();
    assertNotNull(modelVersion.status());
    assertNotNull(modelVersion.createdAt());
    assertNotNull(modelVersion.totalInputCount());
    assertNotNull(modelVersion.activeConceptCount());
  }

  @Test public void shouldHaveFieldsPopulatedUsingGetModelVersionByID() {
    ConceptModel model = client.getModelByID(client.getDefaultModels().nsfwModel().id())
        .executeSync().get().asConceptModel();
    ModelVersion modelVersion = client.getModelVersionByID(model.id(), model.modelVersion().id())
        .executeSync().get();
    assertNotNull(modelVersion.status());
    assertNotNull(modelVersion.createdAt());
    assertNotNull(modelVersion.totalInputCount());
    assertNotNull(modelVersion.activeConceptCount());
  }
}
