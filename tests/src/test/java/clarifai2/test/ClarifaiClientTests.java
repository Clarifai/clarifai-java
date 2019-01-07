package clarifai2.test;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.exception.DeprecationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ClarifaiClientTests  extends BaseClarifaiAPITest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test public void testAppIdSecretShouldBeDeprecated() {
    exception.expect(DeprecationException.class);
    new ClarifaiBuilder("some-app-id", "some-app-secret").buildSync();
  }
}
