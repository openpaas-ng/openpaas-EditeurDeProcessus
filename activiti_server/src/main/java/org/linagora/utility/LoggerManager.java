package org.linagora.utility;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class LoggerManager {
	
	final static Logger logger = Logger.getLogger(LoggerManager.class);	

	public static void loggerTrace(Exception e){
		logger.info("Error" + e.getLocalizedMessage());
		logger.error(ExceptionUtils.getFullStackTrace(e));
	}

}
