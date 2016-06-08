/**
 *
 */
package works.tonny.apps.support;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.AbstractService;

/**
 * @author 祥栋
 * @version 1.0.0
 * @date 2012-12-18
 */
public class EntityLazyProxyImpl extends AbstractService implements EntityLazyProxy {

    @SuppressWarnings("rawtypes")
    private BaseDAOSupport dao;

    /**
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public <T> T refreshObject(Object entity) {
        if (entity instanceof AbstractPersistentCollection) {
            AbstractPersistentCollection collection = (AbstractPersistentCollection) entity;
            if (collection.wasInitialized()) {
                return (T) collection;
            }
            dao.refresh(collection.getOwner());
            try {
                final String field = StringUtils.substringAfterLast(collection.getRole(), ".");
                AbstractPersistentCollection target = (AbstractPersistentCollection) PropertyUtils.getProperty(
                        collection.getOwner(), field);
                PropertyUtils.setProperty(collection.getOwner(), field, target);
                target.toString();
            } catch (Exception e) {
                log.error(e);
            }
        }

        if ((entity instanceof HibernateProxy)
                && ((HibernateProxy) entity).getHibernateLazyInitializer().isUninitialized()) {
            dao.refresh(entity);
            entity.toString();
        }
        return (T) entity;
    }

    /**
     * java.lang.String[])
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public <T> T refreshObject(Object entity, String... fields) {
        if (entity instanceof AbstractPersistentCollection) {
            throw new IllegalArgumentException(entity.getClass() + "不允许访问fields");
        }
        if ((entity instanceof HibernateProxy)
                && ((HibernateProxy) entity).getHibernateLazyInitializer().isUninitialized()) {
            dao.refresh(entity);
        }
        for (String field : fields) {
            try {
                final Object property = PropertyUtils.getProperty(entity, field);
                refresh(property);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        entity.toString();
        return (T) entity;
    }

    /**
     * @see works.tonny.support.zxtx.apps.ssh.EntityLazyProxy#lookup(java.io.Serializable,
     * java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void refresh(Object entity) {
        if (entity instanceof AbstractPersistentCollection) {
            AbstractPersistentCollection collection = (AbstractPersistentCollection) entity;
            if (collection.wasInitialized()) {
                return;
            }
            dao.refresh(collection.getOwner());
            try {
                final String field = StringUtils.substringAfterLast(collection.getRole(), ".");
                AbstractPersistentCollection target = (AbstractPersistentCollection) PropertyUtils.getProperty(
                        collection.getOwner(), field);
                PropertyUtils.setProperty(collection.getOwner(), field, target);
                target.toString();
            } catch (Exception e) {
                log.error(e);
            }
        }

        if ((entity instanceof HibernateProxy)
                && ((HibernateProxy) entity).getHibernateLazyInitializer().isUninitialized()) {
            dao.refresh(entity);
            entity.toString();
        }
    }

    /**
     * @see com.zxtx.apps.support.EntityLazyProxy#refresh(java.lang.Object,
     * java.lang.String[])
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void refresh(Object entity, String... fields) {
        if (entity instanceof AbstractPersistentCollection) {
            throw new IllegalArgumentException(entity.getClass() + "不允许访问fields");
        }
        if ((entity instanceof HibernateProxy)
                && ((HibernateProxy) entity).getHibernateLazyInitializer().isUninitialized()) {
            dao.refresh(entity);
        }
        for (String field : fields) {
            try {
                final Object property = PropertyUtils.getProperty(entity, field);
                refresh(property);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        entity.toString();
    }

    public BaseDAOSupport getDao() {
        return dao;
    }

    public void setDao(BaseDAOSupport dao) {
        this.dao = dao;
    }

}
