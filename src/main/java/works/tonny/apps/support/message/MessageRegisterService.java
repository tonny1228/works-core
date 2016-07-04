/**
 *
 */
package works.tonny.apps.support.message;

import works.tonny.apps.support.message.MessageEvent.Status;

import java.util.List;
import java.util.Map;

/**
 * 接收消息服务
 *
 * @author 祥栋
 */
public interface MessageRegisterService {

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

    /**
     * 获取本应用中注册的监听服务
     *
     * @return
     */
    Map<String, List<MessageListener>> getListeners();
}