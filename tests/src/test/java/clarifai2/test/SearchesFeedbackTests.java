package clarifai2.test;

import clarifai2.api.request.feedback.SearchesFeedbackRequest;
import org.junit.Test;

public class SearchesFeedbackTests extends BaseClarifaiAPITest {

  @Test public void shouldBeSuccessfulWhenValid() {
    SearchesFeedbackRequest request = client.searchesFeedback()
        .withId("@invalidId")
        .withEndUserId("@endUserId")
        .withSessionId("@sesionId")
        .withEventType("search_click")
        .withSearchId("@searchId");
    assertSuccess(request);
  }

  @Test public void shouldFailWhenInvalidEventType() {
    SearchesFeedbackRequest request = client.searchesFeedback()
        .withId("@invalidId")
        .withEndUserId("@endUserId")
        .withSessionId("@sesionId")
        .withEventType("invalid_event_type-321")
        .withSearchId("@searchId");
    assertFailure(request);
  }
}
