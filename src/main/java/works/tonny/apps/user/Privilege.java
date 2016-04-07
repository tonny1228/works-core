package works.tonny.apps.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;
import works.tonny.apps.user.model.DepartmentPrivilege;
import works.tonny.apps.user.model.PositionPrivilege;
import works.tonny.apps.user.model.RolePrivilege;
import works.tonny.apps.user.model.UserPrivilege;

/**
 * 功能权限
 * 
 * @author tonny
 */
@javax.persistence.Entity
@Table(name = "a_privilege")
public class Privilege extends Entity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    protected String name;

    protected Date updateTime;

    protected String admin;

    /**
     * 权限映射的资源
     */
    protected Set<SystemResource> resources;

    protected transient Set<UserPrivilege> userPrivileges;

    protected transient Set<RolePrivilege> rolePrivileges;

    protected transient Set<PositionPrivilege> positionPrivileges;

    protected transient Set<DepartmentPrivilege> departmentPrivileges;


	public Privilege() {
		super();
	}

	public Privilege(String name) {
		super();
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.Entity#getId()
	 */
	@Override
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 50)
	public String getId() {
		return super.getId();
	}

	@Column(length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@OrderBy("packageName,api")
	@JoinTable(name = "a_privilege_resource", joinColumns = { @JoinColumn(name = "privilege_id") }, inverseJoinColumns = { @JoinColumn(name = "resource_id") })
	public Set<SystemResource> getResources() {
		return resources;
	}

	public void setResources(Set<SystemResource> resources) {
		this.resources = resources;
	}

	@Column(length = 100, name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(length = 50)
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	/**
	 * @return the userPrivileges
	 */
	@OneToMany(mappedBy = "privilege", cascade = { CascadeType.REMOVE })
	public Set<UserPrivilege> getUserPrivileges() {
		return userPrivileges;
	}

	/**
	 * @param userPrivileges the userPrivileges to set
	 */
	public void setUserPrivileges(Set<UserPrivilege> userPrivileges) {
		this.userPrivileges = userPrivileges;
	}

	/**
	 * @return the rolePrivileges
	 */
	@OneToMany(mappedBy = "privilege", cascade = { CascadeType.REMOVE })
	public Set<RolePrivilege> getRolePrivileges() {
		return rolePrivileges;
	}

	/**
	 * @param rolePrivileges the rolePrivileges to set
	 */
	public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}

	/**
	 * @return the positionPrivileges
	 */
	@OneToMany(mappedBy = "privilege", cascade = { CascadeType.REMOVE })
	public Set<PositionPrivilege> getPositionPrivileges() {
		return positionPrivileges;
	}

	/**
	 * @param positionPrivileges the positionPrivileges to set
	 */
	public void setPositionPrivileges(Set<PositionPrivilege> positionPrivileges) {
		this.positionPrivileges = positionPrivileges;
	}

	/**
	 * @return the departmentPrivileges
	 */
	@OneToMany(mappedBy = "privilege", cascade = { CascadeType.REMOVE })
	public Set<DepartmentPrivilege> getDepartmentPrivileges() {
		return departmentPrivileges;
	}

	/**
	 * @param departmentPrivileges the departmentPrivileges to set
	 */
	public void setDepartmentPrivileges(Set<DepartmentPrivilege> departmentPrivileges) {
		this.departmentPrivileges = departmentPrivileges;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Privilege))
			return false;
		Privilege castOther = (Privilege) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getName()).toHashCode();
	}

}
