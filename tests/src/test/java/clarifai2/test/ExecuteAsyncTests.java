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

  @Test public void shouldMakeSureExecuteAsyncWorks() throws InterruptedException {
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
    boolean completed = loginLatch.await(3, TimeUnit.SECONDS);
    assertTrue(completed);
    long timeBetween = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - asyncExecutionsDoneTime);
    assertTrue(timeBetween > 10); // It should take at least 10ms for the asynchronous callbacks to be called.
  }

  private void handleResponse(List<ClarifaiOutput<Concept>> clarifaiOutputs) {
    loginLatch.countDown();
  }
  private void handleException(int errorCode) { }
  private void handleNetError(IOException e) { }
}

