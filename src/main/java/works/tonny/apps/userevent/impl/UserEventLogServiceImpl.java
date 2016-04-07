package works.tonny.apps.userevent.impl;

// Generated 2015-6-15 9:48:32 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.llama.library.utils.PagedList;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.user.AbstractUser;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.Member;
import works.tonny.apps.user.User;
import works.tonny.apps.userevent.UserEventLog;
import works.tonny.apps.userevent.UserEventLogQuery;
import works.tonny.apps.userevent.UserEventLogService;

/**
 * Service object for domain model class UserEventLog.
 * 
 * @see .UserEventLog
 * @author Tonny Liu
 */
public class UserEventLogServiceImpl extends AuthedAbstractService implements UserEventLogService {
	private UserEventLogDAO userLogDAO;

	@Override
	public void save(AbstractUser user, String dataId, String title, String type, String detail) {
		UserEventLog log = new UserEventLog();
		log.setDataId(dataId);
		log.setDetail(detail);
		log.setEventTime(new Date());
		log.setName(user.getName());
		log.setTitle(title);
		log.setType(type);
		log.setUserId(user.getId());
		log.setUsername(user.getUsername());
		log.setUserType((user instanceof User) ? "user" : ((Member) user).getUserId());
		userLogDAO.save(log);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(String dataId) {
		List<UserEventLog> list = createQuery().dataId(dataId).list();
		userLogDAO.deleteAll(list);
	}

	public UserEventLogQuery createQuery() {
		try {
			return new UserEventLogQueryImpl((BaseDAOSupport) PropertyUtils.getProperty(userLogDAO,
					"targetSource.target"));
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	public UserEventLogDAO getUserEventLogDAO() {
		return userLogDAO;
	}

	public void setUserEventLogDAO(UserEventLogDAO userLogDAO) {
		this.userLogDAO = userLogDAO;
	}

	public UserEventLogDAO getUserLogDAO() {
		return userLogDAO;
	}

	public void setUserLogDAO(UserEventLogDAO userLogDAO) {
		this.userLogDAO = userLogDAO;
	}

}
