package works.tonny.apps.user.model;

// Generated 2012-12-3 13:14:27 by Hibernate Tools /Coder tools 1.0.0 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Company generated by hbm2java
 * 
 * @author Tonny Liu
 */
public class Company implements java.io.Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String info;
	private Integer orderNo;
	private Integer type;
	private Date createTime;
	private Date updateTime;
	private Set<Department> departments = new HashSet<Department>(0);

	public Company() {
	}

	public Company(String id, String name) {
		setId(id);
		setName(name);
	}

	public Company(String id, String name, String info, Integer orderNo, Integer type, Date createTime,
			Date updateTime, Set<Department> departments) {
		setId(id);
		setName(name);
		setInfo(info);
		setOrderNo(orderNo);
		setType(type);
		setCreateTime(createTime);
		setUpdateTime(updateTime);
		setDepartments(departments);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set<Department> getDepartments() {
		return this.departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

}
