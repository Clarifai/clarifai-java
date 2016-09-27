package clarifai2.api.request;

import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

final class ClarifaiPaginatedRequestImpl<RESULT> implements ClarifaiPaginatedRequest<RESULT> {

  @NotNull private final Gson gson;
  @NotNull private final OkHttpClient client;
  @NotNull private final PaginatedRequestVendor requestVendor;
  @NotNull private final JSONUnmarshaler<RESULT> unmarshaler;

  ClarifaiPaginatedRequestImpl(
      @NotNull Gson gson,
      @NotNull OkHttpClient client,
      @NotNull PaginatedRequestVendor requestVendor,
      @NotNull JSONUnmarshaler<RESULT> unmarshaler
  ) {
    this.gson = gson;
    this.client = client;
    this.requestVendor = requestVendor;
    this.unmarshaler = unmarshaler;
  }

  @NotNull
  @Override
  public ClarifaiRequest<RESULT> getPage(final int page) {
    if (page < 1) {
      throw new IllegalArgumentException(
          "getPage(int) called with invalid page. Pages must be 1 or greater. getPage(int) called with pg#: " + page
      );
    }
    return new ClarifaiRequestImpl<>(gson, client, requestVendor.vendRequest(page), unmarshaler);
  }

  private class PageIterable implements Iterable<ClarifaiRequest<RESULT>> {
    @Override public Iterator<ClarifaiRequest<RESULT>> iterator() {
      return new Iterator<ClarifaiRequest<RESULT>>() {
        private int currentIndex = 0;

        @Override public boolean hasNext() {
          // The API still doesn't tell us whether a paginated endpoint has more results...
          return true;
        }

        @Override public ClarifaiRequest<RESULT> next() {
          currentIndex++;
          return getPage(currentIndex);
        }

        @Override public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }
  }
}

