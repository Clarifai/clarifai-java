package clarifai2.integration_tests;

import clarifai2.Func0;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.internal.InternalUtil;
import com.kevinmost.junit_retry_rule.RetryRule;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public abstract class BaseIntTest {

  @NotNull static final String METRO_NORTH_IMAGE_URL = "https://samples.clarifai.com/metro-north.jpg";
  @NotNull static final String CONAN_GIF_URL = "https://s3.amazonaws.com/samples.clarifai.com/3o6gb3kkXfLvdKEZs4.gif";
  @NotNull static final String LOGO_IMAGE_URL =
      "https://developer.clarifai.com/static/images/model-samples/logo-002.jpg";
  @NotNull static final String STREETBAND_IMAGE_URL = "https://samples.clarifai.com/demographics.jpg";
  @NotNull static final String FAMILY_IMAGE_URL = "https://samples.clarifai.com/family.jpg";
  @NotNull static final String FERRARI_IMAGE_URL =
      "https://s3.amazonaws.com/clarifai-img/5e/00/cb/8476bca5632276903b28701736.png";
  @NotNull static final String FERRARI_IMAGE_URL2 =
      "https://s3.amazonaws.com/clarifai-img/00/c3/ad/78d5ae3b3f2a84fe2bfb69dc28.jpg";
  @NotNull static final String FERRARI_IMAGE_URL3 =
      "https://s3.amazonaws.com/clarifai-img/a3/05/dc/b142653346b98ed0a4998c157f.jpg";
  @NotNull static final String PENGUIN_IMAGE_URL = "https://samples.clarifai.com/penguin.bmp";
  @NotNull static final String HONDA_IMAGE_URL =
      "https://s3.amazonaws.com/clarifai-img/d4/89/e0/67f7f1622bf586c876875c3fc6.jpg";
  @NotNull static final String HONDA_IMAGE_URL2 =
      "https://s3.amazonaws.com/clarifai-img/cd/1d/05/8b9cd2d37560ef9f6c436debc6.jpg";
  @NotNull static final String TOYOTA_IMAGE_URL =
      "https://s3.amazonaws.com/clarifai-img/43/2a/89/163ade86b76b4ba8ec67d22e40.jpg";
  @NotNull static final String SUNGLASSES_IMAGE_URL =
      "https://clarifai.com/developer/static/images/model-samples/apparel-001.jpg";
  @NotNull static final String CELEBRITY_IMAGE_URL = "https://samples.clarifai.com/celebrity.jpeg";
  @NotNull static final String FOOD_IMAGE_URL =
      "https://developer.clarifai.com/static/images/model-samples/food-004.jpg";
  @NotNull static final File METRO_NORTH_IMAGE_FILE = new File("../tests/assets/metro-north.jpg");
  @NotNull static final File KOTLIN_LOGO_IMAGE_FILE = new File("../tests/assets/image.png");
  @NotNull static final File BEER_VIDEO_FILE = new File("../tests/assets/beer.mp4");
  @NotNull @Rule public final TestName testName = new TestName();
  @NotNull @Rule public final RetryRule retryRule = new RetryRule();
  @NotNull @Rule public final ExpectedException thrown = ExpectedException.none();
  @NotNull final Logger logger = LoggerFactory.getLogger(getClass());
  @NotNull final String apiKey = EnvVar.CLARIFAI_API_KEY.value();
  @NotNull final String baseURL = EnvVar.CLARIFAI_API_BASE.value();

  @NotNull final ClarifaiClient client = new ClarifaiBuilder(apiKey)
      .baseURL(baseURL)
      .client(new OkHttpClient.Builder()
          .connectTimeout(60, TimeUnit.SECONDS)
          .readTimeout(60, TimeUnit.SECONDS)
          .writeTimeout(60, TimeUnit.SECONDS)
          .addInterceptor(new HttpLoggingInterceptor(logger::info).setLevel(HttpLoggingInterceptor.Level.BASIC))
          .build()
      )
      .buildSync();

  @NotNull public ClarifaiClient makeClient(String apiKey) {
    return new ClarifaiBuilder(apiKey)
        .baseURL(baseURL)
        .client(new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor(logger::info).setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
        )
        .buildSync();
  }

  static void assertFailure(@NotNull ClarifaiRequest<?> request) {
    final ClarifaiResponse<?> response = request.executeSync();
    if (response.isSuccessful()) {
      Assert.fail("Response was not supposed to be successful! Response was " + response.get());
    }
  }

  static void retryAndTimeout(long time, @NotNull TimeUnit unit, @NotNull Func0<Boolean> op) {
    long startTime = System.currentTimeMillis();
    long timeToRetry = TimeUnit.MILLISECONDS.convert(time, unit);
    while (true) {
      if (System.currentTimeMillis() > startTime + timeToRetry) {
        throw new AssertionError("retried for " + timeToRetry + " milliseconds and still failed");
      }
      if (op.call()) {
        return;
      }
      InternalUtil.sleep(1000);
    }
  }

  static void waitForInputToDownload(@NotNull ClarifaiClient client, @NotNull String inputID) {
    // Need to wait until the input is processed for the search to work.
    retryAndTimeout(2, TimeUnit.MINUTES, () -> {
      // If the input has been downloaded successfully, quit the loop.
      final ClarifaiResponse<ClarifaiInput> r = client.getInputByID(inputID).executeSync();
      return r.isSuccessful() && r.get().status().statusCode() == 30000;
    });
  }

  @Before public void logTestNameBeginning() {
    logTestNameBlock("BEGIN TEST");
  }

  @After public void logTestNameEnd() {
    logTestNameBlock("END TEST");
  }

  private void logTestNameBlock(@NotNull String prefix) {
    logger.info("\n" +
        "#########################################################" + "\n" +
        "### " + prefix + ": " + testName.getMethodName() + "\n" +
        "#########################################################" + "\n"
    );
  }

  <T> T assertSuccess(@NotNull ClarifaiPaginatedRequest<T> request) {
    return assertSuccess(request.getPage(1));
  }

  <T> T assertSuccess(@NotNull ClarifaiRequest<T> request) {
    final T result = request.executeSync().get();
    logger.debug(result.toString());
    return result;
  }
}
