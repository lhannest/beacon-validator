package bio.knowledge.validator;

import java.util.List;
import java.util.Map;

import bio.knowledge.client.ApiException;

/**
 * This exception holds an instance of ApiClient, which can be used to get the
 * history of queries at whatever level the exception is being processed for
 * logging.
 */
public class BeaconException extends ApiException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4335257178039062378L;
	
	private ApiClient client;
	
	public BeaconException(String message, ApiClient client) {
		super(message);
		
		this.client = client;		
	}
	
	/**
	 * A copy constructor
	 * @param e an instance of {@link bio.knowledge.client.ApiException} to be copied
	 */
	public BeaconException(ApiException e, ApiClient client) {
		super(e.getMessage(), e.getCause(), e.getCode(), e.getResponseHeaders(), e.getResponseBody());

		this.client = client;
	}
	
	public ApiClient getApiClient() {
		return this.client;
	}

}