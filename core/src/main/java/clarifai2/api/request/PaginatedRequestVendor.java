package clarifai2.api.request;

import okhttp3.Request;

interface PaginatedRequestVendor {
  Request vendRequest(int page);
}
