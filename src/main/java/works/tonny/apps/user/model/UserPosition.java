/**
 * 
 */
package works.tonny.apps.user.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户岗位映射
 * 
 * @author 祥栋
 * @date 2012-12-11
 * @version 1.0.0
 */
@Entity
@Table(name = "u_user_position")
public class UserPosition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6091911428648762376L;
	private UserPositionID id;

	@EmbeddedId
	public UserPositionID getId() {
		return id;
	}

	public void setId(UserPositionID id) {
		this.id = id;
	}
}
