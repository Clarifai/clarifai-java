package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.Crop;
import clarifai2.dto.model.ColorModel;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.EmbeddingModel;
import clarifai2.dto.model.FaceEmbeddingModel;
import clarifai2.dto.model.DetectionModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Embedding;
import clarifai2.dto.prediction.FaceEmbedding;
import clarifai2.dto.prediction.Frame;
import clarifai2.dto.prediction.Detection;
import clarifai2.dto.prediction.Prediction;
import clarifai2.grpc.FkClarifaiHttpClient;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VariousModelsUnitTests extends BaseUnitTest {

  @Test public void getColorModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getColorModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.getModelByID("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/output_info"));

    assertTrue(response.isSuccessful());
    ColorModel model = response.get().asColorModel();

    assertEquals("@modelID", model.id());
    assertEquals("color", model.name());
    assertEquals("color", model.modelType().typeExt());

    List<Concept> concepts = model.outputInfo().concepts();

    assertEquals("@conceptID1", concepts.get(0).id());
    assertEquals("AliceBlue", concepts.get(0).name());

    assertEquals("@conceptID2", concepts.get(1).id());
    assertEquals("AntiqueWhite", concepts.get(1).name());
  }

  @Test public void predictColor() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predictColor_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictColor_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());
    assertEquals("@outputID", output.id());

    Color color1 = output.data().get(0).asColor();
    assertEquals("WhiteSmoke", color1.webSafeColorName());
    assertEquals("#f2f2f2", color1.hex());
    assertEquals("#f5f5f5", color1.webSafeHex());
    assertEquals(0.929, color1.value(), 10e-6);

    Color color2 = output.data().get(1).asColor();
    assertEquals("SlateGray", color2.webSafeColorName());
    assertEquals("#686078", color2.hex());
    assertEquals("#708090", color2.webSafeHex());
    assertEquals(0.02675, color2.value(), 20e-6);
  }

  @Test public void getEmbeddingModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getEmbeddingModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.getModelByID("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/output_info"));

    assertTrue(response.isSuccessful());
    EmbeddingModel model = response.get().asEmbeddingModel();

    assertEquals("@modelID", model.id());
    assertEquals("general", model.name());
    assertEquals("embed", model.modelType().typeExt());
  }

  @Test public void predictEmbedding() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predictEmbedding_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictEmbedding_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());
    assertEquals("@outputID", output.id());

    Embedding embedding = output.data().get(0).asEmbedding();

    assertEquals(3, embedding.numDimensions());
    assertEquals(0.1f, embedding.embedding()[0], 10e-6);
    assertEquals(0.2f, embedding.embedding()[1], 10e-6);
    assertEquals(0.3f, embedding.embedding()[2], 10e-6);
  }

  @Test public void getFaceEmbeddingModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getFaceEmbeddingModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.getModelByID("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/output_info"));

    assertTrue(response.isSuccessful());
    FaceEmbeddingModel model = response.get().asFaceEmbeddingModel();

    assertEquals("@modelID", model.id());
    assertEquals("face", model.name());
    assertEquals("detect-embed", model.modelType().typeExt());
  }

  @Test public void predictFaceEmbedding() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predictFaceEmbedding_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictFaceEmbedding_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());
    assertEquals("@outputID", output.id());

    FaceEmbedding faceEmbedding = output.data().get(0).asFaceEmbedding();
    assertEquals(Crop.create().top(0.1f).left(0.2f).bottom(0.3f).right(0.4f), faceEmbedding.crop());

    float[] embedding = faceEmbedding.embeddings().get(0).embedding();
    assertEquals(0.1, embedding[0], 10e-6);
    assertEquals(0.2, embedding[1], 10e-6);
    assertEquals(0.3, embedding[2], 10e-6);
  }

  @Test public void getDetectionModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getLogoModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.getModelByID("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/output_info"));

    assertTrue(response.isSuccessful());
    DetectionModel model = response.get().asDetectionModel();

    assertEquals("@modelID", model.id());
    assertEquals("logo", model.name());
    assertEquals("detect-concept", model.modelType().typeExt());

    List<Concept> concepts = model.outputInfo().concepts();
    assertEquals("@conceptID1", concepts.get(0).id());
    assertEquals("@conceptID2", concepts.get(1).id());
  }

  @Test public void predictDetection() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predictLogo_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictLogo_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());
    assertEquals("@outputID", output.id());

    Detection logo = output.data().get(0).asDetection();
    assertEquals(Crop.create().top(0.1f).left(0.2f).bottom(0.3f).right(0.4f), logo.boundingBox());

    assertEquals("@conceptID1", logo.concepts().get(0).id());
    assertEquals("@conceptID2", logo.concepts().get(1).id());
  }

  @Test public void getConceptModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getConceptModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.getModelByID("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/output_info"));

    assertTrue(response.isSuccessful());
    ConceptModel model = response.get().asConceptModel();

    assertEquals("@modelID", model.id());
    assertEquals("@modelName", model.name());
    assertEquals("concept", model.modelType().typeExt());

    List<Concept> concepts = model.outputInfo().concepts();
    String[] concept_id ={"@conceptID1","@conceptID2", "@conceptID3"};
    for(int i = 0; i < 3; i++){
      assertEquals(concept_id[i], concepts.get(i).id());
    }
  }

  @Test public void predictConcept() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predictConcept_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictConcept_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());
    assertEquals("@outputID", output.id());

    Frame frame = output.data().get(0).asFrame();
    assertEquals("@conceptID1", frame.concepts().get(0).id());
    assertEquals("@conceptID2", frame.concepts().get(1).id());
  }

  @Test public void getVideoModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getVideoModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.getModelByID("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/output_info"));

    assertTrue(response.isSuccessful());
    ConceptModel model = response.get().asConceptModel();

    assertEquals("@modelID", model.id());
    assertEquals("nsfw-v1.0", model.name());
    assertEquals("concept", model.modelType().typeExt());

    List<Concept> concepts = model.outputInfo().concepts();
    assertEquals("@conceptID1", concepts.get(0).id());
    assertEquals("@conceptID2", concepts.get(1).id());
  }

  @Test public void predictVideo() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("predictVideo_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict("@modelID")
        .withInputs(ClarifaiInput.forImage("https://some-image-url"))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/outputs"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("predictVideo_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ClarifaiOutput<Prediction> output = response.get().get(0);

    assertEquals("@inputID", output.input().id());
    assertEquals("@outputID", output.id());

    Frame frame = output.data().get(0).asFrame();
    assertEquals("@conceptID1", frame.concepts().get(0).id());
    assertEquals("@conceptID2", frame.concepts().get(1).id());
  }
}
