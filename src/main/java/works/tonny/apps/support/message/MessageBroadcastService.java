/**
 * 
 */
package works.tonny.apps.support.message;

/**
 * 管理平台消息广播服务
 * 
 * @author 祥栋
 */
public interface MessageBroadcastService {

//	/**
//	 * 发送消息广播
//	 * 
//	 * @param message 消息事件
//	 */
//	void notify(MessageEvent message);

	/**
	 * 发送消息广播
	 * 
	 * @param category
	 * @param status
	 * @param data
	 */
	void notify(String category, MessageEvent.Status status, Object data);
}