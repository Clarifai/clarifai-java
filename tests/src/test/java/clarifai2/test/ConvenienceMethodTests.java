package clarifai2.test;

import clarifai2.api.ClarifaiUtil;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import org.junit.Assert;
import org.junit.Test;

public class ConvenienceMethodTests extends BaseClarifaiAPITest {

  @Test public void testAwaitTraining() {

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

    Assert.assertTrue(ClarifaiUtil.trainAndAwaitCompletion(client, "mod1"));
  }
}
