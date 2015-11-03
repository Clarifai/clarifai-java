package com.clarifai.api;

import static com.clarifai.api.TestUtils.loadResource;
import static com.clarifai.api.TestUtils.writeTempFile;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.clarifai.api.RecognitionResult.StatusCode;
import com.clarifai.api.auth.Credential;
import com.clarifai.api.auth.CredentialCache;
import com.clarifai.api.exception.ClarifaiBadRequestException;
import com.clarifai.api.exception.ClarifaiException;
import com.clarifai.api.exception.ClarifaiNotAuthorizedException;
import com.google.gson.JsonArray;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

public class ClarifaiClientTest {
  private static final String MULTIPART_PREFIX = "multipart/form-data; boundary=";
  private static final byte[] FAKE_DATA = "test image data".getBytes();

  private static class TestCredentialCache implements CredentialCache {
    Credential credential;
    public void putCredential(String appId, Credential c) { credential = c; }
    public Credential getCredential(String appId) { return credential; }
    public void removeCredential(String appId) { credential = null; }
  }

  private TestCredentialCache credentialCache;
  private MockWebServer server;
  private ClarifaiClient clarifai;

  @Before public void setUp() throws IOException {
    credentialCache = new TestCredentialCache();
    credentialCache.credential = new Credential("test_token", null, Long.MAX_VALUE);
    server = new MockWebServer();
    server.play();
    String apiRoot = server.getUrl("/v1").toString();
    clarifai = new ClarifaiClient(apiRoot, "app123", "secret", credentialCache);
    clarifai.setReadTimeout(1000);
  }

  @After public void tearDown() throws Exception {
    if (server != null) {
      assertThat("Unexpected request!", server.takeRequest(0, TimeUnit.MILLISECONDS), nullValue());
      server.shutdown();
      server = null;
    }
  }

  // Normal use cases:

  @Test public void testGetInfo() throws Exception {
    server.enqueue(mockResponse(200, "info_ok.json"));

    InfoResult info = clarifai.getInfo();

    assertTrue(info.embedAllowed());
    assertThat(info.getMinImageSize(), equalTo(224));
    assertThat(info.getMaxImageSize(), equalTo(1024));
    assertThat(info.getMaxImageBytes(), equalTo(10485760L));
    assertThat(info.getMaxBatchSize(), equalTo(128));
    assertThat(info.getMinVideoSize(), equalTo(224));
    assertThat(info.getMaxVideoSize(), equalTo(1024));
    assertThat(info.getMaxVideoBytes(), equalTo(104857600L));
    assertThat(info.getMaxVideoBatchSize(), equalTo(1));
    assertThat(info.getMaxVideoDuration(), equalTo(1800));
    checkRequest(server.takeRequest(), "GET", "/v1/info");
  }

  @Test public void testRecognizeTags() throws Exception {
    server.enqueue(mockResponse(200, "tags_ok.json"));

    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(FAKE_DATA));
    assertThat(results.size(), equalTo(1));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    assertThat(result.getStatusMessage(), equalTo("OK"));
    assertThat(result.getDocId(), equalTo("ddd627e16cf679c06be8b9e78b5cbcd1"));
    assertThat(result.getTags().size(), equalTo(20));
    assertThat(result.getTags().get(0).getName(), equalTo("facial expression"));
    assertThat(result.getTags().get(0).getProbability(), equalTo(0.10760640352964401));
    assertThat(result.getTags().get(19).getName(), equalTo("eyewear"));
    assertThat(result.getTags().get(19).getProbability(), equalTo(0.007289715576916933));
    assertThat(result.getEmbedding(), nullValue());
    assertThat(result.getVideoSegments(), nullValue());
    RecordedRequest request = checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertThat(request.getUtf8Body(), containsString("name=\"op\"\r\n\r\ntag\r\n"));
  }

  @Test public void testLocale() throws Exception {
    server.enqueue(mockResponse(200, "tags_localized_ok.json"));

    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(FAKE_DATA)
        .setLocale(Locale.JAPAN));
    assertThat(results.size(), equalTo(1));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    assertThat(result.getStatusMessage(), equalTo("OK"));
    assertThat(result.getDocId(), equalTo("0e2687e3a6c95084e6ce912aa45d3803"));
    assertThat(result.getTags().size(), equalTo(20));
    assertThat(result.getTags().get(0).getName(), equalTo("誰も"));
    assertThat(result.getTags().get(0).getProbability(), equalTo(0.9976773858070374));
    RecordedRequest request = checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertThat(request.getUtf8Body(), containsString("name=\"op\"\r\n\r\ntag\r\n"));
    assertThat(request.getUtf8Body(), containsString("name=\"language\"\r\n\r\nja\r\n"));
  }

  @Test public void testTraditionalChineseLocale() throws Exception {
    server.enqueue(mockResponse(200, "tags_localized_ok.json"));

    // Traditional Chinese is special for now in that we also send the country code.
    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(FAKE_DATA)
        .setLocale(new Locale("zh", "TW")));
    assertThat(results.get(0).getStatusCode(), equalTo(StatusCode.OK));
    RecordedRequest request = checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertThat(request.getUtf8Body(), containsString("name=\"op\"\r\n\r\ntag\r\n"));
    assertThat(request.getUtf8Body(), containsString("name=\"language\"\r\n\r\nzh-TW\r\n"));
  }

  @Test public void testRecognizeEmbedding() throws Exception {
    server.enqueue(mockResponse(200, "embed_ok.json"));

    List<RecognitionResult> results = clarifai.recognize(
        new RecognitionRequest("http://www.clarifai.com/img/metro-north.jpg")
            .setIncludeEmbedding(true)
            .setIncludeTags(false));
    assertThat(results.size(), equalTo(1));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    assertThat(result.getStatusMessage(), equalTo("OK"));
    assertThat(result.getDocId(), equalTo("7e5af83c183507582dfd2ce92a6562d"));
    assertThat( result.getEmbedding().length, equalTo(64));
    assertThat(result.getEmbedding()[0], equalTo(0.0142445657402277));
    assertThat(result.getEmbedding()[63], equalTo(-0.017197977751493454));
    assertThat(result.getTags(), nullValue());
    assertThat(result.getVideoSegments(), nullValue());
    RecordedRequest request = checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertThat(request.getUtf8Body(), containsString("name=\"op\"\r\n\r\nembed\r\n"));
  }

  @Test public void testRecognizeVideoTags() throws Exception {
    server.enqueue(mockResponse(200, "tags_video_ok.json"));

    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(FAKE_DATA));
    checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertThat(results.size(), equalTo(1));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    assertThat(result.getStatusMessage(), equalTo("OK"));
    assertThat(result.getDocId(), equalTo("c8e6b898fd0b4ce46151f06a070fefb0"));
    assertThat(result.getVideoSegments().size(), equalTo(4));

    // Check first segment:
    VideoSegment segment = result.getVideoSegments().get(0);
    assertThat(segment.getTimestampSeconds(), equalTo(0.0));
    assertThat(segment.getTags().size(), equalTo(20));
    assertThat(segment.getTags().get(0).getName(), equalTo("grass"));
    assertThat(segment.getTags().get(0).getProbability(), equalTo(0.972413182258606));
    assertThat(segment.getEmbedding(), nullValue());

    // Check last segment:
    segment = result.getVideoSegments().get(3);
    assertThat(segment.getTimestampSeconds(), equalTo(3.0));
    assertThat(segment.getTags().size(), equalTo(20));
    assertThat(segment.getTags().get(19).getName(), equalTo("field"));
    assertThat(segment.getTags().get(19).getProbability(), equalTo(0.7907412052154541));
    assertThat(segment.getEmbedding(), nullValue());

    assertThat(result.getTags(), equalTo(result.getVideoSegments().get(0).getTags()));
    assertThat(result.getEmbedding(), nullValue());
  }

  @Test public void testRecognizeVideoEmbed() throws Exception {
    server.enqueue(mockResponse(200, "embed_video_ok.json"));

    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(FAKE_DATA));
    checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertThat(results.size(), equalTo(1));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    assertThat(result.getStatusMessage(), equalTo("OK"));
    assertThat(result.getDocId(), equalTo("c8e6b898fd0b4ce46151f06a070fefb0"));
    assertThat(result.getVideoSegments().size(), equalTo(4));

    // Check first segment:
    VideoSegment segment = result.getVideoSegments().get(0);
    assertThat(segment.getTimestampSeconds(), equalTo(0.0));
    assertThat(segment.getEmbedding().length, equalTo(64));
    assertThat(segment.getEmbedding()[0], equalTo(-0.0066182175651192665));
    assertThat(segment.getTags(), nullValue());

    // Check last segment:
    segment = result.getVideoSegments().get(3);
    assertThat(segment.getTimestampSeconds(), equalTo(3.0));
    assertThat(segment.getEmbedding().length, equalTo(64));
    assertThat(segment.getEmbedding()[63], equalTo(-0.017788972705602646));
    assertThat(segment.getTags(), nullValue());

    assertThat(result.getEmbedding(), equalTo(result.getVideoSegments().get(0).getEmbedding()));
    assertThat(result.getTags(), nullValue());
  }

  @Test public void testRecognizeVideoTagAndEmbed() throws Exception {
    server.enqueue(mockResponse(200, "tag_and_embed_video_ok.json"));

    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(FAKE_DATA));
    checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertThat(results.size(), equalTo(1));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    assertThat(result.getStatusMessage(), equalTo("OK"));
    assertThat(result.getDocId(), equalTo("c8e6b898fd0b4ce46151f06a070fefb0"));
    assertThat(result.getVideoSegments().size(), equalTo(4));

    // Check first segment:
    VideoSegment segment = result.getVideoSegments().get(0);
    assertThat(segment.getTimestampSeconds(), equalTo(0.0));
    assertThat(segment.getTags().size(), equalTo(20));
    assertThat(segment.getTags().get(0).getName(), equalTo("grass"));
    assertThat(segment.getTags().get(0).getProbability(), equalTo(0.972413182258606));
    assertThat(segment.getEmbedding().length, equalTo(64));
    assertThat(segment.getEmbedding()[0], equalTo(-0.0066182175651192665));

    // Check last segment:
    segment = result.getVideoSegments().get(3);
    assertThat(segment.getTimestampSeconds(), equalTo(3.0));
    assertThat(segment.getTags().size(), equalTo(20));
    assertThat(segment.getTags().get(19).getName(), equalTo("field"));
    assertThat(segment.getTags().get(19).getProbability(), equalTo(0.7907412052154541));
    assertThat(segment.getEmbedding().length, equalTo(64));
    assertThat(segment.getEmbedding()[63], equalTo(-0.017788972705602646));

    assertThat(result.getTags(), equalTo(result.getVideoSegments().get(0).getTags()));
    assertThat(result.getEmbedding(), equalTo(result.getVideoSegments().get(0).getEmbedding()));
  }


  @Test public void testCustomOperation() throws Exception {
    server.enqueue(mockResponse(200, "customop_ok.json"));
    List<RecognitionResult> results = clarifai.recognize(
        new RecognitionRequest("http://www.clarifai.com/img/metro-north.jpg")
            .setIncludeTags(false)
            .addCustomOperation("mythicalcreature"));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.OK));
    JsonArray customResponses = result.getJsonResponse().get("mythicalcreature").getAsJsonArray();
    assertThat(customResponses.size(), equalTo(1));
    JsonArray classes = customResponses.get(0).getAsJsonObject().get("classes").getAsJsonArray();
    assertThat(classes.get(0).getAsString(), equalTo("unicorn"));
    assertThat(classes.get(1).getAsString(), equalTo("centaur"));
    JsonArray probs = customResponses.get(0).getAsJsonObject().get("probs").getAsJsonArray();
    assertThat(probs.get(0).getAsDouble(), equalTo(0.998));
    assertThat(probs.get(1).getAsDouble(), equalTo(0.378));
    RecordedRequest request = checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertThat(request.getUtf8Body(), containsString("name=\"op\"\r\n\r\nmythicalcreature\r\n"));
  }

  @Test public void testRecognizeRequestPayloads() throws Exception {
    server.enqueue(mockResponse(200, "tags_ok.json"));
    clarifai.recognize(new RecognitionRequest(
        "test stream".getBytes("UTF-8"),
        "another stream".getBytes("UTF-8")));

    RecordedRequest req = checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    assertTrue(req.getHeader("Content-Type").startsWith(MULTIPART_PREFIX));
    String boundary = "--" + req.getHeader("Content-Type").substring(MULTIPART_PREFIX.length());
    assertThat(new String(req.getBody(), "UTF-8"), equalTo(joinLines(
        boundary,
        "Content-Disposition: form-data; name=\"op\"",
        "",
        "tag",
        boundary,
        "Content-Disposition: form-data; name=\"model\"",
        "",
        "default",
        boundary,
        "Content-Disposition: form-data; name=\"encoded_data\"; filename=\"media\"",
        "Content-Type: application/octet-stream",
        "",
        "test stream",
        boundary,
        "Content-Disposition: form-data; name=\"encoded_data\"; filename=\"media\"",
        "Content-Type: application/octet-stream",
        "",
        "another stream",
        boundary + "--")));

    server.enqueue(mockResponse(200, "tags_ok.json"));
    File testFile = writeTempFile("recognizeRequestPayload", "test file".getBytes("UTF-8"));
    clarifai.recognize(new RecognitionRequest(testFile).setIncludeEmbedding(true));

    req = checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    boundary = "--" + req.getHeader("Content-Type").substring(MULTIPART_PREFIX.length());
    assertThat(new String(req.getBody(), "UTF-8"), equalTo(joinLines(
        boundary,
        "Content-Disposition: form-data; name=\"op\"",
        "",
        "tag,embed",
        boundary,
        "Content-Disposition: form-data; name=\"model\"",
        "",
        "default",
        boundary,
        "Content-Disposition: form-data; name=\"encoded_data\"; filename=\"media\"",
        "Content-Type: application/octet-stream",
        "",
        "test file",
        boundary + "--")));

    server.enqueue(mockResponse(200, "tags_ok.json"));
    clarifai.recognize(new RecognitionRequest("http://clarifai.com/img/metro-north.jpg")
        .setModel("the-model-name")
        .setIncludeEmbedding(true)
        .setIncludeTags(false));

    req = checkRequest(server.takeRequest(), "POST", "/v1/multiop");
    boundary = "--" + req.getHeader("Content-Type").substring(MULTIPART_PREFIX.length());
    assertThat(new String(req.getBody(), "UTF-8"), equalTo(joinLines(
        boundary,
        "Content-Disposition: form-data; name=\"op\"",
        "",
        "embed",
        boundary,
        "Content-Disposition: form-data; name=\"model\"",
        "",
        "the-model-name",
        boundary,
        "Content-Disposition: form-data; name=\"url\"",
        "",
        "http://clarifai.com/img/metro-north.jpg",
        boundary + "--")));
  }

  @Test public void testFeedback() throws Exception {
    server.enqueue(mockResponse(201, "feedback_ok.json"));

    clarifai.sendFeedback(new FeedbackRequest()
        .setDocIds("12345")
        .setAddTags("flower", "sky")
        .setRemoveTags("facial expression")
        .setDissimilarDocIds(new String[] { "98765", "43210" })
        .setSimilarDocIds("23456"));

    RecordedRequest req = checkRequest(server.takeRequest(), "POST", "/v1/feedback");
    assertThat(req.getHeader("Content-Type"), equalTo("application/x-www-form-urlencoded"));
    assertThat(new String(req.getBody(), "UTF-8"), equalTo(
        "docids=12345&add_tags=flower%2Csky&remove_tags=facial+expression" +
        "&similar_docids=23456&dissimilar_docids=98765%2C43210"));
  }

  // Error cases:

  @Test public void testBadRequest() throws Exception {
    server.enqueue(mockResponse(400, "tags_bad_request.json"));
    clarifai.setMaxAttempts(1);
    try {
      clarifai.recognize(new RecognitionRequest("http://example.com/x.jpg"));
      fail("Exception expected");
    } catch (ClarifaiBadRequestException e) {
      assertThat(e.getMessage(), equalTo("ALL_ERROR The incoming request could not be be " +
          "deserialized. Please ensure the format of the request matches the documentation. " +
          "Request must provide exactly one of the following fields: ('encoded_image', "+
          "'encoded_data', 'url')."));
    }
    server.takeRequest();
  }

  @Test public void testVisionError() throws Exception {
    server.enqueue(mockResponse(500, "tags_vision_error.json"));
    clarifai.setMaxAttempts(1);
    try {
      clarifai.recognize(new RecognitionRequest("http://example.com/x.jpg"));
      fail("Exception expected");
    } catch (ClarifaiException e) {
      assertSame(e.getClass(), ClarifaiException.class);
      assertThat(e.getMessage(), equalTo(
          "VISION_ERROR Server failed to run system on data in request."));
    }
    server.takeRequest();
  }

  @Test public void testServiceUnavailable() throws Exception {
    server.enqueue(mockResponse(503, "service_unavailable.txt"));
    clarifai.setMaxAttempts(1);
    try {
      clarifai.recognize(new RecognitionRequest("http://example.com/x.jpg"));
      fail("Exception expected");
    } catch (ClarifaiException e) {
      assertSame(e.getClass(), ClarifaiException.class);
      assertThat(e.getMessage(), equalTo("Server returned an unparsable response"));
    }
    server.takeRequest();
  }

  @Test public void testRecognizePartialError() throws Exception {
    server.enqueue(mockResponse(200, "tags_partial_error.json"));

    List<RecognitionResult> results = clarifai.recognize(
        new RecognitionRequest("http://example.com/x.jpg"));
    assertThat(results.size(), equalTo(2));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.CLIENT_ERROR));
    assertThat(result.getStatusMessage(),
        equalTo("Data loading failed. data 0 is not a valid because: Image of min dim 123 is " +
                "below min allowed dimension of 224.."));
    assertThat(result.getTags(), nullValue());
    assertThat(result.getEmbedding(), nullValue());
    result = results.get(1);
    assertThat(result.getStatusMessage(), equalTo("OK"));
    assertThat(result.getDocId(), equalTo("7e5af83c183507582dfd2ce92a6562d"));
    assertThat(result.getTags().size(), equalTo(20));
    server.takeRequest();
  }

  @Test public void testRecognizeAllError() throws Exception {
    server.enqueue(mockResponse(400, "tags_all_error.json"));
    List<RecognitionResult> results = clarifai.recognize(
        new RecognitionRequest("http://example.com/x.jpg", "http://example.com/y.jpg"));
    assertThat(results.size(), equalTo(2));
    RecognitionResult result = results.get(0);
    assertThat(result.getStatusCode(), equalTo(StatusCode.CLIENT_ERROR));
    assertThat(result.getStatusMessage(),
        equalTo("Data loading failed. data 0 is not a valid because: Image of min dim 165 is " +
                "below min allowed dimension of 224.."));
    assertThat(result.getTags(), nullValue());
    assertThat(result.getEmbedding(), nullValue());
    result = results.get(1);
    assertThat(result.getStatusCode(), equalTo(StatusCode.CLIENT_ERROR));
    assertThat(result.getStatusMessage(),
        equalTo("Data loading failed. data 1 is not a valid because: Image of min dim 102 is " +
                "below min allowed dimension of 224.."));
    assertThat(result.getTags(), nullValue());
    assertThat(result.getEmbedding(), nullValue());
    server.takeRequest();
  }

  @Test public void testRetry() throws Exception {
    server.enqueue(mockResponse(500, "tags_vision_error.json"));
    server.enqueue(mockResponse(200, "tags_ok.json"));
    clarifai.setMaxAttempts(2);
    assertThat(clarifai.recognize(new RecognitionRequest("http://example.com/x.jpg")).size(),
        equalTo(1));
    server.takeRequest();
    server.takeRequest();
  }

  @Test public void testNoRetryOnBadRequest() throws Exception {
    server.enqueue(mockResponse(400, "tags_bad_request.json"));
    clarifai.setMaxAttempts(2);
    try {
      clarifai.recognize(new RecognitionRequest("http://example.com/x.jpg"));
      fail("Exception expected");
    } catch (ClarifaiBadRequestException e) {
      assertThat(e.getMessage(), startsWith("ALL_ERROR"));
    }
    server.takeRequest();
  }

  @Test public void testTooManyRetries() throws Exception {
    server.enqueue(mockResponse(500, "tags_vision_error.json"));
    server.enqueue(mockResponse(500, "tags_vision_error.json"));
    server.enqueue(mockResponse(500, "tags_vision_error.json"));
    clarifai.setMaxAttempts(3);
    try {
      clarifai.recognize(new RecognitionRequest("http://example.com/x.jpg"));
      fail("Exception expected");
    } catch (ClarifaiException e) {
      assertSame(e.getClass(), ClarifaiException.class);
      assertThat(e.getMessage(), startsWith("VISION_ERROR"));
    }
    server.takeRequest();
    server.takeRequest();
    server.takeRequest();
  }

  // Auth:

  @Test public void testBadAppSecret() throws Exception {
    credentialCache.credential = null;
    server.enqueue(mockResponse(401, "token_app_invalid.json"));
    clarifai.setMaxAttempts(1);
    try {
      clarifai.recognize(new RecognitionRequest("http://example.com/x.jpg"));
      fail("Exception expected");
    } catch (ClarifaiNotAuthorizedException e) {
      assertThat(e.getMessage(), equalTo("TOKEN_APP_INVALID Application for this token is not " +
          "valid. Please  ensure that you are using ID and SECRET from same application. "));
    }
    server.takeRequest();
  }

  @Test public void testTokenRefresh() throws Exception {
    credentialCache.credential = new Credential(
        "test_token", null, System.currentTimeMillis() + 5000);  // expires too soon to use
    server.enqueue(mockResponse(200, "token_ok.json"));
    server.enqueue(mockResponse(200, "info_ok.json"));

    InfoResult info = clarifai.getInfo();
    assertThat(info.getMinImageSize(), equalTo(224));

    RecordedRequest req = server.takeRequest();
    assertThat(req.getMethod(), equalTo("POST"));
    assertThat(req.getPath(), equalTo("/v1/token"));
    assertThat(req.getHeader("Authorization"), nullValue());
    assertThat(req.getHeader("Content-Type"), equalTo("application/x-www-form-urlencoded"));
    assertThat(new String(req.getBody(), "UTF-8"), equalTo(
        "grant_type=client_credentials&client_id=app123&client_secret=secret"));

    req = server.takeRequest();
    assertThat(req.getMethod(), equalTo("GET"));
    assertThat(req.getPath(), equalTo("/v1/info"));
    assertThat(req.getHeader("Authorization"), equalTo("Bearer the_new_token"));
  }

  @Test public void testTokenExpiredRecovery() throws Exception {
    // The client should fetch a new token and then retry.
    server.enqueue(mockResponse(401, "token_expired.json"));
    server.enqueue(mockResponse(200, "token_ok.json"));
    server.enqueue(mockResponse(200, "info_ok.json"));

    InfoResult info = clarifai.getInfo();
    assertThat(info.getMinImageSize(), equalTo(224));

    checkRequest(server.takeRequest(), "GET", "/v1/info");

    RecordedRequest req = server.takeRequest();
    assertThat(req.getMethod(), equalTo("POST"));
    assertThat(req.getPath(), equalTo("/v1/token"));
    // testTokenRefresh checks the payload, so we don't check here.

    req = server.takeRequest();
    assertThat(req.getMethod(), equalTo("GET"));
    assertThat(req.getPath(), equalTo("/v1/info"));
    assertThat(req.getHeader("Authorization"), equalTo("Bearer the_new_token"));
  }

  private RecordedRequest checkRequest(RecordedRequest request, String method, String path) {
    assertThat(request.getMethod(), equalTo(method));
    assertThat(request.getPath(), equalTo(path));
    assertThat(request.getHeader("Authorization"), equalTo("Bearer test_token"));
    return request;
  }

  private MockResponse mockResponse(int status, String resource) throws IOException {
    return new MockResponse().setResponseCode(status)
        .setHeader("Content-Type", "application/json")
        .setBody(loadResource(resource));
  }

  private static String joinLines(String ... lines) {
    StringBuilder b = new StringBuilder();
    for (String line : lines) {
      b.append(line).append("\r\n");
    }
    return b.toString();
  }
}
