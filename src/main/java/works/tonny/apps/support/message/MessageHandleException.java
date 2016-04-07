/**
 * 
 */
package works.tonny.apps.support.message;

/**
 * 消息处理异常
 * 
 * @author 祥栋
 */
public class MessageHandleException extends Exception {

	/**
	 * 
	 */
	public MessageHandleException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MessageHandleException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public MessageHandleException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MessageHandleException(Throwable cause) {
		super(cause);
	}

}
