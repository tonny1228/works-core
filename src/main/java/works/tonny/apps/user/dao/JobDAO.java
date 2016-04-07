package works.tonny.apps.user.dao;

// Generated 2012-12-3 13:14:29 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.model.Job;

/**
 * DAO interface for domain model class Job.
 * 
 * @see entity.Job
 * @author Tonny Liu
 */
public interface JobDAO extends EntityDAO<Job> {
	/**
	 * 分页查询Job列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	@ListSupport(order = "orderNo")
	List<Job> list();

}
