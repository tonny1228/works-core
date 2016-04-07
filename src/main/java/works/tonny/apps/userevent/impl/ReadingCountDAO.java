package works.tonny.apps.userevent.impl;

// Generated 2015-6-15 9:40:02 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.userevent.ReadingCount;

/**
 * DAO interface for domain model class ReadingCount.
 * 
 * @see .ReadingCount
 * @author Tonny Liu
 */
public interface ReadingCountDAO extends EntityDAO<ReadingCount> {
	/**
	 * 分页查询ReadingCount列表
	 * 
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页条数
	 * @return 分页列表
	 */
	@ListSupport(offsetSupport = true)
	PagedList<ReadingCount> list(int offset, int limit);

}
