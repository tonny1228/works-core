package works.tonny.apps.user.model;

import java.util.HashMap;
import java.util.Map;

import works.tonny.apps.Status;

/**
 * 用户、部门状态
 * 
 * @author tonny
 */
@Deprecated
public class UserStatus extends Status<Integer> {

	public static final UserStatus ALL = new UserStatus(-1, "所有");

	public static final UserStatus INACTIVE = new UserStatus(0, "未激活");

	public static final UserStatus LOCKED = new UserStatus(2, "锁定");

	public static final UserStatus ACTIVE = new UserStatus(100, "活动");

	private static final Map<Integer, UserStatus> USER_STATUSES = new HashMap<Integer, UserStatus>();

	static {
		USER_STATUSES.put(-1, ALL);
		USER_STATUSES.put(0, INACTIVE);
		USER_STATUSES.put(100, ACTIVE);
		USER_STATUSES.put(2, LOCKED);
	}

	private UserStatus(Integer v, String name) {
		super(v, name);
	}

	/**
	 * 根据状态值返回状态
	 * 
	 * @param value 作态值
	 * @return 状态
	 */
	public static UserStatus parse(int value) {
		return USER_STATUSES.get(value);
	}
}
