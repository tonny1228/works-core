package works.tonny.apps.userevent;

/**
 * 用户登录查询
 * 
 * @author tonny
 *
 */
public interface UserLoginEventQuery extends UserEventQuery<UserLoginEventQuery, UserLoginEvent> {

	/**
	 * useragent包含
	 * 
	 * @param agent
	 * @return
	 */
	UserLoginEventQuery userAgentLike(String agent);

	/**
	 * 设备编号等于
	 * 
	 * @param deviceId
	 * @return
	 */
	UserLoginEventQuery deviceId(String deviceId);

	/**
	 * IP段
	 * 
	 * @param ip
	 * @return
	 */
	UserLoginEventQuery ipLike(String ip);

}