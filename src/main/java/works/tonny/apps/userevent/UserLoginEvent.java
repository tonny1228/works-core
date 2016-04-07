package works.tonny.apps.userevent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 用户登录统计
 * 
 * @author tonny
 *
 */
@Entity
@Table(name = "ue_login")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserLoginEvent extends UserEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 网络模式
	 */
	private String networkMode;

	/**
	 * 客户端浏览器信息
	 */
	private String userAgent;

	/**
	 * 客户端操作系统
	 */
	private String os;

	/**
	 * 客户端设备号
	 */
	private String deviceId;

	/**
	 * 登出时间
	 */
	private Date logoutTime;

	/**
	 * 登出类型，0主动退出1 超时退出
	 */
	private int logoutType;

	/**
	 * 备注信息
	 */
	private String info;

	/**
	 * 登录IP
	 */
	private String ip;

	@Transient
	public Date getLoginTime() {
		return getEventTime();
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logout_time")
	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	@Column(name = "logout_type")
	public int getLogoutType() {
		return logoutType;
	}

	public void setLogoutType(int logoutType) {
		this.logoutType = logoutType;
	}

	@Column(length = 500)
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "user_agent", length = 200)
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Column(length = 50)
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "device_id", length = 100)
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column(length = 20)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "network_mode", length = 20)
	public String getNetworkMode() {
		return networkMode;
	}

	public void setNetworkMode(String networkMode) {
		this.networkMode = networkMode;
	}

}