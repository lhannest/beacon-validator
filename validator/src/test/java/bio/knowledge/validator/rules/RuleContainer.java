package bio.knowledge.validator.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.math3.stat.StatUtils;
import org.junit.rules.Stopwatch;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bio.knowledge.validator.ApiClient;
import bio.knowledge.validator.BeaconException;
import bio.knowledge.validator.BeaconExceptionInterface;
import bio.knowledge.validator.logging.Logger;
import bio.knowledge.validator.logging.LoggerFactory;

@Component
public class RuleContainer {
	
	@Value("${basePath}")
	private String BASE_PATH;
	
	@Autowired LoggerFactory loggerFactory;
	
	private TestWatcher testWatcher;
	
	private Stopwatch stopwatch;
	
	private Map<String, Double> time_sec = new HashMap<String, Double>();
	
	@PostConstruct
	private void init() {
		testWatcher = new TestWatcher() {
			
			@Override
			public void failed(Throwable e, Description description) {
				if (e instanceof BeaconExceptionInterface) {
					ApiClient apiClient = ((BeaconExceptionInterface) e).getApiClient();
					
					Logger logger = loggerFactory.get(apiClient);
					
					logger.error(e, description.getMethodName(), apiClient.getQueryHistory());
				} else {
					Logger logger = loggerFactory.get(BASE_PATH);
					logger.error(e, description.getMethodName(), new ArrayList<String>());
				}
		    }
		};
		
		stopwatch = new Stopwatch() {
			@Override
			protected void finished(long nanos, Description description) {
				time_sec.put(description.getMethodName(), (double) nanos / 1e+9);
			}
		};
		
	}
	
	@PreDestroy
	public void finish() {
		double[] values = new double[time_sec.size()];
		
		Logger logger = loggerFactory.get(BASE_PATH);
		
		int i = 0;
		for (String method : time_sec.keySet()) {
			logger.info("Time for " + method + ": " + round(time_sec.get(method)) + " sec");
			values[i++] = time_sec.get(method);
		}
		
		double geometricMean = round(StatUtils.geometricMean(values));
		double mean = round(StatUtils.mean(values));
		double sum = round(StatUtils.sum(values));
		
		logger.info("Gemoetric mean: " + geometricMean + " sec");
		logger.info("Arithmetic mean: " + mean + " sec");
		logger.info("Sum: " + sum + " sec");		
	}
	
	/**
	 * Rounds to three decimal places
	 */
	private double round(double d) {
		return Math.round(d * 1000) / 1000d;
	}
	
	public TestWatcher getTestWatcher() {
		return testWatcher;
	}
	
	public Stopwatch getStopwatch() {
		return this.stopwatch;
	}

}
