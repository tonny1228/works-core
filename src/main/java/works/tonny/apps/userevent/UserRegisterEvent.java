package works.tonny.apps.userevent;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户注册信息
 * 
 * @author tonny
 *
 */
@Table(name = "ue_register")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserRegisterEvent extends UserEvent {
	/**
	 * 网络模式
	 */
	private String networkMode;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * 注册IP
	 */
	private String ip;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 50)
	public String getId() {
		return super.getId();
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
