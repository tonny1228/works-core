package works.tonny.apps.user.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.dom4j.Node;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.Mapping;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.AbstractType;
import org.hibernate.type.IntegerType;

import works.tonny.apps.user.model.UserStatus;

/**
 * hibernate用户状态类型映射
 * 
 * @author tonny
 * 
 */
public class UserStatusType extends AbstractType {
	private static final int[] TYPES = new int[] { Types.INTEGER };

	/**
	 * @param mapping
	 * @return
	 * @throws MappingException
	 * @see org.hibernate.type.Type#sqlTypes(org.hibernate.engine.Mapping)
	 */
	public int[] sqlTypes(Mapping mapping) throws MappingException {
		return TYPES;
	}

	/**
	 * @param mapping
	 * @return
	 * @throws MappingException
	 * @see org.hibernate.type.Type#getColumnSpan(org.hibernate.engine.Mapping)
	 */
	public int getColumnSpan(Mapping mapping) throws MappingException {
		return 1;
	}

	/**
	 * @return
	 * @see org.hibernate.type.Type#getReturnedClass()
	 */
	public Class getReturnedClass() {
		return UserStatus.class;
	}

	/**
	 * @param oldState
	 * @param currentState
	 * @param checkable
	 * @param session
	 * @return
	 * @throws HibernateException
	 * @see org.hibernate.type.Type#isDirty(java.lang.Object, java.lang.Object,
	 *      boolean[], org.hibernate.engine.SessionImplementor)
	 */
	public boolean isDirty(Object oldState, Object currentState, boolean[] checkable, SessionImplementor session)
			throws HibernateException {
		return !isSame(oldState, currentState, session.getEntityMode());
	}

	/**
	 * @param rs
	 * @param names
	 * @param session
	 * @param owner
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 * @see org.hibernate.type.Type#nullSafeGet(java.sql.ResultSet,
	 *      java.lang.String[], org.hibernate.engine.SessionImplementor,
	 *      java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		return nullSafeGet(rs, names[0], session, owner);
	}

	/**
	 * @param rs
	 * @param name
	 * @param session
	 * @param owner
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 * @see org.hibernate.type.Type#nullSafeGet(java.sql.ResultSet,
	 *      java.lang.String, org.hibernate.engine.SessionImplementor,
	 *      java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Integer value = (Integer) IntegerType.INSTANCE.nullSafeGet(rs, name, session, owner);
		if (value == null) {
			return UserStatus.INACTIVE;
		}
		return UserStatus.parse(value);
	}

	/**
	 * @param st
	 * @param value
	 * @param index
	 * @param settable
	 * @param session
	 * @throws HibernateException
	 * @throws SQLException
	 * @see org.hibernate.type.Type#nullSafeSet(java.sql.PreparedStatement,
	 *      java.lang.Object, int, boolean[],
	 *      org.hibernate.engine.SessionImplementor)
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable,
			SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			IntegerType.INSTANCE.nullSafeSet(st, 0, index, session);
			return;
		}
		IntegerType.INSTANCE.nullSafeSet(st, ((UserStatus) value).getValue(), index, session);
	}

	/**
	 * @param st
	 * @param value
	 * @param index
	 * @param session
	 * @throws HibernateException
	 * @throws SQLException
	 * @see org.hibernate.type.Type#nullSafeSet(java.sql.PreparedStatement,
	 *      java.lang.Object, int, org.hibernate.engine.SessionImplementor)
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		IntegerType.INSTANCE.nullSafeSet(st, value, index, session);
	}

	/**
	 * @param node
	 * @param value
	 * @param factory
	 * @throws HibernateException
	 * @see org.hibernate.type.Type#setToXMLNode(org.dom4j.Node,
	 *      java.lang.Object, org.hibernate.engine.SessionFactoryImplementor)
	 */
	public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory) throws HibernateException {
		IntegerType.INSTANCE.setToXMLNode(node, value, factory);
	}

	/**
	 * @param value
	 * @param factory
	 * @return
	 * @throws HibernateException
	 * @see org.hibernate.type.Type#toLoggableString(java.lang.Object,
	 *      org.hibernate.engine.SessionFactoryImplementor)
	 */
	public String toLoggableString(Object value, SessionFactoryImplementor factory) throws HibernateException {
		return IntegerType.INSTANCE.toLoggableString(value, factory);
	}

	/**
	 * @param xml
	 * @param factory
	 * @return
	 * @throws HibernateException
	 * @see org.hibernate.type.Type#fromXMLNode(org.dom4j.Node,
	 *      org.hibernate.engine.Mapping)
	 */
	public Object fromXMLNode(Node xml, Mapping factory) throws HibernateException {
		return IntegerType.INSTANCE.fromXMLNode(xml, factory);
	}

	/**
	 * @return
	 * @see org.hibernate.type.Type#getName()
	 */
	public String getName() {
		return "userStatus";
	}

	/**
	 * @param value
	 * @param entityMode
	 * @param factory
	 * @return
	 * @throws HibernateException
	 * @see org.hibernate.type.Type#deepCopy(java.lang.Object,
	 *      org.hibernate.EntityMode,
	 *      org.hibernate.engine.SessionFactoryImplementor)
	 */
	public Object deepCopy(Object value, EntityMode entityMode, SessionFactoryImplementor factory)
			throws HibernateException {
		return value == null ? UserStatus.INACTIVE : value;
	}

	/**
	 * @param original
	 * @param target
	 * @param session
	 * @param owner
	 * @param copyCache
	 * @return
	 * @throws HibernateException
	 * @see org.hibernate.type.Type#replace(java.lang.Object, java.lang.Object,
	 *      org.hibernate.engine.SessionImplementor, java.lang.Object,
	 *      java.util.Map)
	 */
	public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
			throws HibernateException {
		if (original == null)
			return null;
		if (original.equals(target))
			return target;
		return target;
	}

	/**
	 * @param value
	 * @param mapping
	 * @return
	 * @see org.hibernate.type.Type#toColumnNullness(java.lang.Object,
	 *      org.hibernate.engine.Mapping)
	 */
	public boolean[] toColumnNullness(Object value, Mapping mapping) {
		return IntegerType.INSTANCE.toColumnNullness(value, mapping);
	}

	/**
	 * @return
	 * @see org.hibernate.type.Type#isMutable()
	 */
	public boolean isMutable() {
		return false;
	}

}
