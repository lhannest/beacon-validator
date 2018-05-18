/*
 * Translator Knowledge Beacon API
 * This is the nDexBio (www.ndexbio.org) biomedical graph archive wrapped with the  Translator Knowledge Beacon web service application programming interface (API). 
 *
 * OpenAPI spec version: 1.0.17
 * Contact: richard@starinformatics.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package bio.knowledge.client.api;

import bio.knowledge.client.ApiCallback;
import bio.knowledge.client.ApiClient;
import bio.knowledge.client.ApiException;
import bio.knowledge.client.ApiResponse;
import bio.knowledge.client.Configuration;
import bio.knowledge.client.Pair;
import bio.knowledge.client.ProgressRequestBody;
import bio.knowledge.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import bio.knowledge.client.model.BeaconAnnotation;
import bio.knowledge.client.model.BeaconStatement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatementsApi {
    private ApiClient apiClient;

    public StatementsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public StatementsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /* Build call for getEvidence */
    private com.squareup.okhttp.Call getEvidenceCall(String statementId, List<String> keywords, Integer pageNumber, Integer pageSize, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/evidence/{statementId}".replaceAll("\\{format\\}","json")
        .replaceAll("\\{" + "statementId" + "\\}", apiClient.escapeString(statementId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        if (keywords != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "keywords", keywords));
        if (pageNumber != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "pageNumber", pageNumber));
        if (pageSize != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "pageSize", pageSize));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getEvidenceValidateBeforeCall(String statementId, List<String> keywords, Integer pageNumber, Integer pageSize, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'statementId' is set
        if (statementId == null) {
//            throw new ApiException("Missing the required parameter 'statementId' when calling getEvidence(Async)");
        }
        
        
        com.squareup.okhttp.Call call = getEvidenceCall(statementId, keywords, pageNumber, pageSize, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * 
     * Retrieves a (paged) list of annotations cited as evidence for a specified concept-relationship statement 
     * @param statementId (url-encoded) CURIE identifier of the concept-relationship statement (\&quot;assertion\&quot;, \&quot;claim\&quot;) for which associated evidence is sought  (required)
     * @param keywords (url-encoded, space delimited) keyword filter to apply against the label field of the annotation  (optional)
     * @param pageNumber (1-based) number of the page to be returned in a paged set of query results  (optional)
     * @param pageSize number of cited references per page to be returned in a paged set of query results  (optional)
     * @return List&lt;BeaconAnnotation&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<BeaconAnnotation> getEvidence(String statementId, List<String> keywords, Integer pageNumber, Integer pageSize) throws ApiException {
        ApiResponse<List<BeaconAnnotation>> resp = getEvidenceWithHttpInfo(statementId, keywords, pageNumber, pageSize);
        return resp.getData();
    }

    /**
     * 
     * Retrieves a (paged) list of annotations cited as evidence for a specified concept-relationship statement 
     * @param statementId (url-encoded) CURIE identifier of the concept-relationship statement (\&quot;assertion\&quot;, \&quot;claim\&quot;) for which associated evidence is sought  (required)
     * @param keywords (url-encoded, space delimited) keyword filter to apply against the label field of the annotation  (optional)
     * @param pageNumber (1-based) number of the page to be returned in a paged set of query results  (optional)
     * @param pageSize number of cited references per page to be returned in a paged set of query results  (optional)
     * @return ApiResponse&lt;List&lt;BeaconAnnotation&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<BeaconAnnotation>> getEvidenceWithHttpInfo(String statementId, List<String> keywords, Integer pageNumber, Integer pageSize) throws ApiException {
        com.squareup.okhttp.Call call = getEvidenceValidateBeforeCall(statementId, keywords, pageNumber, pageSize, null, null);
        Type localVarReturnType = new TypeToken<List<BeaconAnnotation>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * Retrieves a (paged) list of annotations cited as evidence for a specified concept-relationship statement 
     * @param statementId (url-encoded) CURIE identifier of the concept-relationship statement (\&quot;assertion\&quot;, \&quot;claim\&quot;) for which associated evidence is sought  (required)
     * @param keywords (url-encoded, space delimited) keyword filter to apply against the label field of the annotation  (optional)
     * @param pageNumber (1-based) number of the page to be returned in a paged set of query results  (optional)
     * @param pageSize number of cited references per page to be returned in a paged set of query results  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getEvidenceAsync(String statementId, List<String> keywords, Integer pageNumber, Integer pageSize, final ApiCallback<List<BeaconAnnotation>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getEvidenceValidateBeforeCall(statementId, keywords, pageNumber, pageSize, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<BeaconAnnotation>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /* Build call for getStatements */
    private com.squareup.okhttp.Call getStatementsCall(List<String> s, String relations, List<String> t, List<String> keywords, List<String> types, Integer pageNumber, Integer pageSize, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/statements".replaceAll("\\{format\\}","json");

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        if (s != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "s", s));
        if (relations != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "relations", relations));
        if (t != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "t", t));
        if (keywords != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "keywords", keywords));
        if (types != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "types", types));
        if (pageNumber != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "pageNumber", pageNumber));
        if (pageSize != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "pageSize", pageSize));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getStatementsValidateBeforeCall(List<String> s, String relations, List<String> t, List<String> keywords, List<String> types, Integer pageNumber, Integer pageSize, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 's' is set
        if (s == null) {
//            throw new ApiException("Missing the required parameter 's' when calling getStatements(Async)");
        }
        
        
        com.squareup.okhttp.Call call = getStatementsCall(s, relations, t, keywords, types, pageNumber, pageSize, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * 
     * Given a specified set of [CURIE-encoded](https://www.w3.org/TR/curie/)  &#39;source&#39; (&#39;s&#39;) concept identifiers,  retrieves a paged list of relationship statements where either the subject or object concept matches any of the input &#39;source&#39; concepts provided.  Optionally, a set of &#39;target&#39; (&#39;t&#39;) concept  identifiers may also be given, in which case a member of the &#39;target&#39; identifier set should match the concept opposing the &#39;source&#39; in the  statement, that is, if the&#39;source&#39; matches a subject, then the  &#39;target&#39; should match the object of a given statement (or vice versa). 
     * @param s a set of [CURIE-encoded](https://www.w3.org/TR/curie/) identifiers of  &#39;source&#39; concepts possibly known to the beacon. Unknown CURIES should simply be ignored (silent match failure).  (required)
     * @param relations a (url-encoded, space-delimited) string of predicate relation identifiers with which to constrain the statement relations retrieved  for the given query seed concept. The predicate ids sent should  be as published by the beacon-aggregator by the /predicates API endpoint.  (optional)
     * @param t (optional) an array set of [CURIE-encoded](https://www.w3.org/TR/curie/)  identifiers of &#39;target&#39; concepts possibly known to the beacon.  Unknown CURIEs should simply be ignored (silent match failure).  (optional)
     * @param keywords a (url-encoded, space-delimited) string of keywords or substrings against which to match the subject, predicate or object names of the set of concept-relations matched by any of the input exact matching concepts  (optional)
     * @param types a (url-encoded, space-delimited) string of concept types (specified as codes gene, pathway, etc.) to which to constrain the subject or object concepts associated with the query seed concept (see [Biolink Model](https://biolink.github.io/biolink-model) for the full list of codes)  (optional)
     * @param pageNumber (1-based) number of the page to be returned in a paged set of query results  (optional)
     * @param pageSize number of concepts per page to be returned in a paged set of query results  (optional)
     * @return List&lt;BeaconStatement&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<BeaconStatement> getStatements(List<String> s, String relations, List<String> t, List<String> keywords, List<String> types, Integer pageNumber, Integer pageSize) throws ApiException {
        ApiResponse<List<BeaconStatement>> resp = getStatementsWithHttpInfo(s, relations, t, keywords, types, pageNumber, pageSize);
        return resp.getData();
    }

    /**
     * 
     * Given a specified set of [CURIE-encoded](https://www.w3.org/TR/curie/)  &#39;source&#39; (&#39;s&#39;) concept identifiers,  retrieves a paged list of relationship statements where either the subject or object concept matches any of the input &#39;source&#39; concepts provided.  Optionally, a set of &#39;target&#39; (&#39;t&#39;) concept  identifiers may also be given, in which case a member of the &#39;target&#39; identifier set should match the concept opposing the &#39;source&#39; in the  statement, that is, if the&#39;source&#39; matches a subject, then the  &#39;target&#39; should match the object of a given statement (or vice versa). 
     * @param s a set of [CURIE-encoded](https://www.w3.org/TR/curie/) identifiers of  &#39;source&#39; concepts possibly known to the beacon. Unknown CURIES should simply be ignored (silent match failure).  (required)
     * @param relations a (url-encoded, space-delimited) string of predicate relation identifiers with which to constrain the statement relations retrieved  for the given query seed concept. The predicate ids sent should  be as published by the beacon-aggregator by the /predicates API endpoint.  (optional)
     * @param t (optional) an array set of [CURIE-encoded](https://www.w3.org/TR/curie/)  identifiers of &#39;target&#39; concepts possibly known to the beacon.  Unknown CURIEs should simply be ignored (silent match failure).  (optional)
     * @param keywords a (url-encoded, space-delimited) string of keywords or substrings against which to match the subject, predicate or object names of the set of concept-relations matched by any of the input exact matching concepts  (optional)
     * @param types a (url-encoded, space-delimited) string of concept types (specified as codes gene, pathway, etc.) to which to constrain the subject or object concepts associated with the query seed concept (see [Biolink Model](https://biolink.github.io/biolink-model) for the full list of codes)  (optional)
     * @param pageNumber (1-based) number of the page to be returned in a paged set of query results  (optional)
     * @param pageSize number of concepts per page to be returned in a paged set of query results  (optional)
     * @return ApiResponse&lt;List&lt;BeaconStatement&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<BeaconStatement>> getStatementsWithHttpInfo(List<String> s, String relations, List<String> t, List<String> keywords, List<String> types, Integer pageNumber, Integer pageSize) throws ApiException {
        com.squareup.okhttp.Call call = getStatementsValidateBeforeCall(s, relations, t, keywords, types, pageNumber, pageSize, null, null);
        Type localVarReturnType = new TypeToken<List<BeaconStatement>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * Given a specified set of [CURIE-encoded](https://www.w3.org/TR/curie/)  &#39;source&#39; (&#39;s&#39;) concept identifiers,  retrieves a paged list of relationship statements where either the subject or object concept matches any of the input &#39;source&#39; concepts provided.  Optionally, a set of &#39;target&#39; (&#39;t&#39;) concept  identifiers may also be given, in which case a member of the &#39;target&#39; identifier set should match the concept opposing the &#39;source&#39; in the  statement, that is, if the&#39;source&#39; matches a subject, then the  &#39;target&#39; should match the object of a given statement (or vice versa). 
     * @param s a set of [CURIE-encoded](https://www.w3.org/TR/curie/) identifiers of  &#39;source&#39; concepts possibly known to the beacon. Unknown CURIES should simply be ignored (silent match failure).  (required)
     * @param relations a (url-encoded, space-delimited) string of predicate relation identifiers with which to constrain the statement relations retrieved  for the given query seed concept. The predicate ids sent should  be as published by the beacon-aggregator by the /predicates API endpoint.  (optional)
     * @param t (optional) an array set of [CURIE-encoded](https://www.w3.org/TR/curie/)  identifiers of &#39;target&#39; concepts possibly known to the beacon.  Unknown CURIEs should simply be ignored (silent match failure).  (optional)
     * @param keywords a (url-encoded, space-delimited) string of keywords or substrings against which to match the subject, predicate or object names of the set of concept-relations matched by any of the input exact matching concepts  (optional)
     * @param types a (url-encoded, space-delimited) string of concept types (specified as codes gene, pathway, etc.) to which to constrain the subject or object concepts associated with the query seed concept (see [Biolink Model](https://biolink.github.io/biolink-model) for the full list of codes)  (optional)
     * @param pageNumber (1-based) number of the page to be returned in a paged set of query results  (optional)
     * @param pageSize number of concepts per page to be returned in a paged set of query results  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getStatementsAsync(List<String> s, String relations, List<String> t, List<String> keywords, List<String> types, Integer pageNumber, Integer pageSize, final ApiCallback<List<BeaconStatement>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getStatementsValidateBeforeCall(s, relations, t, keywords, types, pageNumber, pageSize, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<BeaconStatement>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}