package works.tonny.apps.exception;

/**
 * 服务异常
 * 
 * @author tonny
 * 
 */
public class ServiceException extends CommonException {

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
