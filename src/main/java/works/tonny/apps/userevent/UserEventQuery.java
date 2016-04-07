package works.tonny.apps.userevent;

import java.util.Date;

import works.tonny.apps.Query;

/**
 * 用户事件查询接口
 * 
 * @author tonny
 *
 */
public interface UserEventQuery<T extends UserEventQuery<?, ?>, U extends UserEvent> extends Query<T, U> {
	// <T extends Query<?, ?>, U extends Object>
	/**
	 * 用户id等于
	 * 
	 * @param userId
	 * @return
	 */
	T userId(String userId);

	/**
	 * 用户登录名等于
	 * 
	 * @param username
	 * @return
	 */
	T username(String username);

	/**
	 * 用户姓名包含
	 * 
	 * @param name
	 * @return
	 */
	T nameLike(String name);

	/**
	 * 用户类型
	 * 
	 * @param userType
	 * @return
	 */
	T userType(String userType);

	/**
	 * 登录时间早于
	 * 
	 * @param before
	 * @return
	 */
	T eventTimeBefore(Date before);

	/**
	 * 登录时间晚于
	 * 
	 * @param before
	 * @return
	 */
	T eventTimeAfter(Date before);

	/**
	 * 按登录时间排序
	 * 
	 * @param direction
	 * @return
	 */
	T orderByEventTime(Direction direction);

}