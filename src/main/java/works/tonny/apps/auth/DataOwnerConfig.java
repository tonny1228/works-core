/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-12-3 下午5:08:05
 * @author tonny
 */
package works.tonny.apps.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
@javax.persistence.Entity()
@Table(name = "s_owner_config")
public class DataOwnerConfig extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 当前用户id
	 */
	public static final String CURRENT_USER = "当前用户";

	/**
	 * 当前用户岗位id
	 */
	public static final String CURRENT_Position = "当前岗位";

	/**
	 * 当前用户户部门id
	 */
	public static final String CURRENT_Department = "当前部门";

	/**
	 * 当前用户管理单元id
	 */
	public static final String CURRENT_BussinessUnit = "当前单位";

	/**
	 * 隶属的模块，没什么用
	 */
	private String module;
	/**
	 * 要关联数据的类名
	 */
	private String entity;

	/**
	 * 默认的数据所有者，在更新数据时记录用户的身份,可以有多个
	 */
	private String defaultOwner;

	/**
	 * 是否允许所有者查看
	 */
	private boolean ownerView;

	/**
	 * 是否允许所有者修改
	 */
	private boolean ownerEdit;

	/**
	 * 是否允许所有者上级查看
	 */
	private boolean parentView;

	/**
	 * 是否允许所有者上级修改
	 */
	private boolean parentEdit;

	/**
	 * 是否允许所有者下级查看
	 */
	private boolean childView;

	/**
	 * 单位管理员角色id
	 */
	private String businessUnitRoleId;

	/**
	 * 机构管理员角色id
	 */
	private String organizationRoleId;

	/**
	 * 是否允许数据拥有者编辑数据
	 * 
	 * @return
	 * @author tonny
	 */
	@Column(name = "owner_edit")
	public boolean isOwnerEdit() {
		return ownerEdit;
	}

	/**
	 * 是否允许上级领导、上级 岗位、上级部门编辑数据
	 * 
	 * @return
	 * @author tonny
	 */
	@Column(name = "parent_edit")
	public boolean isParentEdit() {
		return parentEdit;
	}

	/**
	 * 单位管理员的角色id
	 * 
	 * @return
	 * @author tonny
	 */
	@Column(name = "bu_role_id", length = 50)
	public String getBusinessUnitRoleId() {
		return businessUnitRoleId;
	}

	/**
	 * 机构管理员角色id
	 * 
	 * @return
	 * @author tonny
	 */
	@Column(name = "org_role_id", length = 50)
	public String getOrganizationRoleId() {
		return organizationRoleId;
	}

	/**
	 * 是否允许当前用户访问其拥有的数据
	 * 
	 * @return
	 * @author tonny
	 */
	@Column(name = "owner_view")
	public boolean isOwnerView() {
		return ownerView;
	}

	/**
	 * 是否允许当前用户访问其岗位下用户拥有的数据
	 * 
	 * @return
	 * @author tonny
	 */
	@Column(name = "parent_view")
	public boolean isParentView() {
		return parentView;
	}

	/**
	 * 是否允许用户访问其部门及子部门下所有用户创建的数据
	 * 
	 * @return
	 * @author tonny
	 */
	@Column(name = "child_view")
	public boolean isChildView() {
		return childView;
	}

	/**
	 * 是否有数据拥有者是用户
	 * 
	 * @return
	 * @author tonny
	 */
	@Transient
	public boolean isBelongingToUser() {
		return defaultOwner.equals(CURRENT_USER);
	}

	/**
	 * 是否有数据拥有者是岗位
	 * 
	 * @return
	 * @author tonny
	 */
	@Transient
	public boolean isBelongingToPosition() {
		return defaultOwner.equals(CURRENT_Position);
	}

	/**
	 * 是否有数据拥有者是部门
	 * 
	 * @return
	 * @author tonny
	 */
	@Transient
	public boolean isBelongingToDepartment() {
		return defaultOwner.equals(CURRENT_Department);
	}

	/**
	 * 是否有数据拥有者是单位
	 * 
	 * @return
	 * @author tonny
	 */
	@Transient
	public boolean isBelongingToBusinessUnit() {
		return defaultOwner.equals(CURRENT_BussinessUnit);
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 50)
	public String getId() {
		return id;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * @return the defaultOwner
	 */
	@Column(name = "default_owner")
	public String getDefaultOwner() {
		return defaultOwner;
	}

	/**
	 * @param defaultOwner the defaultOwner to set
	 */
	public void setDefaultOwner(String defaultOwner) {
		this.defaultOwner = defaultOwner;
	}

	/**
	 * @param ownerView the ownerView to set
	 */
	public void setOwnerView(boolean ownerView) {
		this.ownerView = ownerView;
	}

	/**
	 * @param ownerEdit the ownerEdit to set
	 */
	public void setOwnerEdit(boolean ownerEdit) {
		this.ownerEdit = ownerEdit;
	}

	/**
	 * @param parentView the parentView to set
	 */
	public void setParentView(boolean parentView) {
		this.parentView = parentView;
	}

	/**
	 * @param parentEdit the parentEdit to set
	 */
	public void setParentEdit(boolean parentEdit) {
		this.parentEdit = parentEdit;
	}

	/**
	 * @param childView the childView to set
	 */
	public void setChildView(boolean childView) {
		this.childView = childView;
	}

	/**
	 * @param businessUnitRoleId the businessUnitRoleId to set
	 */
	public void setBusinessUnitRoleId(String businessUnitRoleId) {
		this.businessUnitRoleId = businessUnitRoleId;
	}

	/**
	 * @param organizationRoleId the organizationRoleId to set
	 */
	public void setOrganizationRoleId(String organizationRoleId) {
		this.organizationRoleId = organizationRoleId;
	}

	public static enum Type {
		VIEW, EDIT, NOT_VERIFY
	}

}