package works.tonny.apps;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import works.tonny.apps.support.jms.JMSHelper;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tonny on 2016/6/15.
 */
@ContextConfiguration(locations = {
        "classpath*:/config/applicationContext-mod-test-message.xml"})
public class JMSTest extends AbstractJUnit4SpringContextTests implements MessageListener {

    @Autowired
    private JMSHelper jmsHelper;

    @Autowired
    private MessageListener messageListener;

    @Test
    public void testSpring() throws InterruptedException {
        jmsHelper.createQueueListener("works_tonny_apps_user_User", messageListener);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 2000);
        Thread.sleep(10000L);
    }


    @Test
    public void test() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, "tcp://192.168.0.242:61616");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // 不支持事务
        Queue destination = session.createQueue("hello");

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        Message msg = null;
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObjectProperty("default", "123456");
        msg = objectMessage;
        connection.start();
//        producer.send(msg);
//        connection.close();


        Session session1 = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // 不支持事务
        Queue destination1 = session1.createQueue("hello");
        Thread.sleep(10000);
    }

    @Override
    public void onMessage(Message message) {
        System.out.println(message);
    }
}
