package works.tonny.apps.support.jms;

import javax.jms.JMSException;
import java.io.Serializable;

/**
 * 消息发送者
 * Created by Tonny on 2016/6/13.
 */
public interface Sender {
    /**
     * 发送消息
     *
     * @param message 可序列化的消息对象
     * @throws JMSException
     */
    void sendMessage(Serializable message) throws JMSException;

    /**
     * 发送消息
     *
     * @param message 文本消息
     * @throws JMSException
     */
    void sendMessage(String message) throws JMSException;

}
