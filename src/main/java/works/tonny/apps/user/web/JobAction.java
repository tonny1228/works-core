package works.tonny.apps.user.web;

// Generated 2012-12-3 13:14:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.model.Job;
import works.tonny.apps.user.model.JobLevel;
import works.tonny.apps.user.service.JobService;

/**
 * <p>
 * Struts2 authed action for domain model class Job.
 * </p>
 * <p>
 * Job管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see entity.Job
 * @author Tonny Liu
 * @version 1.0.0
 */
public class JobAction extends AuthedAction {
	private String id;

	private JobService jobService;

	private Job job;

	private String name;

	private String info;

	private int level;

	public String add() {
		return SUCCESS;
	}

	/**
	 * 保存数据
	 */
	public String save() {
		jobService.save(job);
		return SUCCESS;
	}

	/**
	 * 保存数据
	 */
	public String saveJobLevel() {
		jobService.saveJobLevel(name, info, level, id);
		return SUCCESS;
	}

	/**
	 * 载入数据并进入编辑页面
	 */
	public String edit() {
		job = jobService.get(id);
		return SUCCESS;
	}

	/**
	 * 载入数据并进入编辑页面
	 */
	public String view() {
		job = jobService.get(id);
		return SUCCESS;
	}

	/**
	 * 载入数据并进入编辑页面
	 */
	public String editJobLevel() {
		JobLevel jobLevel = jobService.getJobLevel(id);
		name = jobLevel.getName();
		id = jobLevel.getId();
		info = jobLevel.getInfo();
		level = jobLevel.getLevel();
		job = jobLevel.getJob();
		return SUCCESS;
	}

	/**
	 * 更新数据
	 */
	public String update() {
		jobService.update(job);
		return SUCCESS;
	}

	/**
	 * 更新数据
	 */
	public String updateJobLevel() {
		jobService.updateJobLevel(id, name, info, level);
		id = getParameter("jobId");
		return SUCCESS;
	}

	/**
	 * 批量删除数据
	 */
	public String delete() {
		String[] ids = id.split(", ");
		jobService.delete(ids);
		return SUCCESS;
	}

	/**
	 * 删除数据
	 */
	public String deleteJobLevel() {
		jobService.deleteJobLevel(id);
		id = getParameter("jobId");
		return SUCCESS;
	}

	/**
	 * 查询列表并进入列表页
	 */
	public String list() {
		List<Job> list = jobService.list();
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService service) {
		this.jobService = service;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
