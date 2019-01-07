package clarifai2.api.request;

import com.google.common.util.concurrent.ListenableFuture;

interface PaginatedRequestVendor<T> {
  ListenableFuture vendRequestGrpc(int page);

  T vendResponseGrpc(Object returnedObject);
}
