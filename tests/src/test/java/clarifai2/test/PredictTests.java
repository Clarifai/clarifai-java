package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Prediction;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PredictTests extends BaseClarifaiAPITest {

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

  @NotNull private PredictRequest<Prediction> makePredictRequest() {
    return client.predict(client.getDefaultModels().travelModel().id())
        .withInputs(
            ClarifaiInput.forImage(
                ClarifaiURLImage.of(FAMILY_IMAGE_URL)
                    .withAllowDuplicateUrl(false)));
  }
}
