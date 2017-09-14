package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static clarifai2.internal.InternalUtil.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PredictTests extends BaseClarifaiAPITest {

  @NotNull static final String CAT_IMAGE_URL =
      "https://developer.clarifai.com/static/images/model-samples/focus-001.jpg";

  @Test public void shouldReturnErrorWhenTooManyMaxConcepts() {
    PredictRequest<Prediction> request = makePredictRequest()
        .withMaxConcepts(201);
    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> predictionsResponse = request.executeSync();
    assertFalse(predictionsResponse.isSuccessful());
    assertEquals(21202, predictionsResponse.getStatus().statusCode());
    assertEquals("Invalid model argument", predictionsResponse.getStatus().description());
    assertEquals("'max_concepts' must be within the interval (0, 200]", predictionsResponse.getStatus().errorDetails());
  }

  @Test public void shouldReturnErrorWhenMinValueOutOfRange() {
    PredictRequest<Prediction> request = makePredictRequest()
        .withMinValue(1.01);
    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> predictionsResponse = request.executeSync();
    assertFalse(predictionsResponse.isSuccessful());
    assertEquals(21202, predictionsResponse.getStatus().statusCode());
    assertEquals("Invalid model argument", predictionsResponse.getStatus().description());
    assertEquals("'min_value' must be within the interval [0,1].",
        predictionsResponse.getStatus().errorDetails()
    );
  }

  @Test public void shouldReturnCorrectValuesWhenValid() {
    PredictRequest<Prediction> request = makePredictRequest()
        .withMaxConcepts(3);
    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> predictionsResponse = request.executeSync();
    List<Prediction> predictions = predictionsResponse.get().get(0).data();
    assertTrue(predictions.size() <= 3);
  }

  @Test public void shouldReturnCorrectConceptsWhenSelectConcepts() {
    String catConceptId = "ai_mFqxrph2";
    String dogConceptId = "ai_8S2Vq3cR";
    PredictRequest<Prediction> request = client.predict(client.getDefaultModels().generalModel().id())
        .withInputs(
            ClarifaiInput.forImage(CAT_IMAGE_URL))
        .selectConcepts(Concept.forID(dogConceptId), Concept.forID(catConceptId));
    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> predictionsResponse = request.executeSync();
    List<Prediction> predictions = predictionsResponse.get().get(0).data();
    // Since we selected only 2 concepts, no more than 2 should be returned.
    assertTrue(predictions.size() <= 2);
    // Cat should be in the prediction response.
    assertTrue(predictions.stream().anyMatch(prediction -> prediction.asConcept().id().equals(catConceptId)));
  }

  @Test public void modelPredictShouldBeSuccessfulWhenNonCompleteModel() {
    ConceptModel apparelModel = client.getDefaultModels().apparelModel();
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response =
        apparelModel.predict().withInputs(ClarifaiInput.forImage(SUNGLASSES_IMAGE_URL)).executeSync();
    ClarifaiOutput<Concept> output = response.get().get(0);
    assertNotNull(output.model().modelVersion());
    assertNotNull(output.data());
  }

  @Test public void modelPredictShouldBeSuccessfulWhenCompleteModel() {
    ConceptModel apparelModel =
        client.getModelByID(client.getDefaultModels().apparelModel().id()).executeSync().get().asConceptModel();
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response =
        apparelModel.predict().withInputs(ClarifaiInput.forImage(SUNGLASSES_IMAGE_URL)).executeSync();
    ClarifaiOutput<Concept> output = response.get().get(0);
    assertNotNull(output.model().modelVersion());
    assertNotNull(output.data());
  }

  @NotNull private PredictRequest<Prediction> makePredictRequest() {
    return client.predict(client.getDefaultModels().travelModel().id())
        .withInputs(
            ClarifaiInput.forInputValue(
                ClarifaiImage.of(FAMILY_IMAGE_URL)
                    .withAllowDuplicateUrl(false)));
  }
}
