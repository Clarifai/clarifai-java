package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Frame;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ImageAndVideoTests extends BaseClarifaiAPITest {

  private final String VIDEO_URL = "https://samples.clarifai.com/beer.mp4";

  @Test public void shouldBeSuccessfulWhenValidImageFile() {
    PredictRequest<Concept> request = client.getDefaultModels().generalModel().predict()
        .withInputs(
            ClarifaiInput.forImage(METRO_NORTH_IMAGE_FILE)
        );
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = request.executeSync();
    Assert.assertTrue(response.isSuccessful());
  }

  @Test public void shouldBeSuccessfulWhenValidUrlImage() {
    PredictRequest<Concept> request = client.getDefaultModels().generalModel().predict()
        .withInputs(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL));
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = request.executeSync();
    Assert.assertTrue(response.isSuccessful());
  }

  @Test public void shouldBeSuccessfulWhenValidUrlVideo() {
    PredictRequest<Frame> request = client.getDefaultModels().generalVideoModel().predict()
        .withInputs(ClarifaiInput.forVideo(VIDEO_URL));
    ClarifaiResponse<List<ClarifaiOutput<Frame>>> response = request.executeSync();
    Assert.assertTrue(response.isSuccessful());
  }

  @Test public void shouldBeSuccessfulWhenValidVideoFile() {
    PredictRequest<Frame> request = client.getDefaultModels().generalVideoModel().predict()
        .withInputs(
            ClarifaiInput.forVideo(BEER_VIDEO_FILE)
        );
    ClarifaiResponse<List<ClarifaiOutput<Frame>>> response = request.executeSync();
    Assert.assertTrue(response.isSuccessful());
  }

  @Test public void shouldBeSuccessfulWhenTwoImages() {
    PredictRequest<Concept> request = client.getDefaultModels().weddingModel().predict()
        .withInputs(
            ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL),
            ClarifaiInput.forImage(METRO_NORTH_IMAGE_FILE)
        );
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = request.executeSync();
    Assert.assertTrue(response.isSuccessful());
  }
}
