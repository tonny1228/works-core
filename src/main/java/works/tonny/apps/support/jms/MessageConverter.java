package works.tonny.apps.support.jms;


import org.springframework.jms.support.converter.MessageConversionException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.Serializable;

/**
 * JMS消息转换器
 * Created by Tonny on 2016/6/13.
 */
public class MessageConverter implements org.springframework.jms.support.converter.MessageConverter {

    /**
     * {@inheritDoc}
     * 封装发送的消息
     *
     * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object,
     * javax.jms.Session)
     */
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        if (object == null) {
            return session.createTextMessage(null);
        }

        if (object instanceof String) {
            return session.createTextMessage((String) object);
        }

        if (object instanceof Serializable) {
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setJMSExpiration(120000);
            objectMessage.setObject((Serializable) object);
            return objectMessage;
        }
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setJMSExpiration(120000);
        objectMessage.setObjectProperty("default", object);
        return objectMessage;
    }

    /**
     * {@inheritDoc}
     * 解封收到的消息
     *
     * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
     */
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        }
        if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            return objectMessage.getObject();
        }
        return message.getObjectProperty("default");
    }
}
