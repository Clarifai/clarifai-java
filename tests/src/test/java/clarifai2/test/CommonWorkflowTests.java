package clarifai2.test;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.SearchClause;
import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.SearchHit;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.input.image.Crop;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelTrainingStatus;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import clarifai2.exception.ClarifaiException;
import clarifai2.internal.InternalUtil;
import clarifai2.internal.JSONObjectBuilder;
import com.kevinmost.junit_retry_rule.Retry;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static clarifai2.api.request.input.SearchClause.matchConcept;
import static clarifai2.api.request.input.SearchClause.matchMetadata;


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
            ClarifaiInput.forImage(ClarifaiImage.of("http://i.imgur.com/HEoT5xR.png"))
                .withConcepts(
                    ferrari23.withValue(true)
                ),
            ClarifaiInput.forImage(ClarifaiImage.of("http://i.imgur.com/It5JRaj.jpg"))
                .withConcepts(
                    ferrari23.withValue(true),
                    outdoors23.withValue(false)
                ),
            ClarifaiInput.forImage(ClarifaiImage.of("http://i.imgur.com/9Knw6RS.jpg"))
                .withConcepts(
                    ferrari23.withValue(true),
                    outdoors23
                ),
            ClarifaiInput.forImage(ClarifaiImage.of("http://i.imgur.com/GeMQsiQ.jpg"))
                .withConcepts(
                    ferrari23.withValue(false),
                    outdoors23
                ),
            ClarifaiInput.forImage(ClarifaiImage.of("http://i.imgur.com/eXCE9mf.jpg"))
                .withConcepts(
                    ferrari23.withValue(false),
                    outdoors23
                ),
            ClarifaiInput.forImage(ClarifaiImage.of("http://i.imgur.com/EnrVc0B.jpg"))
                .withConcepts(
                    ferrari23.withValue(false),
                    outdoors23
                ),
            ClarifaiInput.forImage(ClarifaiImage.of("http://s7d1.scene7.com/is/image/BedBathandBeyond/56879143899890p"))
                .withConcepts(
                    ferrari23.withValue(false),
                    outdoors23
                )
        )
    );
  }

  @Retry
  @Test public void t01c_addInputWithMetadata() {
    assertSuccess(client.addInputs().plus(ClarifaiInput.forImage(ClarifaiImage.of(KOTLIN_LOGO_IMAGE_FILE))
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
    assertSuccess(client.mergeConceptsForModel(getModelID())
        .plus(Concept.forID("outdoors23"))
    );
  }

  @Retry
  @Test public void t14b_addConceptsToModel_OO() {
    assertSuccess(client.getModelByID(getModelID()).executeSync().get().asConceptModel()
        .mergeConcepts()
        .plus(Concept.forID("outdoors23"))
    );
  }

  @Retry
  @Test public void t15_trainModel() {
    assertSuccess(client.addInputs()
        .plus(ClarifaiInput.forImage(ClarifaiImage.of("http://i.imgur.com/9Knw6RS.jpg"))
            .withConcepts(Concept.forID("outdoors23"))
        )
        .allowDuplicateURLs(true)
    );
    assertSuccess(client.trainModel(getModelID()));
    while (true) {
      final ModelVersion version = assertSuccess(client.getModelByID(getModelID())).modelVersion();
      if (version == null) {
        Assert.fail("Model version can't be null");
      }
      final ModelTrainingStatus status = version.status();
      if (!status.isTerminalEvent()) {
        InternalUtil.sleep(200);
        continue;
      }
      if (status == ModelTrainingStatus.TRAINED) {
        return;
      }
      Assert.fail("Version had error while training: " + version.status());
    }
  }

  @Retry
  @Test public void t16a_predictWithModel() {
    assertSuccess(client.predict(client.getDefaultModels().generalModel().id())
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(KOTLIN_LOGO_IMAGE_FILE)))
    );
  }

  @Retry
  @Test public void t16b_predictWithModel_OO() {
    assertSuccess(client.getDefaultModels().generalModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(METRO_NORTH_IMAGE_URL)
            .withCrop(Crop.create()
                .top(0.1F)
                .bottom(0.8F)
            )
        )));
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
        client.searchInputs(matchMetadata(new JSONObjectBuilder().add("foo", "bar").build()))
    );
    final ClarifaiInput hit = hits.stream()
        .filter(someHit -> "inputWithMetadata".equals(someHit.input().id()))
        .findFirst()
        .orElseThrow(() -> new AssertionError(""))
        .input();
    Assert.assertEquals("inputWithMetadata", hit.id());
    Assert.assertEquals(new JSONObjectBuilder().add("foo", "bar").build(), hit.metadata());
  }

  @Test public void errorsExposedToUser() {
    final ClarifaiResponse<ConceptModel> response = client.getDefaultModels().generalModel().mergeConcepts()
        .plus(Concept.forID("concept2"))
        .executeSync();
    if (response.isSuccessful()) {
      Assert.fail("You shouldn't be able to add concepts to the built-in general model");
    }
    logger.info(response.getStatus().toString());
  }

  @Retry
  @Test public void testDeleteBatch() {
    assertSuccess(client.addInputs().plus(
        ClarifaiInput.forImage(ClarifaiImage.of(KOTLIN_LOGO_IMAGE_FILE)).withID("kotlin"),
        ClarifaiInput.forImage(ClarifaiImage.of(METRO_NORTH_IMAGE_FILE)).withID("train")
    ));
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
      Assert.fail("this response used a bad port, it should not have been successful. Response: " + badResponse.get());
    }
    final ClarifaiStatus details = badResponse.getStatus();
    Assert.assertTrue(details.networkErrorOccurred());
    logger.info(details.errorDetails());
  }

  @Test public void testBuildClientAsync() throws InterruptedException, ExecutionException {
    final Future<ClarifaiClient> futureClient = new ClarifaiBuilder(appID, appSecret)
        .baseURL(baseURL)
        .build();
    while (!futureClient.isDone()) {
      InternalUtil.sleep(100);
    }
    final ClarifaiClient client = futureClient.get();
    logger.info(client.getToken().toString());
  }

  @Test(expected = ClarifaiException.class)
  public void testClosingClientWorks() {
    final ClarifaiClient toBeClosed = new ClarifaiBuilder(appID, appSecret).buildSync();
    toBeClosed.close();
    toBeClosed.getModels().getPage(1).executeSync();
  }

  @Retry
  @Test
  public void testModifyModel() {
    final String modelName = "modifyingModel" + System.nanoTime();
    assertSuccess(client.createModel(modelName).withOutputInfo(
        ConceptOutputInfo.forConcepts(
            Concept.forID("foo")
        )
    ));
    assertSuccess(client.modifyModel(modelName).withOutputInfo(
        ConceptOutputInfo.forConcepts(
            Concept.forID("bar")
        )
    ));
    final List<Concept> concepts =
        assertSuccess(client.getModelByID(modelName)).asConceptModel().outputInfo().concepts();
    Assert.assertEquals(1, concepts.size());
    Assert.assertEquals("bar", concepts.get(0).name());
  }

  /////////////////

  // Workaround since we can't delete models right now, so we'll make a new model every time that is different every time we run the app
  @NotNull private static String getModelID() {
    return "mod1ID" + startTime;
  }
}
