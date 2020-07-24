package clarifai2.integration_tests;

import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Embedding;
import clarifai2.dto.prediction.FaceEmbedding;
import clarifai2.dto.prediction.Detection;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VariousModelsIntTests extends BaseIntTest {

  @Test public void shouldBeSuccessfulForModerationModel() {
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = client.getDefaultModels().moderationModel().predict()
        .withInputs(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL)).executeSync();
    assertSuccess(response);

    List<Concept> concepts = response.get().get(0).data();
    assertNotNull(concepts);
    assertTrue(concepts.size() > 0);
  }

  @Test public void shouldBeCorrectForFoodModel() {
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = client.getDefaultModels().foodModel().predict()
        .withInputs(ClarifaiInput.forImage(FOOD_IMAGE_URL)).executeSync();
    assertSuccess(response);
    assertNotNull(response.get().get(0).id());
  }

  @Test public void shouldBeCorrectForApparelModel() {
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = client.getDefaultModels().apparelModel().predict()
        .withInputs(ClarifaiInput.forImage(SUNGLASSES_IMAGE_URL))
        .executeSync();
    assertSuccess(response);
    assertNotNull(response.get().get(0).data().get(0));
  }

  @Test public void shouldBeSuccessfulForLandscapeQualityModel() {
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response =
        client.getDefaultModels().landscapeQualityModel().predict()
        .withInputs(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL)).executeSync();
    assertSuccess(response);
    List<Concept> concepts = response.get().get(0).data();
    assertNotNull(concepts);
    assertTrue(concepts.size() > 0);
  }

  @Test public void shouldBeSuccessfulForPortraitQualityModel() {
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response =
        client.getDefaultModels().portraitQualityModel().predict()
        .withInputs(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL)).executeSync();
    assertSuccess(response);
    List<Concept> concepts = response.get().get(0).data();
    assertNotNull(concepts);
    assertTrue(concepts.size() > 0);
  }

  @Test public void shouldBeSuccessfulForTexturesAndPatternsModel() {
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response =
        client.getDefaultModels().texturesAndPatternsModel().predict()
        .withInputs(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL)).executeSync();
    assertSuccess(response);
    List<Concept> concepts = response.get().get(0).data();
    assertNotNull(concepts);
    assertTrue(concepts.size() > 0);
  }

  @Test public void shouldBeCorrectForDetectionModel() {
    ClarifaiResponse<List<ClarifaiOutput<Detection>>> response = client.getDefaultModels().logoModel().predict()
        .withInputs(ClarifaiInput.forImage(LOGO_IMAGE_URL)).executeSync();
    assertSuccess(response);
    assertNotNull(response.get().get(0).data().get(0));
  }

  @Test public void shouldBeCorrectForFaceDetectionModel() {
    ClarifaiResponse<List<ClarifaiOutput<Detection>>> response = client.getDefaultModels().faceDetectionModel()
        .predict()
        .withInputs(ClarifaiInput.forImage(CELEBRITY_IMAGE_URL))
        .executeSync();
    assertSuccess(response);
    assertNotNull(response.get().get(0).data().get(0));
  }

  @Test public void shouldBeCorrectForDemographicsModel() {
    ClarifaiResponse<List<ClarifaiOutput<Detection>>> response = client.getDefaultModels().demographicsModel().predict()
        .withInputs(ClarifaiInput.forImage(STREETBAND_IMAGE_URL))
        .executeSync();
    assertSuccess(response);
    Detection detection = response.get().get(0).data().get(0);
    assertNotNull(detection.crop());

    assertNotEquals(0, detection.multiculturalAppearances());
    assertNotEquals(0, detection.genderAppearances());
    assertNotEquals(0, detection.ageAppearances());
  }

  @Test public void shouldBeCorrectForColorModel() {
    ClarifaiResponse<List<ClarifaiOutput<Color>>> response = client.getDefaultModels().colorModel().predict()
        .withInputs(ClarifaiInput.forImage(LOGO_IMAGE_URL)).executeSync();
    assertSuccess(response);
    assertNotNull(response.get().get(0).data().get(0));
  }

  @Test public void shouldBeCorrectForGeneralEmbeddingModel() {
    ClarifaiResponse<List<ClarifaiOutput<Embedding>>> response =
        client.getDefaultModels().generalEmbeddingModel().predict()
            .withInputs(ClarifaiInput.forImage(CELEBRITY_IMAGE_URL))
            .executeSync();
    assertSuccess(response);
    assertNotNull(response.get().get(0).data().get(0));
  }

  @Test public void shouldBeSuccessfulForFaceEmbeddingsModel() {
    ClarifaiResponse<List<ClarifaiOutput<FaceEmbedding>>> response = client.getDefaultModels().faceEmbeddingModel()
        .predict()
        .withInputs(ClarifaiInput.forImage(FAMILY_IMAGE_URL))
        .executeSync();

    assertSuccess(response);
    assertNotNull(response.get());
    assertNotNull(response.get().get(0));
    List<FaceEmbedding> embeddings = response.get().get(0).data();
    assertNotNull(embeddings);
    assertNotNull(embeddings.get(0));
    assertNotNull(embeddings.get(0).embeddings());
  }
}
