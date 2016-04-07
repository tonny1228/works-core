package works.tonny.apps.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("role")
@javax.persistence.Entity
@Table(name = "u_role")
public class Role extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3110718448558742890L;

	/**
	 * 名字
	 */
	protected String name;

	/*
	 * 会员密码
	 */
	protected String description;

	@XStreamOmitField
	protected transient Set<User> users = new HashSet<User>(0);

	/**
	 * <p>
	 * </p>
	 */
	public Role() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.Entity#getId()
	 */
	@Override
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@Column(length = 50)
	public String getId() {
		return super.getId();
	}

	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Column(length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "roles")
	// @JoinTable(name = "u_user_role", inverseJoinColumns = @JoinColumn(name = "role_id"),
	// joinColumns = @JoinColumn(name = "user_id"))
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.Entity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Role) {
			return getName() != null && getName().equals(((Role) obj).getName());
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return ("Role[" + getName() + "]").hashCode();
	}

}
