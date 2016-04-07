/**
 * 
 */
package works.tonny.apps.exception;

/**
 * 无此服务异常
 * 
 * @author tonny
 * @date 2012-10-9
 * @version 1.0.0
 */
public class NoSuchServiceException extends RuntimeException {

	/**
	 * @param message 服务名
	 */
	public NoSuchServiceException(String message) {
		super(message);
	}

}
