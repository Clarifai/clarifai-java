package clarifai2.integration_tests;

import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.GetInputRequest;
import clarifai2.api.request.input.GetInputsRequest;
import clarifai2.api.request.input.PatchInputRequest;
import clarifai2.api.request.model.CreateModelRequest;
import clarifai2.api.request.model.GetModelInputsRequest;
import clarifai2.api.request.model.GetModelRequest;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;

import org.jetbrains.annotations.Nullable;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class KeysIntTests extends BaseIntTest {

  @Ignore("Ignore because the key doesn't exist")
  @Test public void shouldHaveCorrectStatusWhenInvalidKey() {
    GetInputsRequest request = makeClient("some-invalid-key-321").getInputs();
    ClarifaiResponse<List<ClarifaiInput>> response = request.getPage(1).executeSync();
    assertEquals("API key not found", response.getStatus().description());
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void shouldHaveCorrectStatusWhenKeyHasNoScope() {
    GetInputsRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_NO_SCOPES.value()).getInputs();
    ClarifaiResponse<List<ClarifaiInput>> response = request.getPage(1).executeSync();
    assertEquals("API key has insufficient scopes", response.getStatus().description());
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void inputsRequestShouldReturnConceptsWhenWithPermissions() {
    try {
      addInputWithConcepts(client, FAMILY_IMAGE_URL, "conceptA", "conceptB");
      addInputWithConcepts(client, METRO_NORTH_IMAGE_URL, "conceptA");
      GetInputsRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_ALL_GET.value()).getInputs();
      ClarifaiResponse<List<ClarifaiInput>> response = request.getPage(1).executeSync();
      assertEquals(10000, response.getStatus().statusCode());
      int maxConceptsSize = 0;
      for (ClarifaiInput inputResponse : response.get()) {
        maxConceptsSize = Math.max(inputResponse.concepts().size(), maxConceptsSize);
      }
      assertNotEquals(0, maxConceptsSize);
    } finally {
      client.deleteAllInputs().executeSync();
    }
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void inputsRequestShouldReturnNotReturnConceptsWhenNoPermissions() {
    try {
      addInputWithConcepts(client, FAMILY_IMAGE_URL, "conceptA", "conceptB");
      addInputWithConcepts(client, METRO_NORTH_IMAGE_URL, "conceptA");
      GetInputsRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_INPUTS_GET_PATCH.value()).getInputs();
      ClarifaiResponse<List<ClarifaiInput>> response = request.getPage(1).executeSync();
      assertEquals(10000, response.getStatus().statusCode());
      int maxConceptsSize = 0;
      for (ClarifaiInput inputResponse : response.get()) {
        maxConceptsSize = Math.max(inputResponse.concepts().size(), maxConceptsSize);
      }
      assertEquals(0, maxConceptsSize);
    } finally {
      client.deleteAllInputs().executeSync();
    }
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void inputRequestShouldReturnConceptsWhenWithPermissions() {
    try {
      String inputId = addInputWithConcepts(client, FAMILY_IMAGE_URL, "conceptA", "conceptB");
      GetInputRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_ALL_GET.value())
          .getInputByID(inputId);
      ClarifaiResponse<ClarifaiInput> response = request.executeSync();
      assertEquals(10000, response.getStatus().statusCode());
      ClarifaiInput inputResponse = response.get();
      assertNotEquals(0, inputResponse.concepts().size());
    } finally {
      // best effort -- don't handle failure
      client.deleteAllInputs().executeSync();
    }
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void inputRequestShouldNotReturnConceptsWhenNoPermissions() {
    try {
      String inputId = addInputWithConcepts(client, FAMILY_IMAGE_URL, "conceptA", "conceptB");
      GetInputRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_INPUTS_GET_PATCH.value())
          .getInputByID(inputId);
      ClarifaiResponse<ClarifaiInput> response = request.executeSync();
      assertEquals(10000, response.getStatus().statusCode());
      ClarifaiInput inputResponse = response.get();
      assertEquals(0, inputResponse.concepts().size());
    } finally {
      client.deleteAllInputs().executeSync();
    }
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void patchInputShouldNotReturnConceptsWhenNoPermissions() {
    try {
      String inputId = addInputWithConcepts(client, FAMILY_IMAGE_URL, "concept0", "concept1");
      PatchInputRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_INPUTS_GET_PATCH.value())
          .mergeConceptsForInput(inputId)
          .plus(
              Concept.forID("concept2"),
              Concept.forID("concept3")
          );
      ClarifaiResponse<ClarifaiInput> response = request.executeSync();
      assertEquals(0, response.get().concepts().size());
    } finally {
      client.deleteAllInputs().executeSync();
    }
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void patchInputShouldReturnConceptsWhenWithPermissions() {
    try {
      String inputId = addInputWithConcepts(client, METRO_NORTH_IMAGE_URL, "concept0", "concept1");
      assertNotNull(inputId);
      PatchInputRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_INPUTS_GET_PATCH_AND_CONCEPTS_GET.value())
          .mergeConceptsForInput(inputId)
          .plus(
              Concept.forID("concept2"),
              Concept.forID("concept3")
          );
      ClarifaiResponse<ClarifaiInput> response = request.executeSync();
      assertNotEquals(0, response.get().concepts().size());
    } finally {
      client.deleteAllInputs().executeSync();
    }
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void getModelShouldNotReturnConceptsWhenNoPermissions() {
    GetModelRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_MODELS_GET.value())
        .getModelByID(client.getDefaultModels().travelModel().id());
    ClarifaiResponse<Model<?>> response = request.executeSync();
    assertEquals(0, response.get().asConceptModel().outputInfo().concepts().size());
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void getModelShouldReturnConceptsWhenWithPermissions() {
    GetModelRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_MODELS_AND_CONCEPTS_GET.value())
        .getModelByID(client.getDefaultModels().travelModel().id());
    ClarifaiResponse<Model<?>> response = request.executeSync();
    assertNotEquals(0, response.get().asConceptModel().outputInfo().concepts().size());
  }

  @Ignore("2017/07/12: temporarily disabled")
  @Test public void getModelInputsShouldNotReturnConceptsWhenNoPermissions() {
    try {
      String modelId = addModelWithInputsAndConcepts(client);
      GetModelInputsRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_MODELS_AND_INPUTS_GET.value())
          .getModelInputs(modelId);
      ClarifaiResponse<List<ClarifaiInput>> response = request.getPage(1).executeSync();
      assertEquals(10000, response.getStatus().statusCode());
      int maxConceptsSize = 0;
      for (ClarifaiInput inputResponse : response.get()) {
        maxConceptsSize = Math.max(inputResponse.concepts().size(), maxConceptsSize);
      }
      assertEquals(0, maxConceptsSize);
    } finally {
      client.deleteAllModels().executeSync();
      client.deleteAllInputs().executeSync();
    }
  }

  @Ignore("2017/07/12: temporarily disabled")
  @Test public void getModelInputsShouldReturnConceptsWhenWithPermissions() {
    try {
      String modelId = addModelWithInputsAndConcepts(client);
      GetModelInputsRequest request = makeClient(EnvVar.CLARIFAI_API_KEY_ALL_GET.value())
          .getModelInputs(modelId);
      ClarifaiResponse<List<ClarifaiInput>> response = request.getPage(1).executeSync();
      assertEquals(10000, response.getStatus().statusCode());
      int maxConceptsSize = 0;
      for (ClarifaiInput inputResponse : response.get()) {
        maxConceptsSize = Math.max(inputResponse.concepts().size(), maxConceptsSize);
      }
      assertNotEquals(0, maxConceptsSize);
    } finally {
      client.deleteAllModels().executeSync();
      client.deleteAllInputs().executeSync();
    }
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void predictRequestShouldReturnModelEvenWhenNoPermissions() {
    PredictRequest<Prediction> request = makeClient(EnvVar.CLARIFAI_API_KEY_PREDICT_AND_INPUTS_GET.value())
        .predict(client.getDefaultModels().travelModel().id())
        .withInputs(ClarifaiInput.forImage(FAMILY_IMAGE_URL));
    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = request.executeSync();
    assertEquals(10000, response.getStatus().statusCode());
    assertNotNull(response.get().get(0).model().id());
  }

  @Ignore("Ignore because the key doesn't exist")
  @Test public void predictRequestShouldReturnConceptsWhenWithPermissions() {
    PredictRequest<Prediction> request = makeClient(
          EnvVar.CLARIFAI_API_KEY_PREDICT_AND_INPUTS_MODELS_CONCEPTS_GET.value())
        .predict(client.getDefaultModels().travelModel().id())
        .withInputs(ClarifaiInput.forImage(FAMILY_IMAGE_URL));
    ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = request.executeSync();
    assertEquals(10000, response.getStatus().statusCode());
    assertNotNull(response.get().get(0).model().id());
  }

  // returns the ID or null on failure; the function has preset concepts to add
  @Ignore("Ignore because the key doesn't exist")
  @Nullable private String addInputWithConcepts(ClarifaiClient client, String url, String ...conceptIds) {
    List<Concept> concepts = new ArrayList<>(conceptIds.length);
    for (String concept : conceptIds) {
      concepts.add(Concept.forID(concept));
    }
    ClarifaiInput input = ClarifaiInput.forImage(url).withConcepts(concepts);

    ClarifaiResponse<List<ClarifaiInput>> response =
        client.addInputs().plus(input).allowDuplicateURLs(true).executeSync();
    if(!response.isSuccessful() || response.get().size() != 1) {
      return null;
    }
    return response.get().get(0).id();
  }

  @Ignore("Ignore because the key doesn't exist")
  @Nullable private String addModelWithInputsAndConcepts(ClarifaiClient client) {
    String conceptA = "foo";
    String conceptB = "bar";
    if(addInputWithConcepts(client, FAMILY_IMAGE_URL, conceptA, conceptB) == null ||
       addInputWithConcepts(client, METRO_NORTH_IMAGE_URL, conceptA, conceptB) == null) {
      return null;
    }
    CreateModelRequest request =
          client.createModel("test-model").withOutputInfo(
            ConceptOutputInfo.forConcepts(Concept.forID(conceptA), Concept.forID(conceptB))
          );
    ClarifaiResponse<ConceptModel> response = request.executeSync();
    if(!response.isSuccessful()) {
      return null;
    }
    return response.get().id();
  }
}

