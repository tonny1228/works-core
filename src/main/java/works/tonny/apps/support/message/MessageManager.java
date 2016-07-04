/**
 *
 */
package works.tonny.apps.support.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息服务管理工具
 *
 * @author 祥栋
 */
public class MessageManager {

    /**
     * 消息广播服务
     */
    private static MessageBroadcastService broadcastService;

    /**
     * 消息广播服务
     */
    private static MessageRegisterService messageService;

    private static List<Map> temp;

//    /**
//     *
//     */
//    static {
//        broadcastService = new LocalMessageBroadcastService();
//        messageService = new LocalMessageRegisterService();
//        broadcastService.setNotifyLocalListener(messageService);
//    }

    /**
     * 通知监听器数据改变
     *
     * @param category 分类
     * @param status   状态
     * @param data     数据
     */
    public static void notify(String category, MessageEvent.Status status, Object data) {
        broadcastService.notify(category, status, data);
    }

    /**
     * 通知监听器数据改变
     *
     * @param category 分类
     * @param status   状态
     * @param data     数据
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
     * @param clz    分类
     * @param status 状态
     * @param data   数据
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
        if (messageService == null) {
            if (temp == null) {
                temp = new ArrayList<Map>();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("category", category);
            map.put("listener", listener);
            map.put("status", status);
            temp.add(map);
            return;
        }
        messageService.register(category, listener, status);
    }

    public static void register(Class clz, MessageListener listener, MessageEvent.Status... status) {
        register(clz.getName(), listener, status);
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

    public static MessageBroadcastService getBroadcastService() {
        return broadcastService;
    }

    public static void setBroadcastService(MessageBroadcastService broadcastService) {
        MessageManager.broadcastService = broadcastService;
    }

    public static MessageRegisterService getMessageService() {
        return messageService;
    }

    /**
     * 设置时，如果有已准备注册的服务，给他注册了
     *
     * @param messageService
     */
    public static void setMessageService(MessageRegisterService messageService) {
        MessageManager.messageService = messageService;
        if (temp != null && messageService != null) {
            for (Map map : temp) {
                register((String) map.get("category"), (MessageListener) map.get("listener"), (MessageEvent.Status[]) map.get("status"));
            }
            temp = null;
        }
    }

}
