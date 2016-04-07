package works.tonny.apps.userevent;

// Generated 2015-6-15 9:48:32 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import works.tonny.apps.Query;
import works.tonny.apps.Query.Direction;
import works.tonny.apps.support.QueryCriteria;
import works.tonny.apps.support.QueryOrder;

/**
 * Query interface for domain model class UserEventLog.
 * 
 * @see .UserEventLog
 * @author Tonny Liu
 */
public interface UserEventLogQuery extends Query<UserEventLogQuery, UserEventLog> {
	/**
	 * query with title
	 */
	@QueryCriteria(order = 0)
	UserEventLogQuery title(String title);

	/**
	 * query with type
	 */
	@QueryCriteria(order = 0)
	UserEventLogQuery type(int type);

	/**
	 * query with detail
	 */
	@QueryCriteria(order = 0)
	UserEventLogQuery detail(String detail);

	/**
	 * query with createTime
	 */
	@QueryCriteria(order = 0)
	UserEventLogQuery createTime(Date createTime);

	/**
	 * query with userId
	 */
	@QueryCriteria(order = 0)
	UserEventLogQuery userId(String userId);

	/**
	 * query with userId
	 */
	@QueryCriteria(order = 0)
	UserEventLogQuery dataId(String dataId);

	/**
	 * query with username
	 */
	@QueryCriteria(order = 0)
	UserEventLogQuery username(String username);

	/**
	 * query with name
	 */
	@QueryCriteria(order = 0)
	UserEventLogQuery name(String name);

	@QueryOrder(order = 0)
	UserEventLogQuery orderByEventTime(Direction direction);
}
