/**
 *
 */
package works.tonny.apps.support;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 通过此接口可以更改依赖组件包在spring中注册的bean，一旦被更改，所有的包都使用新的接口实现原有功能
 *
 * @author 祥栋
 */
public class SpringBeanPostPrcessor implements InitializingBean, BeanPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

//    private Properties beans;

    private Map<String, Define> mapping = new HashMap<String, Define>();

    private void load() throws IOException {
//        beans = new Properties();
        Enumeration<URL> resources = getClass().getClassLoader().getResources("config/bean-replace.properties");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            Properties property = new Properties();
            InputStream openStream = url.openStream();
            property.load(openStream);
            IOUtils.closeQuietly(openStream);
            Enumeration<Object> keys = property.keys();
            Set<Map.Entry<Object, Object>> entries = property.entrySet();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement().toString();
                if (!key.contains(".")) {
                    mapping.put(key, new Define(key, null, property.getProperty(key)));
                } else {
                    String beanName = StringUtils.substringBefore(key, ".");
                    mapping.put(beanName, new Define(beanName,
                            StringUtils.substringAfter(key, "."), property.getProperty(key)));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        load();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object,
     * java.lang.String)
     */
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!mapping.containsKey(beanName)) {
            return bean;
        }
        Define define = mapping.get(beanName);

        if (define.property == null)
            return applicationContext.getBean(define.beanName);
        else {
            Object value = applicationContext.getBean(define.target);
            try {
                BeanUtils.setProperty(bean, define.property, value);
            } catch (Exception e) {
                try {
                    throw new TypeMismatchException(value, bean.getClass().getDeclaredField(define.property).getType(), e);
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                }
            }
            return bean;
        }
    }

    /**
     * {@inheritDoc}`
     *
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object,
     * java.lang.String)
     */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!mapping.containsKey(beanName)) {
            return bean;
        }
        Define define = mapping.get(beanName);

        if (define.property == null)
            return applicationContext.getBean(define.beanName);
        else {
            Object value = applicationContext.getBean(define.target);
            try {
                BeanUtils.setProperty(bean, define.property, value);
            } catch (Exception e) {
                try {
                    throw new TypeMismatchException(value, bean.getClass().getDeclaredField(define.property).getType(), e);
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                }
            }
            return bean;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // ApplicationContext applicationContext =
        // event.getApplicationContext();
        // String beanName = null;
        // String newName = null;
        // DefaultListableBeanFactory acf = (DefaultListableBeanFactory)
        // applicationContext
        // .getAutowireCapableBeanFactory();
        // BeanDefinition oldDefinition = acf.getBeanDefinition(beanName);
        // BeanDefinition newDefinition = acf.getBeanDefinition(newName);
        // acf.removeBeanDefinition(beanName);
        // // acf.registerBeanDefinition(beanName, old);
    }

    class Define {
        String beanName;
        String property;
        String target;

        public Define(String beanName, String property, String target) {
            this.beanName = beanName;
            this.property = property;
            this.target = target;
        }
    }
}
