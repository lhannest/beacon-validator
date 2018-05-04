package bio.knowledge.validator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Response;

import bio.knowledge.client.ApiException;
import bio.knowledge.client.ApiResponse;
import bio.knowledge.client.Pair;

public class ApiClient extends bio.knowledge.client.ApiClient {
	
	private static final String HTTP = "http://";
	private static final String HTTPS = "https://";
	
	private List<String> queries = new ArrayList<String>();
	
	public List<String> getQueryHistory() {
		return Collections.unmodifiableList(queries);
	}
	
	public String getQuery() {
		if (queries.isEmpty()) {
			return null;
		} else {
			return queries.get(queries.size());
		}
	}
	
	public void clearQueryHistory() {
		this.queries.clear();
	}
	
	private void addQuery(String query) {		
		try {
			this.queries.add(URLDecoder.decode(query, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public ApiClient(String basePath) {
		super();
		
		this.setConnectTimeout(0);
		
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}
		
		if (basePath.startsWith(HTTP) || basePath.startsWith(HTTPS)) {
			this.setBasePath(basePath);
		} else {
			this.setBasePath(HTTP + basePath);
		}
	}
	
	@Override
	public String buildUrl(String path, List<Pair> queryParams) {
		String query = super.buildUrl(path, queryParams);
		addQuery(query);
		return query;
	}
	
	@Override
	public <T> ApiResponse<T> execute(Call call, Type returnType) throws ApiException {
		try {
			return super.execute(call, returnType);
		} catch (ApiException e) {
			throw new BeaconException(e, this);
		}
    }
}
