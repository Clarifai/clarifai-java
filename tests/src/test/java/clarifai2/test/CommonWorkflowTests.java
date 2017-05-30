package clarifai2.test;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.SearchClause;
import clarifai2.api.request.model.Action;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.PointF;
import clarifai2.dto.Radius;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.SearchHit;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.Crop;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelTrainingStatus;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Color;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Embedding;
import clarifai2.dto.prediction.Focus;
import clarifai2.dto.prediction.Frame;
import clarifai2.dto.prediction.Logo;
import clarifai2.dto.prediction.Region;
import clarifai2.exception.ClarifaiException;
import clarifai2.internal.JSONObjectBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.kevinmost.junit_retry_rule.Retry;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static clarifai2.api.request.input.SearchClause.matchConcept;
import static clarifai2.internal.InternalUtil.assertNotNull;
import static clarifai2.internal.InternalUtil.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommonWorkflowTests extends BaseClarifaiAPITest {

  private static long startTime;

  @BeforeClass
  public static void recordTime() {
    startTime = System.nanoTime();
  }

  @Retry
  @Test public void t00_deleteAllInputs() {
    assertSuccess(client.deleteAllInputs());
    retryAndTimeout(1, TimeUnit.MINUTES, () ->
        client.getInputs().build().getPage(1).executeSync().get().isEmpty()
    );
  }

  @Retry
  @Test public void t01a_addInputs() throws Exception {
    assertSuccess(client.addInputs()
        .plus(ClarifaiInput.forImage(ClarifaiImage.of(KOTLIN_LOGO_IMAGE_FILE)
                .withCrop(Crop.create()
                    .top(0.1F)
                    .left(0.1F)
                    .bottom(0.9F)
                    .right(0.9F)
                )
            )
                .withID("foo1")
                .withConcepts(Concept.forID("concept1").withValue(false))
        )
    );
  }

  @Retry
  @Test public void t01b_addInputs_bulk() throws Exception {
    final Concept ferrari23 = Concept.forID("ferrari23");
    final Concept outdoors23 = Concept.forID("outdoors23");
    assertSuccess(client.addInputs()
        .plus(
            ClarifaiInput.forImage(FERRARI_IMAGE_URL)
                .withConcepts(
                    ferrari23.withValue(true)
                ),
            ClarifaiInput.forImage(FERRARI_IMAGE_URL2)
                .withConcepts(
                    ferrari23.withValue(true),
                    outdoors23.withValue(false)
                ),
            ClarifaiInput.forImage(HONDA_IMAGE_URL)
                .withConcepts(
                    ferrari23.withValue(true),
                    outdoors23
                ).withGeo(PointF.at(30, -24)),
            ClarifaiInput.forImage(HONDA_IMAGE_URL2)
                .withConcepts(
                    ferrari23.withValue(false),
                    outdoors23
                ),
            ClarifaiInput.forImage(FERRARI_IMAGE_URL3)
                .withConcepts(
                    ferrari23.withValue(false),
                    outdoors23
                ),
            ClarifaiInput.forImage(ClarifaiImage.of(
                TOYOTA_IMAGE_URL))
                .withConcepts(
                    ferrari23.withValue(false),
                    outdoors23
                ),
            ClarifaiInput.forImage(
                HONDA_IMAGE_URL)
                .withConcepts(
                    ferrari23.withValue(false),
                    outdoors23
                )
        )
    );
  }

  @Retry
  @Test public void t01c_addInputWithMetadata() {
    assertSuccess(client.addInputs().plus(ClarifaiInput.forImage(KOTLIN_LOGO_IMAGE_FILE)
        .withID("inputWithMetadata")
        .withMetadata(new JSONObjectBuilder()
            .add("foo", "bar")
            .build()
        )
    ));
  }

  @Retry
  @Test public void t02_addConceptsToInput() {
    assertSuccess(client.mergeConceptsForInput("foo1")
        .plus(
            Concept.forID("concept2"),
            Concept.forID("concept3")
        )
    );
  }

  @Retry
  @Test public void t03_getAllInputs() {
    assertSuccess(client.getInputs());
  }

  @Retry
  @Test public void t04_getInputByID() {
    assertSuccess(client.getInputByID("foo1"));
  }

  @Retry
  @Test public void t05_deleteInput() {
    assertSuccess(client.deleteInput("foo1"));
  }

  @Retry
  @Test public void t06_getInputsStatus() {
    assertSuccess(client.getInputsStatus());
  }

  @Retry
  @Test public void t07_getConcepts() {
    assertSuccess(client.getConcepts());
  }

  @Retry
  @Test public void t08_getConceptByID() {
    assertSuccess(client.getConceptByID("concept2"));
  }

  @Retry
  @Test public void t09_searchConcepts() {
    assertSuccess(client.searchConcepts("conc*"));
  }

  @Retry
  @Test public void t09b_searchConcepts_multi_language() {
    assertSuccess(client.searchConcepts("狗*").withLanguage("zh")); // "zh" = Chinese
  }

  @Retry
  @Test public void t10_getAllModels() {
    assertSuccess(client.getModels());
  }

  @Retry
  @Test public void t11_deleteAllModels() {
    assertSuccess(client.deleteAllModels());
  }

  @Retry
  @Test public void t12a_createModel() {
    assertSuccess(client.createModel(getModelID())
        .withOutputInfo(ConceptOutputInfo.forConcepts(
            Concept.forID("ferrari23")
        ))
    );
  }


  @Retry
  @Test public void t13_getModelByID() {
    assertSuccess(client.getModelByID(getModelID()));
  }

  @Retry
  @Test public void t14a_addConceptsToModel() {
    assertSuccess(client.modifyModel(getModelID())
        .withConcepts(Action.MERGE, Concept.forID("outdoors23"))
    );
  }

  @Retry
  @Test public void t14b_addConceptsToModel_00() {
    assertSuccess(client.getModelByID(getModelID()).executeSync().get().asConceptModel()
        .modify().withConcepts(Action.MERGE, Concept.forID("outdoors23"))
    );
  }

  @Retry
  @Test public void t14c_addConceptsToModel_multi_lang() {
    assertSuccess(client.getModelByID(getModelID()).executeSync().get().asConceptModel()
        .modify().withConcepts(Action.MERGE, Concept.forID("outdoors23")).withLanguage("zh"));
  }

  @Retry
  @Test public void t15_trainModel() {
    assertSuccess(client.addInputs()
        .plus(ClarifaiInput.forImage(PENGUIN_IMAGE_URL)
            .withConcepts(Concept.forID("outdoors23"))
        )
        .allowDuplicateURLs(true)
    );
    assertSuccess(client.trainModel(getModelID()));
    retryAndTimeout(2, TimeUnit.MINUTES, () -> {
      final ModelVersion version = assertSuccess(client.getModelByID(getModelID())).modelVersion();
      assertNotNull(version);
      final ModelTrainingStatus status = version.status();
      if (!status.isTerminalEvent()) {
        return false;
      }
      if (status == ModelTrainingStatus.TRAINED) {
        return true;
      }
      fail("Version had error while training: " + version.status());
      return false;
    });
  }

  @Retry
  @Test public void t16a_predictWithModel() {
    assertSuccess(client.predict(client.getDefaultModels().generalModel().id())
        .withInputs(ClarifaiInput.forImage(KOTLIN_LOGO_IMAGE_FILE))
    );
  }

  @Retry
  @Test public void t16b_predictWithModel_00() {
    assertSuccess(client.getDefaultModels().generalModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(METRO_NORTH_IMAGE_URL)
            .withCrop(Crop.create()
                .top(0.1F)
                .bottom(0.8F)
            )
        )));
  }

  @Retry
  @Test public void t16c_predictBatchWithModel_01() {
    List<ClarifaiInput> inputs = new ArrayList<>();
    inputs.add(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL).withID("myID1"));
    inputs.add(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL).withID("myID2"));
    PredictRequest<Concept> request = client.getDefaultModels().generalModel().predict()
        .withInputs(inputs);
    assertSuccess(request);
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = request.executeSync();
    assertTrue(response.isSuccessful());
  }

  @Retry
  @Test public void t16d_predictBatchBase64WithModel() {
    List<ClarifaiInput> inputs = new ArrayList<>();
    inputs.add(ClarifaiInput.forImage(KOTLIN_LOGO_IMAGE_FILE).withID("myID1"));
    inputs.add(ClarifaiInput.forImage(KOTLIN_LOGO_IMAGE_FILE).withID("myID2"));
    PredictRequest<Concept> request = client.getDefaultModels().generalModel().predict()
        .withInputs(inputs);
    assertSuccess(request);
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = request.executeSync();
    assertTrue(response.isSuccessful());
  }

  @Retry
  @Test public void t16f_predictWithModel_multi_lang() {
    assertSuccess(client.predict(client.getDefaultModels().generalModel().id())
        .withInputs(ClarifaiInput.forImage(KOTLIN_LOGO_IMAGE_FILE))
        .withLanguage("zh")
    );
  }

  @Retry
  @Test public void t17a_searchInputsWithModel() {
    assertSuccess(client.searchInputs(
        SearchClause.matchImageURL(ClarifaiImage.of(METRO_NORTH_IMAGE_URL))
    ));
  }

  @Retry
  @Test public void t17b_searchInputsWithModel_complexSearch() {
    assertSuccess(
        client.searchInputs(matchConcept(Concept.forID("outdoors23").withValue(true)))
            .and(SearchClause.matchImageURL(ClarifaiImage.of(METRO_NORTH_IMAGE_URL)))
            .build()
    );
  }

  @Retry
  @Test public void t17c_searchInputsWithModel_metadata() {
    final List<SearchHit> hits = assertSuccess(
        client.searchInputs(SearchClause.matchMetadata(new JSONObjectBuilder().add("foo", "bar").build()))
    );
    final ClarifaiInput hit = hits.stream()
        .filter(someHit -> "inputWithMetadata".equals(someHit.input().id()))
        .findFirst()
        .orElseThrow(() -> new AssertionError(""))
        .input();
    assertEquals("inputWithMetadata", hit.id());
    assertEquals(new JSONObjectBuilder().add("foo", "bar").build(), hit.metadata());
  }

  @Retry
  @Test public void t17d_searchInputsWithModel_multi_language() {
    assertSuccess(client.searchInputs(
        SearchClause.matchImageURL(ClarifaiImage.of(METRO_NORTH_IMAGE_URL))).withLanguage("zh"));
  }

  @Test public void t17e_searchInputsWithModel_geo() {
    assertSuccess(client.addInputs().plus(
        ClarifaiInput.forImage(ClarifaiImage.of(METRO_NORTH_IMAGE_URL))
            .withGeo(PointF.at(90F, 23F))
    ));
    assertSuccess(
        client.searchInputs(matchConcept(Concept.forID("outdoors23").withValue(true)))
            .and(SearchClause.matchImageURL(ClarifaiImage.of(METRO_NORTH_IMAGE_URL)))
            .and(SearchClause.matchGeo(PointF.at(90F, 23F), Radius.of(5, Radius.Unit.MILE)))
            .build()
    );
  }

  @Retry
  @Test public void t18_testGeo() {
    {
        final List<SearchHit> hitsBeforeAdding = assertSuccess(
        client.searchInputs(SearchClause.matchGeo(PointF.at(59F, 29.75F), Radius.of(500, Radius.Unit.MILE)))
            );
    assertEquals(0, hitsBeforeAdding.size());
    }
    assertSuccess(client.addInputs().plus(
        ClarifaiInput.forImage(KOTLIN_LOGO_IMAGE_FILE)
            .withGeo(PointF.at(60F, 29.75F))
        ));
    {
        final List<SearchHit> hitsAfterAdding = assertSuccess(
        client.searchInputs(SearchClause.matchGeo(PointF.at(59F, 29.75F), Radius.of(500, Radius.Unit.MILE)))
            );
    assertEquals(1, hitsAfterAdding.size());
    }
    {
        final List<SearchHit> hits = assertSuccess(
        client.searchInputs(SearchClause.matchGeo(PointF.at(3F, 0F), PointF.at(70, 30F)))
            );
    assertEquals(1, hits.size());
    }
    }

  @Retry
  @Test public void t19_testBatch_partialFailure() {
    List<ClarifaiInput> batch = new ArrayList<>();
    batch.add(ClarifaiInput.forImage(
        FERRARI_IMAGE_URL));
    batch.add(ClarifaiInput.forImage(
        FERRARI_IMAGE_URL2));
    batch.add(ClarifaiInput.forImage("https://this_should_fail.jpg"));
    ClarifaiResponse<List<ClarifaiOutput<Concept>>> response = client.getDefaultModels().generalModel().predict()
        .withInputs(batch).executeSync();
    assertTrue(response.isMixedSuccess());
    assertNotNull(response.get());
    List<ClarifaiOutput<Concept>> concepts = response.get();
    assertEquals(concepts.get(2).status().statusCode(), 30002);
  }

  @Retry
  @Test public void t20_testDemographicsModel() {
    ClarifaiResponse<List<ClarifaiOutput<Region>>> faceDetects = client.getDefaultModels().demographicsModel().predict()
        .withInputs(ClarifaiInput.forImage(STREETBAND_IMAGE_URL))
        .executeSync();
    Assert.assertNotNull(faceDetects.get().get(0).data().get(0).crop());
    Assert.assertNotNull(faceDetects.get().get(0).data().get(0).ageAppearances());
    Assert.assertNotNull(faceDetects.get().get(0).data().get(0).genderAppearances());
    Assert.assertNotNull(faceDetects.get().get(0).data().get(0).multiculturalAppearances());
  }

  @Retry
  @Test public void t21_testApparelModel() {
    assertSuccess(client.predict(client.getDefaultModels().apparelModel().id())
        .withInputs(ClarifaiInput.forImage(FAMILY_IMAGE_URL))
    );
  }

  @Retry
  @Test public void t22_testFocusModel() {
    ClarifaiResponse<List<ClarifaiOutput<Focus>>> focii = client.getDefaultModels().focusModel().predict()
        .withInputs(ClarifaiInput.forImage(STREETBAND_IMAGE_URL))
        .executeSync();
    Assert.assertNotNull(focii.get());
    Assert.assertNotNull(focii.get().get(0));
    Assert.assertNotNull(focii.get().get(0).data());
    Assert.assertNotNull(focii.get().get(0).data().get(0));
    Assert.assertNotNull(focii.get().get(0).data().get(0).crop());
  }

  @Retry
  @Test public void t23_testgeneralEmbedModel() {
    ClarifaiResponse<List<ClarifaiOutput<Embedding>>> embeddings = client.getDefaultModels().generalEmbeddingModel()
        .predict()
        .withInputs(ClarifaiInput.forImage(STREETBAND_IMAGE_URL))
        .executeSync();
    Assert.assertNotNull(embeddings.get());
    Assert.assertNotNull(embeddings.get().get(0));
    Assert.assertNotNull(embeddings.get().get(0).data());
    Assert.assertNotNull(embeddings.get().get(0).data().get(0));
    Assert.assertNotNull(embeddings.get().get(0).data().get(0).embedding());
  }

  @Retry
  @Test public void t24_testLogoModel() {
    ClarifaiResponse<List<ClarifaiOutput<Logo>>> logos = client.getDefaultModels().logoModel().predict()
        .withInputs(ClarifaiInput.forImage(LOGO_IMAGE_URL))
        .executeSync();
    Assert.assertNotNull(logos.get());
    Assert.assertNotNull(logos.get().get(0));
    Assert.assertNotNull(logos.get().get(0).data());
  }

  @Retry
  @Test public void t25_testColorModel() {
    ClarifaiResponse<List<ClarifaiOutput<Color>>> colors = client.getDefaultModels().colorModel().predict()
        .withInputs(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL))
        .executeSync();
    Assert.assertNotNull(colors.get());
    Assert.assertNotNull(colors.get().get(0));
    Assert.assertNotNull(colors.get().get(0).data());
    Assert.assertNotNull(colors.get().get(0).data().get(0));
    Assert.assertNotNull(colors.get().get(0).data().get(0).hex());
    Assert.assertNotNull(colors.get().get(0).data().get(0).webSafeHex());
    Assert.assertNotNull(colors.get().get(0).data().get(0).webSafeColorName());
  }

  @Retry
  @Test public void t26_testGeneralVideoModel() {
    ClarifaiResponse<List<ClarifaiOutput<Frame>>> frames = client.getDefaultModels().generalVideoModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.ofVideo(CONAN_GIF_URL)))
        .executeSync();
    Assert.assertNotNull(frames.get());
    Assert.assertNotNull(frames.get().get(0));
    Assert.assertNotNull(frames.get().get(0).data());
    Assert.assertNotNull(frames.get().get(0).data().get(0));
    Assert.assertNotNull(frames.get().get(0).data().get(0).index());
    Assert.assertNotNull(frames.get().get(0).data().get(0).time());
    Assert.assertNotNull(frames.get().get(0).data().get(0).concepts());
  }

  @Retry
  @Test public void t26_testFoodVideoModel() {
    ClarifaiResponse<List<ClarifaiOutput<Frame>>> frames = client.getDefaultModels().foodVideoModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.ofVideo(CONAN_GIF_URL)))
        .executeSync();
    Assert.assertNotNull(frames.get());
    Assert.assertNotNull(frames.get().get(0));
    Assert.assertNotNull(frames.get().get(0).data());
    Assert.assertNotNull(frames.get().get(0).data().get(0));
    Assert.assertNotNull(frames.get().get(0).data().get(0).index());
    Assert.assertNotNull(frames.get().get(0).data().get(0).time());
    Assert.assertNotNull(frames.get().get(0).data().get(0).concepts());
  }

  @Retry
  @Test public void t26_testTravelVideoModel() {
    ClarifaiResponse<List<ClarifaiOutput<Frame>>> frames = client.getDefaultModels().travelVideoModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.ofVideo(CONAN_GIF_URL)))
        .executeSync();
    Assert.assertNotNull(frames.get());
    Assert.assertNotNull(frames.get().get(0));
    Assert.assertNotNull(frames.get().get(0).data());
    Assert.assertNotNull(frames.get().get(0).data().get(0));
    Assert.assertNotNull(frames.get().get(0).data().get(0).index());
    Assert.assertNotNull(frames.get().get(0).data().get(0).time());
    Assert.assertNotNull(frames.get().get(0).data().get(0).concepts());
  }

  @Retry
  @Test public void t26_testNSFWVideoModel() {
    ClarifaiResponse<List<ClarifaiOutput<Frame>>> frames = client.getDefaultModels().nsfwVideoModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.ofVideo(CONAN_GIF_URL)))
        .executeSync();
    Assert.assertNotNull(frames.get());
    Assert.assertNotNull(frames.get().get(0));
    Assert.assertNotNull(frames.get().get(0).data());
    Assert.assertNotNull(frames.get().get(0).data().get(0));
    Assert.assertNotNull(frames.get().get(0).data().get(0).index());
    Assert.assertNotNull(frames.get().get(0).data().get(0).time());
    Assert.assertNotNull(frames.get().get(0).data().get(0).concepts());
  }

  @Retry
  @Test public void t26_testWeddingVideoModel() {
    ClarifaiResponse<List<ClarifaiOutput<Frame>>> frames = client.getDefaultModels().weddingVideoModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.ofVideo(CONAN_GIF_URL)))
        .executeSync();
    Assert.assertNotNull(frames.get());
    Assert.assertNotNull(frames.get().get(0));
    Assert.assertNotNull(frames.get().get(0).data());
    Assert.assertNotNull(frames.get().get(0).data().get(0));
    Assert.assertNotNull(frames.get().get(0).data().get(0).index());
    Assert.assertNotNull(frames.get().get(0).data().get(0).time());
    Assert.assertNotNull(frames.get().get(0).data().get(0).concepts());
  }

  @Retry
  @Test public void t26_testApparelVideoModel() {
    ClarifaiResponse<List<ClarifaiOutput<Frame>>> frames = client.getDefaultModels().apparelVideoModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.ofVideo(CONAN_GIF_URL)))
        .executeSync();
    Assert.assertNotNull(frames.get());
    Assert.assertNotNull(frames.get().get(0));
    Assert.assertNotNull(frames.get().get(0).data());
    Assert.assertNotNull(frames.get().get(0).data().get(0));
    Assert.assertNotNull(frames.get().get(0).data().get(0).index());
    Assert.assertNotNull(frames.get().get(0).data().get(0).time());
    Assert.assertNotNull(frames.get().get(0).data().get(0).concepts());
  }

  @Test public void errorsExposedToUser() {
    final ClarifaiResponse<ConceptModel> response = client.getDefaultModels().generalModel().modify()
        .withConcepts(Action.MERGE, Concept.forID("concept2"))
        .executeSync();
    if (response.isSuccessful()) {
      fail("You shouldn't be able to add concepts to the built-in general model");
    }
    logger.debug(response.getStatus().toString());
  }

  @Retry
  @Test public void testDeleteBatch() {
    assertSuccess(client.addInputs().plus(
        ClarifaiInput.forImage(KOTLIN_LOGO_IMAGE_FILE).withID("kotlin"),
        ClarifaiInput.forImage(METRO_NORTH_IMAGE_FILE).withID("train")
    ));
    sleep(5000);
    assertSuccess(client.deleteInputsBatch().plus("kotlin", "train"));
  }

  @Test public void testSyncNetworkExceptions() throws ExecutionException, InterruptedException {
    final ClarifaiResponse<List<Model<?>>> badResponse = new ClarifaiBuilder(appID, appSecret)
        .baseURL(baseURL)
        .client(new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(chain -> {
              // Don't mess with the token request that happens behind the scenes
              if (chain.request().url().pathSegments().contains("token")) {
                return chain.proceed(chain.request());
              }
              // Change the port on our actual requests so that we get IOExceptions
              return chain.proceed(chain.request().newBuilder()
                  .url(chain.request().url().newBuilder().port(383).build())
                  .build()
              );
            })
            .build()
        )
        .buildSync()
        .getModels()
        .getPage(1)
        .executeSync();
    if (badResponse.isSuccessful()) {
      fail("this response used a bad port, it should not have been successful. Response: " + badResponse.get());
    }
    final ClarifaiStatus details = badResponse.getStatus();
    assertTrue(details.networkErrorOccurred());
    logger.debug(details.errorDetails());
  }

  @Test public void testBuildClientAsync() throws InterruptedException, ExecutionException {
    final Future<ClarifaiClient> futureClient = new ClarifaiBuilder(appID, appSecret)
        .baseURL(baseURL)
        .build();
    retryAndTimeout(30, TimeUnit.SECONDS, futureClient::isDone);
    final ClarifaiClient client = futureClient.get();
    logger.debug(client.getToken().toString());
  }

  @Test(expected = ClarifaiException.class)
  public void testClosingClientWorks() {
    final ClarifaiClient toBeClosed = new ClarifaiBuilder(appID, appSecret).buildSync();
    toBeClosed.close();
    toBeClosed.getModels().getPage(1).executeSync();
  }

  @Retry
  @Test
  public void testCreateModel() {
    final String modelID = "creatingModel" + System.nanoTime();
    assertSuccess(client.createModel(modelID).withOutputInfo(
        ConceptOutputInfo.forConcepts(
            Concept.forID("foo")
        )
    ));
  }

  @Retry
  @Test
  public void testCreateModel_multi_lang() {
    final String modelID = "creatingModel" + System.nanoTime();
    assertSuccess(client.createModel(modelID).withOutputInfo(
        ConceptOutputInfo.forConcepts(
            Concept.forID("foo")
        ).withLanguage("zh")
    ));
  }

  @Retry
  @Test
  public void testModifyModel() {
    final String modelID = "modifyingModel" + System.nanoTime();
    assertSuccess(client.createModel(modelID).withOutputInfo(
        ConceptOutputInfo.forConcepts(
            Concept.forID("foo")
        )
    ));
    assertSuccess(client.modifyModel(modelID)
        .withConcepts(Action.OVERWRITE, Concept.forID("bar"))
    );
    final List<Concept> concepts =
        assertSuccess(client.getModelByID(modelID)).asConceptModel().outputInfo().concepts();
    assertEquals(1, concepts.size());
    assertEquals("bar", concepts.get(0).name());
  }

  @Retry
  @Test
  public void testMergeMetadata() {
    final String inputID = assertSuccess(client.addInputs()
        .allowDuplicateURLs(true)
        .plus(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL)
        )
    ).get(0).id();
    assertNotNull(inputID);
    final JsonObject newMetadata = assertSuccess(
        client.addMetadataForInput(
            inputID,
            new JSONObjectBuilder()
                .add("foo", "bar")
                .build()
        )
    ).metadata();
    assertEquals(new JSONObjectBuilder().add("foo", "bar").build(), newMetadata);
  }

  @Test public void testMetadataDoesNotAllowNullDictionaryValues() {
    thrown.expect(IllegalArgumentException.class);
    client.addInputs()
        .allowDuplicateURLs(true)
        .plus(ClarifaiInput.forImage(METRO_NORTH_IMAGE_URL)
            // Will throw IAE because we have a null value
            .withMetadata(new JSONObjectBuilder().add("foo", JsonNull.INSTANCE).build())
        )
        .executeSync();
  }

  /////////////////

  // Workaround since we can't delete models right now, so we'll make a new model every time that is different every
  // time we run the app
  @NotNull private static String getModelID() {
    return "mod1ID" + startTime;
  }
}
