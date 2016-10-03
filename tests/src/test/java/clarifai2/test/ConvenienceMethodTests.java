package clarifai2.test;

import clarifai2.api.ClarifaiUtil;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import org.junit.Test;

public class ConvenienceMethodTests extends BaseClarifaiAPITest {

  @Test public void testAwaitTraining() {

    for (int i = 0; i < 1; i++) {
      assertSuccess(client.deleteAllModels());

      assertSuccess(client.createModel("mod1")
          .withOutputInfo(ConceptOutputInfo.forConcepts(
              Concept.forID("train")
          ))
      );

      assertSuccess(client.addInputs()
          .plus(
              ClarifaiInput.forImage(ClarifaiImage.of(METRO_NORTH_IMAGE_FILE))
                  .withConcepts(
                      Concept.forID("train")
                  )
          )
      );

      final ClarifaiRequest<Model<?>> request = ClarifaiUtil.trainAndAwaitCompletion(client, "mod1");
      if (i == 0) {
        assertSuccessAsync(request);
      } else {
        assertSuccess(request);
      }
    }
  }
}
