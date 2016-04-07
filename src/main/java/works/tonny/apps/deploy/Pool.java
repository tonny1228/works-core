/**
 * 
 */
package works.tonny.apps.deploy;

import java.io.Serializable;
import java.util.Date;

/**
 * 要动态创建的连接池
 * 
 * @author 祥栋
 * @date 2012-11-20
 * @version 1.0.0
 */
public class Pool implements Serializable {

	protected String id;

	protected String name;

	protected String host;

	protected int port;

	protected String url;

	protected String username;

	protected String password;

	protected int initialSize = 5;

	protected int maxSize = 20;

	protected int minSize = 1;

	protected Date createTime;

	protected Date updateTime;

	/**
	 * @param id
	 * @param name
	 * @param host
	 * @param port
	 * @param url
	 * @param username
	 * @param password
	 * @param initialSize
	 * @param maxSize
	 * @param minSize
	 */
	public Pool(String id, String name, String host, int port, String url, String username, String password,
			int initialSize, int minSize, int maxSize) {
		super();
		this.id = id;
		this.name = name;
		this.host = host;
		this.port = port;
		this.url = url;
		this.username = username;
		this.password = password;
		this.initialSize = initialSize;
		this.maxSize = maxSize;
		this.minSize = minSize;
	}
	
	

	/**
	 * @param id
	 */
	public Pool(String id) {
		super();
		this.id = id;
	}



	/**
	 * @param url
	 * @param username
	 * @param password
	 * @param initialSize
	 * @param maxSize
	 * @param minSize
	 */
	public Pool(String id, String url, String username, String password, int initialSize, int minSize, int maxSize) {
		super();
		this.id = id;
		this.url = url;
		this.username = username;
		this.password = password;
		this.initialSize = initialSize;
		this.maxSize = maxSize;
		this.minSize = minSize;
	}

	/**
	 * @param url
	 * @param username
	 * @param password
	 */
	public Pool(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * 
	 */
	public Pool() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
