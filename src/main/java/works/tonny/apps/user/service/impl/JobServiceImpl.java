package works.tonny.apps.user.service.impl;

// Generated 2012-12-3 13:14:30 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.llama.library.utils.Assert;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.dao.JobDAO;
import works.tonny.apps.user.dao.JobLevelDAO;
import works.tonny.apps.user.model.Job;
import works.tonny.apps.user.model.JobLevel;
import works.tonny.apps.user.service.JobService;

/**
 * Service object for domain model class Job.
 * 
 * @see entity.Job
 * @author Tonny Liu
 */
public class JobServiceImpl extends AuthedAbstractService implements JobService {
	private JobDAO jobDAO;

	private JobLevelDAO jobLevelDAO;

	/**
	 * 读取Job
	 * 
	 * @param id 编号
	 * @return Job
	 */
	public Job get(String id) {
		Job job = jobDAO.get(id);
		job.getJobLevels().size();
		return job;
	}

	/**
	 * 创建Job
	 * 
	 * @param job
	 * @return
	 */
	public String save(Job job) {
		validate(job);
		return jobDAO.save(job);
	}

	/**
	 * 验证并初始数据
	 */
	private void validate(Job job) {
	}

	/**
	 * 更新Job
	 * 
	 * @param job
	 */
	public void update(Job job) {
		Job db = get(job.getId());
		PropertiesUtils.copyProperties(db, job, "name", "info", "orderNo", "jobLevels");
		jobDAO.update(db);
	}

	/**
	 * 通过id删除Job
	 * 
	 * @param id 编号
	 */
	public void delete(String id) {
		Job job = jobDAO.get(id);
		Assert.notNull(job);
		jobDAO.delete(job);
	}

	/**
	 * 通过多个id删除Job
	 * 
	 * @param ids id数组
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String[] ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	/**
	 * 分页查询Job列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	public List<Job> list() {
		return jobDAO.list();
	}

	public JobDAO getJobDAO() {
		return jobDAO;
	}

	public void setJobDAO(JobDAO jobDAO) {
		this.jobDAO = jobDAO;
	}

	public JobLevelDAO getJobLevelDAO() {
		return jobLevelDAO;
	}

	public void setJobLevelDAO(JobLevelDAO jobLevelDAO) {
		this.jobLevelDAO = jobLevelDAO;
	}

	/**
	 * 
	 * @see com.zxtx.apps.user.JobService#saveJobLevel(com.zxtx.apps.user.JobLevel,
	 *      java.lang.String)
	 */
	public String saveJobLevel(String name, String info, int level, String jobId) {
		Job job = jobDAO.get(jobId);
		JobLevel jobLevel = new JobLevel(job, name, info, level);
		return jobLevelDAO.save(jobLevel);
	}

	/**
	 * 
	 * @see com.zxtx.apps.user.JobService#updateJobLevel(java.lang.String,
	 *      java.lang.String, java.lang.String, int)
	 */
	public void updateJobLevel(String id, String name, String info, int level) {
		JobLevel jobLevel = jobLevelDAO.get(id);
		jobLevel.setInfo(info);
		jobLevel.setName(name);
		jobLevel.setLevel(level);
		jobLevelDAO.update(jobLevel);
	}

	/**
	 * 
	 * @see com.zxtx.apps.user.JobService#deleteJobLevel(java.lang.String)
	 */
	public void deleteJobLevel(String id) {
		jobLevelDAO.delete(getJobLevel(id));
	}

	/**
	 * 
	 * @see com.zxtx.apps.user.JobService#getJobLevel(java.lang.String)
	 */
	public JobLevel getJobLevel(String id) {
		return jobLevelDAO.get(id);
	}

}
