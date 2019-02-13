package dianfan.exception;
/**
 * @ClassName VCloudException
 * @Description 网易云接口操作异常
 * @author cjy
 * @date 2018年1月24日 下午12:02:03
 */
public class VCloudException extends Exception {

	/** @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public VCloudException() {
		super("网易云直播接口错误");
	}

	public VCloudException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VCloudException(String message, Throwable cause) {
		super(message, cause);
	}

	public VCloudException(String message) {
		super(message);
	}

	public VCloudException(Throwable cause) {
		super(cause);
	}
	
	
}
