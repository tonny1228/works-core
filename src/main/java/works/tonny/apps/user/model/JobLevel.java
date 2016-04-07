package works.tonny.apps.user.model;

// Generated 2012-12-3 13:14:27 by Hibernate Tools /Coder tools 1.0.0 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * JobLevel generated by hbm2java
 * 
 * @author Tonny Liu
 */
@Entity
@Table(name = "u_job_level")
public class JobLevel implements java.io.Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private Job job;
	private String name;
	private String info;
	private Integer level;
	private transient Set<Position> positions = new HashSet<Position>(0);

	public JobLevel() {
	}

	public JobLevel(Job job, String name, String info, Integer level) {
		setId(id);
		setJob(job);
		setName(name);
		setInfo(info);
		setLevel(level);
	}

	public JobLevel(String id, Job job, String name, String info, Integer level, Set<Position> positions) {
		setId(id);
		setJob(job);
		setName(name);
		setInfo(info);
		setLevel(level);
		setPositions(positions);
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id")
	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Column(length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 500)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Column(name = "jlevel")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@OneToMany(mappedBy = "jobLevel")
	// @JoinTable(name = "u_position", joinColumns = @JoinColumn(name = "job_level_id"))
	public Set<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}

}
