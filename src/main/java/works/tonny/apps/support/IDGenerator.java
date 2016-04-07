/**
 * 
 */
package works.tonny.apps.support;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

/**
 * @author 祥栋
 */
public class IDGenerator implements IdentifierGenerator, Configurable {
	private String entityName;
	private String gid;
	private works.tonny.apps.core.IDGeneratorService idGenerator;

	public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {
		// TODO: cache the persister, this shows up in yourkit
		Serializable id = session.getEntityPersister(entityName, obj).getIdentifier(obj, session);
		if (id == null) {
			id = idGenerator.create(gid, null);
		}
		return id;
	}

	/**
	 * @return the idGenerator
	 */
	public works.tonny.apps.core.IDGeneratorService getIdGenerator() {
		return idGenerator;
	}

	/**
	 * @param idGenerator the idGenerator to set
	 */
	public void setIdGenerator(works.tonny.apps.core.IDGeneratorService idGenerator) {
		this.idGenerator = idGenerator;
	}

	public void configure(Type type, Properties params, Dialect d) throws MappingException {
		entityName = params.getProperty(ENTITY_NAME);
		gid = params.getProperty("gid");
		if (entityName == null || gid == null) {
			throw new MappingException("no entity name");
		}
	}
}
