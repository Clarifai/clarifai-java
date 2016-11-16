package clarifai2.test;

import clarifai2.api.request.input.SearchClause;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.input.image.Crop;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.ModelType;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

@Ignore // These would all fail because they use strings like "@@sampleTrain" for the sake of the docs
public class GuideExamples extends BaseClarifaiAPITest {
  @Test public void retrieveAnAccessToken() {
    // NOTE: you probably won't have to handle this. The API client automatically refreshes your access token
    // before making any network calls if it is expired
    client.getToken();
  }

  @Test public void addAnInputUsingAPubliclyAccessibleURL() {
    client.addInputs()
        .plus(ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain")))
        .allowDuplicateURLs(true)
        .executeSync();
  }

  @Test public void addAnInputUsingBytes() {
    client.addInputs()
        .plus(ClarifaiInput.forImage(ClarifaiImage.of(new File("image.png"))))
        .allowDuplicateURLs(true)
        .executeSync();
  }

  @Test public void addInputsWithConcepts() {
    client.addInputs()
        .plus(ClarifaiInput.forImage(ClarifaiImage.of("@@samplePuppy"))
            .withConcepts(
                // To mark a concept as being absent, chain `.withValue(false)`
                Concept.forID("boscoe")
            )
        )
        .allowDuplicateURLs(true)
        .executeSync();
  }

  @Test public void getInputs() {
    client.getInputs() // optionally takes a perPage parameter
        .getPage(1)
        .executeSync();
  }

  @Test public void getInputByID() {
    client.getInputByID("{{id}}").executeSync();
  }

  @Test public void getInputsStatus() {
    client.getInputsStatus().executeSync();
  }

  @Test public void bulkUpdateInputWithConcepts() {
    client.mergeConceptsForInput("{{id}}")
        .plus(
            Concept.forID("tree"),
            Concept.forID("water").withValue(false)
        )
        .executeSync();
  }

  @Test public void bulkDeleteConceptsFromAnInput() {
    client.mergeConceptsForInput("{{id}}")
        .plus(
            Concept.forID("tree"),
            Concept.forID("water")
        )
        .executeSync();
  }

  @Test public void deleteInputByID() {
    client.deleteInput("{{id}}").executeSync();
  }

  @Test public void deleteAllInputs() {
    client.deleteAllInputs().executeSync();
  }

  @Test public void addImagesToSearchIndex() {
    client.addInputs()
        .plus(
            ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain")),
            ClarifaiInput.forImage(ClarifaiImage.of("@@sampleWedding"))
        )
        .allowDuplicateURLs(true)
        .executeSync();
  }

  @Test public void searchByConcept() {
    client.searchInputs(SearchClause.matchConcept(Concept.forName("dress")))
        .build()
        .getPage(1)
        .executeSync();
  }

  @Test public void reverseImageSearch() {
    client.searchInputs(SearchClause.matchImageVisually(ClarifaiImage.of("http://i.imgur.com/HEoT5xR.png")))
        .getPage(1)
        .executeSync();
  }

  @Test public void createModel() {
    client.createModel("pets").executeSync();
  }

  @Test public void createModelWithConcepts() {
    client.createModel("pets")
        .withOutputInfo(ConceptOutputInfo.forConcepts(
            Concept.forID("boscoe")
        ))
        .executeSync();
  }

  @Test public void addConceptsToModel() {
    client.mergeConceptsForModel("{{model_id}}")
        .plus(Concept.forID("dogs"))
        .executeSync();

    // Or, if you have a ConceptModel object, you can do it in an OO fashion
    final ConceptModel model = client.getModelByID("{{model_id}}").executeSync().get().asConceptModel();
    model.mergeConcepts()
        .plus(Concept.forID("dogs"))
        .executeSync();
  }

  @Test public void removeConceptsFromAModel() {
    client.removeConceptsForModel("{{model_id}}")
        .plus(Concept.forID("dogs"))
        .executeSync();

    // Or, if you have a ConceptModel object, you can do it in an OO fashion
    final ConceptModel model = client.getModelByID("{{model_id}}").executeSync().get().asConceptModel();
    model.removeConcepts()
        .plus(Concept.forID("dogs"))
        .executeSync();
  }

  @Test public void getModels() {
    client.getModels().getPage(1).executeSync();
  }

  @Test public void getModelByID() {
    client.getModelByID("{{model_id}}").executeSync();
  }

  @Test public void listModelVersions() {
    client.getModelVersions("{{model_id}}").getPage(1).executeSync();
  }

  @Test public void getModelVersionByID() {
    client.getModelVersionByID("{{model_id}}", "{{version_id}}").executeSync();

    // Or in a more object-oriented manner:
    client.getModelByID("{{model_id}}")
        .executeSync().get() // Returns Model object
        .getVersionByID("{{version_id}}").executeSync();
  }

  @Test public void getModelTrainingInputs() {
    client.getModelInputs("{{model_id}}").getPage(1).executeSync();
  }

  @Test public void getModelTrainingInputsByVersion() {
    client.getModelInputs("{{model_id}}")
        .fromSpecificModelVersion("{{version_id}}")
        .getPage(1)
        .executeSync();
  }

  @Test public void deleteAModel() {
    client.deleteModel("{{model_id}}").executeSync();
  }


  @Test public void deleteAModelVersion() {
    client.deleteModelVersion("{{model_id}}", "{{version_id}}").executeSync();

    // Or in a more object-oriented manner:
    client.getModelByID("{{model_id}}")
        .executeSync().get() // Returns Model object
        .deleteVersion("{{version_id}}")
        .executeSync();
  }

  @Test public void deleteAllModels() {
    client.deleteAllModels().executeSync();
  }

  @Test public void predictViaURL() {
    client.getDefaultModels().generalModel().predict()
        .withInputs(
            ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain"))
        )
        .executeSync();
  }

  @Test public void predictViaImageBytes() {
    client.getDefaultModels().generalModel().predict()
        .withInputs(
            ClarifaiInput.forImage(ClarifaiImage.of(new File("/home/user/image.jpeg")))
        )
        .executeSync();
  }

  @Test public void predictGeneralModel() {
    client.getDefaultModels().generalModel().predict()
        .withInputs(
            ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain"))
        )
        .executeSync();
  }

  @Test public void addImageWithConcepts() {
    client.addInputs()
        .plus(
            ClarifaiInput.forImage(ClarifaiImage.of("@@samplePuppy"))
                .withConcepts(Concept.forID("boscoe"))
        )
        .allowDuplicateURLs(true)
        .executeSync();
  }

  @Test public void createAModel() {
    client.createModel("pets")
        .withOutputInfo(ConceptOutputInfo.forConcepts(
            Concept.forID("boscoe")
        ))
        .executeSync();
  }

  @Test public void trainTheModel() {
    client.trainModel("{{model_id}}").executeSync();
  }

  @Test public void predictWithTheModel() {
    client.predict("{{model_id}}")
        .withInputs(
            ClarifaiInput.forImage(ClarifaiImage.of("@@samplePuppy"))
        )
        .executeSync();
  }

  @Test public void searchByUserSuppliedConcept() {
    // Search concept by name
    client.searchInputs(SearchClause.matchUserTaggedConcept(Concept.forName("cat")))
        .getPage(1)
        .executeSync();

    // Search concept by ID
    client.searchInputs(SearchClause.matchUserTaggedConcept(Concept.forID("ai_mFqxrph2")))
        .getPage(1)
        .executeSync();

    // Search multiple concepts
    client.searchInputs(SearchClause.matchUserTaggedConcept(Concept.forID("cat")))
        .and(SearchClause.matchUserTaggedConcept(Concept.forID("cute")))
        .getPage(1)
        .executeSync();

    // Search NOT by concept
    client.searchInputs(SearchClause.matchUserTaggedConcept(Concept.forID("cat").withValue(false)))
        .getPage(1)
        .executeSync();
  }

  @Test public void searchByReverseImage() {
    // Search by image (image URL represented by: String or java.net.URL)
    client.searchInputs(SearchClause.matchImageVisually(ClarifaiImage.of("@@sampleTrain")))
        .getPage(1)
        .executeSync();

    // Search by image (image represented by: java.io.File or byte[])
    client.searchInputs(SearchClause.matchImageVisually(ClarifaiImage.of(new File("image.png"))))
        .getPage(1)
        .executeSync();
  }

  @Test public void searchMatchURL() {
    // Lookup images with this URL
    client.searchInputs(SearchClause.matchImageURL(ClarifaiImage.of("")))
        .getPage(1)
        .executeSync();
  }

  @Test public void searchByPredictedConcepts() {
    // Search concept by name
    client.searchInputs(SearchClause.matchConcept(Concept.forName("cat")))
        .getPage(1)
        .executeSync();

    // Search concept by ID
    client.searchInputs(SearchClause.matchConcept(Concept.forID("ai_mFqxrph2")))
        .getPage(1)
        .executeSync();

    // Search multiple concepts
    client.searchInputs(SearchClause.matchConcept(Concept.forID("cat")))
        .and(SearchClause.matchConcept(Concept.forID("cute")))
        .getPage(1)
        .executeSync();

    // Search NOT by concept
    client.searchInputs(SearchClause.matchConcept(Concept.forID("cat").withValue(false)))
        .getPage(1)
        .executeSync();
  }

  @Test public void searchByConceptAndPrediction() {
    client.searchInputs()
        // Matches images we tagged as "cat", and that the API tagged as not having "dog"
        .ands(
            SearchClause.matchUserTaggedConcept(Concept.forName("cat")),
            SearchClause.matchConcept(Concept.forName("dog").withValue(false))
        )
        .getPage(1)
        .executeSync();
  }

  @Test public void searchANDing() {
    client.searchInputs()
        .ands(
            SearchClause.matchUserTaggedConcept(Concept.forName("cat")),
            SearchClause.matchConcept(Concept.forName("dog").withValue(false)),
            SearchClause.matchImageVisually(ClarifaiImage.of("@@sampleTrain"))
        )
        .getPage(1)
        .executeSync();
  }

  @Test public void cropping() {
    client.addInputs()
        .plus(
            ClarifaiInput.forImage(
                ClarifaiImage.of("@@sampleTrain")
                    .withCrop(Crop.create()
                        .top(0.2F)
                        .left(0.4F)
                        .bottom(0.3F)
                        .right(0.6F)
                    )
            )
        )
        .allowDuplicateURLs(true)
        .executeSync();
  }

  @Test public void addMultipleInputsWithIDs() {
    client.addInputs()
        .plus(
            ClarifaiInput.forImage(
                ClarifaiImage.of("@@sampleTrain")
            ).withConcepts(
                Concept.forID("id1")
            ),
            ClarifaiInput.forImage(
                ClarifaiImage.of("@@sampleWedding")
            ).withConcepts(
                Concept.forID("id2")
            )
        )
        .allowDuplicateURLs(true)
        .executeSync();
  }

  @Test public void searchModelsByNameAndType() {
    client.findModel()
        .withModelType(ModelType.CONCEPT)
        .withName("general-v1.3")
        .getPage(1)
        .executeSync();
  }

  @Test public void weddingModel() {
    client.getDefaultModels().weddingModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain")))
        .executeSync();
  }

  @Test public void nsfwModel() {
    client.getDefaultModels().nsfwModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain")))
        .executeSync();
  }

  @Test public void colorModel() {
    client.getDefaultModels().colorModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain")))
        .executeSync();
  }

  @Test public void foodModel() {
    client.getDefaultModels().foodModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain")))
        .executeSync();
  }

  @Test public void travelModel() {
    client.getDefaultModels().travelModel().predict()
        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of("@@sampleTrain")))
        .executeSync();
  }
}
