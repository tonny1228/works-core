/**  
 * @Title: Query.java
 * @Package works.tonny.framework.dao
 * @author Tonny
 * @date 2012-7-26 上午8:45:34
 */
package works.tonny.apps;

import java.util.Arrays;
import java.util.List;

import org.llama.library.utils.PagedList;

/**
 * @ClassName: Query
 * @Description:
 * @author Tonny
 * @date 2012-7-26 上午8:45:34
 * @version 1.0
 */
public interface Query<T extends Query<?, ?>, U extends Object> {

	public static enum Direction {
		ASC, DESC
	};

	/** Executes the query and returns the number of results */
	long count();

	/**
	 * Executes the query and returns the resulting entity or null if no entity
	 * matches the query criteria.
	 * 
	 * @throws ActivitiException
	 *             when the query results in more than one entities.
	 */
	U singleResult();

	/**
	 * 数据列表
	 * 
	 * @Title: list
	 * @return 数据列表
	 * @date 2012-7-26 上午8:45:44
	 * @author tonny
	 * @version 1.0
	 */
	List<U> list();

	/**
	 * 分页列表
	 * 
	 * @Title: list
	 * @param page
	 *            页码
	 * @param pagesize
	 *            页面大小
	 * @return
	 * @date 2012-7-26 上午8:45:57
	 * @author tonny
	 * @version 1.0
	 */
	@Deprecated
	PagedList<U> list(int page, int pagesize);

	/**
	 * 分页列表
	 * 
	 * @Title: list
	 * @param offset
	 *            跳过的记录数
	 * @param limit
	 *            查询的数据条数
	 * @return
	 * @date 2012-7-26 上午8:45:57
	 * @author tonny
	 * @version 1.0
	 */
	PagedList<U> listRange(int offset, int limit);
}
