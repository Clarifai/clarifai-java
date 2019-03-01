package clarifai2.integration_tests;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.exception.DeprecationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ClarifaiClientIntTests extends BaseIntTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test public void testAppIdSecretShouldBeDeprecated() {
    exception.expect(DeprecationException.class);
    new ClarifaiBuilder("some-app-id", "some-app-secret").buildSync();
  }
}
