package bio.knowledge.validator;

public class BeaconAssertionError extends AssertionError implements BeaconExceptionInterface {
	
	private static final long serialVersionUID = 5689523783852419320L;
	
	private ApiClient client;
	
	public BeaconAssertionError(ApiClient client) {
		super();
		
		this.client = client;
	}
	
	public BeaconAssertionError(ApiClient client, String message) {
		super(message);
		
		this.client = client;
	}

	@Override
	public ApiClient getApiClient() {
		return this.client;
	}
}
