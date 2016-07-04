package works.tonny.apps.support.message.jms;

import org.apache.commons.lang3.StringUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import works.tonny.apps.support.jms.JMSHelper;
import works.tonny.apps.support.jms.MessageConverter;
import works.tonny.apps.support.message.LocalMessageRegisterService;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * jms 消息注册服务,将服务注册到服务中心,由服务中心决定哪些服务可以注册,并返回到各客户端,客户端将监听服务注册到本地接口
 * Created by Tonny on 2016/6/12.
 */
public class JMSMessageRegisterService extends LocalMessageRegisterService implements javax.jms.MessageListener {
    private Logger log = LogFactory.getLogger(getClass());

    /**
     * 注册的临时监听，jms返回成功或失败后删除数据并注册
     */
    private Map<String, Object[]> temp = new HashMap<String, Object[]>();

    /**
     * 客户端名称，每个节点都不应该相同
     */
    private String client;

    /**
     * jms发送接收服务
     */
    private JMSHelper jmsHelper;

    private javax.jms.MessageListener listener;


    private MessageConverter converter;


    /**
     * 初始化监听
     */
    public JMSMessageRegisterService() {
        super();
        log.info(Thread.currentThread().getName() + ":" + this);
    }


    /**
     * 往消息中心注册服务
     *
     * @param category 监听分类
     * @param listener 消息监听类
     * @param status   监听哪些操作
     */
    @Override
    public void register(String category, MessageListener listener, MessageEvent.Status... status) {
        String name = listener.getClass().getName();
        temp.put(name, new Object[]{category, listener, status});
        Map<String, String> map = new HashMap<String, String>();
        map.put("category", category);
        map.put("listener", name);
        map.put("status", StringUtils.join(status, ","));
        map.put("client", client);
        map.put("singleton", String.valueOf(listener.isSingleton()));

        while (true) {
            //发送消息直到成功
            try {
                log.info("准备申请注册监听{0}", category);
                jmsHelper.createQueueSender("ServiceRegister").sendMessage((Serializable) map);
                break;
            } catch (JMSException e) {
                try {
                    Thread.sleep(10000L);
                } catch (InterruptedException e1) {
                    log.error(e);
                }
            }
        }
    }

    /**
     * 注册为本地的服务
     *
     * @param listener 监听服务类名
     */
    public void doRegsiter(String listener) {
        Object[] objects = temp.get(listener);
        temp.remove(listener);
        MessageListener messageListener = (MessageListener) objects[1];
        String category = (String) objects[0];
        super.register(category, messageListener, (MessageEvent.Status[]) objects[2]);
        log.info("准备注册监听{0}", category);
        if (messageListener.isSingleton()) {
            jmsHelper.createQueueSender(category);
            jmsHelper.createQueueListener(category, this.listener);
        } else {
            jmsHelper.createTopicSender(category);
            jmsHelper.createTopicListener(category, this.listener);
        }
    }


    @Override
    public void onMessage(Message message) {
        try {
            Map<String, String> map = (Map<String, String>) converter.fromMessage(message);
            if (!map.get("client").equals(client)) {
                return;
            }
            log.info("收到监听返回{0}", map);
            if (map.get("success").equals("true")) {
                doRegsiter(map.get("listener"));
            } else {
                doUnRegsiter(map.get("listener"));
            }

        } catch (JMSException e) {
            log.error(e);
        }
    }

    public void createTopicListener() {
        log.info("创建 ServiceRegisterResponse 监听");
        jmsHelper.createTopicListener("ServiceRegisterResponse", this);
    }

    /**
     * 取消注册服务
     *
     * @param listener 服务类名
     */
    public void doUnRegsiter(String listener) {
        temp.remove(listener);
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public JMSHelper getJmsHelper() {
        return jmsHelper;
    }

    public void setJmsHelper(JMSHelper jmsHelper) {
        this.jmsHelper = jmsHelper;
        createTopicListener();
    }

    public javax.jms.MessageListener getListener() {
        return listener;
    }

    public void setListener(javax.jms.MessageListener listener) {
        this.listener = listener;
    }

    public MessageConverter getConverter() {
        return converter;
    }

    public void setConverter(MessageConverter converter) {
        this.converter = converter;
    }
}
