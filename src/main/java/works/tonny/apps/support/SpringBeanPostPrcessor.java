/**
 * 
 */
package works.tonny.apps.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 通过此接口可以更改依赖组件包在spring中注册的bean，一旦被更改，所有的包都使用新的接口实现原有功能
 * 
 * @author 祥栋
 */
public class SpringBeanPostPrcessor implements InitializingBean, BeanPostProcessor, ApplicationContextAware {
	private ApplicationContext applicationContext;

	private Properties beans;

	private void load() throws IOException {
		beans = new Properties();
		Enumeration<URL> resources = getClass().getClassLoader().getResources("config/bean-replace.properties");
		while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			Properties property = new Properties();
			InputStream openStream = url.openStream();
			property.load(openStream);
			IOUtils.closeQuietly(openStream);
			beans.putAll(property);
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
	 *      java.lang.String)
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beans.containsKey(beanName)) {
			return applicationContext.getBean(beans.getProperty(beanName));
		}
		return bean;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object,
	 *      java.lang.String)
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beans.containsKey(beanName)) {
			return applicationContext.getBean(beans.getProperty(beanName));
		}
		return bean;
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
}
