/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-11-28 下午2:42:58
 * @author tonny
 */
package works.tonny.apps.auth;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * <p>
 * 数据所有者
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DataOwner<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String ownerId;

	protected String ownerName;

	protected String ownerType;

	protected Date authBeginTime;

	protected Date authEndTime;

	protected Date authTime;
	/**
	 * 是否可修改删除。默认有记录就有读权限
	 */
	protected boolean writable;

	protected T data;

	/**
	 * @return the id
	 */
	@Column(length = 50)
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Id
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ownerId
	 */
	@Column(length = 50, name = "owner_id")
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the onwerName
	 */
	@Column(length = 150, name = "owner_name")
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param onwerName the onwerName to set
	 */
	public void setOwnerName(String onwerName) {
		this.ownerName = onwerName;
	}

	/**
	 * @return the ownerType
	 */
	@Column(length = 50, name = "owner_type")
	public String getOwnerType() {
		return ownerType;
	}

	/**
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * @return the authBeginTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auth_begin_time")
	public Date getAuthBeginTime() {
		return authBeginTime;
	}

	/**
	 * @param authBeginTime the authBeginTime to set
	 */
	public void setAuthBeginTime(Date authBeginTime) {
		this.authBeginTime = authBeginTime;
	}

	/**
	 * @return the authEndTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auth_end_time")
	public Date getAuthEndTime() {
		return authEndTime;
	}

	/**
	 * @param authEndTime the authEndTime to set
	 */
	public void setAuthEndTime(Date authEndTime) {
		this.authEndTime = authEndTime;
	}

	/**
	 * @return the authTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "authn_time")
	public Date getAuthTime() {
		return authTime;
	}

	/**
	 * @param authTime the authTime to set
	 */
	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	/**
	 * @return the t
	 */
	@Transient
	public T getData() {
		return data;
	}

	/**
	 * @param t the t to set
	 */
	public void setData(T t) {
		this.data = t;
	}

	/**
	 * @return the writable
	 */
	public boolean isWritable() {
		return writable;
	}

	/**
	 * @param writable the writable to set
	 */
	public void setWritable(boolean writable) {
		this.writable = writable;
	}

}
