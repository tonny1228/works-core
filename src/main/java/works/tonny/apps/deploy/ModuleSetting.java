/**
 * 
 */
package works.tonny.apps.deploy;

import java.io.Serializable;

/**
 * 组件的配置地址信息
 * 
 * @author 祥栋
 */
public class ModuleSetting implements Serializable {
	private String name;

	private String url;

	private String auth;

	/**
	 * @param name
	 * @param url
	 * @param auth
	 */
	public ModuleSetting(String name, String url, String auth) {
		super();
		this.name = name;
		this.url = url;
		this.auth = auth;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the auth
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

}
