package com.clarifai.api;

import com.google.gson.JsonElement;

/** Common envelope used by all Clarifai API responses. */
class BaseResponse {
  String statusCode;
  String statusMsg;
  JsonElement results;
}
