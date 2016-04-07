package works.tonny.apps.userevent.impl;

// Generated 2015-6-15 9:48:32 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import works.tonny.apps.impl.AbstractCriteriaQuery;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.userevent.UserEventLog;
import works.tonny.apps.userevent.UserEventLogQuery;

/**
 * Query interface for domain model class UserEventLog.
 * 
 * @see .UserEventLog
 * @author Tonny Liu
 */
public class UserEventLogQueryImpl extends AbstractCriteriaQuery<UserEventLogQuery, UserEventLog> implements
		UserEventLogQuery {

	/**
	 * query with id
	 */
	private String id;

	/**
	 * query with title
	 */
	private String title;

	/**
	 * query with type
	 */
	private int type;

	/**
	 * query with detail
	 */
	private String detail;

	/**
	 * query with createTime
	 */
	private Date createTime;

	/**
	 * query with userId
	 */
	private String userId;

	/**
	 * query with username
	 */
	private String username;

	/**
	 * query with name
	 */
	private String name;

	/**
	 * query with ref
	 */
	private String dataId;

	/**
	 * @param baseDAOSupport
	 *            BaseDAOSupport
	 */
	public UserEventLogQueryImpl(BaseDAOSupport baseDAOSupport) {
		this.dao = baseDAOSupport;
	}

	/**
	 */
	protected void doBuild() {
		addParameter(id, "id", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(title, "title", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(type, "type", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(detail, "detail", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(createTime, "createTime", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(userId, "userId", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(username, "username", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(name, "name", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(dataId, "dataId", ListSupport.MUST, ListSupport.EQUALS);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#id(String)
	 */
	public UserEventLogQuery id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#title(String)
	 */
	public UserEventLogQuery title(String title) {
		this.title = title;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#type(int)
	 */
	public UserEventLogQuery type(int type) {
		this.type = type;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#detail(String)
	 */
	public UserEventLogQuery detail(String detail) {
		this.detail = detail;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#createTime(Date)
	 */
	public UserEventLogQuery createTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#userId(String)
	 */
	public UserEventLogQuery userId(String userId) {
		this.userId = userId;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#username(String)
	 */
	public UserEventLogQuery username(String username) {
		this.username = username;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#name(String)
	 */
	public UserEventLogQuery name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.core.UserEventLogQuery#dataId(String)
	 */
	public UserEventLogQuery dataId(String dataId) {
		this.dataId = dataId;
		return this;
	}

	public UserEventLogQuery orderByEventTime(Direction direction) {
		return orderBy("eventTime", direction);
	}
}
