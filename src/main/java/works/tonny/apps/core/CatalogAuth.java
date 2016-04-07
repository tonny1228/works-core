package works.tonny.apps.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;
import works.tonny.apps.user.Role;

@javax.persistence.Entity
@Table(name = "s_catalog_auth")
public class CatalogAuth extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	// private Position position;

	private Role role;
	/**
	 * 栏目编号组
	 */
	private Catalog catalog;

	/**
	 * 栏目层
	 */
	private String catalogLayer;

	/**
	 * 子栏目是否继承该权限
	 */
	private boolean extend;

	/**
	 * 添加权限
	 */
	private boolean edit;

	/**
	 * 审核权限
	 */
	private boolean verify;

	/**
	 * 删除权限
	 */
	private boolean remove;

	/**
	 * 删除权限
	 */
	private boolean read;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 操作人
	 */
	private String operator;

	// public Position getPosition() {
	// return position;
	// }
	//
	// public void setPosition(Position position) {
	// this.position = position;
	// }

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

	@ManyToOne
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne
	@JoinColumn(name = "catalog_id")
	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public boolean isExtend() {
		return extend;
	}

	public void setExtend(boolean extend) {
		this.extend = extend;
	}

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public boolean isVerify() {
		return verify;
	}

	public void setVerify(boolean verify) {
		this.verify = verify;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	@Column(name = "reading")
	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(length = 50)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "catalog_layer", length = 1000)
	public String getCatalogLayer() {
		return catalogLayer;
	}

	public void setCatalogLayer(String catalogLayer) {
		this.catalogLayer = catalogLayer;
	}

}
