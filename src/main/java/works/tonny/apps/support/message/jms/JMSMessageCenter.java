package works.tonny.apps.support.message.jms;


import org.apache.commons.lang3.BooleanUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import works.tonny.apps.support.jms.JMSHelper;
import works.tonny.apps.support.jms.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 消息服务中心，用于消息的分发、监听器的注册管理。
 * Created by Tonny on 2016/6/12.
 */
public class JMSMessageCenter implements MessageListener {
    private static Logger log = LogFactory.getLogger(MesssageReceiver.class);

    /**
     * 所有注册的监听
     */
    private Set<String> messageListeners = new HashSet<String>();

    /**
     * 所有客户端注册的监听
     */
    private Map<String, List<Map>> mapping = new HashMap<String, List<Map>>();

    /**
     * jms服务
     */
    private JMSHelper jmsHelper;

    /**
     * 消息转换服务
     */
    private MessageConverter messageConverter;


    /**
     * 注册监听
     */
    public void init() {
        jmsHelper.createQueueListener("ServiceRegister", this);
    }

    /**
     * 接收到消息，判断是否可以注册监听服务，返回客户端可以注册的监听接口
     *
     * @param message
     */
    @Override
    public synchronized void onMessage(Message message) {
        try {
            Map<String, String> map = (Map<String, String>) messageConverter.fromMessage(message);
            boolean singleton = BooleanUtils.toBoolean(map.get("singleton"));
            String category = map.get("category");
            String listener = map.get("listener");
            String status = map.get("status");
            String client = map.get("client");
            if (!singleton || (singleton && !messageListeners.contains(listener))) {
                messageListeners.add(listener);
                if (!mapping.containsKey(client)) {
                    mapping.put(client, new ArrayList<Map>());
                }
                mapping.get(client).add(map);
                map.put("success", "true");
            } else {
                map.put("success", "false");
            }
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jmsHelper.createTopicSender("ServiceRegisterResponse").sendMessage((Serializable) map);
            log.info("返回注册结果{0}", map);
        } catch (JMSException e) {
            log.error(e);
        }
    }

    public JMSHelper getJmsHelper() {
        return jmsHelper;
    }

    public void setJmsHelper(JMSHelper jmsHelper) {
        this.jmsHelper = jmsHelper;
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}