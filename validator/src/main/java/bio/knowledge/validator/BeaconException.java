package bio.knowledge.validator;

import bio.knowledge.client.ApiException;

/**
 * This exception holds an instance of ApiClient, which can be used to get the
 * history of queries at whatever level the exception is being processed for
 * logging.
 */
public class BeaconException extends ApiException implements BeaconExceptionInterface {

	private static final long serialVersionUID = -2392100649988848998L;

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
	
	@Override
	public ApiClient getApiClient() {
		return this.client;
	}

}