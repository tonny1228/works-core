package works.tonny.apps.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * 用户岗位。岗位隶属部门，用户隶属岗位
 * 
 * @author tonny
 */
@javax.persistence.Entity
@Table(name = "u_position")
public class Position extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 名字
	 */
	protected String name;

	/*
	 * 会员密码
	 */
	protected String description;

	// protected String idLayer;
	//
	// protected String parentId;
	//
	// protected int orderNo;

	protected PositionTreeNode treeNode;

	protected transient Department department;

	protected transient JobLevel jobLevel;

	protected boolean commander;

	private transient Set<UserInfo> users = new HashSet<UserInfo>(0);

	public Position() {
		super();
	}

	public Position(String name, String description, String parentId) {
		super();
		this.name = name;
		this.description = description;
		this.treeNode = new PositionTreeNode();
		this.treeNode.setParentId(parentId);
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

	// @Column(length = 500, name = "id_layer")
	// public String getIdLayer() {
	// return idLayer;
	// }
	//
	// public void setIdLayer(String idLayer) {
	// this.idLayer = idLayer;
	// }
	//
	// @Column(length = 50, name = "parent_id")
	// public String getParentId() {
	// return parentId;
	// }
	//
	// public void setParentId(String parentId) {
	// this.parentId = parentId;
	// }
	//
	// @Column(name = "order_no")
	// public int getOrderNo() {
	// return orderNo;
	// }
	//
	// public void setOrderNo(int orderNo) {
	// this.orderNo = orderNo;
	// }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	/*
	 * @see works.tonny.user.model.VersionedObject#toString()
	 */
	@Override
	public String toString() {
		return "Position[" + name + "@" + id + "]";
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "positions", fetch = FetchType.LAZY)
	// @JoinTable(name = "u_user_position", inverseJoinColumns = @JoinColumn(name = "position_id"),
	// joinColumns = @JoinColumn(name = "user_id"))
	public Set<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(Set<UserInfo> users) {
		this.users = users;
	}

	@ManyToOne
	@JoinColumn(name = "job_level_id")
	public JobLevel getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(JobLevel jobLevel) {
		this.jobLevel = jobLevel;
	}

	@Transient
	public boolean isCommander() {
		return commander;
	}

	public void setCommander(boolean commander) {
		this.commander = commander;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Position)) {
			return false;
		}
		return id.equals(((Position) obj).getId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * @return the treeNode
	 */
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "id")
	public PositionTreeNode getTreeNode() {
		return treeNode;
	}

	/**
	 * @param treeNode the treeNode to set
	 */
	public void setTreeNode(PositionTreeNode treeNode) {
		this.treeNode = treeNode;
	}
}
