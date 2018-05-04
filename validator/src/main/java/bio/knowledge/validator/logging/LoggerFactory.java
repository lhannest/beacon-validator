package bio.knowledge.validator.logging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import bio.knowledge.validator.ApiClient;
import bio.knowledge.validator.Utils;

@Component
public class LoggerFactory {
	
	private Map<String, Logger> map = new HashMap<String, Logger>();
	
	public Logger get(String basePath) {
		
		if (basePath == null) {
			throw new RuntimeException("Beacon base path may not be null");
		}
		
		basePath = Utils.getHost(basePath);
		
		if (map.containsKey(basePath)) {
			return map.get(basePath);
		} else {			
			Logger logger = new Logger(basePath);
			
			map.put(basePath, logger);
			
			return logger;
		}
	}
	
	public Logger get(ApiClient apiClient) {
		return get(Utils.getHost(apiClient.getBasePath()));
	}
	
}
