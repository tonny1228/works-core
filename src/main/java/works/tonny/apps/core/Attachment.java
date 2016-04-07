package works.tonny.apps.core;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * 附件
 * 
 * @author tonny
 */
@javax.persistence.Entity
@Table(name = "s_attachment")
public class Attachment extends Entity {

	/** 站点编号 */
	private String catalog;

	/** 名称 */
	private String title;

	/** 说明 */
	private String info;

	/** 文件名称 */
	private String filename;

	/** 文件大小 */
	private int filesize;

	/** 文件类型 */
	private String mimetype;

	/** 附件后缀 */
	private String fileext;

	/** 文件路径 */
	private String path;

	/** 上传时间 */
	private Date updateTime;

	/** 上传人 */
	private String admin;

	/** 附件使用关联 */
	private Set<AttachReference> attachReferences;

	public Attachment(String id, String siteCode, String title, String info, String filename, int filesize,
			String mimetype, String fileext, String path, Date updateTime, String admin, Set attachReferences) {
		this.id = id;
		this.catalog = siteCode;
		this.title = title;
		this.info = info;
		this.filename = filename;
		this.filesize = filesize;
		this.mimetype = mimetype;
		this.fileext = fileext;
		this.path = path;
		this.updateTime = updateTime;
		this.admin = admin;
		this.attachReferences = attachReferences;
	}

	/** default constructor */
	public Attachment() {
	}

	public Attachment(String id, Set<AttachReference> attachReferences) {
		this.id = id;
		this.attachReferences = attachReferences;
	}

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@Column(length = 50)
	public String getId() {
		return super.getId();
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @hibernate.property column="title" length="100"
	 */
	@Column(length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length = 200)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(length = 300)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getFilesize() {
		return this.filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	@Column(length = 100)
	public String getMimetype() {
		return this.mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	@Column(length = 20)
	public String getFileext() {
		return this.fileext;
	}

	public void setFileext(String fileext) {
		this.fileext = fileext;
	}

	@Column(length = 300)
	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
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

	@OneToMany(mappedBy = "attachment", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	public Set<AttachReference> getAttachReferences() {
		return this.attachReferences;
	}

	public void setAttachReferences(Set<AttachReference> attachReferences) {
		this.attachReferences = attachReferences;
	}

	@Column(length = 50)
	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

}
