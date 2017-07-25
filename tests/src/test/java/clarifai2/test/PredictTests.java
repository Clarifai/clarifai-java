package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

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
    List<ClarifaiOutput<Prediction>> predictions = predictionsResponse.get();
    assertEquals(3, predictions.get(0).data().size());
  }

  @Test public void shouldReturnCorrectConceptsWhenSelectConcepts() {
    String catConceptId = "ai_mFqxrph2";
    String dogConceptId = "ai_8S2Vq3cR";
    PredictRequest<Prediction> request = client.predict(client.getDefaultModels().generalModel().id())
        .withInputs(
            ClarifaiInput.forImage(
                ClarifaiURLImage.of(CAT_IMAGE_URL)))
        .selectConcepts(Concept.forID(dogConceptId), Concept.forID(catConceptId));
    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> predictionsResponse = request.executeSync();
    List<Prediction> predictions = predictionsResponse.get().get(0).data();
    // Since we selected only 2 concepts, no more than 2 should be returned.
    assertTrue(predictions.size() <= 2);
    // Cat should be in the prediction response.
    assertTrue(predictions.stream().anyMatch(prediction -> prediction.asConcept().id().equals(catConceptId)));
  }

  @NotNull private PredictRequest<Prediction> makePredictRequest() {
    return client.predict(client.getDefaultModels().travelModel().id())
        .withInputs(
            ClarifaiInput.forImage(
                ClarifaiURLImage.of(FAMILY_IMAGE_URL)
                    .withAllowDuplicateUrl(false)));
  }
}
