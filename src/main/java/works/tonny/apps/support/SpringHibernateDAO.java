package works.tonny.apps.support;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.ThreadLocalMap;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import works.tonny.apps.deploy.SessionFactoryManager;

/**
 * 
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 2.0.0
 */
public class SpringHibernateDAO extends DaoSupport implements HibernateDAO {

	private Map<String, HibernateTemplate> hibernateTemplate;

	private SessionFactoryManager sessionFactoryManager;

	/**
	 * 
	 */
	public SpringHibernateDAO() {
		hibernateTemplate = new HashMap<String, HibernateTemplate>();
	}

	public void flush() {
		getHibernateTemplate().flush();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object get(Class entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object get(Class entityClass, Serializable id, String lockMode) {
		return getHibernateTemplate().get(entityClass, id, LockMode.parse(lockMode));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object load(Class entityClass, Serializable id) {
		return getHibernateTemplate().load(entityClass, id);

	}

	public void load(Object entity, Serializable id) {
		getHibernateTemplate().load(entity, id);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object load(Class entityClass, Serializable id, String lockMode) {
		return getHibernateTemplate().load(entityClass, id, LockMode.parse(lockMode));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List loadAll(Class entityClass) {
		return getHibernateTemplate().loadAll(entityClass);

	}

	public void lock(Object entity, String lockMode) {
		getHibernateTemplate().lock(entity, LockMode.parse(lockMode));

	}

	public Object merge(Object entity) {
		return getHibernateTemplate().merge(entity);

	}

	public void refresh(Object entity) {
		getHibernateTemplate().refresh(entity);

	}

	public void refresh(Object entity, String lockMode) {
		getHibernateTemplate().refresh(entity, LockMode.parse(lockMode));

	}

	public Serializable save(Object entity) {
		return getHibernateTemplate().save(entity);

	}

	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);

	}

	@SuppressWarnings({ "rawtypes" })
	public void saveOrUpdateAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);

	}

	public void setFetchSize(int fetchSize) {
		getHibernateTemplate().setFetchSize(fetchSize);

	}

	public void setFlushMode(int flushMode) {
		getHibernateTemplate().setFlushMode(flushMode);
	}

	public void setFlushModeName(String constantName) {
		getHibernateTemplate().setFlushModeName(constantName);
	}

	public void setMaxResults(int maxResults) {
		getHibernateTemplate().setMaxResults(maxResults);

	}

	public void update(Object entity) {
		getHibernateTemplate().update(entity);

	}

	public void update(Object entity, String lockMode) {
		getHibernateTemplate().update(entity, LockMode.parse(lockMode));

	}

	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public void delete(Object entity, String lockMode) {
		getHibernateTemplate().delete(entity, LockMode.parse(lockMode));
	}

	@SuppressWarnings("rawtypes")
	public void deleteAll(Collection entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

	public void setSessionFactoryManager(SessionFactoryManager sessionFactoryManager) {
		this.sessionFactoryManager = sessionFactoryManager;
	}

	synchronized HibernateTemplate getHibernateTemplate() {
		String schema = ThreadLocalMap.getInstance().getObject(SessionFactoryManager.SCHEMA);
		if (hibernateTemplate.get(schema) == null && sessionFactoryManager.getSessionFactory(schema) != null) {
			hibernateTemplate.put(schema, new HibernateTemplate(sessionFactoryManager.getSessionFactory(schema)));
		} else {
			hibernateTemplate.put(schema, new HibernateTemplate(sessionFactoryManager.getSessionFactory(schema)));
		}
		return this.hibernateTemplate.get(schema);
	}

	/**
	 * @see org.springframework.dao.support.DaoSupport#checkDaoConfig()
	 */
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		if (this.sessionFactoryManager == null) {
			throw new IllegalArgumentException("'sessionFactoryManager'  is required");
		}
	}

	/**
	 * 查询
	 * 
	 * @see works.tonny.support.zxtx.apps.hibernate.HibernateDAO#find(java.lang.String,
	 *      java.lang.Object[])
	 */
	@SuppressWarnings("rawtypes")
	public List find(String queryString, Object... values) {
		// return getHibernateTemplate().find(queryString, values);
		return getHibernateTemplate().execute(new DefaultHibernateCallBack(queryString, values));
	}

	/**
	 * 返回列表
	 * 
	 * @param queryName
	 *            语句名称，不要前缀
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	// public List find(final org.llama.library.sqlmapping.Query query) {
	// return getHibernateTemplate().find(query.toString(), query.parameters());
	// }

	/**
	 * 返回分页列表
	 * 
	 * @param queryName
	 *            语句名称，不要前缀
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	@SuppressWarnings({ "rawtypes" })
	public PagedList find(final String queryString, final int page, final int pagesize, final Object... values) {
		PagedList l = (PagedList) getHibernateTemplate().execute(
				new PagedHibernateCallBack(queryString, values, (page - 1) * pagesize, pagesize));
		return l;
	}

	/**
	 * 返回分页列表
	 * 
	 * @param queryName
	 *            语句名称，不要前缀
	 * @param offset
	 *            offset
	 * @param pagesize
	 *            每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	@SuppressWarnings({ "rawtypes" })
	public PagedList list(final String queryString, final int offset, final int pagesize, final Object... values) {
		PagedList l = getHibernateTemplate().execute(new PagedHibernateCallBack(queryString, values, offset, pagesize));
		return l;
	}

	/**
	 * 返回分页列表
	 * 
	 * @param queryName
	 *            语句名称，不要前缀
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	// public PagedList find(final org.llama.library.sqlmapping.Query query,
	// final int page, final
	// int pagesize) {
	// final PagedList list = new PagedList();
	// final String queryString = query.toString();
	// list.setPage(page);
	// list.setPagesize(pagesize);
	// List l = getHibernateTemplate().executeFind(new HibernateCallback() {
	// public Object doInHibernate(Session session) throws HibernateException,
	// SQLException {
	// Query q = session.createQuery(queryString);
	// bindParameters(q, query.parameters());
	// list.setTotal(totalRow(session, queryString, query.parameters()));
	// q.setFirstResult((page - 1) * pagesize);
	// q.setMaxResults(pagesize);
	// return q.list();
	// }
	// });
	// list.addAll(l);
	// return list;
	// }

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.support.HibernateDAO#findByNamedParam(java.lang.String,java.lang.String[],
	 *      java.lang.Object[])
	 */
	@SuppressWarnings({ "rawtypes" })
	public List findByNamedParam(String queryString, Object[] values, final String[] paramName) {
		return getHibernateTemplate().findByNamedParam(queryString, paramName, values);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes" })
	public PagedList findByNamedParam(final String queryString, final Object[] values, final String[] paramNames,
			final int page, final int pagesize) {
		if (paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		PagedHibernateCallBack action = new PagedHibernateCallBack(queryString, values, paramNames, (page - 1)
				* pagesize, pagesize);
		return getHibernateTemplate().execute(action);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes" })
	public PagedList listByNamedParam(final String queryString, final Object[] values, final String[] paramNames,
			final int offset, final int pagesize) {
		if (paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}

		PagedHibernateCallBack action = new PagedHibernateCallBack(queryString, values, paramNames, offset, pagesize);
		return getHibernateTemplate().execute(action);
	}

	/**
	 * 按命名查询
	 * 
	 * @see works.tonny.support.zxtx.apps.hibernate.HibernateDAO#findByNamedQuery(java.lang.String,
	 *      java.lang.Object[])
	 */
	@SuppressWarnings("rawtypes")
	public List findByNamedQuery(final String queryName, final Object... values) {
		return getHibernateTemplate().execute(new NamedQueryAction(queryName, values));
	}

	/**
	 * 返回分页列表
	 * 
	 * @param queryName
	 *            语句名称，不要前缀
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	@SuppressWarnings("rawtypes")
	public PagedList findByNamedQuery(final String queryName, final int page, final int pagesize,
			final Object... values) {
		return getHibernateTemplate().execute(
				new NamedPageQueryAction(queryName, values, (page - 1) * pagesize, pagesize));
	}

	/**
	 * 返回分页列表
	 * 
	 * @param queryName
	 *            语句名称，不要前缀
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	@SuppressWarnings("rawtypes")
	public PagedList listByNamedQuery(final String queryName, final int offset, final int pagesize,
			final Object... values) {
		return getHibernateTemplate().execute(new NamedPageQueryAction(queryName, values, offset, pagesize));
	}

	/**
	 * 绑定参数
	 * 
	 * @Title: bindParameters
	 * @param q
	 *            查询
	 * @param values
	 *            参数
	 */
	protected void bindParameters(Query q, Object[] values) {
		if (values == null) {
			return;
		}
		int j = 0;
		int m = 0;
		String[] v = q.getNamedParameters();

		for (Object value : values) {
			if (value != null && (value instanceof Object[] || value.getClass().isArray())) {// in
																								// 这种情况用数组
				q.setParameterList(v[j++], (Object[]) value);
			} else if (value != null && value instanceof Collection) {
				q.setParameterList(v[j++], (Collection) value);
			} else
				q.setParameter(m++, value);
		}
	}

	/**
	 * 查询总的记录数
	 * 
	 * @Title: totalRow
	 * @param q
	 * @return
	 */
	protected int totalRow(Session session, String hql, Object[] value) {
		Pattern pattern = Pattern.compile("(\\sfrom|^from\\s)|(\\sgroup\\s)|(\\sorder\\s)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(hql);
		int gIndex = -1;
		int oIndex = -1;
		int fIndex = -1;
		while (matcher.find()) {
			if (matcher.groupCount() >= 1 && matcher.group(1) != null)
				fIndex = hql.indexOf(matcher.group(1));
			if (matcher.groupCount() >= 2 && matcher.group(2) != null)
				gIndex = hql.indexOf(matcher.group(2));
			if (matcher.groupCount() >= 3 && matcher.group(3) != null)
				oIndex = hql.indexOf(matcher.group(3));
		}

		String countHql = null;
		if (oIndex > 0 && gIndex > 0 && oIndex > gIndex) {
			countHql = "select count(distinct " + StringUtils.substring(hql, gIndex + 10, oIndex) + ") ";
			countHql += hql.substring(fIndex, gIndex);
		} else if (oIndex < 0 && gIndex > 0) {
			countHql = "select count(distinct " + StringUtils.substring(hql, gIndex + 10) + ") ";
			countHql += hql.substring(fIndex, gIndex);
		} else if (gIndex < 0) {
			countHql = "select count(*) ";
			countHql += (oIndex > -1 ? hql.substring(fIndex, oIndex) : hql.substring(fIndex));
		}

		// int indexOf = StringUtils.substring(hql, gIndex + 10).indexOf(" ");
		// countHql = (gIndex > -1 ? "select count(distinct "
		// + StringUtils.substring(hql, gIndex + 10, gIndex + 10 + (indexOf >= 0
		// ? indexOf : 0))
		// : "select count(*) ")
		// + (oIndex > -1 ? hql.substring(fIndex, oIndex) :
		// hql.substring(fIndex));
		Query q = session.createQuery(countHql);
		if (value != null)
			bindParameters(q, value);
		List<Long> list = q.list();
		if (list.isEmpty())
			return 0;
		else
			return list.get(0).intValue();
	}

	/**
	 * 查询总的记录数
	 * 
	 * @Title: totalRow
	 * @param q
	 * @return
	 */
	protected int totalRow(Session session, String hql, String[] paramNames, Object[] value) {
		// Pattern pattern =
		// Pattern.compile("(\\sfrom|^from\\s)|(\\sgroup\\s)|(\\sorder\\s)",
		// Pattern.CASE_INSENSITIVE);
		// Matcher matcher = pattern.matcher(hql);
		// int gIndex = -1;
		// int oIndex = -1;
		// int fIndex = -1;
		// while (matcher.find()) {
		// if (matcher.groupCount() >= 1 && matcher.group(1) != null)
		// fIndex = hql.indexOf(matcher.group(1));
		// if (matcher.groupCount() >= 2 && matcher.group(2) != null)
		// gIndex = hql.indexOf(matcher.group(2));
		// if (matcher.groupCount() >= 3 && matcher.group(3) != null)
		// oIndex = hql.indexOf(matcher.group(3));
		// }
		int gIndex = -1;
		int oIndex = -1;
		int fIndex = -1;

		Pattern pattern = Pattern.compile("(\\sfrom|^from\\s)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(hql);
		if (matcher.find()) {
			if (matcher.groupCount() >= 1 && matcher.group(1) != null)
				fIndex = hql.indexOf(matcher.group(1));
		}
		pattern = Pattern.compile("(\\sgroup\\s)", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(hql);
		if (matcher.find()) {
			if (matcher.groupCount() >= 1 && matcher.group(1) != null)
				gIndex = hql.indexOf(matcher.group(1));
		}
		pattern = Pattern.compile("(\\sorder\\s)", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(hql);
		if (matcher.find()) {
			if (matcher.groupCount() >= 1 && matcher.group(1) != null)
				oIndex = hql.indexOf(matcher.group(1));
		}

		String countHql = null;
		if (oIndex > 0 && gIndex > 0 && oIndex > gIndex) {
			countHql = "select count(distinct " + StringUtils.substring(hql, gIndex + 10, oIndex) + ") ";
		} else if (oIndex < 0 && gIndex > 0) {
			countHql = "select count(distinct " + StringUtils.substring(hql, gIndex + 10) + ") ";
		} else if (gIndex < 0) {
			countHql = "select count(*) ";
		}
		countHql += (gIndex > -1 ? hql.substring(fIndex, gIndex) : hql.substring(fIndex));
		// int indexOf = StringUtils.substring(hql, gIndex + 10).indexOf(" ");
		// countHql = (gIndex > -1 ? "select count(distinct "
		// + StringUtils.substring(hql, gIndex + 10, gIndex + 10 + (indexOf >= 0
		// ? indexOf : 0))
		// : "select count(*) ")
		// + (oIndex > -1 ? hql.substring(fIndex, oIndex) :
		// hql.substring(fIndex));
		Query q = session.createQuery(countHql);
		if (value != null) {
			for (int i = 0; i < value.length; i++) {
				applyNamedParameterToQuery(q, paramNames[i], value[i]);
			}
		}
		List<Long> list = q.list();
		if (list.isEmpty())
			return 0;
		else
			return list.get(0).intValue();
	}

	/**
	 * 设置查询的命名化参数
	 * 
	 * @param queryObject
	 * @param paramName
	 * @param value
	 * @throws HibernateException
	 * @author tonny
	 */
	protected void applyNamedParameterToQuery(Query queryObject, String paramName, Object value)
			throws HibernateException {
		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection) value);
		} else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[]) value);
		} else {
			queryObject.setParameter(paramName, value);
		}
	}

	/**
	 * 
	 * <p>
	 * 通用的查询分类
	 * </p>
	 * 
	 * @author tonny
	 * @version 1.0.0
	 */
	abstract class QueryAction<T extends List> implements HibernateCallback<T> {
		protected String queryString;

		protected Object[] values;

		protected String[] paramNames;

		private T list;

		public QueryAction(String queryString, Object[] values) {
			this.queryString = queryString;
			this.values = values;
		}

		public QueryAction(String queryString, Object[] values, String[] paramNames) {
			this(queryString, values);
			this.paramNames = paramNames;

		}

		public QueryAction(String queryString, Object[] values, String[] paramNames, int offset, int pagesize) {
			this.queryString = queryString;
			this.values = values;
			this.paramNames = paramNames;
			PagedList l = new PagedList();
			l.setPage(1 + (int) Math.ceil(offset / pagesize));
			l.setOffset(offset);
			l.setPagesize(pagesize);
			list = (T) l;
		}

		/**
		 * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
		 */
		@Override
		public T doInHibernate(Session session) throws HibernateException, SQLException {
			Query q = createQuery(session);
			if (values != null && paramNames != null) {
				for (int i = 0; i < values.length; i++) {
					applyNamedParameterToQuery(q, paramNames[i], values[i]);
				}
			} else if (values != null) {
				bindParameters(q, values);
			}
			if (list instanceof PagedList) {
				PagedList pl = (PagedList) list;
				pl.setTotal(totalRow(session, queryString, paramNames, values));
				q.setFirstResult(pl.getOffset());
				q.setMaxResults(pl.getPagesize());
				pl.addAll(q.list());
				return (T) pl;
			} else {
				return (T) q.list();
			}
		}

		protected abstract Query createQuery(Session session);
	}

	/**
	 * 
	 * <p>
	 * 查询所有的，不带分页的
	 * </p>
	 * 
	 * @author tonny
	 * @version 1.0.0
	 */
	@SuppressWarnings("rawtypes")
	class DefaultHibernateCallBack extends QueryAction<List> {

		/**
		 * @param queryString
		 * @param values
		 * @param paramNames
		 * @param offset
		 * @param pagesize
		 * @param total
		 */
		public DefaultHibernateCallBack(String queryString, Object[] values, String[] paramNames) {
			super(queryString, values, paramNames);
		}

		/**
		 * @param queryString
		 * @param values
		 * @param paramNames
		 * @param offset
		 * @param pagesize
		 * @param total
		 */
		public DefaultHibernateCallBack(String queryString, Object[] values) {
			super(queryString, values);
		}

		/**
		 * @see works.tonny.apps.support.SpringHibernateDAO.QueryAction#createQuery(org.hibernate.Session)
		 */
		@Override
		protected Query createQuery(Session session) {
			return session.createQuery(queryString);
		}
	}

	/**
	 * 
	 * <p>
	 * hibernate 查询分页数据和总条数
	 * </p>
	 * 
	 * @author tonny
	 * @version 1.0.0
	 */
	@SuppressWarnings("rawtypes")
	class PagedHibernateCallBack extends QueryAction<PagedList> {

		/**
		 * @param queryString
		 * @param values
		 * @param paramNames
		 * @param offset
		 * @param pagesize
		 * @param total
		 */
		public PagedHibernateCallBack(String queryString, Object[] values, String[] paramNames, int offset, int pagesize) {
			super(queryString, values, paramNames, offset, pagesize);
		}

		/**
		 * @param queryString
		 * @param values
		 * @param paramNames
		 * @param offset
		 * @param pagesize
		 * @param total
		 */
		public PagedHibernateCallBack(String queryString, Object[] values, int offset, int pagesize) {
			super(queryString, values, null, offset, pagesize);
		}

		/**
		 * @see works.tonny.apps.support.SpringHibernateDAO.QueryAction#createQuery(org.hibernate.Session)
		 */
		@Override
		protected Query createQuery(Session session) {
			return session.createQuery(queryString);
		}
	}

	/**
	 * 
	 * <p>
	 * 命名的查询
	 * </p>
	 * 
	 * @author tonny
	 * @version 1.0.0
	 */
	@SuppressWarnings("rawtypes")
	class NamedQueryAction extends QueryAction<List> {
		/**
		 * @param queryName
		 * @param values
		 */
		public NamedQueryAction(String queryName, Object[] values) {
			super(queryName, values);
		}

		/**
		 * @see works.tonny.apps.support.SpringHibernateDAO.QueryAction#createQuery(org.hibernate.Session)
		 */
		@Override
		protected Query createQuery(Session session) {
			return session.getNamedQuery(queryString);
		}
	}

	/**
	 * 
	 * <p>
	 * 命名的查询
	 * </p>
	 * 
	 * @author tonny
	 * @version 1.0.0
	 */
	@SuppressWarnings("rawtypes")
	class NamedPageQueryAction extends QueryAction<PagedList> {

		/**
		 * @param queryString
		 * @param values
		 * @param paramNames
		 * @param offset
		 * @param pagesize
		 */
		public NamedPageQueryAction(String queryString, Object[] values, int offset, int pagesize) {
			super(queryString, values, null, offset, pagesize);
		}

		/**
		 * @see works.tonny.apps.support.SpringHibernateDAO.QueryAction#createQuery(org.hibernate.Session)
		 */
		@Override
		protected Query createQuery(Session session) {
			return session.getNamedQuery(queryString);
		}
	}

}
