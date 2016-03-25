package com.clarifai.api;

import static com.clarifai.api.TestUtils.loadResource;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.clarifai.api.RecognitionResult.StatusCode;
import com.clarifai.api.exception.ClarifaiBadRequestException;
import com.clarifai.api.exception.ClarifaiNotAuthorizedException;

/**
 * These tests run against the production Clarifai API server, but only if the
 * clarifai.appId and clarifai.appSecret system properties are set.
 */
public class ClarifaiClientServerTest {
  private static String appId;
  private static String appSecret;
  private ClarifaiClient clarifai;

  @BeforeClass public static void checkAppIdAndSecret() {
    appId = System.getProperty("clarifai.appId");
    appSecret = System.getProperty("clarifai.appSecret");
    if (appId == null || appSecret == null) {
      System.err.println(ClarifaiClientServerTest.class.getSimpleName() +
          ": Skipping tests because the clarifai.appId and clarifai.appSecret system\n" +
          "properties are not defined. You can enable this by setting them. For example:\n" +
          "  mvn test -DargLine=\"-Dclarifai.appId=YOUR_ID -Dclarifai.appSecret=YOUR_SECRET\"");
    }
  }

  @Rule public TestName name = new TestName();

  @Before public void setUp() {
    if (appId != null && appSecret != null) {
      clarifai = new ClarifaiClient(appId, appSecret);
    }
  }

  @Test public void testInfo() {
    if (shouldSkipTest()) return;

    InfoResult info = clarifai.getInfo();
    // Verify that we have something reasonable:
    assertThat(info.getMaxImageSize(), greaterThan(256));
    assertThat(info.getMaxImageSize(), lessThan(10000000));
    assertThat(info.getMaxBatchSize(), greaterThan(1));
    assertThat(info.getMaxBatchSize(), lessThan(10000000));
  }

  @Test public void testRecognizeSingle() {
    if (shouldSkipTest()) return;

    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(
        "http://www.clarifai.com/img/metro-north.jpg"));

    assertThat(results.size(), equalTo(1));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    assertThat(result.getDocId(), equalTo("31fdb2316ff87fb5d747554ba5267313"));
    assertThat(findTag(result.getTags(), "train"), notNullValue());
    assertThat(findTag(result.getTags(), "train").getProbability(), greaterThan(0.0));
    assertThat(findTag(result.getTags(), "train").getProbability(), lessThan(1.0));
    assertThat(result.getEmbedding(), nullValue());
  }

  @Test public void testRecognizeWithSelectClasses() {
    if (shouldSkipTest()) return;

    List<RecognitionResult> results = clarifai.recognizeTags(new RecognitionRequest(
	        "http://www.clarifai.com/img/metro-north.jpg").addTagForSelectClasses("cookie"));

    assertThat(results.size(), equalTo(1));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    assertThat(result.getDocId(), equalTo("31fdb2316ff87fb5d747554ba5267313"));
    assertThat(findTag(result.getTags(), "cookie"), notNullValue());
    assertThat(findTag(result.getTags(), "cookie").getProbability(), greaterThan(0.0));
    assertThat(findTag(result.getTags(), "cookie").getProbability(), lessThan(1.0));
    assertThat(result.getEmbedding(), nullValue());
  }

  @Test public void testRecognizeMultiple() throws IOException {
    if (shouldSkipTest()) return;

    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(
        loadResource("automobile.jpg"),
        "not an image".getBytes(),
        loadResource("sky.jpg"))
        .setIncludeEmbedding(true));

    assertThat(results.size(), equalTo(3));
    assertThat(results.get(0).getStatusCode(), equalTo(StatusCode.OK));
    assertThat(results.get(0).getDocId(), equalTo("7e32b2b93aa515c51c8e31f655f6dca4"));
    assertThat(results.get(0).getTags().size(), greaterThanOrEqualTo(5));
    assertThat(results.get(0).getEmbedding().length, greaterThanOrEqualTo(64));
    assertThat(findTag(results.get(0).getTags(), "vehicle"), notNullValue());

    assertThat(results.get(1).getStatusCode(), equalTo(StatusCode.CLIENT_ERROR));
    assertThat(results.get(1).getTags(), nullValue());
    assertThat(results.get(1).getEmbedding(), nullValue());

    assertThat(results.get(2).getStatusCode(), equalTo(StatusCode.OK));
    assertThat(results.get(2).getDocId(), equalTo("4c2a06b3d99c34e13f85597487e8ed6b"));
    assertThat(results.get(2).getTags().size(), greaterThanOrEqualTo(5));
    assertThat(results.get(2).getEmbedding().length, greaterThanOrEqualTo(64));
    assertThat(findTag(results.get(2).getTags(), "sky"), notNullValue());
  }

  @Test public void testRecognizeVideo() throws IOException {
    if (shouldSkipTest()) return;

    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(
        loadResource("automobile_sky.mp4"))
        .setIncludeEmbedding(true));

    assertThat(results.size(), equalTo(1));
    assertThat(results.get(0).getStatusCode(), equalTo(StatusCode.OK));
    assertThat(results.get(0).getDocId(), equalTo("834f820ebf36df0fac7d13454a236471"));
    assertThat(results.get(0).getVideoSegments().size(), equalTo(3));

    VideoSegment segment = results.get(0).getVideoSegments().get(0);
    assertThat(segment.getTags().size(), greaterThanOrEqualTo(5));
    assertThat(segment.getEmbedding().length, greaterThanOrEqualTo(64));
    assertThat(findTag(segment.getTags(), "vehicle"), notNullValue());

    segment = results.get(0).getVideoSegments().get(2);
    assertThat(segment.getTags().size(), greaterThanOrEqualTo(5));
    assertThat(segment.getEmbedding().length, greaterThanOrEqualTo(64));
    assertThat(findTag(segment.getTags(), "sky"), notNullValue());
  }

  @Test public void testAllErrorCase() throws IOException {
    if (shouldSkipTest()) return;
    List<RecognitionResult> results = clarifai.recognize(
        new RecognitionRequest("not an image".getBytes(), "not an image".getBytes()));
    assertThat(results.size(), equalTo(2));
    assertThat(results.get(0).getStatusCode(), equalTo(StatusCode.CLIENT_ERROR));
    assertThat(results.get(1).getStatusCode(), equalTo(StatusCode.CLIENT_ERROR));
  }

  @Test public void testBadRequest() {
    if (shouldSkipTest()) return;
    try {
      clarifai.recognize(new RecognitionRequest(new String[0]));
      fail("Exception expected");
    } catch (ClarifaiBadRequestException e) {
      assertThat(e.getMessage(), startsWith("ALL_ERROR"));
    }
  }

  @Test public void testBadCredentials() {
    if (shouldSkipTest()) return;
    try {
      clarifai = new ClarifaiClient(appId, "not_the_real_app_secret");
      clarifai.getInfo();
      fail("Exception expected");
    } catch (ClarifaiNotAuthorizedException e) {
      assertThat(e.getMessage(), startsWith("TOKEN_APP_INVALID"));
    }
  }

  private boolean shouldSkipTest() {
    if (clarifai == null) {
      System.err.println("Skipped test: " + name.getMethodName() + " because clarifai.appId and " +
          "clarifai.appSecret properties are not defined.");
      return true;
    }
    return false;
  }

  private Tag findTag(List<Tag> tags, String tagClass) {
    for (Tag tag : tags) {
      if (tag.getName().equals(tagClass)) {
        return tag;
      }
    }
    return null;
  }
}
