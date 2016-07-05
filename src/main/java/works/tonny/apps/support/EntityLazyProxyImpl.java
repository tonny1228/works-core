/**
 *
 */
package works.tonny.apps.support;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.mapping.Collection;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.AbstractService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

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
                Object owner = collection.getOwner();
                dao.refresh(owner);

                Object target = search(collection, owner, field);
                if (target == null) {
                    return (T) collection;
                }
                PropertyUtils.setProperty(collection.getOwner(), field, target);
                ((AbstractPersistentCollection) target).forceInitialization();
                return (T) target;
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
     * String)
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void refresh(Object entity) {
        if (entity instanceof AbstractPersistentCollection) {
            AbstractPersistentCollection collection = (AbstractPersistentCollection) entity;
            if (collection.wasInitialized()) {
                return;
            }

            try {
                final String field = StringUtils.substringAfterLast(collection.getRole(), ".");
                Object owner = collection.getOwner();
                dao.refresh(owner);

                Object target = search(collection, owner, field);
                if (target == null) {
                    return;
                }
                PropertyUtils.setProperty(collection.getOwner(), field, target);
                ((AbstractPersistentCollection) target).forceInitialization();
//                target.toString();
            } catch (Exception e) {
                log.error(e);
            }
            return;
        }

        if ((entity instanceof HibernateProxy)
                && ((HibernateProxy) entity).getHibernateLazyInitializer().isUninitialized()) {
            dao.refresh(entity);
            entity.toString();
        }
    }

    private Object search(Object originalTarget, Object originalOwner, String originalField) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object target = PropertyUtils.getProperty(originalOwner, originalField);
        if (originalTarget != target) {
            return target;
        }

        Set scaned = new HashSet();

        Field[] fields = originalOwner.getClass().getDeclaredFields();
//        scaned.add(originalOwner);
        for (Field field1 : fields) {
            if (field1.getType().isPrimitive() || field1.getType().equals(String.class) || field1.getType().isAssignableFrom(Collection.class) || field1.getType().getName().startsWith("java.")) {
                continue;
            }
            Object value = PropertyUtils.getProperty(originalOwner, field1.getName());
            Object o = scan(scaned, originalTarget, originalOwner, originalField, value, field1);
            if (o != null) {
                return o;
            }
        }
        return null;
    }


    private Object scan(Set scaned, Object originalTarget, Object originalOwner, String originalField, Object value, Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (value == null) {
            return null;
        }
        Field[] fields = value.getClass().getDeclaredFields();
        scaned.add(value);
        for (Field field1 : fields) {
            if (field1.getType().isPrimitive() || field1.getType().equals(String.class) || field1.getType().getName().startsWith("java.")) {
                continue;
            }

            try {
                Object v = PropertyUtils.getProperty(value, field1.getName());
                if (v == null) {
                    continue;
                }
                if (scaned.contains(v)) {
                    continue;
                }
                if (v.getClass().equals(originalOwner.getClass())) {
                    Object target = PropertyUtils.getProperty(v, originalField);
                    if (target != originalTarget) {
                        return target;
                    }
                } else {
                    Object newValue = scan(scaned, originalTarget, originalOwner, originalField, v, field1);
                    if (newValue != null) {
                        return newValue;
                    }
                }
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
            } catch (InvocationTargetException e) {
//                e.printStackTrace();
            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @see EntityLazyProxy#refresh(Object,
     * String[])
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
