package bio.knowledge.validator;

public class Assert {
	
	static public void fail(ApiClient apiClient) {
		fail(apiClient, null);
	}

	static public void fail(ApiClient apiClient, String message) {
		if (message == null) {
            throw new BeaconAssertionError(apiClient);
        }
		throw new BeaconAssertionError(apiClient, message);
	}
	
	static public void assertTrue(ApiClient apiClient, String message, boolean condition) {
        if (!condition) {
            fail(apiClient, message);
        }
    }
	
	static public void assertTrue(ApiClient apiClient, boolean condition) {
        if (!condition) {
            fail(apiClient);
        }
    }
	
	static public void assertFalse(ApiClient apiClient, String message, boolean condition) {
		assertTrue(apiClient, message, !condition);
    }
	
	static public void assertFalse(ApiClient apiClient, boolean condition) {
		assertTrue(apiClient, !condition);
    }

}
