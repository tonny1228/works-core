package works.tonny.apps.exception;

/**
 * 数据存储访问异常
 * 
 * @author tonny
 * 
 */
public class DAOException extends CommonException {

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

}
