package clarifai2.test;

import clarifai2.dto.model.ColorModel;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ModelEqualityTests extends BaseClarifaiAPITest {

  @Test public void shouldBeEqualIfAllFieldsEqual() {
    ConceptModel model1 = Model._create(ModelType.CONCEPT, client, "id", "name", null);
    ConceptModel model2 = Model._create(ModelType.CONCEPT, client, "id", "name", null);

    assertEquals(model1, model2);
  }

  @Test public void shouldBeEqualIfDifferentName() {
    ConceptModel model1 = Model._create(ModelType.CONCEPT, client, "id", "name1", null);
    ConceptModel model2 = Model._create(ModelType.CONCEPT, client, "id", "name2", null);

    assertEquals(model1, model2);
  }

  @Test public void shouldBeEqualIfDifferentOutputInfo() {
    ConceptModel model1 = Model._create(ModelType.CONCEPT, client, "id", "name",
        ConceptOutputInfo.forConcepts(Concept.forID("dog")));
    ConceptModel model2 = Model._create(ModelType.CONCEPT, client, "id", "name",
        ConceptOutputInfo.forConcepts(Concept.forID("cat")));

    assertEquals(model1, model2);
  }

  @Test public void shouldNotBeEqualIfDifferentTypes() {
    ConceptModel conceptModel = Model._create(ModelType.CONCEPT, client, "id", "name", null);
    ColorModel colorModel = Model._create(ModelType.COLOR, client, "id", "name", null);

    assertNotEquals(conceptModel, colorModel);
  }

  @Test public void shouldNotBeEqualIfDifferentId() {
    ConceptModel model1 = Model._create(ModelType.CONCEPT, client, "id1", "name", null);
    ConceptModel model2 = Model._create(ModelType.CONCEPT, client, "id2", "name", null);

    assertNotEquals(model1, model2);
  }
}
