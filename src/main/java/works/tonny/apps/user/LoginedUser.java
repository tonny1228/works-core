package works.tonny.apps.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录系统的用户
 * 
 * @author tonny
 */
public abstract class LoginedUser implements Serializable {

	/**
	 * 当前登录用户
	 */
	protected AbstractUser user;

	/**
	 * 用户权限
	 */
	protected List<Privilege> privileges;

	protected Date loginTime = new Date();

	protected Date lastActiveTime = new Date();

	// private Set<String> apiCodes;

	private String schema;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * 
	 */
	public LoginedUser() {
	}

	/**
	 * <p>
	 * </p>
	 */
	public LoginedUser(User user) {
		this();
		this.user = user;
	}

	public LoginedUser(User user, List<Privilege> privileges) {
		this();
		this.user = user;
		this.privileges = privileges;
	}

	/**
	 * 验证用户功能权限
	 * 
	 * @Title: hasAuth
	 * @param authCode
	 * @return
	 * @date 2011-11-21 上午11:34:52
	 * @author tonny
	 * @version 1.0
	 */
	public abstract boolean hasAuth(String authCode);

	/**
	 * 更新用户最后活动时间
	 * 
	 * @author tonny
	 */
	public void active() {
		lastActiveTime.setTime(System.currentTimeMillis());
	}

	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}

	public <T> T getAttribute(String key) {
		return (T) attributes.get(key);
	}

	public AbstractUser getUser() {
		return user;
	}

	public void setUser(AbstractUser user) {
		this.user = user;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime
	 *            the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the lastActiveTime
	 */
	public Date getLastActiveTime() {
		return lastActiveTime;
	}

	/**
	 * @param lastActiveTime
	 *            the lastActiveTime to set
	 */
	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

}