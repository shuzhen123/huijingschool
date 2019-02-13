package dianfan.exception;

public class ExcelException extends Exception {
	/**
	 * Excel异常类
	 */
	private static final long serialVersionUID = 1L;

	public ExcelException() {
	}

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(Throwable cause) {
		super(cause);
	}

	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}
}
