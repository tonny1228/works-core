package works.tonny.apps.userevent.impl;

import java.util.Date;

import works.tonny.apps.Query;
import works.tonny.apps.impl.AbstractCriteriaQuery;
import works.tonny.apps.support.ListSupport;

public abstract class AbstractUserEventQuery<T extends Query<?, ?>, U> extends AbstractCriteriaQuery<T, U> {
	private String userId;
	private String username;
	private String nameLike;
	private String userType;
	private Date beforeDate;
	private Date afterDate;

	@Override
	protected void doBuild() {
		addParameter(userId, "userId", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(username, "username", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(nameLike, "name", ListSupport.MUST, ListSupport.LIKE);
		addParameter(userType, "userType", ListSupport.MUST, ListSupport.EQUALS);
		if (afterDate != null && beforeDate == null)
			addParameter(afterDate, "eventTime", ListSupport.MUST, ListSupport.GREATER_EQUALS);
		if (beforeDate != null && afterDate == null)
			addParameter(new Date(beforeDate.getTime() + 24 * 3600000 - 1), "eventTime", ListSupport.MUST,
					ListSupport.LESS_EQUALS);
		if (beforeDate != null && afterDate != null)
			addParameter(new Date[] { afterDate, new Date(beforeDate.getTime() + 24 * 3600000 - 1) }, "eventTime",
					ListSupport.MUST, ListSupport.BETWEEN);
	}

	public T userId(String userId) {
		this.userId = userId;
		return (T) this;
	}

	public T username(String username) {
		this.username = username;
		return (T) this;
	}

	public T nameLike(String name) {
		this.nameLike = name;
		return (T) this;
	}

	public T userType(String userType) {
		this.userType = userType;
		return (T) this;
	}

	public T eventTimeBefore(Date before) {
		this.beforeDate = before;
		return (T) this;
	}

	public T eventTimeAfter(Date after) {
		this.afterDate = after;
		return (T) this;
	}

	public T orderByEventTime(works.tonny.apps.Query.Direction direction) {
		orderBy("eventTime", direction);
		return (T) this;
	}

}