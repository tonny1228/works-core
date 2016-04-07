package works.tonny.apps.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * 应用资源
 * 
 * @author tonny
 */
@javax.persistence.Entity
@Table(name = "a_system_resource")
public class SystemResource extends Entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7610884826115365556L;

	/**
	 * 名称
	 */
	private String name;

	private String packageName;

	private String api;

    private transient SystemResource parent;

    private transient Set<SystemResource> subs;
	/**
	 * 描述串
	 */
	private String url;

	/**
	 * 类型：0为接口，1为uri
	 */
	private int type;

	/**
	 * 描述
	 */
	private String description;

    private transient Set<Privilege> privileges;

	private Date updateTime;

	private String admin;

	public SystemResource() {
		super();
	}

	public SystemResource(String name, String uri, int type, String description) {
		super();
		this.name = name;
		this.url = uri;
		this.type = type;
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

	@Column(length = 50, nullable = false)
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources")
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	@Column(length = 200)
	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public SystemResource getParent() {
		return parent;
	}

	public void setParent(SystemResource parent) {
		this.parent = parent;
	}

	@Column(length = 300)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	public Set<SystemResource> getSubs() {
		return subs;
	}

	public void setSubs(Set<SystemResource> subs) {
		this.subs = subs;
	}

	@Column(name = "package_name", length = 200)
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
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
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

}
