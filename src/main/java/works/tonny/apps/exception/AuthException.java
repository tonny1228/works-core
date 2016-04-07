package works.tonny.apps.exception;

/**
 * 权限异常类
 * 
 * @author tonny
 * 
 */
public class AuthException extends CommonException {

	public AuthException() {
		super();
	}

	public AuthException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public AuthException(String arg0) {
		super(arg0);
	}

	public AuthException(Throwable arg0) {
		super(arg0);
	}

}
