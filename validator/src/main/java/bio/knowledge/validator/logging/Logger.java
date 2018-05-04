package bio.knowledge.validator.logging;

import java.util.List;

import bio.knowledge.validator.ApiClient;
import bio.knowledge.validator.BeaconException;
import bio.knowledge.validator.Utils;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * Wraps a customized instance of {@link ch.qos.logback.classic.Logger}.
 */
public class Logger {
	
	private final String BASE_DIR = "log";
	private final String DEFAULT_FILE_NAME = "application";
	
	private final String HOST;
	private final ch.qos.logback.classic.Logger logger;
	private final FileAppender<ILoggingEvent> fileAppender;
	
	protected Logger(String beaconURI) {
		
		HOST = Utils.getHost(beaconURI);
		
		LoggerContext loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
		fileAppender = new FileAppender<ILoggingEvent>();
		
		fileAppender.setContext(loggerContext);
		fileAppender.setName("timestamp");
		fileAppender.setFile(BASE_DIR + "/" + HOST + "/" + DEFAULT_FILE_NAME + ".log");
		
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(loggerContext);
	    encoder.setPattern("%r %thread %level - %msg%n");
	    encoder.start();

	    fileAppender.setEncoder(encoder);
	    fileAppender.start();
	    
	    logger = loggerContext.getLogger(HOST);
	    logger.addAppender(fileAppender);
	    
//	    // OPTIONAL: print logback internal status messages
	    StatusPrinter.print(loggerContext);
	}
	
	public void setFileName(String fileName) {		
		if (!fileName.endsWith(".log")) {
			fileName += ".log";
		}
		
		fileName = BASE_DIR + "/" + HOST + "/" + fileName;
		
		this.fileAppender.stop();
		this.fileAppender.setFile(fileName);
		this.fileAppender.start();
	}
	
	public void error(Throwable e, String methodName, List<String> queryHistory) {
		
		if (e instanceof BeaconException) {
			if (queryHistory == null || queryHistory.isEmpty()) {
				ApiClient apiClient = ((BeaconException) e).getApiClient();
				queryHistory = apiClient.getQueryHistory();
			}
		}
		
		
		String  msg = e.toString();
		for (String query : queryHistory) {
			 msg += "\n\t" + query;
		}
		
		if (methodName == null) {
			methodName = DEFAULT_FILE_NAME;
		}
		
		setFileName(methodName);
		
		logger.error(msg);
	}
	
	public void error(String msg) {
		this.logger.error(msg);
	}
	
	public void info(String msg) {
		this.logger.info(msg);
	}
	
	public void warn(String msg) {
		this.logger.warn(msg);
	}

}
