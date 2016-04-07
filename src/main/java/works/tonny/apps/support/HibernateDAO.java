/**  
 * @Title: HibernateDAO.java
 * @Package works.tonny.hibernate
 * @author Tonny
 * @date 2012-4-19 上午9:53:17
 */
package works.tonny.apps.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.llama.library.utils.PagedList;

/**
 * @ClassName: HibernateDAO
 * @Description:
 * @author Tonny
 * @date 2012-4-19 上午9:53:17
 * @version 2.0
 */
public interface HibernateDAO {
	void delete(Object entity);

	void delete(Object entity, String lockMode);

	void deleteAll(Collection entities);

	void evict(Object entity);

	void flush();

	Object get(Class entityClass, Serializable id);

	Object get(Class entityClass, Serializable id, String lockMode);

	Object load(Class entityClass, Serializable id);

	Object load(Class entityClass, Serializable id, String lockMode);

	List loadAll(Class entityClass);

	void lock(Object entity, String lockMode);

	Object merge(Object entity);

	void refresh(Object entity);

	void refresh(Object entity, String lockMode);

	Serializable save(Object entity);

	void saveOrUpdate(Object entity);

	void saveOrUpdateAll(Collection entities);

	void setFetchSize(int fetchSize);

	void setFlushMode(int flushMode);

	void setFlushModeName(String constantName);

	void setMaxResults(int maxResults);

	void update(Object entity);

	void update(Object entity, String lockMode);

	// void setSchema(String schema);

	List find(String queryString, Object... values);

	// List find(final org.llama.library.sqlmapping.Query query);

	/**
	 * 返回分页列表
	 * 
	 * @param queryName 语句名称，不要前缀
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	@Deprecated
	PagedList find(final String queryString, final int page, final int pagesize, final Object... value);

	/**
	 * 返回分页列表
	 * 
	 * @param queryName 语句名称，不要前缀
	 * @param offset 越过数量
	 * @param pagesize 每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	PagedList list(final String queryString, final int offset, final int pagesize, final Object... value);

	// PagedList find(final org.llama.library.sqlmapping.Query query, final int
	// page, final int
	// pagesize);

	List findByNamedParam(String queryString, Object[] values, final String[] paramName);

	/**
	 * 根据命名的参数查询返回分页列表
	 * 
	 * @param queryName 语句名称，不要前缀
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	@Deprecated
	PagedList findByNamedParam(final String queryString, final Object[] value, final String[] paramName,
			final int page, final int pagesize);

	/**
	 * 返回分页列表
	 * 
	 * @param queryName 语句名称，不要前缀
	 * @param offset 越过条数
	 * @param pagesize 每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	PagedList listByNamedParam(final String queryString, final Object[] value, final String[] paramName,
			final int offset, final int pagesize);

	List findByNamedQuery(final String queryName, final Object... values);

	/**
	 * 返回分页列表
	 * 
	 * @param queryName 语句名称，不要前缀
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	PagedList findByNamedQuery(final String queryName, final int page, final int pagesize, final Object... values);

	/**
	 * 返回分页列表
	 * 
	 * @param queryName 语句名称，不要前缀
	 * @param offset 越过条数
	 * @param pagesize 每页条数
	 * @value 参数值
	 * @return 分页列表
	 */
	PagedList listByNamedQuery(final String queryName, final int offset, final int pagesize, final Object... values);

}
