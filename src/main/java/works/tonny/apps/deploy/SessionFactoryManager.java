/**
 * 
 */
package works.tonny.apps.deploy;

import java.util.List;

import org.hibernate.SessionFactory;

/**
 * @author 祥栋
 */
public interface SessionFactoryManager {

	String SCHEMA = "schema";

	/**
	 * @param empty
	 */
	void createSchema(Schema empty);

	/**
	 * @param host
	 * @return
	 */
	SessionFactory getSessionFactory(String host);

	AbstractSchemaService getSchemaService();

	List<Schema> getSchemas();

	/**
	 * @return
	 */
	SessionFactory getSessionFactory();

}
