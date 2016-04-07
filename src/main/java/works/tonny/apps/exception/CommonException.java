package works.tonny.apps.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class CommonException extends NestableRuntimeException {

	public CommonException() {
		super();
	}

	public CommonException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CommonException(String msg) {
		super(msg);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

}
