package dianfan.exception;

/**
 * @ClassName PayException
 * @Description 支付异常
 * @author cjy
 * @date 2018年4月17日 上午10:40:48
 */
public class PayException extends Exception {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public PayException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PayException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public PayException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PayException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
