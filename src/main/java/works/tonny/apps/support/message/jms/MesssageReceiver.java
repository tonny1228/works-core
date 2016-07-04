package works.tonny.apps.support.message.jms;


import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import works.tonny.apps.support.jms.MessageConverter;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.NotifyLocalListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * JMS消息接收者
 * Created by Tonny on 2016/6/13.
 */
public class MesssageReceiver implements MessageListener {
    private static Logger log = LogFactory.getLogger(MesssageReceiver.class);

    /**
     * 消息转换器
     */
    private MessageConverter converter;

    /**
     * 通知本地的监听
     */
    private NotifyLocalListener notifyLocalListener;


    /**
     * 接收到消息后，由消息处理器进行处理
     *
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message m) {
        try {
            MessageEvent event = (MessageEvent) converter.fromMessage(m);
            log.info("收到普通消息{0}", event);
            notifyLocalListener.notify(event);
        } catch (JMSException e) {
            log.error(e);
        }
    }

    public MessageConverter getConverter() {
        return converter;
    }

    public void setConverter(MessageConverter converter) {
        this.converter = converter;
    }

    public NotifyLocalListener getNotifyLocalListener() {
        return notifyLocalListener;
    }

    public void setNotifyLocalListener(NotifyLocalListener notifyLocalListener) {
        this.notifyLocalListener = notifyLocalListener;
    }
}
