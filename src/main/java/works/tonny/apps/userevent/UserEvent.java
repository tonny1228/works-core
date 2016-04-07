package works.tonny.apps.userevent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * 用户行为统计模型
 * 
 * @author tonny
 *
 */
@MappedSuperclass
public abstract class UserEvent extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 登录名
	 */
	private String username;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 发生时间
	 */
	private Date eventTime;

	/**
	 * 用户类型
	 */
	private String userType;

	@Override
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 50)
	public String getId() {
		return super.getId();
	}

	@Column(length = 50)
	public String getUsername() {
		return username;
	}

	public void setUsername(String usernmae) {
		this.username = usernmae;
	}

	@Column(length = 50, name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "event_time")
	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	@Column(name = "user_type", length = 50)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
