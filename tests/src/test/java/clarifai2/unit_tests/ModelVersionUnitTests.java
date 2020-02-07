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
import clarifai2.dto.model.ModelVersion;
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

public class ModelVersionUnitTests extends BaseUnitTest {

  @Test public void getModelVersion() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getModelVersion_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<ModelVersion> response = client.getModelVersionByID("@modelID", "@modelVersionID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/versions/@modelVersionID"));
    assertEquals("GET", httpClient.requestMethod());

    assertTrue(response.isSuccessful());
    ModelVersion modelVersion = response.get();

    assertEquals("@modelVersionID", modelVersion.id());
    assertEquals(5, modelVersion.activeConceptCount());
  }

  @Test public void getModelVersions() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("getModelVersions_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ModelVersion>> response = client.getModelVersions("@modelID")
        .getPage(1)
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/versions?page=1"));
    assertEquals("GET", httpClient.requestMethod());

    assertTrue(response.isSuccessful());
    List<ModelVersion> modelVersions = response.get();

    ModelVersion modelVersion1 = modelVersions.get(0);
    assertEquals("@modelVersionID1", modelVersion1.id());
    assertEquals(5, modelVersion1.activeConceptCount());

    ModelVersion modelVersion2 = modelVersions.get(1);
    assertEquals("@modelVersionID2", modelVersion2.id());
    assertEquals(5, modelVersion2.activeConceptCount());
  }

  @Test public void deleteModelVersion() throws IOException {
    FkClarifaiHttpClient httpClient = new FkClarifaiHttpClient(readResourceFile("deleteModelVersion_response.json"));
    ClarifaiClient client = new ClarifaiBuilder(httpClient).buildSync();

    ClarifaiResponse<List<ModelVersion>> response = client.deleteModelVersion("@modelID", "@modelVersionID")
        .executeSync();

    assertTrue(httpClient.requestUrl().endsWith("/v2/models/@modelID/versions/@modelVersionID"));
    assertEquals("DELETE", httpClient.requestMethod());

    assertTrue(response.isSuccessful());
  }
}
