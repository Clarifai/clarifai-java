package clarifai2.integration_tests;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.VideoModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Frame;
import clarifai2.dto.prediction.Prediction;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class InputIntTests extends BaseIntTest {

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

  @Test public void shouldBeSuccessfulWhenSpecifyingModelVersionID() {
    ConceptModel model = client.getDefaultModels().generalModel();
    // It's reasonably safe to have this hard-coded in a test as this is the general's model version ID which is
    // unlikely to be ever removed.
    String modelVersionID = "aa9ca48295b37401f8af92ad1af0d91d";

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict(model.id())
        .withInputs(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL))
        .withVersion(modelVersionID)
        .executeSync();
    Assert.assertTrue(response.isSuccessful());
  }

  @Test public void videoURLShouldBeSuccessfulWhenSpecifyingSampleMs() {
    VideoModel model = client.getDefaultModels().generalVideoModel();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict(model.id())
        .withInputs(ClarifaiInput.forVideo(VIDEO_URL))
        .withSampleMs(2000)
        .executeSync();
    Assert.assertTrue(response.isSuccessful());

    for (Prediction prediction : response.get().get(0).data()) {
      Frame frame = prediction.asFrame();
      Assert.assertEquals(0, frame.time() % 2000);
    }
  }

  @Test public void videoFileShouldBeSuccessfulWhenSpecifyingSampleMs() {
    VideoModel model = client.getDefaultModels().generalVideoModel();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict(model.id())
        .withInputs(ClarifaiInput.forVideo(BEER_VIDEO_FILE))
        .withSampleMs(2000)
        .executeSync();
    Assert.assertTrue(response.isSuccessful());

    for (Prediction prediction : response.get().get(0).data()) {
      Frame frame = prediction.asFrame();
      Assert.assertEquals(0, frame.time() % 2000);
    }
  }
}

