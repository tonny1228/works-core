/**  
 * @Title: Entity.java
 * @Package works.tonny.model
 * @author Tonny
 * @date 2012-4-18 下午5:01:53
 */
package works.tonny.apps;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * 实体类，id为字符串!
 * 
 * @ClassName: Entity
 * @Description:
 * @author Tonny
 * @date 2012-4-18 下午5:01:53
 * @version 1.0
 */
public abstract class Entity implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 只要id相同就认为两个对象相同
	 * 
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (id == null) {
			return false;
		}
		if (obj == null) {
			return false;
		}
		if (!obj.getClass().equals(this.getClass())) {
			return false;
		}
		if (id.equals(((Entity) obj).getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[id=" + id + "]@" + hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (getClass() + getId()).hashCode();
	}
}
