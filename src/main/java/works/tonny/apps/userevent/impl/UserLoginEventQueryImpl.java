package works.tonny.apps.userevent.impl;

import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.userevent.UserLoginEvent;
import works.tonny.apps.userevent.UserLoginEventQuery;

public class UserLoginEventQueryImpl extends AbstractUserEventQuery<UserLoginEventQuery, UserLoginEvent> implements
		UserLoginEventQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String agent;
	private String deviceId;
	private String ip;

	public UserLoginEventQueryImpl(BaseDAOSupport property) {
		this.dao = property;
	}

	@Override
	protected void doBuild() {
		addParameter(agent, "userAgent", ListSupport.MUST, ListSupport.LIKE);
		addParameter(deviceId, "deviceId", ListSupport.MUST, ListSupport.EQUALS);
		addParameter(ip, "ip", ListSupport.MUST, ListSupport.RLIKE);
	}

	@Override
	public UserLoginEventQuery userAgentLike(String agent) {
		this.agent = agent;
		return this;
	}

	@Override
	public UserLoginEventQuery deviceId(String deviceId) {
		this.deviceId = deviceId;
		return this;
	}

	@Override
	public UserLoginEventQuery ipLike(String ip) {
		this.ip = ip;
		return this;
	}

}
