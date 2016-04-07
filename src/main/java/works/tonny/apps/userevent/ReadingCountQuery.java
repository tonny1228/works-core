package works.tonny.apps.userevent;

// Generated 2015-6-15 9:26:22 by Hibernate Tools 3.4.0.CR1

import works.tonny.apps.Query;
import works.tonny.apps.support.QueryCriteria;
import works.tonny.apps.support.QueryOrder;

/**
 * Query interface for domain model class ReadingDetail.
 * 
 * @see .ReadingDetail
 * @author Tonny Liu
 */
public interface ReadingCountQuery extends Query<ReadingCountQuery, ReadingCount> {

	/**
	 * query with mainFunction
	 */
	@QueryCriteria(order = 0)
	ReadingCountQuery mainFunction(String mainFunction);

	/**
	 * query with subFunction
	 */
	@QueryCriteria(order = 0)
	ReadingCountQuery subFunction(String subFunction);

	/**
	 * query with dataId
	 */
	@QueryCriteria(order = 0)
	ReadingCountQuery dataId(String dataId);

	@QueryOrder(order = 0)
	ReadingCountQuery orderByNum(Direction direction);
}
