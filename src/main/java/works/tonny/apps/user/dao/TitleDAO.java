package works.tonny.apps.user.dao;

// Generated 2012-12-3 13:14:29 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.model.Title;

/**
 * DAO interface for domain model class Title.
 * 
 * @see entity.Title
 * @author Tonny Liu
 */
public interface TitleDAO extends EntityDAO<Title> {
	/**
	 * 分页查询Title列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	@ListSupport(order = "orderNo")
	List<Title> list();

}
