/**
 * 
 */
package works.tonny.apps.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.user.Privilege;

/**
 * 权限配置。配置用户、岗位、角色等的权限
 * 
 * @author 祥栋
 * @date 2012-12-17
 * @version 1.0.0
 */
@MappedSuperclass
public abstract class EntityPrivilege implements Serializable {
	protected String id;
	protected Privilege privilege;

	/**
	 * @return 拥有权限的对象
	 */
	@Transient
	public abstract Object getEntity();

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne()
	@JoinColumn(name = "privilege_id", nullable = false)
	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

}
