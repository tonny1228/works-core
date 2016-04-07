/**
 * 
 */
package works.tonny.apps.deploy;

import java.util.Properties;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.support.lob.LobHandler;

/**
 * @author 祥栋
 */
public abstract class SessionFactoryManagerFactory {
	protected Logger logger = LogFactory.getLogger(getClass());

	protected String writeDataSource;

	protected ApplicationContext app;

	protected String readDataSource;

	protected LobHandler lobHandler;

	protected Resource[] mappingLocations;

	protected String[] mappingPackages;

	protected Properties hibernateProperties;

	protected static SessionFactoryManager sessionFactoryManager;

	protected AbstractSchemaService schemaService;

	protected boolean multiSchemaSupport;
	
	

	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public Resource[] getMappingLocations() {
		return mappingLocations;
	}

	public void setMappingLocations(Resource[] mappingLocations) {
		this.mappingLocations = mappingLocations;
	}

	public Properties getHibernateProperties() {
		return hibernateProperties;
	}

	public void setHibernateProperties(Properties hibernateProperties) {
		this.hibernateProperties = hibernateProperties;
	}

	/**
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.app = applicationContext;
	}

	public String getWriteDataSource() {
		return writeDataSource;
	}

	public void setWriteDataSource(String writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

	public String getReadDataSource() {
		return readDataSource;
	}

	public void setReadDataSource(String readDataSource) {
		this.readDataSource = readDataSource;
	}

	public AbstractSchemaService getSchemaService() {
		return schemaService;
	}

	public void setSchemaService(AbstractSchemaService schemaService) {
		this.schemaService = schemaService;
		if (schemaService != null)
			multiSchemaSupport = true;
	}

	/**
	 * @return the mappingPackages
	 */
	public String[] getMappingPackages() {
		return mappingPackages;
	}

	/**
	 * @param mappingPackages the mappingPackages to set
	 */
	public void setMappingPackages(String[] mappingPackages) {
		this.mappingPackages = mappingPackages;
	}

	/**
	 * @return
	 */
	public boolean isMultiSchemaSupport() {
		return multiSchemaSupport;
	}

	/**
	 * @return the sessionFactoryManager
	 */
	public static SessionFactoryManager getSessionFactoryManager() {
		return sessionFactoryManager;
	}
}
