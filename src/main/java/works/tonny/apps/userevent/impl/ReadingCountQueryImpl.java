package works.tonny.apps.userevent.impl;

// Generated 2015-6-15 9:40:02 by Hibernate Tools 3.4.0.CR1

import works.tonny.apps.impl.AbstractCriteriaQuery;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.userevent.ReadingCount;
import works.tonny.apps.userevent.ReadingCountQuery;

/**
 * Query interface for domain model class ReadingCount.
 * 
 * @see .ReadingCount
 * @author Tonny Liu
 */
public class ReadingCountQueryImpl extends AbstractCriteriaQuery<ReadingCountQuery, ReadingCount> implements
		ReadingCountQuery {

	/**
	 * query with mainFunction
	 */
	private String mainFunction;

	/**
	 * query with subFunction
	 */
	private String subFunction;

	/**
	 * query with dataId
	 */
	private String dataId;

	/**
	 * @param baseDAOSupport
	 *            BaseDAOSupport
	 */
	public ReadingCountQueryImpl(BaseDAOSupport baseDAOSupport) {
		this.dao = baseDAOSupport;
	}

	/**
	 */
	protected void doBuild() {
		addParameter(mainFunction, "mainFunction", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(subFunction, "subFunction", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(dataId, "dataId", ListSupport.MUST, ListSupport.EQUALS);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.stat.ReadingCountQuery#mainFunction(String)
	 */
	public ReadingCountQuery mainFunction(String mainFunction) {
		this.mainFunction = mainFunction;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.stat.ReadingCountQuery#subFunction(String)
	 */
	public ReadingCountQuery subFunction(String subFunction) {
		this.subFunction = subFunction;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.stat.ReadingCountQuery#dataId(String)
	 */
	public ReadingCountQuery dataId(String dataId) {
		this.dataId = dataId;
		return this;
	}

	public ReadingCountQuery orderByNum(Direction direction) {
		return orderBy("num", direction);
	}
}
