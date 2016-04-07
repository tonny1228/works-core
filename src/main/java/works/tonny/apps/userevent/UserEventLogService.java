package works.tonny.apps.userevent;

import works.tonny.apps.user.AbstractUser;

// Generated 2015-6-15 9:48:32 by Hibernate Tools 3.4.0.CR1

/**
 * Service interface for domain model class UserEventLog.
 * 
 * @see .UserEventLog
 * @author Tonny Liu
 */
public interface UserEventLogService {

	/**
	 * 创建新的日志
	 * 
	 * @param user
	 * @param dataId
	 * @param title
	 * @param type
	 * @param detail
	 */
	void save(AbstractUser user, String dataId, String title, String type, String detail);

	/**
	 * 通过关联数据编号删除日志
	 * 
	 * @param dataId
	 */
	void delete(String dataId);

	/**
	 * 创建复合查询
	 */
	UserEventLogQuery createQuery();

}
