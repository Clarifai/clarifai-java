package clarifai2.test;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.exception.ClarifaiException;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

public abstract class BaseClarifaiAPITest {

  @NotNull static final String METRO_NORTH_IMAGE_URL = "https://samples.clarifai.com/metro-north.jpg";
  @NotNull static final File METRO_NORTH_IMAGE_FILE = new File("../tests/assets/metro-north.jpg");
  @NotNull static final File KOTLIN_LOGO_IMAGE_FILE = new File("../tests/assets/image.png");

  @NotNull protected final String appID = EnvVar.CLARIFAI_APP_ID.value();
  @NotNull protected final String appSecret = EnvVar.CLARIFAI_APP_SECRET.value();
  @NotNull protected final String baseURL = EnvVar.CLARIFAI_API_BASE.value();

  @NotNull final ClarifaiClient client = new ClarifaiBuilder(appID, appSecret)
      .baseURL(baseURL)
      .client(new OkHttpClient.Builder()
          .connectTimeout(60, TimeUnit.SECONDS)
          .readTimeout(60, TimeUnit.SECONDS)
          .writeTimeout(60, TimeUnit.SECONDS)
          .addInterceptor(new HttpLoggingInterceptor(System.out::println).setLevel(HttpLoggingInterceptor.Level.BODY))
          .build()
      )
      .buildSync();

  @Test(expected = ClarifaiException.class)
  public void testIncorrectAppID() {
    new ClarifaiBuilder("fewjiofjewiofjewiojwejfewoi", appSecret).buildSync();
  }

  @Test(expected = ClarifaiException.class)
  public void testIncorrectAppSecret() {
    new ClarifaiBuilder(appID, "fjweiojf2983fj203jf23ofj23ofj").buildSync();
  }

  static <T> T assertSuccess(@NotNull ClarifaiPaginatedRequest<T> request) {
    return assertSuccess(request.getPage(1));
  }

  static <T> T assertSuccess(@NotNull ClarifaiRequest<T> request) {
    final T result = request.executeSync().get();
    System.out.println(result);
    return result;
  }
}
