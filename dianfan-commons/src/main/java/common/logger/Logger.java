package common.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class Logger {
	// 声明logger
	// 采用slf4j接口实现
	private static Log logger = LogFactory.getLog(Logger.class);

	/**
	 * DEBUG不带异常信息
	 * 
	 * @param str
	 *            内容
	 */
	public static void debug(String str) {
		logger.debug(str);
	}

	/**
	 * DEBUG带异常信息
	 * 
	 * @param str
	 *            内容
	 * @param e
	 *            异常
	 */
	public static void debug(String str, Exception e) {
		logger.debug(str, e);
	}

	/**
	 * info不带异常信息
	 * 
	 * @param str
	 *            内容
	 */
	public static void info(String str) {
		logger.info(str);
	}

	/**
	 * info带异常信息
	 * 
	 * @param str
	 *            内容
	 * @param e
	 *            异常
	 */
	public static void info(String str, Exception e) {
		logger.info(str, e);
	}

	/**
	 * warn不带异常信息
	 * 
	 * @param str
	 *            内容
	 */
	public static void warn(String str) {
		logger.warn(str);
	}

	/**
	 * warn带异常信息
	 * 
	 * @param str
	 *            内容
	 * @param e
	 *            异常
	 */
	public static void warn(String str, Exception e) {
		logger.warn(str, e);
	}

	/**
	 * error不带异常信息
	 * 
	 * @param str
	 *            内容
	 */
	public static void error(String str) {
		logger.error(str);
	}

	/**
	 * error带异常信息
	 * 
	 * @param str
	 *            内容
	 * @param e
	 *            异常
	 */
	public static void error(String str, Exception e) {
		logger.error(str, e);
	}

}
