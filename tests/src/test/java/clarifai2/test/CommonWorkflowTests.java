package clarifai2.test;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.ClarifaiUtil;
import clarifai2.api.request.input.SearchClause;
import clarifai2.dto.ClarifaiStatus;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.input.image.Crop;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static clarifai2.api.request.input.SearchClause.matchConcept;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommonWorkflowTests extends BaseClarifaiAPITest {

  private static long startTime;

  @BeforeClass
  public static void recordTime() {
    startTime = System.nanoTime();
  }

  @Test public void t00_deleteAllInputs() {
    assertSuccess(client.deleteAllInputs());
  }

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

  @Test public void t02_addConceptsToInput() {
    assertSuccess(client.addConceptsToInput("foo1")
        .plus(
            Concept.forID("concept2"),
            Concept.forID("concept3")
        )
    );
  }

  @Test public void t03_getAllInputs() {
    assertSuccess(client.getInputs());
  }

  @Test public void t04_getInputByID() {
    assertSuccess(client.getInputByID("foo1"));
  }

  @Test public void t05_deleteInputs() {
    assertSuccess(client.deleteInputs()
        .delete("foo1")
    );
  }

  @Test public void t06_getInputsStatus() {
    assertSuccess(client.getInputsStatus());
  }

  @Test public void t07_getConcepts() {
    assertSuccess(client.getConcepts());
  }

  @Test public void t08_getConceptByID() {
    assertSuccess(client.getConceptByID("concept2"));
  }

  @Test public void t09_searchConcepts() {
    assertSuccess(client.searchConcepts("conc*"));
  }

  @Test public void t10_getAllModels() {
    assertSuccess(client.getModels());
  }

  @Test public void t11_deleteAllModels() {
    assertSuccess(client.deleteAllModels());
  }

  @Test public void t12a_createModel() {
    assertSuccess(client.createModel(getModelID())
        .withOutputInfo(ConceptOutputInfo.forConcepts(
            Concept.forID("ferrari23")
        ))
    );
  }


  @Test public void t13_getModelByID() {
    assertSuccess(client.getModelByID(getModelID()));
  }

  @Test public void t14a_addConceptsToModel() {
    assertSuccess(client.addConceptsToModel(getModelID())
        .plus(Concept.forID("outdoors23"))
    );
  }

  @Test public void t14b_addConceptsToModel_OO() {
    assertSuccess(client.getModelByID(getModelID()).executeSync().get().asConceptModel()
        .addConcepts()
        .plus(Concept.forID("outdoors23"))
    );
  }

  @Test public void t15_trainModel() {
    assertSuccess(ClarifaiUtil.trainAndAwaitCompletion(client, getModelID()));
  }


  @Test public void t16a_predictWithModel() {
    assertSuccess(client.predict(client.getDefaultModels().generalModel().id())
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(KOTLIN_LOGO_IMAGE_FILE)))
    );
  }

  @Test public void t16b_predictWithModel_OO() {
    assertSuccess(client.getDefaultModels().generalModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(METRO_NORTH_IMAGE_URL)
            .withCrop(Crop.create()
                .top(0.1F)
                .bottom(0.8F)
            )
        )));
  }

  @Test public void t17a_searchInputsWithModel() {
    assertSuccess(client.searchInputs(
        SearchClause.matchImageURL(ClarifaiImage.of(METRO_NORTH_IMAGE_URL))
    ));
  }

  @Test public void t17b_searchInputsWithModel_complexSearch() {
    assertSuccess(
        client.searchInputs(matchConcept(Concept.forID("outdoors23").withValue(true)))
            .and(SearchClause.matchImageURL(ClarifaiImage.of(METRO_NORTH_IMAGE_URL)))
            .build()
    );
  }

  @Test public void errorsExposedToUser() {
    final ClarifaiResponse<ConceptModel> response = client.getDefaultModels().generalModel().addConcepts()
        .plus(Concept.forID("concept2"))
        .executeSync();
    if (response.isSuccessful()) {
      Assert.fail("You shouldn't be able to add concepts to the built-in general model");
    }
    System.out.println(response.getStatus());
  }

  @Test public void testAsyncUnsuccessfulWorks() {
    final CountDownLatch lock = new CountDownLatch(1);

    client.getModelByID("myID").executeAsync(
        (model) -> Assert.fail("The model 'myID' shouldn't exist"),
        (code) -> {
          // We should get here, because there's no "myID" model
          lock.countDown();
        }
    );
    try {
      if (!lock.await(60, TimeUnit.SECONDS)) {
        Assert.fail("testAsync timed out");
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
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
    System.out.println(details.errorDetails());
  }

  @Test public void testBuildClientAsync() throws InterruptedException, ExecutionException {
    final Future<ClarifaiClient> futureClient = new ClarifaiBuilder(appID, appSecret)
        .baseURL(baseURL)
        .build();
    while (!futureClient.isDone()) {
      Thread.sleep(100);
    }
    final ClarifaiClient client = futureClient.get();
    System.out.println(client.getToken());
  }

  /////////////////

  // Workaround since we can't delete models right now, so we'll make a new model every time that is different every time we run the app
  @NotNull private static String getModelID() {
    return "mod1ID" + startTime;
  }
}
