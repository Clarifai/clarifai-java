package clarifai2.integration_tests;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.SearchClause;
import clarifai2.api.request.input.SearchInputsRequest;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.search.SearchInputsResult;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class SearchInputsIntTests extends BaseIntTest {

  private String imageWithNoSearchHits = CONAN_GIF_URL;

  @Test public void allDataShouldBeExposedAndNotNull() {
    SearchInputsRequest request = client.searchInputs(SearchClause.matchImageURL(
        ClarifaiImage.of(imageWithNoSearchHits)));
    ClarifaiResponse<SearchInputsResult> response = request.getPage(1).executeSync();
    assertNotNull(response.get().id());
    assertNotNull(response.getStatus());
    assertNotNull(response.get().searchHits());
  }
}
