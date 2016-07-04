package works.tonny.apps.support.jms;

import org.apache.commons.lang3.StringUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.llama.library.utils.Assert;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

/**
 * Created by Tonny on 2016/6/14.
 */
public class ActiveMQSpringHelper implements JMSHelper, ApplicationContextAware {
    private static Logger log = LogFactory.getLogger(ActiveMQSpringHelper.class);
    /**
     * spring 上下文环境
     */
    private ApplicationContext context;

    /**
     * jMS 连接
     */
    private ConnectionFactory connectionFactory;

    /**
     * 消息转换器
     */
    private MessageConverter messageConverter;

    /**
     * 节点名称
     */
    private String client;


    /**
     * 创建队列消息发送服务
     *
     * @param name 目标名
     */
    @Override
    public Sender createQueueSender(String name) {
        Assert.notNull(name);
        String beanPrefix = StringUtils.replace(name, ".", "_");
        Object destination = createDestination(name, beanPrefix, true);
//        log.info("create queue sender {0} {1} with {2}.", name, beanPrefix, destination);
        JmsTemplate jmsTemplate = createJMSTemplate(name, beanPrefix, true, destination);
        Sender sender = createSender(name, beanPrefix, true, jmsTemplate);
//        log.info("create queue sender {0} {1}", name, sender);
        return sender;
    }


    /**
     * 创建jms订阅消息发送服务
     *
     * @param name 目标名
     * @return
     */
    @Override
    public Sender createTopicSender(String name) {
        Assert.notNull(name);
        String beanPrefix = StringUtils.replace(name, ".", "_");
        Object destination = createDestination(name, beanPrefix, false);
//        log.info("create topic sender {0} {1} with {2}.", name, beanPrefix, destination);
        JmsTemplate jmsTemplate = createJMSTemplate(name, beanPrefix, false, destination);
        Sender sender = createSender(name, beanPrefix, false, jmsTemplate);

        return sender;
    }

    /**
     * 创建JMS 消息发送服务
     *
     * @param name        目标名称
     * @param beanPrefix  bean的前缀
     * @param queue       是queue还是topic
     * @param jmsTemplate jms template
     * @return
     */
    private Sender createSender(String name, String beanPrefix, boolean queue, JmsTemplate jmsTemplate) {
        String beanName = beanPrefix + (queue ? "Queue" : "Topic") + "MessageSender";
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        if (acf.containsBeanDefinition(beanName)) {
            Sender bean = (Sender) acf.getBean(beanName);
            log.info("return exist sender {0} {1}", name, bean);
            return bean;
        }

        GenericBeanDefinition sender = new GenericBeanDefinition();
        sender.setBeanClass(MessageSender.class);
        MutablePropertyValues prop = new MutablePropertyValues();
        prop.add("template", jmsTemplate);
        sender.setPropertyValues(prop);
        acf.registerBeanDefinition(beanName, sender);
        Sender bean = (Sender) acf.getBean(beanName);
        log.info("create sender {0} {1}", name, bean);
        return bean;
    }

    /**
     * 创建JMS template
     *
     * @param name        目标名称
     * @param beanPrefix  bean的前缀
     * @param queue       是queue还是topic
     * @param destination 目标
     */
    private JmsTemplate createJMSTemplate(String name, String beanPrefix, boolean queue, Object destination) {
        String beanName = beanPrefix + (queue ? "Queue" : "Topic") + "Template";
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        if (acf.containsBeanDefinition(beanName)) {
            JmsTemplate bean = (JmsTemplate) acf.getBean(beanName);
            log.info("return exist JmsTemplate {0} {1}", name, bean);
            return bean;
        }

        GenericBeanDefinition listenerDefinition = new GenericBeanDefinition();
        listenerDefinition.setBeanClass(org.springframework.jms.core.JmsTemplate.class);
        MutablePropertyValues prop = new MutablePropertyValues();
        prop.add("connectionFactory", connectionFactory);
        prop.add("defaultDestination", destination);
        prop.add("pubSubDomain", true);
        prop.add("messageConverter", messageConverter);
        listenerDefinition.setPropertyValues(prop);
        acf.registerBeanDefinition(beanName, listenerDefinition);
        JmsTemplate bean = (JmsTemplate) acf.getBean(beanName);
        log.info("create JmsTemplate {0} {1}", name, bean);
        return bean;
    }


    /**
     * 创建队列消息监听
     *
     * @param name
     */
    @Override
    public void createQueueListener(String name, MessageListener listener) {
        Assert.notNull(name);
        String beanPrefix = StringUtils.replace(name, ".", "_");
        Object destination = createDestination(name, beanPrefix, true);
//        log.info("create queue listener {0} {1} with {2}.", name, beanPrefix, destination);
        createMessageListenerContainer(name, beanPrefix, true, destination, listener);
    }


    /**
     * 创建订阅消息监听
     *
     * @param name
     */
    @Override
    public void createTopicListener(String name, MessageListener listener) {
        Assert.notNull(name);
        String beanPrefix = StringUtils.replace(name, ".", "_");
        Object destination = createDestination(name, beanPrefix, false);
//        log.info("create topic listener {0} {1} with {2}.", name, beanPrefix, destination);
        createMessageListenerContainer(name, beanPrefix, false, destination, listener);
    }


    /**
     * 创建JMS message listener
     *
     * @param name        目标名称
     * @param beanPrefix  bean的前缀
     * @param queue       是queue还是topic
     * @param destination 目标
     * @param listener    监听器
     */
    private void createMessageListenerContainer(String name, String beanPrefix, boolean queue, Object destination, MessageListener listener) {
        String beanName = beanPrefix + (queue ? "Queue" : "Topic") + "MessageListenerContainer";
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        if (acf.containsBeanDefinition(beanName)) {
            log.info("return exist listener {0} {1}", name, beanName);
            return;
        }

        GenericBeanDefinition listenerDefinition = new GenericBeanDefinition();
        listenerDefinition.setBeanClass(MessageListnerContainer.class);
        MutablePropertyValues prop = new MutablePropertyValues();
        prop.add("connectionFactory", connectionFactory);
        prop.add("destination", destination);
        prop.add("messageListener", listener);
        prop.add("pubSubDomain", true);
        if (!queue) {
            prop.add("subscriptionDurable", true);
            prop.add("clientId", client);
        }
        listenerDefinition.setPropertyValues(prop);
        acf.registerBeanDefinition(beanName, listenerDefinition);
        MessageListnerContainer bean = (MessageListnerContainer) acf.getBean(beanName);
        if (!bean.started) {
            bean.start();
        }
        log.info("create listener {0} with {1} {2}, running:{3}", name, destination, beanName, bean.isActive());
    }

    /**
     * 创建JMS Destination
     *
     * @param name       目标名称
     * @param beanPrefix bean的前缀
     * @param queue      是queue还是topic
     */
    private Object createDestination(String name, String beanPrefix, boolean queue) {
        String beanName = beanPrefix + (queue ? "Queue" : "Topic") + "Destination";
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        if (acf.containsBeanDefinition(beanName)) {
            Object bean = acf.getBean(beanName);
            log.info("return exist destination {0} {1}", name, bean);
            return bean;
        }

        GenericBeanDefinition destinationDefinition = new GenericBeanDefinition();
        destinationDefinition.setScope(GenericBeanDefinition.SCOPE_SINGLETON);
        if (queue) {
            destinationDefinition.setBeanClass(org.apache.activemq.command.ActiveMQQueue.class);
        } else {
            destinationDefinition.setBeanClass(org.apache.activemq.command.ActiveMQTopic.class);
        }
        ConstructorArgumentValues args = new ConstructorArgumentValues();
        args.addIndexedArgumentValue(0, beanPrefix);
        destinationDefinition.setConstructorArgumentValues(args);
        acf.registerBeanDefinition(beanName, destinationDefinition);
        Object bean = acf.getBean(beanName);
        log.info("create destination {0} {1}", name, bean);
        return bean;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public static class MessageListnerContainer extends DefaultMessageListenerContainer {
        boolean started;


        @Override
        public void start() {
            started = true;
            super.start();
            log.info(this + " started");
        }

    }
}
