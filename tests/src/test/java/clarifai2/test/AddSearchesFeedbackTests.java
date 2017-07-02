package clarifai2.test;

import clarifai2.api.request.feedback.AddSearchesFeedbackRequest;
import org.junit.Test;

public class AddSearchesFeedbackTests extends BaseClarifaiAPITest {

  @Test public void shouldBeSuccessfulWhenValid() {
    AddSearchesFeedbackRequest request = client.addSearchesFeedback()
        .withId("@invalidId")
        .withEndUserId("@endUserId")
        .withSessionId("@sesionId")
        .withEventType("search_click")
        .withSearchId("@searchId");
    assertSuccess(request);
  }

  @Test public void shouldFailWhenInvalidEventType() {
    AddSearchesFeedbackRequest request = client.addSearchesFeedback()
        .withId("@invalidId")
        .withEndUserId("@endUserId")
        .withSessionId("@sesionId")
        .withEventType("invalid_event_type-321")
        .withSearchId("@searchId");
    assertFailure(request);
  }
}
