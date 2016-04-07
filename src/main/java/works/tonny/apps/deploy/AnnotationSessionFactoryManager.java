/**
 * 
 */
package works.tonny.apps.deploy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.llama.library.exception.ExceptionUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.llama.library.utils.ThreadLocalMap;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import works.tonny.apps.exception.DAOException;
import works.tonny.apps.exception.ServiceException;
import works.tonny.apps.support.HibernateTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * hibernate sessionfactory 管理器,通过schema和事务创建或返回只读或可写sessionfactory
 * 
 * @author 祥栋
 * @date 2012-11-15
 * @version 1.0.0
 */
public class AnnotationSessionFactoryManager implements SessionFactoryManager {
	private Logger logger = LogFactory.getLogger(getClass());

	private Map<String, SessionFactory> factories;

	private Map<String, Configuration> configs;

	private Map<String, SessionFactory> readFactories;

	private SessionFactoryManagerFactory factory;

	private List<Schema> schemas = new ArrayList<Schema>();

	protected AbstractSchemaService schemaService;

	/**
	 * 
	 */
	AnnotationSessionFactoryManager(SessionFactoryManagerFactory factory) {
		factories = new HashMap<String, SessionFactory>();
		readFactories = new HashMap<String, SessionFactory>();
		configs = new HashMap<String, Configuration>();
		this.factory = factory;
	}

	/**
	 * 创建session factory
	 * 
	 * @param schema
	 */
	public void createSchema(Schema schema) {
		bindReadDataSource(schema);
		bindWriteDataSource(schema);
		if (!factory.isMultiSchemaSupport() && !factories.isEmpty()) {
			throw new ServiceException("配置不支持多schema");
		}
		schemas.add(schema);

		DataSource writeDataSource = schema.getWriteDataSource();
		SessionFactory sessionFactory = createSessionFactory(writeDataSource, schema);
		factories.put(schema.getHost(), sessionFactory);

		if (schema.getReadPool() == null) {
			readFactories.put(schema.getHost(), sessionFactory);
			return;
		}
		DataSource readDataSource = schema.getReadDataSource();
		SessionFactory readSessionFactory = null;
		if (readDataSource != null) {
			readSessionFactory = createSessionFactory(readDataSource, schema);
		} else {
			readSessionFactory = sessionFactory;
		}
		readFactories.put(schema.getHost(), readSessionFactory);

	}

	/**
	 * 获取或创建只读数据源,配置信息为空返回默认数据源
	 * 
	 * @param schema 数据源配置
	 * @return 数据源
	 */
	void bindReadDataSource(Schema schema) {
		Pool readPool = schema.getReadPool();
		if (readPool == null) {
			if (factory.readDataSource == null || !factory.app.containsBean(factory.readDataSource)) {
				return;
			}
			DataSource bean = (DataSource) factory.app.getBean(factory.readDataSource);
			schema.setReadDataSource(bean);
			return;
		}

		String name = "ds-" + readPool.getId();
		if (factory.app.containsBean(name)) {
			DataSource bean = (DataSource) factory.app.getBean(name);
			schema.setReadDataSource(bean);
			return;
		}

		BeanDefinition beanDefinition = new ChildBeanDefinition(
				factory.readDataSource == null ? factory.writeDataSource : factory.readDataSource);
		DefaultListableBeanFactory acf = (DefaultListableBeanFactory) factory.app.getAutowireCapableBeanFactory();
		acf.registerBeanDefinition(name, beanDefinition);
		ComboPooledDataSource cpds = (ComboPooledDataSource) factory.app.getBean(name);
		cpds.setJdbcUrl(readPool.getUrl());
		cpds.setUser(readPool.getUsername());
		cpds.setPassword(readPool.getPassword());
		cpds.setInitialPoolSize(readPool.getInitialSize());
		cpds.setMinPoolSize(readPool.getMinSize());
		cpds.setMaxPoolSize(readPool.getMaxSize());
		schema.setReadDataSource(cpds);
	}

	/**
	 * 获取或创建可写数据源,配置信息为空返回默认数据源
	 * 
	 * @param schema 数据源配置
	 * @return 数据源
	 */
	void bindWriteDataSource(Schema schema) {
		Pool writePool = schema.getWritePool();
		if (writePool == null) {
			DataSource bean = (DataSource) factory.app.getBean(factory.writeDataSource);
			schema.setWriteDataSource(bean);
			return;
		}

		String name = "ds-" + writePool.getId();
		if (factory.app.containsBean(name)) {
			DataSource bean = (DataSource) factory.app.getBean(name);
			schema.setWriteDataSource(bean);
			return;
		}

		BeanDefinition beanDefinition = new ChildBeanDefinition(factory.writeDataSource);
		DefaultListableBeanFactory acf = (DefaultListableBeanFactory) factory.app.getAutowireCapableBeanFactory();
		acf.registerBeanDefinition(name, beanDefinition);
		ComboPooledDataSource cpds = (ComboPooledDataSource) factory.app.getBean(name);
		cpds.setJdbcUrl(writePool.getUrl());
		cpds.setUser(writePool.getUsername());
		cpds.setPassword(writePool.getPassword());
		cpds.setInitialPoolSize(writePool.getInitialSize());
		cpds.setMinPoolSize(writePool.getMinSize());
		cpds.setMaxPoolSize(writePool.getMaxSize());
		schema.setReadDataSource(cpds);
	}

	/**
	 * 根据配置文件生成session factory
	 * 
	 * @param writeDataSource
	 * @param schema
	 * @return
	 */
	private SessionFactory createSessionFactory(DataSource dataSource, Schema schema) {
		// LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		AnnotationSessionFactoryBean factoryBean = new AnnotationSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setLobHandler(factory.getLobHandler());
		factoryBean.setMappingLocations(factory.getMappingLocations());
		factoryBean.setPackagesToScan(factory.getMappingPackages());
		// factoryBean.set
		Properties hibernateProperties = new Properties();
		hibernateProperties.putAll(factory.getHibernateProperties());
		if (StringUtils.isNotEmpty(schema.getId())) {
			hibernateProperties.put("hibernate.default_schema", schema.getId());
		}
		factoryBean.setHibernateProperties(hibernateProperties);
		try {
			factoryBean.afterPropertiesSet();
		} catch (Exception e) {
			Exception e1 = ExceptionUtils.handle(e, "dao", "创建session factory出错");
			if (e1 != null) {
				throw new DAOException(e1);
			}
		}
		if (configs.get(schema.getHost()) == null) {
			configs.put(schema.getHost(), factoryBean.getConfiguration());
		}
		SessionFactory sessionFactory = (SessionFactory) factoryBean.getObject();
		logger.info("创建SessionFactory[name:{0},dataSource:{1}]", schema.getId(), dataSource);
		return sessionFactory;
	}

	/**
	 * 获取session factory
	 * 
	 * @param schema
	 * @return
	 */
	public SessionFactory getSessionFactory(String host) {
		if (null == ThreadLocalMap.getInstance().getObject(HibernateTransactionManager.USE_TRANSACTION)
				&& readFactories.get(host) != null) {
			logger.debug("使用只读会话");
			return readFactories.get(host);
		}

		if (!factory.isMultiSchemaSupport()) {
			return factories.get(null);
		}

		logger.debug("使用可写会话");
		return factories.get(host);
	}

	/**
	 * 获取session factory
	 * 
	 * @param schema
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		String host = ThreadLocalMap.getInstance().getObject(SessionFactoryManager.SCHEMA);
		if (null == ThreadLocalMap.getInstance().getObject(HibernateTransactionManager.USE_TRANSACTION)
				&& readFactories.get(host) != null) {
			logger.debug("使用只读会话");
			return readFactories.get(host);
		}

		if (!factory.isMultiSchemaSupport()) {
			return factories.get(null);
		}

		logger.debug("使用可写会话");
		return factories.get(host);
	}

	/**
	 * 获取session factory
	 * 
	 * @param schema
	 * @param schema
	 * @return
	 */
	public Configuration getConfiguration(String schema) {
		logger.debug("使用可写会话");
		return configs.get(schema);
	}

	public Set<String> schemas() {
		return configs.keySet();
	}

	/**
	 * 获取session factory
	 * 
	 * @param schema
	 * @return
	 */
	public SessionFactory getDefaultSessionFactory() {
		return factories.get(null);
	}

	void destroy() {
		for (String key : factories.keySet()) {
			factories.get(key).close();
		}
	}

	/**
	 * @return the factory
	 */
	public SessionFactoryManagerFactory getFactory() {
		return factory;
	}

	/**
	 * @return the schemas
	 */
	public List<Schema> getSchemas() {
		return schemas;
	}

	/**
	 * @return the schemaService
	 */
	public AbstractSchemaService getSchemaService() {
		return schemaService;
	}

	/**
	 * @param schemaService the schemaService to set
	 */
	public void setSchemaService(AbstractSchemaService schemaService) {
		this.schemaService = schemaService;
	}

}
