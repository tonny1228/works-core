/**
 * 
 */
package works.tonny.apps.support.message;

/**
 * 消息服务管理工具
 * 
 * @author 祥栋
 */
public class MessageManager {

	/**
	 * 消息广播服务
	 */
	private static MessageBroadcastServiceImpl broadcastService = new MessageBroadcastServiceImpl();

	/**
	 * 消息广播服务
	 */
	private static MessageServiceImpl messageService = new MessageServiceImpl();

	/**
	 * 
	 */
	static {
		broadcastService = new MessageBroadcastServiceImpl();
		messageService = new MessageServiceImpl();
		broadcastService.setMessageServiceImpl(messageService);
	}

	/**
	 * 通知监听器数据改变
	 * 
	 * @param category 分类
	 * @param status 状态
	 * @param data 数据
	 */
	public static void notify(String category, MessageEvent.Status status, Object data) {
		broadcastService.notify(category, status, data);
	}

	/**
	 * 通知监听器数据改变
	 * 
	 * @param category 分类
	 * @param status 状态
	 * @param data 数据
	 */
	public static void notify(String category, MessageEvent.Status status, Object... data) {
		if (data != null && data.length == 1) {
			broadcastService.notify(category, status, data[0]);
		} else
			broadcastService.notify(category, status, data);
	}

	/**
	 * 通知监听器数据改变
	 * 
	 * @param category 分类
	 * @param status 状态
	 * @param data 数据
	 */
	public static void notify(Class clz, MessageEvent.Status status, Object... data) {
		if (data != null && data.length == 1) {
			broadcastService.notify(clz.getName(), status, data[0]);
		} else
			broadcastService.notify(clz.getName(), status, data);
	}

	/**
	 * 注册消息监听器
	 * 
	 * @param category 消息分类
	 * @param listener 监听器
	 */
	public static void register(String category, MessageListener listener, MessageEvent.Status... status) {
		messageService.register(category, listener, status);
	}

	public static void register(Class clz, MessageListener listener, MessageEvent.Status... status) {
		messageService.register(clz.getName(), listener, status);
	}

	/**
	 * 取消监听器的注册
	 * 
	 * @param category 消息分类
	 * @param listener 消息监听器
	 */
	public static void unRegister(String category, MessageListener listener, MessageEvent.Status... status) {
		messageService.unRegister(category, listener, status);
	}
}
