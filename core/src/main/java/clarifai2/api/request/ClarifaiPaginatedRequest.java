package clarifai2.api.request;

import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.ClarifaiClient;
import clarifai2.internal.JSONObjectBuilder;
import clarifai2.internal.JSONUnmarshaler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

import static clarifai2.internal.InternalUtil.MEDIA_TYPE_JSON;

/**
 * An interface returned by the {@link ClarifaiClient} used to create {@link ClarifaiRequest} objects for each
 * individual page of the returned paginated request.
 * <p>
 * This should be thought of not as a request in itself, but as a request "factory". Calling {@link #getPage(int)}
 * will return a {@link ClarifaiRequest} that will retrieve results for the given page when invoked
 *
 * @param <RESULT> the type of data that will be returned from successful API calls when executing requests that are
 *                 returned by the {{@link #getPage(int)}} method
 */
public interface ClarifaiPaginatedRequest<RESULT> {
  /**
   * NOTE: Because the search API does not indicate whether there are more pages in a paginated request, the user will
   * have to manually handle this situation by executing until a blank response is returned from the API.
   *
   * @param page the page to go to
   * @return the request that, when executed, will return the results at the given page.
   */
  @NotNull ClarifaiRequest<RESULT> getPage(final int page);

  abstract class Builder<T, SELF extends Builder<T, SELF>> implements ClarifaiPaginatedRequest<T> {

    @NotNull protected final Gson gson;
    @NotNull protected final BaseClarifaiClient client;

    private int perPage = 0;

    protected Builder(@NotNull final BaseClarifaiClient client) {
      this.gson = client.gson;
      this.client = client;
    }

    public final int perPage() {
      return perPage;
    }

    @SuppressWarnings("unchecked")
    @NotNull public final SELF perPage(final int perPage) {
      this.perPage = perPage;
      return ((SELF) this);
    }

    @NotNull @Override public final ClarifaiRequest<T> getPage(final int page) {
      return build().getPage(page);
    }

    @NotNull public final ClarifaiPaginatedRequest<T> build() {
      return new ClarifaiPaginatedRequestImpl<>(client, new PaginatedRequestVendor() {
        @Override public Request vendRequest(final int page) {
          return buildRequest(page);
        }
      }, unmarshaler());
    }

    @NotNull protected abstract JSONUnmarshaler<T> unmarshaler();

    @NotNull protected abstract Request buildRequest(int page);

    @NotNull protected final RequestBody toRequestBody(@NotNull final JsonObject json, final int page) {
      final JSONObjectBuilder pagination = new JSONObjectBuilder()
          .add("page", page);
      if (perPage > 0) {
        pagination.add("per_page", perPage);
      }
      final JsonObject outJSON = new JSONObjectBuilder(json)
          .add("pagination", pagination)
          .build();

      return RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(outJSON));
    }

    @NotNull protected final HttpUrl buildURL(@NotNull final String path, final int page) {
      final HttpUrl.Builder urlBuilder = client.baseURL.newBuilder()
          .addPathSegments(path.charAt(0) == '/' ? path.substring(1) : path)
          .setQueryParameter("page", String.valueOf(page));
      if (perPage > 0) {
        urlBuilder.setQueryParameter("per_page", String.valueOf(perPage));
      }
      return urlBuilder.build();
    }
  }


  final class ClarifaiPaginatedRequestImpl<RESULT> implements ClarifaiPaginatedRequest<RESULT> {

    @NotNull private final BaseClarifaiClient client;
    @NotNull private final PaginatedRequestVendor requestVendor;
    @NotNull private final JSONUnmarshaler<RESULT> unmarshaler;

    ClarifaiPaginatedRequestImpl(
        @NotNull BaseClarifaiClient client,
        @NotNull PaginatedRequestVendor requestVendor,
        @NotNull JSONUnmarshaler<RESULT> unmarshaler
    ) {
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
      return new ClarifaiRequest.Impl<>(client, new ClarifaiRequest.DeserializedRequest<RESULT>() {
        @NotNull @Override public Request httpRequest() {
          return requestVendor.vendRequest(page);
        }

        @NotNull @Override public JSONUnmarshaler<RESULT> unmarshaler() {
          return unmarshaler;
        }
      });
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
}
