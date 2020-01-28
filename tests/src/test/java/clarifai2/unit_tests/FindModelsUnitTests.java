package clarifai2.unit_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelType;
import clarifai2.grpc.FkClarifaiHttpClient;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FindModelsUnitTests extends BaseUnitTest {

  @Test public void findModelByName() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("findModelByName_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<Model<?>>> response = client.findModel()
        .withName("celeb*")
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/searches?page=1"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("findModelByName_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    List<Model<?>> models = response.get();

    assertEquals(1, models.size());
    assertEquals("@modelID", models.get(0).id());
    assertEquals("@modelVersionID", models.get(0).modelVersion().id());
  }

  @Test public void findModelByNameAndType() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(
        readResourceFile("findModelByNameAndType_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<Model<?>>> response = client.findModel()
        .withName("*")
        .withModelType(ModelType.COLOR)
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/searches?page=1"));
    assertEquals("POST", httpClient.requestMethod());
    assertJsonEquals(readResourceFile("findModelByNameAndType_request.json"), httpClient.requestBody());

    assertTrue(response.isSuccessful());

    List<Model<?>> models = response.get();

    assertEquals(1, models.size());
    assertEquals("@modelID", models.get(0).id());
    assertEquals("@modelVersionID", models.get(0).modelVersion().id());
  }
}
