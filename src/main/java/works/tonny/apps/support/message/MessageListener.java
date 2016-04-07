/**
 * 
 */
package works.tonny.apps.support.message;

import java.util.EventListener;

/**
 * 消息监听服务
 * 
 * @author 祥栋
 */
public interface MessageListener<T> extends EventListener {
	/**
	 * 监听到接收到消息后的处理方法
	 * 
	 * @param message 接收到的消息
	 * @throws SystemErrorExcetion
	 */
	void onRecieved(MessageEvent<T> message) throws MessageHandleException;
}
