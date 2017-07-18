package clarifai2.test;

import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.input.SearchClause;
import clarifai2.api.request.input.SearchInputsRequest;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.SearchHit;
import clarifai2.dto.search.SearchInputsResult;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SearchInputsTests extends BaseClarifaiAPITest {

  private String imageWithTwoSearchHits = METRO_NORTH_IMAGE_URL;
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
