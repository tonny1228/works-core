package works.tonny.apps.userevent.impl;

// Generated 2015-6-15 9:48:32 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.userevent.UserEventLog;

/**
 * DAO interface for domain model class UserEventLog.
 * 
 * @see .UserEventLog
 * @author Tonny Liu
 */
public interface UserEventLogDAO extends EntityDAO<UserEventLog> {
	/**
	 * 分页查询UserEventLog列表
	 * 
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页条数
	 * @return 分页列表
	 */
	@ListSupport(offsetSupport = true)
	PagedList<UserEventLog> list(int offset, int limit);

}
