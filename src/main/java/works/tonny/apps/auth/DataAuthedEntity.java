/**
 * 
 */
package works.tonny.apps.auth;

import works.tonny.apps.Entity;

/**
 * @author 祥栋
 * @date 2012-12-12
 * @version 1.0.0
 */
public abstract class DataAuthedEntity extends Entity {

	protected String creator;

	protected String updator;

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

}
