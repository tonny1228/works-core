package works.tonny.apps.exception;

/**
 * 数据异常，指数据类型、数据合法性等异常
 * 
 * @author 刘祥栋
 * 
 */
public class DataException extends CommonException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4333339864594993678L;

	/**
	 * 
	 * 
	 */
	public DataException() {
		super();
	}

	/**
	 * 
	 * @param message message
	 * @param cause cause
	 */
	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message message
	 */
	public DataException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause cause
	 */
	public DataException(Throwable cause) {
		super(cause);
	}

}
