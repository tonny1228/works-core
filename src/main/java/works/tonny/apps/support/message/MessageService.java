/**
 * 
 */
package works.tonny.apps.support.message;

import works.tonny.apps.support.message.MessageEvent.Status;

/**
 * 接收消息服务
 * 
 * @author 祥栋
 */
public interface MessageService {

	/**
	 * 注册消息监听器
	 * 
	 * @param category 消息分类
	 * @param listener 监听器
	 */
	void register(String category, MessageListener listener, Status... status);

	/**
	 * 取消监听器的注册
	 * 
	 * @param category 消息分类
	 * @param listener 消息监听器
	 */
	void unRegister(String category, MessageListener listener, Status... status);
}