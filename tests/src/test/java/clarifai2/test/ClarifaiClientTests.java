package clarifai2.test;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.exception.ClarifaiException;
import org.jetbrains.annotations.Nullable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ClarifaiClientTests  extends BaseClarifaiAPITest {
  @Nullable final String appID = EnvVar.CLARIFAI_APP_ID.value();
  @Nullable final String appSecret = EnvVar.CLARIFAI_APP_SECRET.value();

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test public void testIncorrectAppID() {
    if (appSecret != null) {
      exception.expect(ClarifaiException.class);
      new ClarifaiBuilder("fewjiofjewiofjewiojwejfewoi", appSecret).buildSync();
    }
  }

  @Test public void testIncorrectAppSecret() {
    if (appID != null) {
      exception.expect(ClarifaiException.class);
      new ClarifaiBuilder(appID, "fjweiojf2983fj203jf23ofj23ofj").buildSync();
    }
  }
}
