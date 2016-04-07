package works.tonny.apps.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * 附件关联表
 */
@javax.persistence.Entity
@Table(name = "s_attach_reference")
public class AttachReference extends Entity {

	/** nullable persistent field */
	private String catalog;

	/** nullable persistent field */
	private String objectId;

	/** nullable persistent field */
	private Date updateTime;

	/** nullable persistent field */
	private String admin;

	/** persistent field */
	private Attachment attachment;

	/** full constructor */
	public AttachReference(String id, String function, String objectId, Date updateTime, String admin,
			Attachment attachment) {
		this.id = id;
		this.catalog = function;
		this.objectId = objectId;
		this.updateTime = updateTime;
		this.admin = admin;
		this.attachment = attachment;
	}

	/** default constructor */
	public AttachReference() {
	}

	/** minimal constructor */
	public AttachReference(Attachment attachment) {
		this.attachment = attachment;
	}

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 50, name = "object_id")
	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(length = 50)
	public String getAdmin() {
		return this.admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "attach_id")
	public Attachment getAttachment() {
		return this.attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof AttachReference))
			return false;
		AttachReference castOther = (AttachReference) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Column(length = 50)
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

}
