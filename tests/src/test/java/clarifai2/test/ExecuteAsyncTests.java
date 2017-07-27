package clarifai2.test;

import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class ExecuteAsyncTests extends BaseClarifaiAPITest {

  private int simultaneousCallsNum = 10;
  @NotNull private final CountDownLatch loginLatch = new CountDownLatch(simultaneousCallsNum);

  // A collection of heuristics that attempt to capture whether these calls are actually
  // running asynchronously.  It is theoretically possible for every one of these asserts
  // to fail, depending on timing/thread scheduling, even if things are working properly.
  // Nonetheless, on nearly all real world hosts this test will pass if and only if async
  // behaves correctly.
  @Test public void shouldMakeSureExecuteAsyncWorks() throws InterruptedException {
    long asyncExecutionsStartedTime = System.nanoTime();
    for (int i = 0; i < simultaneousCallsNum; i++) {
      client.getDefaultModels().generalModel().predict()
          .withInputs(ClarifaiInput.forImage(
              "https://developer.clarifai.com/static/images/model-samples/demographics-001.jpg"))
          .executeAsync(resp -> handleResponse(resp),
              failCode -> handleException(failCode),
              io -> handleNetError(io)
          );
    }
    long asyncExecutionsDoneTime = System.nanoTime();
    // the login latch should not already be completed, assuming that the asynchronous
    // calls did not actually happen synchronously:
    assertTrue(loginLatch.getCount() > 0);

    // but they should eventually complete:
    boolean completed = loginLatch.await(3, TimeUnit.SECONDS);
    assertTrue(completed);
    long timeToCallbacks = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - asyncExecutionsDoneTime);
    
    // The time it takes to make the async calls should be less than 300ms (we're not blocking
    // on these calls:
    long timeAsyncCalls = TimeUnit.NANOSECONDS.toMillis(asyncExecutionsDoneTime - asyncExecutionsStartedTime);
    assertTrue(timeAsyncCalls < 300);

    // and it should take at least 30ms for the async callbacks -- assuming that the calls
    // actually happen:
    assertTrue(timeToCallbacks > 30);
  }

  private void handleResponse(List<ClarifaiOutput<Concept>> clarifaiOutputs) {
    loginLatch.countDown();
  }
  private void handleException(int errorCode) { }
  private void handleNetError(IOException e) { }
}

