package works.tonny.apps.support.message.jms;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import works.tonny.apps.support.jms.JMSHelper;
import works.tonny.apps.support.jms.MessageConverter;
import works.tonny.apps.support.message.LocalMessageRegisterService;
import works.tonny.apps.support.message.MessageEvent;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.HashMap;
import java.util.Map;

/**
 * JMS消息注册服务监听端口，接收哪些类可以被注册为监听服务
 * Created by Tonny on 2016/6/13.
 */
public class MessageRegisterMessageReceiver extends LocalMessageRegisterService implements MessageListener {
    private Logger log = LogFactory.getLogger(getClass());
    /**
     * 注册的临时监听，jms返回成功或失败后删除数据并注册
     */
    private Map<String, Object[]> temp = new HashMap<String, Object[]>();


    private MessageConverter converter;
    /**
     * 客户端名称，每个节点都不应该相同，要同JMSMessageService中的一置
     */
    private String client;


    private JMSMessageRegisterService jmsMessageService;

    private JMSHelper jmsHelper;

    /**
     * 监听消息，判断是否自己
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        try {
            Map<String, String> map = (Map<String, String>) converter.fromMessage(message);
            log.info("收到服务注册返回消息{0}", map);
            if (!map.get("client").equals(client)) {
                return;
            }

            if (map.get("success").equals("true")) {
                doRegsiter(map.get("listener"));
            } else {
                jmsMessageService.doUnRegsiter(map.get("listener"));
            }

        } catch (JMSException e) {
            log.error(e);
        }
    }

    public void add(String category, works.tonny.apps.support.message.MessageListener listener, MessageEvent.Status... status) {
        String name = listener.getClass().getName();
        temp.put(name, new Object[]{category, listener, status});
    }


    /**
     * 注册为本地的服务
     *
     * @param listener 监听服务类名
     */
    public void doRegsiter(String listener) {
        Object[] objects = temp.get(listener);
        temp.remove(listener);
        works.tonny.apps.support.message.MessageListener messageListener = (works.tonny.apps.support.message.MessageListener) objects[1];
        String category = (String) objects[0];
        super.register(category, messageListener, (MessageEvent.Status[]) objects[2]);
        if (messageListener.isSingleton()) {
//            jmsHelper.createQueueListener(category);
        } else {
//            jmsHelper.createTopicListener(category);
        }
    }

    /**
     * 取消注册服务
     *
     * @param listener 服务类名
     */
    public void doUnRegsiter(String listener) {
        temp.remove(listener);
    }

    public MessageConverter getConverter() {
        return converter;
    }

    public void setConverter(MessageConverter converter) {
        this.converter = converter;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public JMSMessageRegisterService getJmsMessageService() {
        return jmsMessageService;
    }

    public void setJmsMessageService(JMSMessageRegisterService jmsMessageService) {
        this.jmsMessageService = jmsMessageService;
    }
}
