package clarifai2.internal.grpc.api;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.14.0)",
    comments = "Source: proto/clarifai/api/endpoint.proto")
public final class V2Grpc {

  private V2Grpc() {}

  public static final String SERVICE_NAME = "clarifai.api.V2";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse> getGetConceptCountsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConceptCounts",
      requestType = clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse> getGetConceptCountsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse> getGetConceptCountsMethod;
    if ((getGetConceptCountsMethod = V2Grpc.getGetConceptCountsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetConceptCountsMethod = V2Grpc.getGetConceptCountsMethod) == null) {
          V2Grpc.getGetConceptCountsMethod = getGetConceptCountsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetConceptCounts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetConceptCounts"))
                  .build();
          }
        }
     }
     return getGetConceptCountsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse> getGetConceptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConcept",
      requestType = clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse> getGetConceptMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest, clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse> getGetConceptMethod;
    if ((getGetConceptMethod = V2Grpc.getGetConceptMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetConceptMethod = V2Grpc.getGetConceptMethod) == null) {
          V2Grpc.getGetConceptMethod = getGetConceptMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest, clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetConcept"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetConcept"))
                  .build();
          }
        }
     }
     return getGetConceptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListConceptsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListConcepts",
      requestType = clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListConceptsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListConceptsMethod;
    if ((getListConceptsMethod = V2Grpc.getListConceptsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListConceptsMethod = V2Grpc.getListConceptsMethod) == null) {
          V2Grpc.getListConceptsMethod = getListConceptsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListConcepts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListConcepts"))
                  .build();
          }
        }
     }
     return getListConceptsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostConceptsSearchesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostConceptsSearches",
      requestType = clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostConceptsSearchesMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostConceptsSearchesMethod;
    if ((getPostConceptsSearchesMethod = V2Grpc.getPostConceptsSearchesMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostConceptsSearchesMethod = V2Grpc.getPostConceptsSearchesMethod) == null) {
          V2Grpc.getPostConceptsSearchesMethod = getPostConceptsSearchesMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostConceptsSearches"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostConceptsSearches"))
                  .build();
          }
        }
     }
     return getPostConceptsSearchesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostConceptsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostConcepts",
      requestType = clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostConceptsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostConceptsMethod;
    if ((getPostConceptsMethod = V2Grpc.getPostConceptsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostConceptsMethod = V2Grpc.getPostConceptsMethod) == null) {
          V2Grpc.getPostConceptsMethod = getPostConceptsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostConcepts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostConcepts"))
                  .build();
          }
        }
     }
     return getPostConceptsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPatchConceptsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PatchConcepts",
      requestType = clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPatchConceptsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPatchConceptsMethod;
    if ((getPatchConceptsMethod = V2Grpc.getPatchConceptsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPatchConceptsMethod = V2Grpc.getPatchConceptsMethod) == null) {
          V2Grpc.getPatchConceptsMethod = getPatchConceptsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PatchConcepts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PatchConcepts"))
                  .build();
          }
        }
     }
     return getPatchConceptsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest,
      clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse> getGetVocabMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVocab",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest.class,
      responseType = clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest,
      clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse> getGetVocabMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest, clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse> getGetVocabMethod;
    if ((getGetVocabMethod = V2Grpc.getGetVocabMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetVocabMethod = V2Grpc.getGetVocabMethod) == null) {
          V2Grpc.getGetVocabMethod = getGetVocabMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest, clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetVocab"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetVocab"))
                  .build();
          }
        }
     }
     return getGetVocabMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest,
      clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getListVocabsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListVocabs",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest.class,
      responseType = clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest,
      clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getListVocabsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest, clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getListVocabsMethod;
    if ((getListVocabsMethod = V2Grpc.getListVocabsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListVocabsMethod = V2Grpc.getListVocabsMethod) == null) {
          V2Grpc.getListVocabsMethod = getListVocabsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest, clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListVocabs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListVocabs"))
                  .build();
          }
        }
     }
     return getListVocabsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest,
      clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getPostVocabsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostVocabs",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest.class,
      responseType = clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest,
      clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getPostVocabsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest, clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getPostVocabsMethod;
    if ((getPostVocabsMethod = V2Grpc.getPostVocabsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostVocabsMethod = V2Grpc.getPostVocabsMethod) == null) {
          V2Grpc.getPostVocabsMethod = getPostVocabsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest, clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostVocabs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostVocabs"))
                  .build();
          }
        }
     }
     return getPostVocabsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest,
      clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getPatchVocabsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PatchVocabs",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest.class,
      responseType = clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest,
      clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getPatchVocabsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest, clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> getPatchVocabsMethod;
    if ((getPatchVocabsMethod = V2Grpc.getPatchVocabsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPatchVocabsMethod = V2Grpc.getPatchVocabsMethod) == null) {
          V2Grpc.getPatchVocabsMethod = getPatchVocabsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest, clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PatchVocabs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PatchVocabs"))
                  .build();
          }
        }
     }
     return getPatchVocabsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteVocab",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabMethod;
    if ((getDeleteVocabMethod = V2Grpc.getDeleteVocabMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteVocabMethod = V2Grpc.getDeleteVocabMethod) == null) {
          V2Grpc.getDeleteVocabMethod = getDeleteVocabMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteVocab"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteVocab"))
                  .build();
          }
        }
     }
     return getDeleteVocabMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteVocabs",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabsMethod;
    if ((getDeleteVocabsMethod = V2Grpc.getDeleteVocabsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteVocabsMethod = V2Grpc.getDeleteVocabsMethod) == null) {
          V2Grpc.getDeleteVocabsMethod = getDeleteVocabsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteVocabs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteVocabs"))
                  .build();
          }
        }
     }
     return getDeleteVocabsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListVocabConceptsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListVocabConcepts",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListVocabConceptsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListVocabConceptsMethod;
    if ((getListVocabConceptsMethod = V2Grpc.getListVocabConceptsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListVocabConceptsMethod = V2Grpc.getListVocabConceptsMethod) == null) {
          V2Grpc.getListVocabConceptsMethod = getListVocabConceptsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListVocabConcepts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListVocabConcepts"))
                  .build();
          }
        }
     }
     return getListVocabConceptsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostVocabConceptsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostVocabConcepts",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostVocabConceptsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getPostVocabConceptsMethod;
    if ((getPostVocabConceptsMethod = V2Grpc.getPostVocabConceptsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostVocabConceptsMethod = V2Grpc.getPostVocabConceptsMethod) == null) {
          V2Grpc.getPostVocabConceptsMethod = getPostVocabConceptsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostVocabConcepts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostVocabConcepts"))
                  .build();
          }
        }
     }
     return getPostVocabConceptsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabConceptMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteVocabConcept",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabConceptMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabConceptMethod;
    if ((getDeleteVocabConceptMethod = V2Grpc.getDeleteVocabConceptMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteVocabConceptMethod = V2Grpc.getDeleteVocabConceptMethod) == null) {
          V2Grpc.getDeleteVocabConceptMethod = getDeleteVocabConceptMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteVocabConcept"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteVocabConcept"))
                  .build();
          }
        }
     }
     return getDeleteVocabConceptMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabConceptsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteVocabConcepts",
      requestType = clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabConceptsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteVocabConceptsMethod;
    if ((getDeleteVocabConceptsMethod = V2Grpc.getDeleteVocabConceptsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteVocabConceptsMethod = V2Grpc.getDeleteVocabConceptsMethod) == null) {
          V2Grpc.getDeleteVocabConceptsMethod = getDeleteVocabConceptsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteVocabConcepts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteVocabConcepts"))
                  .build();
          }
        }
     }
     return getDeleteVocabConceptsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest,
      clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse> getGetConceptLanguageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetConceptLanguage",
      requestType = clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest,
      clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse> getGetConceptLanguageMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest, clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse> getGetConceptLanguageMethod;
    if ((getGetConceptLanguageMethod = V2Grpc.getGetConceptLanguageMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetConceptLanguageMethod = V2Grpc.getGetConceptLanguageMethod) == null) {
          V2Grpc.getGetConceptLanguageMethod = getGetConceptLanguageMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest, clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetConceptLanguage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetConceptLanguage"))
                  .build();
          }
        }
     }
     return getGetConceptLanguageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest,
      clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getListConceptLanguagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListConceptLanguages",
      requestType = clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest,
      clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getListConceptLanguagesMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest, clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getListConceptLanguagesMethod;
    if ((getListConceptLanguagesMethod = V2Grpc.getListConceptLanguagesMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListConceptLanguagesMethod = V2Grpc.getListConceptLanguagesMethod) == null) {
          V2Grpc.getListConceptLanguagesMethod = getListConceptLanguagesMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest, clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListConceptLanguages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListConceptLanguages"))
                  .build();
          }
        }
     }
     return getListConceptLanguagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest,
      clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getPostConceptLanguagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostConceptLanguages",
      requestType = clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest,
      clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getPostConceptLanguagesMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest, clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getPostConceptLanguagesMethod;
    if ((getPostConceptLanguagesMethod = V2Grpc.getPostConceptLanguagesMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostConceptLanguagesMethod = V2Grpc.getPostConceptLanguagesMethod) == null) {
          V2Grpc.getPostConceptLanguagesMethod = getPostConceptLanguagesMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest, clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostConceptLanguages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostConceptLanguages"))
                  .build();
          }
        }
     }
     return getPostConceptLanguagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest,
      clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getPatchConceptLanguagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PatchConceptLanguages",
      requestType = clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest,
      clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getPatchConceptLanguagesMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest, clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> getPatchConceptLanguagesMethod;
    if ((getPatchConceptLanguagesMethod = V2Grpc.getPatchConceptLanguagesMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPatchConceptLanguagesMethod = V2Grpc.getPatchConceptLanguagesMethod) == null) {
          V2Grpc.getPatchConceptLanguagesMethod = getPatchConceptLanguagesMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest, clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PatchConceptLanguages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PatchConceptLanguages"))
                  .build();
          }
        }
     }
     return getPatchConceptLanguagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest,
      clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse> getListConceptReferencesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListConceptReferences",
      requestType = clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest,
      clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse> getListConceptReferencesMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest, clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse> getListConceptReferencesMethod;
    if ((getListConceptReferencesMethod = V2Grpc.getListConceptReferencesMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListConceptReferencesMethod = V2Grpc.getListConceptReferencesMethod) == null) {
          V2Grpc.getListConceptReferencesMethod = getListConceptReferencesMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest, clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListConceptReferences"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListConceptReferences"))
                  .build();
          }
        }
     }
     return getListConceptReferencesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListConceptRelationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListConceptRelations",
      requestType = clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest.class,
      responseType = clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest,
      clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListConceptRelationsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> getListConceptRelationsMethod;
    if ((getListConceptRelationsMethod = V2Grpc.getListConceptRelationsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListConceptRelationsMethod = V2Grpc.getListConceptRelationsMethod) == null) {
          V2Grpc.getListConceptRelationsMethod = getListConceptRelationsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest, clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListConceptRelations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListConceptRelations"))
                  .build();
          }
        }
     }
     return getListConceptRelationsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest,
      clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse> getGetInputCountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetInputCount",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest.class,
      responseType = clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest,
      clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse> getGetInputCountMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest, clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse> getGetInputCountMethod;
    if ((getGetInputCountMethod = V2Grpc.getGetInputCountMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetInputCountMethod = V2Grpc.getGetInputCountMethod) == null) {
          V2Grpc.getGetInputCountMethod = getGetInputCountMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest, clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetInputCount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetInputCount"))
                  .build();
          }
        }
     }
     return getGetInputCountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getStreamInputsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamInputs",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest.class,
      responseType = clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getStreamInputsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getStreamInputsMethod;
    if ((getStreamInputsMethod = V2Grpc.getStreamInputsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getStreamInputsMethod = V2Grpc.getStreamInputsMethod) == null) {
          V2Grpc.getStreamInputsMethod = getStreamInputsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "StreamInputs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("StreamInputs"))
                  .build();
          }
        }
     }
     return getStreamInputsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest,
      clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse> getGetInputMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetInput",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest.class,
      responseType = clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest,
      clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse> getGetInputMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest, clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse> getGetInputMethod;
    if ((getGetInputMethod = V2Grpc.getGetInputMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetInputMethod = V2Grpc.getGetInputMethod) == null) {
          V2Grpc.getGetInputMethod = getGetInputMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest, clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetInput"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetInput"))
                  .build();
          }
        }
     }
     return getGetInputMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getListInputsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListInputs",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest.class,
      responseType = clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getListInputsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getListInputsMethod;
    if ((getListInputsMethod = V2Grpc.getListInputsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListInputsMethod = V2Grpc.getListInputsMethod) == null) {
          V2Grpc.getListInputsMethod = getListInputsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListInputs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListInputs"))
                  .build();
          }
        }
     }
     return getListInputsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getPostInputsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostInputs",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest.class,
      responseType = clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getPostInputsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getPostInputsMethod;
    if ((getPostInputsMethod = V2Grpc.getPostInputsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostInputsMethod = V2Grpc.getPostInputsMethod) == null) {
          V2Grpc.getPostInputsMethod = getPostInputsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostInputs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostInputs"))
                  .build();
          }
        }
     }
     return getPostInputsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getPatchInputsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PatchInputs",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest.class,
      responseType = clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getPatchInputsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getPatchInputsMethod;
    if ((getPatchInputsMethod = V2Grpc.getPatchInputsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPatchInputsMethod = V2Grpc.getPatchInputsMethod) == null) {
          V2Grpc.getPatchInputsMethod = getPatchInputsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PatchInputs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PatchInputs"))
                  .build();
          }
        }
     }
     return getPatchInputsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteInputMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteInput",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteInputMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteInputMethod;
    if ((getDeleteInputMethod = V2Grpc.getDeleteInputMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteInputMethod = V2Grpc.getDeleteInputMethod) == null) {
          V2Grpc.getDeleteInputMethod = getDeleteInputMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteInput"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteInput"))
                  .build();
          }
        }
     }
     return getDeleteInputMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteInputsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteInputs",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteInputsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteInputsMethod;
    if ((getDeleteInputsMethod = V2Grpc.getDeleteInputsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteInputsMethod = V2Grpc.getDeleteInputsMethod) == null) {
          V2Grpc.getDeleteInputsMethod = getDeleteInputsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteInputs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteInputs"))
                  .build();
          }
        }
     }
     return getDeleteInputsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest,
      clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse> getPostModelOutputsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostModelOutputs",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest.class,
      responseType = clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest,
      clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse> getPostModelOutputsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest, clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse> getPostModelOutputsMethod;
    if ((getPostModelOutputsMethod = V2Grpc.getPostModelOutputsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostModelOutputsMethod = V2Grpc.getPostModelOutputsMethod) == null) {
          V2Grpc.getPostModelOutputsMethod = getPostModelOutputsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest, clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostModelOutputs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostModelOutputs"))
                  .build();
          }
        }
     }
     return getPostModelOutputsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getPostModelFeedbackMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostModelFeedback",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getPostModelFeedbackMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getPostModelFeedbackMethod;
    if ((getPostModelFeedbackMethod = V2Grpc.getPostModelFeedbackMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostModelFeedbackMethod = V2Grpc.getPostModelFeedbackMethod) == null) {
          V2Grpc.getPostModelFeedbackMethod = getPostModelFeedbackMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostModelFeedback"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostModelFeedback"))
                  .build();
          }
        }
     }
     return getPostModelFeedbackMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getGetModelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetModel",
      requestType = clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getGetModelMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest, clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getGetModelMethod;
    if ((getGetModelMethod = V2Grpc.getGetModelMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetModelMethod = V2Grpc.getGetModelMethod) == null) {
          V2Grpc.getGetModelMethod = getGetModelMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest, clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetModel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetModel"))
                  .build();
          }
        }
     }
     return getGetModelMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getGetModelOutputInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetModelOutputInfo",
      requestType = clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getGetModelOutputInfoMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest, clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getGetModelOutputInfoMethod;
    if ((getGetModelOutputInfoMethod = V2Grpc.getGetModelOutputInfoMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetModelOutputInfoMethod = V2Grpc.getGetModelOutputInfoMethod) == null) {
          V2Grpc.getGetModelOutputInfoMethod = getGetModelOutputInfoMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest, clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetModelOutputInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetModelOutputInfo"))
                  .build();
          }
        }
     }
     return getGetModelOutputInfoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getListModelsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListModels",
      requestType = clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getListModelsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest, clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getListModelsMethod;
    if ((getListModelsMethod = V2Grpc.getListModelsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListModelsMethod = V2Grpc.getListModelsMethod) == null) {
          V2Grpc.getListModelsMethod = getListModelsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest, clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListModels"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListModels"))
                  .build();
          }
        }
     }
     return getListModelsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getPostModelsSearchesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostModelsSearches",
      requestType = clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getPostModelsSearchesMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest, clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getPostModelsSearchesMethod;
    if ((getPostModelsSearchesMethod = V2Grpc.getPostModelsSearchesMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostModelsSearchesMethod = V2Grpc.getPostModelsSearchesMethod) == null) {
          V2Grpc.getPostModelsSearchesMethod = getPostModelsSearchesMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest, clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostModelsSearches"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostModelsSearches"))
                  .build();
          }
        }
     }
     return getPostModelsSearchesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getPostModelsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostModels",
      requestType = clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getPostModelsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest, clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getPostModelsMethod;
    if ((getPostModelsMethod = V2Grpc.getPostModelsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostModelsMethod = V2Grpc.getPostModelsMethod) == null) {
          V2Grpc.getPostModelsMethod = getPostModelsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest, clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostModels"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostModels"))
                  .build();
          }
        }
     }
     return getPostModelsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getPatchModelsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PatchModels",
      requestType = clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getPatchModelsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest, clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> getPatchModelsMethod;
    if ((getPatchModelsMethod = V2Grpc.getPatchModelsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPatchModelsMethod = V2Grpc.getPatchModelsMethod) == null) {
          V2Grpc.getPatchModelsMethod = getPatchModelsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest, clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PatchModels"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PatchModels"))
                  .build();
          }
        }
     }
     return getPatchModelsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteModel",
      requestType = clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelMethod;
    if ((getDeleteModelMethod = V2Grpc.getDeleteModelMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteModelMethod = V2Grpc.getDeleteModelMethod) == null) {
          V2Grpc.getDeleteModelMethod = getDeleteModelMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteModel"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteModel"))
                  .build();
          }
        }
     }
     return getDeleteModelMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteModels",
      requestType = clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelsMethod;
    if ((getDeleteModelsMethod = V2Grpc.getDeleteModelsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteModelsMethod = V2Grpc.getDeleteModelsMethod) == null) {
          V2Grpc.getDeleteModelsMethod = getDeleteModelsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteModels"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteModels"))
                  .build();
          }
        }
     }
     return getDeleteModelsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getListModelInputsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListModelInputs",
      requestType = clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest.class,
      responseType = clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest,
      clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getListModelInputsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> getListModelInputsMethod;
    if ((getListModelInputsMethod = V2Grpc.getListModelInputsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListModelInputsMethod = V2Grpc.getListModelInputsMethod) == null) {
          V2Grpc.getListModelInputsMethod = getListModelInputsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest, clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListModelInputs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListModelInputs"))
                  .build();
          }
        }
     }
     return getListModelInputsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest,
      clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getGetModelVersionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetModelVersion",
      requestType = clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest,
      clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getGetModelVersionMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest, clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getGetModelVersionMethod;
    if ((getGetModelVersionMethod = V2Grpc.getGetModelVersionMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetModelVersionMethod = V2Grpc.getGetModelVersionMethod) == null) {
          V2Grpc.getGetModelVersionMethod = getGetModelVersionMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest, clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetModelVersion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetModelVersion"))
                  .build();
          }
        }
     }
     return getGetModelVersionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest,
      clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse> getListModelVersionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListModelVersions",
      requestType = clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest,
      clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse> getListModelVersionsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest, clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse> getListModelVersionsMethod;
    if ((getListModelVersionsMethod = V2Grpc.getListModelVersionsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListModelVersionsMethod = V2Grpc.getListModelVersionsMethod) == null) {
          V2Grpc.getListModelVersionsMethod = getListModelVersionsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest, clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListModelVersions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListModelVersions"))
                  .build();
          }
        }
     }
     return getListModelVersionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getPostModelVersionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostModelVersions",
      requestType = clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest,
      clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getPostModelVersionsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest, clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getPostModelVersionsMethod;
    if ((getPostModelVersionsMethod = V2Grpc.getPostModelVersionsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostModelVersionsMethod = V2Grpc.getPostModelVersionsMethod) == null) {
          V2Grpc.getPostModelVersionsMethod = getPostModelVersionsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest, clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostModelVersions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostModelVersions"))
                  .build();
          }
        }
     }
     return getPostModelVersionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelVersionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteModelVersion",
      requestType = clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelVersionMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteModelVersionMethod;
    if ((getDeleteModelVersionMethod = V2Grpc.getDeleteModelVersionMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteModelVersionMethod = V2Grpc.getDeleteModelVersionMethod) == null) {
          V2Grpc.getDeleteModelVersionMethod = getDeleteModelVersionMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteModelVersion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteModelVersion"))
                  .build();
          }
        }
     }
     return getDeleteModelVersionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest,
      clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getGetModelVersionMetricsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetModelVersionMetrics",
      requestType = clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest,
      clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getGetModelVersionMetricsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest, clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getGetModelVersionMetricsMethod;
    if ((getGetModelVersionMetricsMethod = V2Grpc.getGetModelVersionMetricsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetModelVersionMetricsMethod = V2Grpc.getGetModelVersionMetricsMethod) == null) {
          V2Grpc.getGetModelVersionMetricsMethod = getGetModelVersionMetricsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest, clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetModelVersionMetrics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetModelVersionMetrics"))
                  .build();
          }
        }
     }
     return getGetModelVersionMetricsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest,
      clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getPostModelVersionMetricsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostModelVersionMetrics",
      requestType = clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest.class,
      responseType = clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest,
      clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getPostModelVersionMetricsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest, clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getPostModelVersionMetricsMethod;
    if ((getPostModelVersionMetricsMethod = V2Grpc.getPostModelVersionMetricsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostModelVersionMetricsMethod = V2Grpc.getPostModelVersionMetricsMethod) == null) {
          V2Grpc.getPostModelVersionMetricsMethod = getPostModelVersionMetricsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest, clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostModelVersionMetrics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostModelVersionMetrics"))
                  .build();
          }
        }
     }
     return getPostModelVersionMetricsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse> getGetWorkflowMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetWorkflow",
      requestType = clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest.class,
      responseType = clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse> getGetWorkflowMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse> getGetWorkflowMethod;
    if ((getGetWorkflowMethod = V2Grpc.getGetWorkflowMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetWorkflowMethod = V2Grpc.getGetWorkflowMethod) == null) {
          V2Grpc.getGetWorkflowMethod = getGetWorkflowMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetWorkflow"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetWorkflow"))
                  .build();
          }
        }
     }
     return getGetWorkflowMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getListWorkflowsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListWorkflows",
      requestType = clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest.class,
      responseType = clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getListWorkflowsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getListWorkflowsMethod;
    if ((getListWorkflowsMethod = V2Grpc.getListWorkflowsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListWorkflowsMethod = V2Grpc.getListWorkflowsMethod) == null) {
          V2Grpc.getListWorkflowsMethod = getListWorkflowsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListWorkflows"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListWorkflows"))
                  .build();
          }
        }
     }
     return getListWorkflowsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getListPublicWorkflowsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListPublicWorkflows",
      requestType = clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest.class,
      responseType = clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getListPublicWorkflowsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getListPublicWorkflowsMethod;
    if ((getListPublicWorkflowsMethod = V2Grpc.getListPublicWorkflowsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListPublicWorkflowsMethod = V2Grpc.getListPublicWorkflowsMethod) == null) {
          V2Grpc.getListPublicWorkflowsMethod = getListPublicWorkflowsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListPublicWorkflows"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListPublicWorkflows"))
                  .build();
          }
        }
     }
     return getListPublicWorkflowsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getPostWorkflowsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostWorkflows",
      requestType = clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest.class,
      responseType = clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getPostWorkflowsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getPostWorkflowsMethod;
    if ((getPostWorkflowsMethod = V2Grpc.getPostWorkflowsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostWorkflowsMethod = V2Grpc.getPostWorkflowsMethod) == null) {
          V2Grpc.getPostWorkflowsMethod = getPostWorkflowsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostWorkflows"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostWorkflows"))
                  .build();
          }
        }
     }
     return getPostWorkflowsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getPatchWorkflowsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PatchWorkflows",
      requestType = clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest.class,
      responseType = clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getPatchWorkflowsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> getPatchWorkflowsMethod;
    if ((getPatchWorkflowsMethod = V2Grpc.getPatchWorkflowsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPatchWorkflowsMethod = V2Grpc.getPatchWorkflowsMethod) == null) {
          V2Grpc.getPatchWorkflowsMethod = getPatchWorkflowsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PatchWorkflows"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PatchWorkflows"))
                  .build();
          }
        }
     }
     return getPatchWorkflowsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteWorkflowMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteWorkflow",
      requestType = clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteWorkflowMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteWorkflowMethod;
    if ((getDeleteWorkflowMethod = V2Grpc.getDeleteWorkflowMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteWorkflowMethod = V2Grpc.getDeleteWorkflowMethod) == null) {
          V2Grpc.getDeleteWorkflowMethod = getDeleteWorkflowMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteWorkflow"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteWorkflow"))
                  .build();
          }
        }
     }
     return getDeleteWorkflowMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteWorkflowsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteWorkflows",
      requestType = clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteWorkflowsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getDeleteWorkflowsMethod;
    if ((getDeleteWorkflowsMethod = V2Grpc.getDeleteWorkflowsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getDeleteWorkflowsMethod = V2Grpc.getDeleteWorkflowsMethod) == null) {
          V2Grpc.getDeleteWorkflowsMethod = getDeleteWorkflowsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "DeleteWorkflows"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("DeleteWorkflows"))
                  .build();
          }
        }
     }
     return getDeleteWorkflowsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse> getPostWorkflowResultsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostWorkflowResults",
      requestType = clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest.class,
      responseType = clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest,
      clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse> getPostWorkflowResultsMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse> getPostWorkflowResultsMethod;
    if ((getPostWorkflowResultsMethod = V2Grpc.getPostWorkflowResultsMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostWorkflowResultsMethod = V2Grpc.getPostWorkflowResultsMethod) == null) {
          V2Grpc.getPostWorkflowResultsMethod = getPostWorkflowResultsMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest, clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostWorkflowResults"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostWorkflowResults"))
                  .build();
          }
        }
     }
     return getPostWorkflowResultsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Search.PostSearchesRequest,
      clarifai2.internal.grpc.api.Search.MultiSearchResponse> getPostSearchesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostSearches",
      requestType = clarifai2.internal.grpc.api.Search.PostSearchesRequest.class,
      responseType = clarifai2.internal.grpc.api.Search.MultiSearchResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Search.PostSearchesRequest,
      clarifai2.internal.grpc.api.Search.MultiSearchResponse> getPostSearchesMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Search.PostSearchesRequest, clarifai2.internal.grpc.api.Search.MultiSearchResponse> getPostSearchesMethod;
    if ((getPostSearchesMethod = V2Grpc.getPostSearchesMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostSearchesMethod = V2Grpc.getPostSearchesMethod) == null) {
          V2Grpc.getPostSearchesMethod = getPostSearchesMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Search.PostSearchesRequest, clarifai2.internal.grpc.api.Search.MultiSearchResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostSearches"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Search.PostSearchesRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Search.MultiSearchResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostSearches"))
                  .build();
          }
        }
     }
     return getPostSearchesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getPostSearchFeedbackMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostSearchFeedback",
      requestType = clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest.class,
      responseType = clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest,
      clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getPostSearchFeedbackMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> getPostSearchFeedbackMethod;
    if ((getPostSearchFeedbackMethod = V2Grpc.getPostSearchFeedbackMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostSearchFeedbackMethod = V2Grpc.getPostSearchFeedbackMethod) == null) {
          V2Grpc.getPostSearchFeedbackMethod = getPostSearchFeedbackMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest, clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostSearchFeedback"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostSearchFeedback"))
                  .build();
          }
        }
     }
     return getPostSearchFeedbackMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest,
      clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> getGetSubscriptionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSubscription",
      requestType = clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest.class,
      responseType = clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest,
      clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> getGetSubscriptionMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest, clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> getGetSubscriptionMethod;
    if ((getGetSubscriptionMethod = V2Grpc.getGetSubscriptionMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetSubscriptionMethod = V2Grpc.getGetSubscriptionMethod) == null) {
          V2Grpc.getGetSubscriptionMethod = getGetSubscriptionMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest, clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetSubscription"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetSubscription"))
                  .build();
          }
        }
     }
     return getGetSubscriptionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest,
      clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> getPostSubscriptionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostSubscription",
      requestType = clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest.class,
      responseType = clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest,
      clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> getPostSubscriptionMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest, clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> getPostSubscriptionMethod;
    if ((getPostSubscriptionMethod = V2Grpc.getPostSubscriptionMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostSubscriptionMethod = V2Grpc.getPostSubscriptionMethod) == null) {
          V2Grpc.getPostSubscriptionMethod = getPostSubscriptionMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest, clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostSubscription"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostSubscription"))
                  .build();
          }
        }
     }
     return getPostSubscriptionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest,
      clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getGetAppVisualizationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAppVisualization",
      requestType = clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest.class,
      responseType = clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest,
      clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getGetAppVisualizationMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest, clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getGetAppVisualizationMethod;
    if ((getGetAppVisualizationMethod = V2Grpc.getGetAppVisualizationMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetAppVisualizationMethod = V2Grpc.getGetAppVisualizationMethod) == null) {
          V2Grpc.getGetAppVisualizationMethod = getGetAppVisualizationMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest, clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetAppVisualization"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetAppVisualization"))
                  .build();
          }
        }
     }
     return getGetAppVisualizationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest,
      clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getGetVisualizationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetVisualization",
      requestType = clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest.class,
      responseType = clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest,
      clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getGetVisualizationMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest, clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getGetVisualizationMethod;
    if ((getGetVisualizationMethod = V2Grpc.getGetVisualizationMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetVisualizationMethod = V2Grpc.getGetVisualizationMethod) == null) {
          V2Grpc.getGetVisualizationMethod = getGetVisualizationMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest, clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetVisualization"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetVisualization"))
                  .build();
          }
        }
     }
     return getGetVisualizationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest,
      clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getPostVisualizationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostVisualization",
      requestType = clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest.class,
      responseType = clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest,
      clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getPostVisualizationMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest, clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getPostVisualizationMethod;
    if ((getPostVisualizationMethod = V2Grpc.getPostVisualizationMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getPostVisualizationMethod = V2Grpc.getPostVisualizationMethod) == null) {
          V2Grpc.getPostVisualizationMethod = getPostVisualizationMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest, clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "PostVisualization"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("PostVisualization"))
                  .build();
          }
        }
     }
     return getPostVisualizationMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Code.ListStatusCodesRequest,
      clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse> getListStatusCodesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListStatusCodes",
      requestType = clarifai2.internal.grpc.api.Code.ListStatusCodesRequest.class,
      responseType = clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Code.ListStatusCodesRequest,
      clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse> getListStatusCodesMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Code.ListStatusCodesRequest, clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse> getListStatusCodesMethod;
    if ((getListStatusCodesMethod = V2Grpc.getListStatusCodesMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getListStatusCodesMethod = V2Grpc.getListStatusCodesMethod) == null) {
          V2Grpc.getListStatusCodesMethod = getListStatusCodesMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Code.ListStatusCodesRequest, clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "ListStatusCodes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Code.ListStatusCodesRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("ListStatusCodes"))
                  .build();
          }
        }
     }
     return getListStatusCodesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Code.GetStatusCodeRequest,
      clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse> getGetStatusCodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStatusCode",
      requestType = clarifai2.internal.grpc.api.Code.GetStatusCodeRequest.class,
      responseType = clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Code.GetStatusCodeRequest,
      clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse> getGetStatusCodeMethod() {
    io.grpc.MethodDescriptor<clarifai2.internal.grpc.api.Code.GetStatusCodeRequest, clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse> getGetStatusCodeMethod;
    if ((getGetStatusCodeMethod = V2Grpc.getGetStatusCodeMethod) == null) {
      synchronized (V2Grpc.class) {
        if ((getGetStatusCodeMethod = V2Grpc.getGetStatusCodeMethod) == null) {
          V2Grpc.getGetStatusCodeMethod = getGetStatusCodeMethod = 
              io.grpc.MethodDescriptor.<clarifai2.internal.grpc.api.Code.GetStatusCodeRequest, clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "clarifai.api.V2", "GetStatusCode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Code.GetStatusCodeRequest.getDefaultInstance()))
              .setResponseMarshaller(clarifai2.grpc.JsonMarshaller.jsonMarshaller(
                  clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new V2MethodDescriptorSupplier("GetStatusCode"))
                  .build();
          }
        }
     }
     return getGetStatusCodeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static V2Stub newStub(io.grpc.Channel channel) {
    return new V2Stub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static V2BlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new V2BlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static V2FutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new V2FutureStub(channel);
  }

  /**
   */
  public static abstract class V2ImplBase implements io.grpc.BindableService {

    /**
     */
    public void getConceptCounts(clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetConceptCountsMethod(), responseObserver);
    }

    /**
     */
    public void getConcept(clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetConceptMethod(), responseObserver);
    }

    /**
     */
    public void listConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListConceptsMethod(), responseObserver);
    }

    /**
     */
    public void postConceptsSearches(clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostConceptsSearchesMethod(), responseObserver);
    }

    /**
     */
    public void postConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostConceptsMethod(), responseObserver);
    }

    /**
     */
    public void patchConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPatchConceptsMethod(), responseObserver);
    }

    /**
     */
    public void getVocab(clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetVocabMethod(), responseObserver);
    }

    /**
     */
    public void listVocabs(clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListVocabsMethod(), responseObserver);
    }

    /**
     */
    public void postVocabs(clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostVocabsMethod(), responseObserver);
    }

    /**
     */
    public void patchVocabs(clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPatchVocabsMethod(), responseObserver);
    }

    /**
     */
    public void deleteVocab(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteVocabMethod(), responseObserver);
    }

    /**
     */
    public void deleteVocabs(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteVocabsMethod(), responseObserver);
    }

    /**
     */
    public void listVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListVocabConceptsMethod(), responseObserver);
    }

    /**
     */
    public void postVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostVocabConceptsMethod(), responseObserver);
    }

    /**
     */
    public void deleteVocabConcept(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteVocabConceptMethod(), responseObserver);
    }

    /**
     */
    public void deleteVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteVocabConceptsMethod(), responseObserver);
    }

    /**
     */
    public void getConceptLanguage(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetConceptLanguageMethod(), responseObserver);
    }

    /**
     */
    public void listConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListConceptLanguagesMethod(), responseObserver);
    }

    /**
     */
    public void postConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostConceptLanguagesMethod(), responseObserver);
    }

    /**
     */
    public void patchConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPatchConceptLanguagesMethod(), responseObserver);
    }

    /**
     */
    public void listConceptReferences(clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListConceptReferencesMethod(), responseObserver);
    }

    /**
     */
    public void listConceptRelations(clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListConceptRelationsMethod(), responseObserver);
    }

    /**
     */
    public void getInputCount(clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetInputCountMethod(), responseObserver);
    }

    /**
     */
    public void streamInputs(clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getStreamInputsMethod(), responseObserver);
    }

    /**
     */
    public void getInput(clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetInputMethod(), responseObserver);
    }

    /**
     */
    public void listInputs(clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListInputsMethod(), responseObserver);
    }

    /**
     */
    public void postInputs(clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostInputsMethod(), responseObserver);
    }

    /**
     */
    public void patchInputs(clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPatchInputsMethod(), responseObserver);
    }

    /**
     */
    public void deleteInput(clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteInputMethod(), responseObserver);
    }

    /**
     */
    public void deleteInputs(clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteInputsMethod(), responseObserver);
    }

    /**
     */
    public void postModelOutputs(clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostModelOutputsMethod(), responseObserver);
    }

    /**
     */
    public void postModelFeedback(clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostModelFeedbackMethod(), responseObserver);
    }

    /**
     */
    public void getModel(clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetModelMethod(), responseObserver);
    }

    /**
     */
    public void getModelOutputInfo(clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetModelOutputInfoMethod(), responseObserver);
    }

    /**
     */
    public void listModels(clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListModelsMethod(), responseObserver);
    }

    /**
     */
    public void postModelsSearches(clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostModelsSearchesMethod(), responseObserver);
    }

    /**
     */
    public void postModels(clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostModelsMethod(), responseObserver);
    }

    /**
     */
    public void patchModels(clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPatchModelsMethod(), responseObserver);
    }

    /**
     */
    public void deleteModel(clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteModelMethod(), responseObserver);
    }

    /**
     */
    public void deleteModels(clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteModelsMethod(), responseObserver);
    }

    /**
     */
    public void listModelInputs(clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListModelInputsMethod(), responseObserver);
    }

    /**
     */
    public void getModelVersion(clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetModelVersionMethod(), responseObserver);
    }

    /**
     */
    public void listModelVersions(clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListModelVersionsMethod(), responseObserver);
    }

    /**
     */
    public void postModelVersions(clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostModelVersionsMethod(), responseObserver);
    }

    /**
     */
    public void deleteModelVersion(clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteModelVersionMethod(), responseObserver);
    }

    /**
     */
    public void getModelVersionMetrics(clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetModelVersionMetricsMethod(), responseObserver);
    }

    /**
     */
    public void postModelVersionMetrics(clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostModelVersionMetricsMethod(), responseObserver);
    }

    /**
     */
    public void getWorkflow(clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetWorkflowMethod(), responseObserver);
    }

    /**
     */
    public void listWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListWorkflowsMethod(), responseObserver);
    }

    /**
     */
    public void listPublicWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListPublicWorkflowsMethod(), responseObserver);
    }

    /**
     */
    public void postWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostWorkflowsMethod(), responseObserver);
    }

    /**
     */
    public void patchWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPatchWorkflowsMethod(), responseObserver);
    }

    /**
     */
    public void deleteWorkflow(clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteWorkflowMethod(), responseObserver);
    }

    /**
     */
    public void deleteWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteWorkflowsMethod(), responseObserver);
    }

    /**
     */
    public void postWorkflowResults(clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostWorkflowResultsMethod(), responseObserver);
    }

    /**
     */
    public void postSearches(clarifai2.internal.grpc.api.Search.PostSearchesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Search.MultiSearchResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostSearchesMethod(), responseObserver);
    }

    /**
     */
    public void postSearchFeedback(clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostSearchFeedbackMethod(), responseObserver);
    }

    /**
     */
    public void getSubscription(clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetSubscriptionMethod(), responseObserver);
    }

    /**
     */
    public void postSubscription(clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostSubscriptionMethod(), responseObserver);
    }

    /**
     */
    public void getAppVisualization(clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAppVisualizationMethod(), responseObserver);
    }

    /**
     */
    public void getVisualization(clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetVisualizationMethod(), responseObserver);
    }

    /**
     */
    public void postVisualization(clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPostVisualizationMethod(), responseObserver);
    }

    /**
     */
    public void listStatusCodes(clarifai2.internal.grpc.api.Code.ListStatusCodesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getListStatusCodesMethod(), responseObserver);
    }

    /**
     */
    public void getStatusCode(clarifai2.internal.grpc.api.Code.GetStatusCodeRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStatusCodeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetConceptCountsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse>(
                  this, METHODID_GET_CONCEPT_COUNTS)))
          .addMethod(
            getGetConceptMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse>(
                  this, METHODID_GET_CONCEPT)))
          .addMethod(
            getListConceptsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>(
                  this, METHODID_LIST_CONCEPTS)))
          .addMethod(
            getPostConceptsSearchesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>(
                  this, METHODID_POST_CONCEPTS_SEARCHES)))
          .addMethod(
            getPostConceptsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>(
                  this, METHODID_POST_CONCEPTS)))
          .addMethod(
            getPatchConceptsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>(
                  this, METHODID_PATCH_CONCEPTS)))
          .addMethod(
            getGetVocabMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest,
                clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse>(
                  this, METHODID_GET_VOCAB)))
          .addMethod(
            getListVocabsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest,
                clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>(
                  this, METHODID_LIST_VOCABS)))
          .addMethod(
            getPostVocabsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest,
                clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>(
                  this, METHODID_POST_VOCABS)))
          .addMethod(
            getPatchVocabsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest,
                clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>(
                  this, METHODID_PATCH_VOCABS)))
          .addMethod(
            getDeleteVocabMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_VOCAB)))
          .addMethod(
            getDeleteVocabsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_VOCABS)))
          .addMethod(
            getListVocabConceptsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>(
                  this, METHODID_LIST_VOCAB_CONCEPTS)))
          .addMethod(
            getPostVocabConceptsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>(
                  this, METHODID_POST_VOCAB_CONCEPTS)))
          .addMethod(
            getDeleteVocabConceptMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_VOCAB_CONCEPT)))
          .addMethod(
            getDeleteVocabConceptsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_VOCAB_CONCEPTS)))
          .addMethod(
            getGetConceptLanguageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest,
                clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse>(
                  this, METHODID_GET_CONCEPT_LANGUAGE)))
          .addMethod(
            getListConceptLanguagesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest,
                clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>(
                  this, METHODID_LIST_CONCEPT_LANGUAGES)))
          .addMethod(
            getPostConceptLanguagesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest,
                clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>(
                  this, METHODID_POST_CONCEPT_LANGUAGES)))
          .addMethod(
            getPatchConceptLanguagesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest,
                clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>(
                  this, METHODID_PATCH_CONCEPT_LANGUAGES)))
          .addMethod(
            getListConceptReferencesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest,
                clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse>(
                  this, METHODID_LIST_CONCEPT_REFERENCES)))
          .addMethod(
            getListConceptRelationsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest,
                clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>(
                  this, METHODID_LIST_CONCEPT_RELATIONS)))
          .addMethod(
            getGetInputCountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest,
                clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse>(
                  this, METHODID_GET_INPUT_COUNT)))
          .addMethod(
            getStreamInputsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest,
                clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>(
                  this, METHODID_STREAM_INPUTS)))
          .addMethod(
            getGetInputMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest,
                clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse>(
                  this, METHODID_GET_INPUT)))
          .addMethod(
            getListInputsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest,
                clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>(
                  this, METHODID_LIST_INPUTS)))
          .addMethod(
            getPostInputsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest,
                clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>(
                  this, METHODID_POST_INPUTS)))
          .addMethod(
            getPatchInputsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest,
                clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>(
                  this, METHODID_PATCH_INPUTS)))
          .addMethod(
            getDeleteInputMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_INPUT)))
          .addMethod(
            getDeleteInputsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_INPUTS)))
          .addMethod(
            getPostModelOutputsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest,
                clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse>(
                  this, METHODID_POST_MODEL_OUTPUTS)))
          .addMethod(
            getPostModelFeedbackMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_POST_MODEL_FEEDBACK)))
          .addMethod(
            getGetModelMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest,
                clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>(
                  this, METHODID_GET_MODEL)))
          .addMethod(
            getGetModelOutputInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest,
                clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>(
                  this, METHODID_GET_MODEL_OUTPUT_INFO)))
          .addMethod(
            getListModelsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest,
                clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>(
                  this, METHODID_LIST_MODELS)))
          .addMethod(
            getPostModelsSearchesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest,
                clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>(
                  this, METHODID_POST_MODELS_SEARCHES)))
          .addMethod(
            getPostModelsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest,
                clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>(
                  this, METHODID_POST_MODELS)))
          .addMethod(
            getPatchModelsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest,
                clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>(
                  this, METHODID_PATCH_MODELS)))
          .addMethod(
            getDeleteModelMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_MODEL)))
          .addMethod(
            getDeleteModelsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_MODELS)))
          .addMethod(
            getListModelInputsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest,
                clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>(
                  this, METHODID_LIST_MODEL_INPUTS)))
          .addMethod(
            getGetModelVersionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest,
                clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>(
                  this, METHODID_GET_MODEL_VERSION)))
          .addMethod(
            getListModelVersionsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest,
                clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse>(
                  this, METHODID_LIST_MODEL_VERSIONS)))
          .addMethod(
            getPostModelVersionsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest,
                clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>(
                  this, METHODID_POST_MODEL_VERSIONS)))
          .addMethod(
            getDeleteModelVersionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_MODEL_VERSION)))
          .addMethod(
            getGetModelVersionMetricsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest,
                clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>(
                  this, METHODID_GET_MODEL_VERSION_METRICS)))
          .addMethod(
            getPostModelVersionMetricsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest,
                clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>(
                  this, METHODID_POST_MODEL_VERSION_METRICS)))
          .addMethod(
            getGetWorkflowMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest,
                clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse>(
                  this, METHODID_GET_WORKFLOW)))
          .addMethod(
            getListWorkflowsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest,
                clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>(
                  this, METHODID_LIST_WORKFLOWS)))
          .addMethod(
            getListPublicWorkflowsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest,
                clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>(
                  this, METHODID_LIST_PUBLIC_WORKFLOWS)))
          .addMethod(
            getPostWorkflowsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest,
                clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>(
                  this, METHODID_POST_WORKFLOWS)))
          .addMethod(
            getPatchWorkflowsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest,
                clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>(
                  this, METHODID_PATCH_WORKFLOWS)))
          .addMethod(
            getDeleteWorkflowMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_WORKFLOW)))
          .addMethod(
            getDeleteWorkflowsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_DELETE_WORKFLOWS)))
          .addMethod(
            getPostWorkflowResultsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest,
                clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse>(
                  this, METHODID_POST_WORKFLOW_RESULTS)))
          .addMethod(
            getPostSearchesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Search.PostSearchesRequest,
                clarifai2.internal.grpc.api.Search.MultiSearchResponse>(
                  this, METHODID_POST_SEARCHES)))
          .addMethod(
            getPostSearchFeedbackMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest,
                clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>(
                  this, METHODID_POST_SEARCH_FEEDBACK)))
          .addMethod(
            getGetSubscriptionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest,
                clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse>(
                  this, METHODID_GET_SUBSCRIPTION)))
          .addMethod(
            getPostSubscriptionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest,
                clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse>(
                  this, METHODID_POST_SUBSCRIPTION)))
          .addMethod(
            getGetAppVisualizationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest,
                clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>(
                  this, METHODID_GET_APP_VISUALIZATION)))
          .addMethod(
            getGetVisualizationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest,
                clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>(
                  this, METHODID_GET_VISUALIZATION)))
          .addMethod(
            getPostVisualizationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest,
                clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>(
                  this, METHODID_POST_VISUALIZATION)))
          .addMethod(
            getListStatusCodesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Code.ListStatusCodesRequest,
                clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse>(
                  this, METHODID_LIST_STATUS_CODES)))
          .addMethod(
            getGetStatusCodeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                clarifai2.internal.grpc.api.Code.GetStatusCodeRequest,
                clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse>(
                  this, METHODID_GET_STATUS_CODE)))
          .build();
    }
  }

  /**
   */
  public static final class V2Stub extends io.grpc.stub.AbstractStub<V2Stub> {
    private V2Stub(io.grpc.Channel channel) {
      super(channel);
    }

    private V2Stub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected V2Stub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new V2Stub(channel, callOptions);
    }

    /**
     */
    public void getConceptCounts(clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetConceptCountsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConcept(clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetConceptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListConceptsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postConceptsSearches(clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostConceptsSearchesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostConceptsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void patchConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPatchConceptsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getVocab(clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVocabMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listVocabs(clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListVocabsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postVocabs(clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostVocabsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void patchVocabs(clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPatchVocabsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteVocab(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteVocabMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteVocabs(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteVocabsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListVocabConceptsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostVocabConceptsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteVocabConcept(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteVocabConceptMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteVocabConceptsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getConceptLanguage(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetConceptLanguageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListConceptLanguagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostConceptLanguagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void patchConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPatchConceptLanguagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listConceptReferences(clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListConceptReferencesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listConceptRelations(clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListConceptRelationsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getInputCount(clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetInputCountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void streamInputs(clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStreamInputsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getInput(clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetInputMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listInputs(clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListInputsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postInputs(clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostInputsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void patchInputs(clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPatchInputsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteInput(clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteInputMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteInputs(clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteInputsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postModelOutputs(clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostModelOutputsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postModelFeedback(clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostModelFeedbackMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getModel(clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetModelMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getModelOutputInfo(clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetModelOutputInfoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listModels(clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListModelsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postModelsSearches(clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostModelsSearchesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postModels(clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostModelsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void patchModels(clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPatchModelsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteModel(clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteModelMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteModels(clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteModelsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listModelInputs(clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListModelInputsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getModelVersion(clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetModelVersionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listModelVersions(clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListModelVersionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postModelVersions(clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostModelVersionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteModelVersion(clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteModelVersionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getModelVersionMetrics(clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetModelVersionMetricsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postModelVersionMetrics(clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostModelVersionMetricsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getWorkflow(clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetWorkflowMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListWorkflowsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listPublicWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListPublicWorkflowsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostWorkflowsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void patchWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPatchWorkflowsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteWorkflow(clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteWorkflowMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteWorkflowsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postWorkflowResults(clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostWorkflowResultsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postSearches(clarifai2.internal.grpc.api.Search.PostSearchesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Search.MultiSearchResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostSearchesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postSearchFeedback(clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostSearchFeedbackMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSubscription(clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetSubscriptionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postSubscription(clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostSubscriptionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAppVisualization(clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAppVisualizationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getVisualization(clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVisualizationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postVisualization(clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostVisualizationMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listStatusCodes(clarifai2.internal.grpc.api.Code.ListStatusCodesRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListStatusCodesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStatusCode(clarifai2.internal.grpc.api.Code.GetStatusCodeRequest request,
        io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetStatusCodeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class V2BlockingStub extends io.grpc.stub.AbstractStub<V2BlockingStub> {
    private V2BlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private V2BlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected V2BlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new V2BlockingStub(channel, callOptions);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse getConceptCounts(clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetConceptCountsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse getConcept(clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetConceptMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse listConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListConceptsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse postConceptsSearches(clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostConceptsSearchesMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse postConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostConceptsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse patchConcepts(clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPatchConceptsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse getVocab(clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVocabMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse listVocabs(clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListVocabsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse postVocabs(clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostVocabsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse patchVocabs(clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPatchVocabsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteVocab(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteVocabMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteVocabs(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteVocabsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse listVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListVocabConceptsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse postVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostVocabConceptsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteVocabConcept(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteVocabConceptMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteVocabConcepts(clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteVocabConceptsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse getConceptLanguage(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetConceptLanguageMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse listConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest request) {
      return blockingUnaryCall(
          getChannel(), getListConceptLanguagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse postConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostConceptLanguagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse patchConceptLanguages(clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest request) {
      return blockingUnaryCall(
          getChannel(), getPatchConceptLanguagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse listConceptReferences(clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest request) {
      return blockingUnaryCall(
          getChannel(), getListConceptReferencesMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse listConceptRelations(clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListConceptRelationsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse getInputCount(clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetInputCountMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse streamInputs(clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest request) {
      return blockingUnaryCall(
          getChannel(), getStreamInputsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse getInput(clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetInputMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse listInputs(clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListInputsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse postInputs(clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostInputsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse patchInputs(clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPatchInputsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteInput(clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteInputMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteInputs(clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteInputsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse postModelOutputs(clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostModelOutputsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse postModelFeedback(clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostModelFeedbackMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse getModel(clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetModelMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse getModelOutputInfo(clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetModelOutputInfoMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse listModels(clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListModelsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse postModelsSearches(clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostModelsSearchesMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse postModels(clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostModelsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse patchModels(clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPatchModelsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteModel(clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteModelMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteModels(clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteModelsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse listModelInputs(clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListModelInputsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse getModelVersion(clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetModelVersionMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse listModelVersions(clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListModelVersionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse postModelVersions(clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostModelVersionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteModelVersion(clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteModelVersionMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse getModelVersionMetrics(clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetModelVersionMetricsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse postModelVersionMetrics(clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostModelVersionMetricsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse getWorkflow(clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetWorkflowMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse listWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListWorkflowsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse listPublicWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest request) {
      return blockingUnaryCall(
          getChannel(), getListPublicWorkflowsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse postWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostWorkflowsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse patchWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPatchWorkflowsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteWorkflow(clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteWorkflowMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse deleteWorkflows(clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteWorkflowsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse postWorkflowResults(clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostWorkflowResultsMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.Search.MultiSearchResponse postSearches(clarifai2.internal.grpc.api.Search.PostSearchesRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostSearchesMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse postSearchFeedback(clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostSearchFeedbackMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse getSubscription(clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetSubscriptionMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse postSubscription(clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostSubscriptionMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse getAppVisualization(clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAppVisualizationMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse getVisualization(clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetVisualizationMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse postVisualization(clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest request) {
      return blockingUnaryCall(
          getChannel(), getPostVisualizationMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse listStatusCodes(clarifai2.internal.grpc.api.Code.ListStatusCodesRequest request) {
      return blockingUnaryCall(
          getChannel(), getListStatusCodesMethod(), getCallOptions(), request);
    }

    /**
     */
    public clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse getStatusCode(clarifai2.internal.grpc.api.Code.GetStatusCodeRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetStatusCodeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class V2FutureStub extends io.grpc.stub.AbstractStub<V2FutureStub> {
    private V2FutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private V2FutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected V2FutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new V2FutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse> getConceptCounts(
        clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetConceptCountsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse> getConcept(
        clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetConceptMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> listConcepts(
        clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListConceptsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> postConceptsSearches(
        clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostConceptsSearchesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> postConcepts(
        clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostConceptsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> patchConcepts(
        clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPatchConceptsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse> getVocab(
        clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVocabMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> listVocabs(
        clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListVocabsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> postVocabs(
        clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostVocabsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse> patchVocabs(
        clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPatchVocabsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteVocab(
        clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteVocabMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteVocabs(
        clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteVocabsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> listVocabConcepts(
        clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListVocabConceptsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> postVocabConcepts(
        clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostVocabConceptsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteVocabConcept(
        clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteVocabConceptMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteVocabConcepts(
        clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteVocabConceptsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse> getConceptLanguage(
        clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetConceptLanguageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> listConceptLanguages(
        clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListConceptLanguagesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> postConceptLanguages(
        clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostConceptLanguagesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse> patchConceptLanguages(
        clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPatchConceptLanguagesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse> listConceptReferences(
        clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListConceptReferencesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse> listConceptRelations(
        clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListConceptRelationsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse> getInputCount(
        clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetInputCountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> streamInputs(
        clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStreamInputsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse> getInput(
        clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetInputMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> listInputs(
        clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListInputsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> postInputs(
        clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostInputsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> patchInputs(
        clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPatchInputsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteInput(
        clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteInputMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteInputs(
        clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteInputsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse> postModelOutputs(
        clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostModelOutputsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> postModelFeedback(
        clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostModelFeedbackMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getModel(
        clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetModelMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> getModelOutputInfo(
        clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetModelOutputInfoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> listModels(
        clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListModelsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> postModelsSearches(
        clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostModelsSearchesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> postModels(
        clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostModelsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse> patchModels(
        clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPatchModelsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteModel(
        clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteModelMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteModels(
        clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteModelsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse> listModelInputs(
        clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListModelInputsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getModelVersion(
        clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetModelVersionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse> listModelVersions(
        clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListModelVersionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse> postModelVersions(
        clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostModelVersionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteModelVersion(
        clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteModelVersionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> getModelVersionMetrics(
        clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetModelVersionMetricsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse> postModelVersionMetrics(
        clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostModelVersionMetricsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse> getWorkflow(
        clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetWorkflowMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> listWorkflows(
        clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListWorkflowsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> listPublicWorkflows(
        clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListPublicWorkflowsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> postWorkflows(
        clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostWorkflowsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse> patchWorkflows(
        clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPatchWorkflowsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteWorkflow(
        clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteWorkflowMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> deleteWorkflows(
        clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteWorkflowsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse> postWorkflowResults(
        clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostWorkflowResultsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.Search.MultiSearchResponse> postSearches(
        clarifai2.internal.grpc.api.Search.PostSearchesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostSearchesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse> postSearchFeedback(
        clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostSearchFeedbackMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> getSubscription(
        clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetSubscriptionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse> postSubscription(
        clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostSubscriptionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getAppVisualization(
        clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAppVisualizationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> getVisualization(
        clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVisualizationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse> postVisualization(
        clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPostVisualizationMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse> listStatusCodes(
        clarifai2.internal.grpc.api.Code.ListStatusCodesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getListStatusCodesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse> getStatusCode(
        clarifai2.internal.grpc.api.Code.GetStatusCodeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetStatusCodeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CONCEPT_COUNTS = 0;
  private static final int METHODID_GET_CONCEPT = 1;
  private static final int METHODID_LIST_CONCEPTS = 2;
  private static final int METHODID_POST_CONCEPTS_SEARCHES = 3;
  private static final int METHODID_POST_CONCEPTS = 4;
  private static final int METHODID_PATCH_CONCEPTS = 5;
  private static final int METHODID_GET_VOCAB = 6;
  private static final int METHODID_LIST_VOCABS = 7;
  private static final int METHODID_POST_VOCABS = 8;
  private static final int METHODID_PATCH_VOCABS = 9;
  private static final int METHODID_DELETE_VOCAB = 10;
  private static final int METHODID_DELETE_VOCABS = 11;
  private static final int METHODID_LIST_VOCAB_CONCEPTS = 12;
  private static final int METHODID_POST_VOCAB_CONCEPTS = 13;
  private static final int METHODID_DELETE_VOCAB_CONCEPT = 14;
  private static final int METHODID_DELETE_VOCAB_CONCEPTS = 15;
  private static final int METHODID_GET_CONCEPT_LANGUAGE = 16;
  private static final int METHODID_LIST_CONCEPT_LANGUAGES = 17;
  private static final int METHODID_POST_CONCEPT_LANGUAGES = 18;
  private static final int METHODID_PATCH_CONCEPT_LANGUAGES = 19;
  private static final int METHODID_LIST_CONCEPT_REFERENCES = 20;
  private static final int METHODID_LIST_CONCEPT_RELATIONS = 21;
  private static final int METHODID_GET_INPUT_COUNT = 22;
  private static final int METHODID_STREAM_INPUTS = 23;
  private static final int METHODID_GET_INPUT = 24;
  private static final int METHODID_LIST_INPUTS = 25;
  private static final int METHODID_POST_INPUTS = 26;
  private static final int METHODID_PATCH_INPUTS = 27;
  private static final int METHODID_DELETE_INPUT = 28;
  private static final int METHODID_DELETE_INPUTS = 29;
  private static final int METHODID_POST_MODEL_OUTPUTS = 30;
  private static final int METHODID_POST_MODEL_FEEDBACK = 31;
  private static final int METHODID_GET_MODEL = 32;
  private static final int METHODID_GET_MODEL_OUTPUT_INFO = 33;
  private static final int METHODID_LIST_MODELS = 34;
  private static final int METHODID_POST_MODELS_SEARCHES = 35;
  private static final int METHODID_POST_MODELS = 36;
  private static final int METHODID_PATCH_MODELS = 37;
  private static final int METHODID_DELETE_MODEL = 38;
  private static final int METHODID_DELETE_MODELS = 39;
  private static final int METHODID_LIST_MODEL_INPUTS = 40;
  private static final int METHODID_GET_MODEL_VERSION = 41;
  private static final int METHODID_LIST_MODEL_VERSIONS = 42;
  private static final int METHODID_POST_MODEL_VERSIONS = 43;
  private static final int METHODID_DELETE_MODEL_VERSION = 44;
  private static final int METHODID_GET_MODEL_VERSION_METRICS = 45;
  private static final int METHODID_POST_MODEL_VERSION_METRICS = 46;
  private static final int METHODID_GET_WORKFLOW = 47;
  private static final int METHODID_LIST_WORKFLOWS = 48;
  private static final int METHODID_LIST_PUBLIC_WORKFLOWS = 49;
  private static final int METHODID_POST_WORKFLOWS = 50;
  private static final int METHODID_PATCH_WORKFLOWS = 51;
  private static final int METHODID_DELETE_WORKFLOW = 52;
  private static final int METHODID_DELETE_WORKFLOWS = 53;
  private static final int METHODID_POST_WORKFLOW_RESULTS = 54;
  private static final int METHODID_POST_SEARCHES = 55;
  private static final int METHODID_POST_SEARCH_FEEDBACK = 56;
  private static final int METHODID_GET_SUBSCRIPTION = 57;
  private static final int METHODID_POST_SUBSCRIPTION = 58;
  private static final int METHODID_GET_APP_VISUALIZATION = 59;
  private static final int METHODID_GET_VISUALIZATION = 60;
  private static final int METHODID_POST_VISUALIZATION = 61;
  private static final int METHODID_LIST_STATUS_CODES = 62;
  private static final int METHODID_GET_STATUS_CODE = 63;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final V2ImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(V2ImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CONCEPT_COUNTS:
          serviceImpl.getConceptCounts((clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptCountsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptCountResponse>) responseObserver);
          break;
        case METHODID_GET_CONCEPT:
          serviceImpl.getConcept((clarifai2.internal.grpc.api.ConceptOuterClass.GetConceptRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.SingleConceptResponse>) responseObserver);
          break;
        case METHODID_LIST_CONCEPTS:
          serviceImpl.listConcepts((clarifai2.internal.grpc.api.ConceptOuterClass.ListConceptsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>) responseObserver);
          break;
        case METHODID_POST_CONCEPTS_SEARCHES:
          serviceImpl.postConceptsSearches((clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsSearchesRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>) responseObserver);
          break;
        case METHODID_POST_CONCEPTS:
          serviceImpl.postConcepts((clarifai2.internal.grpc.api.ConceptOuterClass.PostConceptsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>) responseObserver);
          break;
        case METHODID_PATCH_CONCEPTS:
          serviceImpl.patchConcepts((clarifai2.internal.grpc.api.ConceptOuterClass.PatchConceptsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>) responseObserver);
          break;
        case METHODID_GET_VOCAB:
          serviceImpl.getVocab((clarifai2.internal.grpc.api.VocabOuterClass.GetVocabRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.SingleVocabResponse>) responseObserver);
          break;
        case METHODID_LIST_VOCABS:
          serviceImpl.listVocabs((clarifai2.internal.grpc.api.VocabOuterClass.ListVocabsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>) responseObserver);
          break;
        case METHODID_POST_VOCABS:
          serviceImpl.postVocabs((clarifai2.internal.grpc.api.VocabOuterClass.PostVocabsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>) responseObserver);
          break;
        case METHODID_PATCH_VOCABS:
          serviceImpl.patchVocabs((clarifai2.internal.grpc.api.VocabOuterClass.PatchVocabsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.VocabOuterClass.MultiVocabResponse>) responseObserver);
          break;
        case METHODID_DELETE_VOCAB:
          serviceImpl.deleteVocab((clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_DELETE_VOCABS:
          serviceImpl.deleteVocabs((clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_LIST_VOCAB_CONCEPTS:
          serviceImpl.listVocabConcepts((clarifai2.internal.grpc.api.VocabOuterClass.ListVocabConceptsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>) responseObserver);
          break;
        case METHODID_POST_VOCAB_CONCEPTS:
          serviceImpl.postVocabConcepts((clarifai2.internal.grpc.api.VocabOuterClass.PostVocabConceptsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>) responseObserver);
          break;
        case METHODID_DELETE_VOCAB_CONCEPT:
          serviceImpl.deleteVocabConcept((clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_DELETE_VOCAB_CONCEPTS:
          serviceImpl.deleteVocabConcepts((clarifai2.internal.grpc.api.VocabOuterClass.DeleteVocabConceptsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_GET_CONCEPT_LANGUAGE:
          serviceImpl.getConceptLanguage((clarifai2.internal.grpc.api.ConceptLanguageOuterClass.GetConceptLanguageRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.SingleConceptLanguageResponse>) responseObserver);
          break;
        case METHODID_LIST_CONCEPT_LANGUAGES:
          serviceImpl.listConceptLanguages((clarifai2.internal.grpc.api.ConceptLanguageOuterClass.ListConceptLanguagesRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>) responseObserver);
          break;
        case METHODID_POST_CONCEPT_LANGUAGES:
          serviceImpl.postConceptLanguages((clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PostConceptLanguagesRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>) responseObserver);
          break;
        case METHODID_PATCH_CONCEPT_LANGUAGES:
          serviceImpl.patchConceptLanguages((clarifai2.internal.grpc.api.ConceptLanguageOuterClass.PatchConceptLanguagesRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptLanguageOuterClass.MultiConceptLanguageResponse>) responseObserver);
          break;
        case METHODID_LIST_CONCEPT_REFERENCES:
          serviceImpl.listConceptReferences((clarifai2.internal.grpc.api.ConceptReferenceOuterClass.ListConceptReferencesRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptReferenceOuterClass.MultiConceptReferenceResponse>) responseObserver);
          break;
        case METHODID_LIST_CONCEPT_RELATIONS:
          serviceImpl.listConceptRelations((clarifai2.internal.grpc.api.ConceptGraph.ListConceptRelationsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ConceptOuterClass.MultiConceptResponse>) responseObserver);
          break;
        case METHODID_GET_INPUT_COUNT:
          serviceImpl.getInputCount((clarifai2.internal.grpc.api.InputOuterClass.GetInputCountRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.SingleInputCountResponse>) responseObserver);
          break;
        case METHODID_STREAM_INPUTS:
          serviceImpl.streamInputs((clarifai2.internal.grpc.api.InputOuterClass.StreamInputsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>) responseObserver);
          break;
        case METHODID_GET_INPUT:
          serviceImpl.getInput((clarifai2.internal.grpc.api.InputOuterClass.GetInputRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.SingleInputResponse>) responseObserver);
          break;
        case METHODID_LIST_INPUTS:
          serviceImpl.listInputs((clarifai2.internal.grpc.api.InputOuterClass.ListInputsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>) responseObserver);
          break;
        case METHODID_POST_INPUTS:
          serviceImpl.postInputs((clarifai2.internal.grpc.api.InputOuterClass.PostInputsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>) responseObserver);
          break;
        case METHODID_PATCH_INPUTS:
          serviceImpl.patchInputs((clarifai2.internal.grpc.api.InputOuterClass.PatchInputsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>) responseObserver);
          break;
        case METHODID_DELETE_INPUT:
          serviceImpl.deleteInput((clarifai2.internal.grpc.api.InputOuterClass.DeleteInputRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_DELETE_INPUTS:
          serviceImpl.deleteInputs((clarifai2.internal.grpc.api.InputOuterClass.DeleteInputsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_POST_MODEL_OUTPUTS:
          serviceImpl.postModelOutputs((clarifai2.internal.grpc.api.InputOuterClass.PostModelOutputsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.OutputOuterClass.MultiOutputResponse>) responseObserver);
          break;
        case METHODID_POST_MODEL_FEEDBACK:
          serviceImpl.postModelFeedback((clarifai2.internal.grpc.api.InputOuterClass.PostModelFeedbackRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_GET_MODEL:
          serviceImpl.getModel((clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>) responseObserver);
          break;
        case METHODID_GET_MODEL_OUTPUT_INFO:
          serviceImpl.getModelOutputInfo((clarifai2.internal.grpc.api.ModelOuterClass.GetModelRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>) responseObserver);
          break;
        case METHODID_LIST_MODELS:
          serviceImpl.listModels((clarifai2.internal.grpc.api.ModelOuterClass.ListModelsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>) responseObserver);
          break;
        case METHODID_POST_MODELS_SEARCHES:
          serviceImpl.postModelsSearches((clarifai2.internal.grpc.api.ModelOuterClass.PostModelsSearchesRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>) responseObserver);
          break;
        case METHODID_POST_MODELS:
          serviceImpl.postModels((clarifai2.internal.grpc.api.ModelOuterClass.PostModelsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>) responseObserver);
          break;
        case METHODID_PATCH_MODELS:
          serviceImpl.patchModels((clarifai2.internal.grpc.api.ModelOuterClass.PatchModelsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.MultiModelResponse>) responseObserver);
          break;
        case METHODID_DELETE_MODEL:
          serviceImpl.deleteModel((clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_DELETE_MODELS:
          serviceImpl.deleteModels((clarifai2.internal.grpc.api.ModelOuterClass.DeleteModelsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_LIST_MODEL_INPUTS:
          serviceImpl.listModelInputs((clarifai2.internal.grpc.api.InputOuterClass.ListModelInputsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.InputOuterClass.MultiInputResponse>) responseObserver);
          break;
        case METHODID_GET_MODEL_VERSION:
          serviceImpl.getModelVersion((clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>) responseObserver);
          break;
        case METHODID_LIST_MODEL_VERSIONS:
          serviceImpl.listModelVersions((clarifai2.internal.grpc.api.ModelVersionOuterClass.ListModelVersionsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.MultiModelVersionResponse>) responseObserver);
          break;
        case METHODID_POST_MODEL_VERSIONS:
          serviceImpl.postModelVersions((clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelOuterClass.SingleModelResponse>) responseObserver);
          break;
        case METHODID_DELETE_MODEL_VERSION:
          serviceImpl.deleteModelVersion((clarifai2.internal.grpc.api.ModelVersionOuterClass.DeleteModelVersionRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_GET_MODEL_VERSION_METRICS:
          serviceImpl.getModelVersionMetrics((clarifai2.internal.grpc.api.ModelVersionOuterClass.GetModelVersionMetricsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>) responseObserver);
          break;
        case METHODID_POST_MODEL_VERSION_METRICS:
          serviceImpl.postModelVersionMetrics((clarifai2.internal.grpc.api.ModelVersionOuterClass.PostModelVersionMetricsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.ModelVersionOuterClass.SingleModelVersionResponse>) responseObserver);
          break;
        case METHODID_GET_WORKFLOW:
          serviceImpl.getWorkflow((clarifai2.internal.grpc.api.WorkflowOuterClass.GetWorkflowRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.SingleWorkflowResponse>) responseObserver);
          break;
        case METHODID_LIST_WORKFLOWS:
          serviceImpl.listWorkflows((clarifai2.internal.grpc.api.WorkflowOuterClass.ListWorkflowsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>) responseObserver);
          break;
        case METHODID_LIST_PUBLIC_WORKFLOWS:
          serviceImpl.listPublicWorkflows((clarifai2.internal.grpc.api.WorkflowOuterClass.ListPublicWorkflowsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>) responseObserver);
          break;
        case METHODID_POST_WORKFLOWS:
          serviceImpl.postWorkflows((clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>) responseObserver);
          break;
        case METHODID_PATCH_WORKFLOWS:
          serviceImpl.patchWorkflows((clarifai2.internal.grpc.api.WorkflowOuterClass.PatchWorkflowsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.MultiWorkflowResponse>) responseObserver);
          break;
        case METHODID_DELETE_WORKFLOW:
          serviceImpl.deleteWorkflow((clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_DELETE_WORKFLOWS:
          serviceImpl.deleteWorkflows((clarifai2.internal.grpc.api.WorkflowOuterClass.DeleteWorkflowsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_POST_WORKFLOW_RESULTS:
          serviceImpl.postWorkflowResults((clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.WorkflowOuterClass.PostWorkflowResultsResponse>) responseObserver);
          break;
        case METHODID_POST_SEARCHES:
          serviceImpl.postSearches((clarifai2.internal.grpc.api.Search.PostSearchesRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Search.MultiSearchResponse>) responseObserver);
          break;
        case METHODID_POST_SEARCH_FEEDBACK:
          serviceImpl.postSearchFeedback((clarifai2.internal.grpc.api.Search.PostSearchFeedbackRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.status.StatusOuterClass.BaseResponse>) responseObserver);
          break;
        case METHODID_GET_SUBSCRIPTION:
          serviceImpl.getSubscription((clarifai2.internal.grpc.api.Subscription.GetSubscriptionRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse>) responseObserver);
          break;
        case METHODID_POST_SUBSCRIPTION:
          serviceImpl.postSubscription((clarifai2.internal.grpc.api.Subscription.PostSubscriptionRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Subscription.SingleSubscriptionResponse>) responseObserver);
          break;
        case METHODID_GET_APP_VISUALIZATION:
          serviceImpl.getAppVisualization((clarifai2.internal.grpc.api.Visualization.GetAppVisualizationRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>) responseObserver);
          break;
        case METHODID_GET_VISUALIZATION:
          serviceImpl.getVisualization((clarifai2.internal.grpc.api.Visualization.GetVisualizationRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>) responseObserver);
          break;
        case METHODID_POST_VISUALIZATION:
          serviceImpl.postVisualization((clarifai2.internal.grpc.api.Visualization.PostVisualizationRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Visualization.SingleVisualizationResponse>) responseObserver);
          break;
        case METHODID_LIST_STATUS_CODES:
          serviceImpl.listStatusCodes((clarifai2.internal.grpc.api.Code.ListStatusCodesRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Code.MultiStatusCodeResponse>) responseObserver);
          break;
        case METHODID_GET_STATUS_CODE:
          serviceImpl.getStatusCode((clarifai2.internal.grpc.api.Code.GetStatusCodeRequest) request,
              (io.grpc.stub.StreamObserver<clarifai2.internal.grpc.api.Code.SingleStatusCodeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class V2BaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    V2BaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return clarifai2.internal.grpc.api.Endpoint.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("V2");
    }
  }

  private static final class V2FileDescriptorSupplier
      extends V2BaseDescriptorSupplier {
    V2FileDescriptorSupplier() {}
  }

  private static final class V2MethodDescriptorSupplier
      extends V2BaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    V2MethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (V2Grpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new V2FileDescriptorSupplier())
              .addMethod(getGetConceptCountsMethod())
              .addMethod(getGetConceptMethod())
              .addMethod(getListConceptsMethod())
              .addMethod(getPostConceptsSearchesMethod())
              .addMethod(getPostConceptsMethod())
              .addMethod(getPatchConceptsMethod())
              .addMethod(getGetVocabMethod())
              .addMethod(getListVocabsMethod())
              .addMethod(getPostVocabsMethod())
              .addMethod(getPatchVocabsMethod())
              .addMethod(getDeleteVocabMethod())
              .addMethod(getDeleteVocabsMethod())
              .addMethod(getListVocabConceptsMethod())
              .addMethod(getPostVocabConceptsMethod())
              .addMethod(getDeleteVocabConceptMethod())
              .addMethod(getDeleteVocabConceptsMethod())
              .addMethod(getGetConceptLanguageMethod())
              .addMethod(getListConceptLanguagesMethod())
              .addMethod(getPostConceptLanguagesMethod())
              .addMethod(getPatchConceptLanguagesMethod())
              .addMethod(getListConceptReferencesMethod())
              .addMethod(getListConceptRelationsMethod())
              .addMethod(getGetInputCountMethod())
              .addMethod(getStreamInputsMethod())
              .addMethod(getGetInputMethod())
              .addMethod(getListInputsMethod())
              .addMethod(getPostInputsMethod())
              .addMethod(getPatchInputsMethod())
              .addMethod(getDeleteInputMethod())
              .addMethod(getDeleteInputsMethod())
              .addMethod(getPostModelOutputsMethod())
              .addMethod(getPostModelFeedbackMethod())
              .addMethod(getGetModelMethod())
              .addMethod(getGetModelOutputInfoMethod())
              .addMethod(getListModelsMethod())
              .addMethod(getPostModelsSearchesMethod())
              .addMethod(getPostModelsMethod())
              .addMethod(getPatchModelsMethod())
              .addMethod(getDeleteModelMethod())
              .addMethod(getDeleteModelsMethod())
              .addMethod(getListModelInputsMethod())
              .addMethod(getGetModelVersionMethod())
              .addMethod(getListModelVersionsMethod())
              .addMethod(getPostModelVersionsMethod())
              .addMethod(getDeleteModelVersionMethod())
              .addMethod(getGetModelVersionMetricsMethod())
              .addMethod(getPostModelVersionMetricsMethod())
              .addMethod(getGetWorkflowMethod())
              .addMethod(getListWorkflowsMethod())
              .addMethod(getListPublicWorkflowsMethod())
              .addMethod(getPostWorkflowsMethod())
              .addMethod(getPatchWorkflowsMethod())
              .addMethod(getDeleteWorkflowMethod())
              .addMethod(getDeleteWorkflowsMethod())
              .addMethod(getPostWorkflowResultsMethod())
              .addMethod(getPostSearchesMethod())
              .addMethod(getPostSearchFeedbackMethod())
              .addMethod(getGetSubscriptionMethod())
              .addMethod(getPostSubscriptionMethod())
              .addMethod(getGetAppVisualizationMethod())
              .addMethod(getGetVisualizationMethod())
              .addMethod(getPostVisualizationMethod())
              .addMethod(getListStatusCodesMethod())
              .addMethod(getGetStatusCodeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
