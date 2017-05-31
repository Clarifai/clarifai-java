package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.SearchClause;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.SearchHit;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class QuickstartGuideExamples extends BaseClarifaiAPITest {

  @Before public void cleanBeforeEveryExample() {
    assertSuccess(client.deleteAllInputs());
    assertSuccess(client.deleteAllModels());
  }

  @Test public void quickStartPredict() {
    final ClarifaiResponse<List<ClarifaiOutput<Concept>>> predictionResults =
        client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get custom models
            .predict()
            .withInputs(
                ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain"))
            )
            .executeSync();
  }

  @Test public void quickStartSearch() {
    final ClarifaiResponse<List<SearchHit>> trainImages = client.searchInputs(
        // Finds images that match this picture of a train OR images that match the "train" concept tag
        SearchClause.matchImageVisually(ClarifaiImage.of("@@sampleTrain"))
    )
        .getPage(1)
        .executeSync();
  }

  @Test
  public void quickStartTrain() {
    // Create some concepts
    client.addConcepts()
        .plus(
            Concept.forID("boscoe")
        )
        .executeSync();

    // All concepts need at least one "positive example" (ie, an input whose image file contains that concept)
    // So we will add a positive and a negative example of Boscoe
    client.addInputs()
        .plus(
            ClarifaiInput.forImage(ClarifaiImage.of("@@samplePuppy"))
                .withConcepts(
                    Concept.forID("boscoe")
                ),
            ClarifaiInput.forImage(ClarifaiImage.of("@@sampleWedding"))
                .withConcepts(
                    Concept.forID("boscoe").withValue(false)
                )
        )
        .executeSync();


    // Now that you have created the boscoe concept, and you have positive
    // examples of this concept, you can create a Model that knows this concept
    final ConceptModel petsModel = client.createModel("pets")
        .withOutputInfo(ConceptOutputInfo.forConcepts(
            Concept.forID("boscoe")
        ))
        .executeSync()
        .get();

    // Now that your app contains inputs with the concepts that you wanted to
    // detect, you can train your "pets" model
    petsModel.train().executeSync();
  }
}
