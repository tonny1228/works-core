package works.tonny.apps.support.jms;

import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import java.io.Serializable;

/**
 * Spring 消息发送接口
 * Created by Tonny on 2016/6/13.
 */
public class MessageSender implements Sender {
    private JmsTemplate template;

    @Override
    public void sendMessage(Serializable message) throws JMSException {
        template.convertAndSend(message);
    }

    @Override
    public void sendMessage(String message) throws JMSException {
        template.convertAndSend(message);
    }

    /**
     * @return the template
     */
    public JmsTemplate getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }


}
