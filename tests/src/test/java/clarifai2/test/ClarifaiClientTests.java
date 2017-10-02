package clarifai2.test;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.exception.ClarifaiException;
import org.junit.Test;

public class ClarifaiClientTests  extends BaseClarifaiAPITest {
  @Test(expected = ClarifaiException.class)
  public void testIncorrectAppID() {
    new ClarifaiBuilder("fewjiofjewiofjewiojwejfewoi", appSecret).buildSync();
  }

  @Test(expected = ClarifaiException.class)
  public void testIncorrectAppSecret() {
    new ClarifaiBuilder(appID, "fjweiojf2983fj203jf23ofj23ofj").buildSync();
  }
}
