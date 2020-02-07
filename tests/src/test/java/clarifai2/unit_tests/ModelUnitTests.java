package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.Action;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.ClarifaiURLImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.FaceEmbeddingModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import clarifai2.grpc.FkClarifaiHttpClient;
import com.google.gson.JsonNull;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModelUnitTests extends BaseUnitTest {

  @Test public void createModelGeneric() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("createModelGeneric_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.createModelGeneric("@modelID")
        .withName("@modelName")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("createModelGeneric_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ConceptModel model = (ConceptModel) response.get();

    assertEquals("@modelID", model.id());
    assertEquals("@modelName", model.name());
    assertEquals("concept", model.modelType().typeExt());
  }

  @Test public void createModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("createModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<ConceptModel> response = client.createModel("@modelID")
        .withName("@modelName")
        .withOutputInfo(ConceptOutputInfo.forConcepts(Concept.forID("dog"), Concept.forID("cat")))
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("createModel_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ConceptModel model = response.get();

    assertEquals("@modelID", model.id());
    assertEquals("@modelName", model.name());
    assertEquals("concept", model.modelType().typeExt());
  }

  @Test public void getModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<Model<?>> response = client.getModelByID("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/output_info"));

    assertTrue(response.isSuccessful());
    Model<?> model = response.get();

    assertEquals("@modelID", model.id());
    assertEquals("@modelName", model.name());
    assertEquals("concept", model.modelType().typeExt());
  }

  @Test public void getModels() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getModels_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<Model<?>>> response = client.getModels()
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models?page=1"));

    assertTrue(response.isSuccessful());
    List<Model<?>> models = response.get();

    assertEquals(2, models.size());

    ConceptModel model1 = (ConceptModel) models.get(0);
    assertEquals("@modelID1", model1.id());
    assertEquals("@modelName1", model1.name());
    assertEquals("concept", model1.modelType().typeExt());

    FaceEmbeddingModel model2 = (FaceEmbeddingModel) models.get(1);
    assertEquals("@modelID2", model2.id());
    assertEquals("@modelName2", model2.name());
    assertEquals("detect-embed", model2.modelType().typeExt());
  }

  @Test public void getModelInputs() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getModelInputs_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ClarifaiInput>> response = client.getModelInputs("@modelID")
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/inputs?page=1"));

    assertTrue(response.isSuccessful());
    List<ClarifaiInput> inputs = response.get();

    ClarifaiInput input = inputs.get(0);
    assertEquals("@inputID", input.id());

    ClarifaiURLImage image = (ClarifaiURLImage) input.inputValue();
    assertEquals("https://imageURL", image.url().toString());

    Concept concept = input.concepts().get(0);
    assertEquals("@conceptID", concept.id());
    assertEquals("@conceptName", concept.name());
  }

  @Test public void modifyModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("modifyModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    @NotNull ClarifaiResponse<ConceptModel> response = client.modifyModel("@modelID")
        .withName("@newModelName")
        .withConcepts(Action.MERGE, Concept.forID("@conceptID1"))
        .withClosedEnvironment(true)
        .withConceptsMutuallyExclusive(true)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models"));
    assertEquals("PATCH", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("modifyModel_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ConceptModel model = response.get();

    assertEquals("@modelID", model.id());
    assertEquals("@newModelName", model.name());
    assertEquals("@modelVersionID", model.modelVersion().id());
    assertTrue(model.outputInfo().isEnvironmentClosed());
    assertTrue(model.outputInfo().areConceptsMutuallyExclusive());
  }

  @Test public void trainModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("trainModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    @NotNull ClarifaiResponse<Model<?>> response = client.trainModel("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/versions"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("trainModel_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());
    ConceptModel model = (ConceptModel) response.get();

    assertEquals("@modelID", model.id());
    assertEquals("@modelName", model.name());
    assertEquals("@modelVersionID", model.modelVersion().id());
  }

  @Test public void deleteAllModels() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("deleteAllModels_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.deleteAllModels()
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models"));
    assertEquals("DELETE", httpClient.requestMethod());

    assertTrue(response.isSuccessful());
  }

  @Test public void deleteModelsBatch() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("deleteModelsBatch_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.deleteModelsBatch()
        .plus("@modelID1", "@modelID2")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models"));
    assertEquals("DELETE", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("deleteModelsBatch_request.json"), httpClient.requestBody());


    assertTrue(response.isSuccessful());
  }

  @Test public void deleteModel() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("deleteModel_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<JsonNull> response = client.deleteModel("@modelID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID"));
    assertEquals("DELETE", httpClient.requestMethod());

    assertTrue(response.isSuccessful());
  }
}
