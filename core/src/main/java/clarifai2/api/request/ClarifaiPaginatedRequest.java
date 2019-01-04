package clarifai2.api.request;

import clarifai2.internal.grpc.api.V2Grpc;
import clarifai2.api.BaseClarifaiClient;
import clarifai2.api.ClarifaiClient;
import clarifai2.grpc.JsonChannel;
import clarifai2.internal.JSONObjectBuilder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;

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
  @NotNull ClarifaiRequest<RESULT> getPage(int page);

  abstract class Builder<T, SELF extends Builder<T, SELF>> implements ClarifaiPaginatedRequest<T> {

    @NotNull protected final Gson gson;
    @NotNull protected final BaseClarifaiClient client;

    private int perPage = 0;

    @NotNull protected V2Grpc.V2FutureStub stub(int page) {
      return V2Grpc.newFutureStub(new JsonChannel(client.httpClient))
          .withOption(JsonChannel.CLARIFAI_METHOD_OPTION, method())
          .withOption(JsonChannel.CLARIFAI_BASE_URL_OPTION, client.baseURL.toString())
          .withOption(JsonChannel.CLARIFAI_SUB_URL_OPTION, subUrl(page));
    }

    protected Builder(@NotNull BaseClarifaiClient client) {
      this.gson = client.gson;
      this.client = client;
    }

    public final int perPage() {
      return perPage;
    }

    @SuppressWarnings("unchecked")
    @NotNull public final SELF perPage(int perPage) {
      this.perPage = perPage;
      return ((SELF) this);
    }

    @NotNull @Override public final ClarifaiRequest<T> getPage(final int page) {
      return build().getPage(page);
    }

    @NotNull public final ClarifaiPaginatedRequest<T> build() {
      return new ClarifaiPaginatedRequestImpl<>(
          client,
          new PaginatedRequestVendor() {
              @Override public ListenableFuture vendRequestGrpc(final int page) {
                return buildRequestGrpc(page);
              }

              @Override public T vendResponseGrpc(Object returnedObject) {
                return unmarshalerGrpc(returnedObject);
              }
            });
    }

    @NotNull protected abstract String method();
    @NotNull protected abstract String subUrl(final int page);

    @NotNull protected abstract T unmarshalerGrpc(Object returnedObject);

    @NotNull protected abstract ListenableFuture buildRequestGrpc(int page);

    @NotNull protected final String buildURL(@NotNull String path, int page) {
      StringBuilder sb = new StringBuilder();
      sb.append(path);
      sb.append("?page=" + page);
      if (perPage > 0) {
        sb.append("&per_page=" + perPage);
      }
      return sb.toString();
    }
  }


  final class ClarifaiPaginatedRequestImpl<RESULT> implements ClarifaiPaginatedRequest<RESULT> {

    @NotNull private final BaseClarifaiClient client;
    @NotNull private final PaginatedRequestVendor<RESULT> requestVendor;

    ClarifaiPaginatedRequestImpl(
        @NotNull BaseClarifaiClient client,
        @NotNull PaginatedRequestVendor<RESULT> requestVendor
    ) {
      this.client = client;
      this.requestVendor = requestVendor;
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
        @NotNull @Override public ListenableFuture httpRequestGrpc() {
          return requestVendor.vendRequestGrpc(page);
        }

        @NotNull @Override public RESULT unmarshalerGrpc(Object returnedObject) {
          return requestVendor.vendResponseGrpc(returnedObject);
        }
      });
    }
  }
}
