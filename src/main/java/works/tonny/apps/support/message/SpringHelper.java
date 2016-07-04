package works.tonny.apps.support.message;

import org.apache.commons.lang.StringUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import works.tonny.apps.support.message.jms.JMSMessageCenter;

import java.util.Map;

/**
 * 通过spring配置决定消息服务使用的类
 * Created by Tonny on 2016/6/14.
 */
public class SpringHelper implements ApplicationContextAware {
    private Logger log = LogFactory.getLogger(SpringHelper.class);
    public static final String BROADCAST_BEAN = "broadcastBean";
    public static final String MESSAGE_SERVICE_BEAN = "messageServiceBean";

    private String type;

    private Map<String, String> initMap;

    private String center;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (StringUtils.isEmpty(type) || type.equals("${message.type}")) {
            type = "local";
        }
        log.info("使用{0}进行消息广播", (type.equals("local") ? "本地" : type));
        MessageManager.setBroadcastService((MessageBroadcastService) applicationContext.getBean(initMap.get(type + "." + BROADCAST_BEAN)));
        MessageManager.setMessageService((MessageRegisterService) applicationContext.getBean(initMap.get(type + "." + MESSAGE_SERVICE_BEAN)));
        if ("true".equalsIgnoreCase(center) && "jms".equalsIgnoreCase(type)) {
            JMSMessageCenter jmsMessageCenter = (JMSMessageCenter) applicationContext.getBean("jmsMessageCenter");
            jmsMessageCenter.init();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getInitMap() {
        return initMap;
    }

    public void setInitMap(Map<String, String> initMap) {
        this.initMap = initMap;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
