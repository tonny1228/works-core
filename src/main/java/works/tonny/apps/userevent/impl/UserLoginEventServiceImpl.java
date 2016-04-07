package works.tonny.apps.userevent.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.llama.library.utils.HttpRequestUtils;
import org.llama.library.utils.HttpRequestUtils.UserAgent;

import works.tonny.apps.AbstractService;
import works.tonny.apps.Query.Direction;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.user.AbstractUser;
import works.tonny.apps.user.Member;
import works.tonny.apps.user.User;
import works.tonny.apps.userevent.UserLoginEvent;
import works.tonny.apps.userevent.UserLoginEventQuery;
import works.tonny.apps.userevent.UserLoginEventService;

public class UserLoginEventServiceImpl extends AbstractService implements UserLoginEventService {

	private UserLoginEventDAO userLoginEventDAO;

	@Override
	public String login(AbstractUser user) {
		UserLoginEvent event = new UserLoginEvent();
		event.setEventTime(new Date());
		event.setName(user.getName());
		event.setUserId(user.getId());
		event.setUsername(user.getUsername());
		event.setUserType((user instanceof User) ? "user" : ((Member) user).getUserId());
		return userLoginEventDAO.save(event);
	}

	@Override
	public String login(AbstractUser user, HttpServletRequest request) {
		UserLoginEvent event = new UserLoginEvent();
		event.setEventTime(new Date());
		event.setName(user.getName());
		event.setUserId(user.getId());
		event.setUsername(user.getUsername());
		event.setUserType((user instanceof User) ? "user" : ((Member) user).getUserId());
		event.setEventTime(new Date());

		event.setDeviceId(request.getParameter("deviceId"));
		String header = request.getHeader("User-Agent");
		UserAgent userAgent = HttpRequestUtils.getUserAgent(header);
		event.setIp(HttpRequestUtils.getRemoteIP(request));
		// event.setNetworkMode(request);
		event.setOs(userAgent.getOs());
		event.setUserAgent(header);
		return userLoginEventDAO.save(event);
	}

	@Override
	public void logout(AbstractUser user, String logId, boolean timeout) {
		UserLoginEvent event = null;
		if (logId != null) {
			event = userLoginEventDAO.get(logId);
		} else if (user != null) {
			event = createQuery().userId(user.getId()).orderByEventTime(Direction.DESC).singleResult();
		}
		if (event != null && event.getLogoutTime() == null) {
			event.setLogoutTime(new Date());
			event.setLogoutType(timeout ? 1 : 0);
			userLoginEventDAO.update(event);
		}
	}

	@Override
	public UserLoginEventQuery createQuery() {
		try {
			return new UserLoginEventQueryImpl((BaseDAOSupport) PropertyUtils.getProperty(userLoginEventDAO,
					"targetSource.target"));
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	public UserLoginEventDAO getUserLoginEventDAO() {
		return userLoginEventDAO;
	}

	public void setUserLoginEventDAO(UserLoginEventDAO userLoginEventDAO) {
		this.userLoginEventDAO = userLoginEventDAO;
	}

}
