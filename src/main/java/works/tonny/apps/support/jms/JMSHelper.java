package works.tonny.apps.support.jms;

import javax.jms.MessageListener;

/**
 * Created by Tonny on 2016/6/13.
 */
public interface JMSHelper {
    /**
     * 创建队列消息发送服务
     *
     * @param name 目标名
     */
    Sender createQueueSender(String name);

    /**
     * 创建jms订阅消息发送服务
     *
     * @param name 目标名
     * @return
     */
    Sender createTopicSender(String name);

    /**
     * 创建队列消息监听
     *
     * @param name
     */
    void createQueueListener(String name, MessageListener listener);

    /**
     * 创建订阅消息监听
     *
     * @param name
     */
    void createTopicListener(String name, MessageListener listener);
}
