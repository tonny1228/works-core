package works.tonny.apps.user.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * 部门
 * 
 * @version 1.1
 */
@Entity
@Table(name = "u_department")
@Inheritance(strategy = InheritanceType.JOINED)
public class Department implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 347993353092913182L;

	protected String id;

	protected String name;

	protected String description;

	// protected String idLayer;

	// protected String parentId;

	/**
	 * 编码
	 */
	protected String code;

	protected String properties;

	protected Position commander;

	protected String commanderPositionId;

	// protected int orderNo;

	private DepartmentType type;

	@Transient
	private Map<String, BusinessUnit> bu;

	private transient Set<Position> positions = new HashSet<Position>(0);

	/**
	 * 父部门
	 */
	private transient Department parentDept;

	private DepartmentTreeNode treeNode;

	private Date createTime;

	private String admin;

	/**
	 * 部门级别
	 * 
	 * @since 1.1
	 */
	private int level;

	public Department() {
		super();
	}

	public Department(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@Column(length = 50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// @Column(length = 300, name = "id_layer")
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
	@JoinColumn(name = "parent_id", insertable = false, updatable = false)
	public Department getParentDept() {
		return parentDept;
	}

	public void setParentDept(Department parentDept) {
		this.parentDept = parentDept;
	}

	@Transient
	public String getRootId() {
		return StringUtils.substringBefore(treeNode.getIdLayer(), ",");
	}

	@Column(length = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Department[" + id + "," + name + "]";
	}

	@Column(name = "type", length = 50)
	@Enumerated(EnumType.STRING)
	public DepartmentType getType() {
		return type;
	}

	public void setType(DepartmentType type) {
		this.type = type;
	}

	@OneToMany(mappedBy = "department")
	// @JoinTable(name = "u_position", joinColumns = @JoinColumn(name =
	// "dept_id"),
	// inverseJoinColumns = @JoinColumn(name = "dept_id"))
	public Set<Position> getPositions() {
		return positions;
	}

	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}

	@Column(length = 500)
	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	// @ManyToOne
	// @JoinColumn(name = "position_id")
	@Transient
	public Position getCommander() {
		return commander;
	}

	public void setCommander(Position commander) {
		this.commander = commander;
	}

	@Transient
	public Map<String, BusinessUnit> getBu() {
		return bu;
	}

	public void setBu(Map<String, BusinessUnit> bu) {
		this.bu = bu;
	}

	/**
	 * @return the code
	 */
	@Column(length = 50)
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the commanderPositionId
	 */
	@Column(name = "position_id", length = 50)
	public String getCommanderPositionId() {
		return commanderPositionId;
	}

	/**
	 * @param commanderPositionId
	 *            the commanderPositionId to set
	 */
	public void setCommanderPositionId(String commanderPositionId) {
		this.commanderPositionId = commanderPositionId;
	}

	/**
	 * @return the treeNode
	 */
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "id")
	public DepartmentTreeNode getTreeNode() {
		return treeNode;
	}

	/**
	 * @param treeNode
	 *            the treeNode to set
	 */
	public void setTreeNode(DepartmentTreeNode treeNode) {
		this.treeNode = treeNode;
	}

	/**
	 * 
	 * @return the level
	 */
	@Column(name = "dp_level")
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(length = 50)
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

}
