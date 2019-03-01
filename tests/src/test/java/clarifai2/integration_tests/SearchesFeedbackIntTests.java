package clarifai2.integration_tests;

import clarifai2.api.request.feedback.SearchesFeedbackRequest;
import org.junit.Test;

public class SearchesFeedbackIntTests extends BaseIntTest {

  @Test public void shouldBeSuccessfulWhenValid() {
    SearchesFeedbackRequest request = client.searchesFeedback()
        .withId("@invalidId")
        .withEndUserId("@endUserId")
        .withSessionId("@sesionId")
        .withEventType("search_click")
        .withSearchId("@searchId");
    assertSuccess(request);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldFailWhenInvalidEventType() {
    SearchesFeedbackRequest request = client.searchesFeedback()
        .withId("@invalidId")
        .withEndUserId("@endUserId")
        .withSessionId("@sesionId")
        .withEventType("invalid_event_type-321")
        .withSearchId("@searchId");
    request.executeSync();
  }
}
