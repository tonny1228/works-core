package works.tonny.apps.support.message.jms;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import works.tonny.apps.support.jms.JMSHelper;
import works.tonny.apps.support.jms.Sender;
import works.tonny.apps.support.message.MessageBroadcastService;
import works.tonny.apps.support.message.MessageEvent;

import javax.jms.JMSException;
import java.util.Map;

/**
 * 通过jms来发送消息广播
 * Created by Tonny on 2016/6/13.
 */
public class JMSBroadcastService implements MessageBroadcastService {

    private Logger log = LogFactory.getLogger(getClass());

    /**
     * 消息发送服务
     */
    private Map<String, Sender> senderMap;


    /**
     * 消息服务
     */
    private JMSHelper springHelper;

    /**
     * 收到待发送消息后通过jms queue队列和topic队列都发送消息
     *
     * @param category 要发送的数据分类(类名)
     * @param status   监听操作
     * @param data     数据源
     */
    @Override
    public void notify(String category, MessageEvent.Status status, Object data) {
        try {
            MessageEvent message = new MessageEvent(category, status, data);
            springHelper.createQueueSender(category).sendMessage(message);
            springHelper.createTopicSender(category).sendMessage(message);
        } catch (JMSException e) {
            log.error(e);
        }
    }

    public JMSHelper getSpringHelper() {
        return springHelper;
    }

    public void setSpringHelper(JMSHelper springHelper) {
        this.springHelper = springHelper;
    }
}
