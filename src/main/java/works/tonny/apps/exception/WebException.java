package works.tonny.apps.exception;

/**
 * web层面的异常
 * 
 * @author tonny
 * 
 */
public class WebException extends CommonException {

	private static final long serialVersionUID = 1L;

	public WebException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebException(String message) {
		super(message);
	}

	public WebException() {
		super();
	}

	public WebException(Throwable cause) {
		super(cause);
	}

}
