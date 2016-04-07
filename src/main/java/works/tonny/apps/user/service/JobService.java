package works.tonny.apps.user.service;

// Generated 2012-12-3 13:14:30 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.EntityService;
import works.tonny.apps.user.model.Job;
import works.tonny.apps.user.model.JobLevel;

/**
 * Service interface for domain model class Job.
 * 
 * @see entity.Job
 * @author Tonny Liu
 */
public interface JobService extends EntityService<Job> {
	/**
	 * 分页查询Job列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	List<Job> list();

	/**
	 * 保存职级
	 * 
	 * @param jobLevel
	 * @return
	 */
	String saveJobLevel(String name, String info, int level, String jobId);

	/**
	 * 更新职级
	 * 
	 * @param jobLevel
	 */
	void updateJobLevel(String id, String name, String info, int level);

	/**
	 * 删除职级
	 * 
	 * @param id
	 */
	void deleteJobLevel(String id);

	/**
	 * 读取职级信息
	 * 
	 * @param id
	 * @return
	 */
	JobLevel getJobLevel(String id);
}
