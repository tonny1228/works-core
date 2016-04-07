package works.tonny.apps.core.dao;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.Element;
import works.tonny.apps.support.ListSupport;


/**
 * DAO interface for domain model class Element.
 * 
 * @see works.tonny.core.elearning.entity.Element
 * @author Tonny Liu
 */
public interface ElementDAO extends EntityDAO<Element> {
	/**
	 * 分页查询Element列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	@ListSupport()
	PagedList<Element> list(int page, int pagesize);

	/**
	 * 分页查询Element列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	@ListSupport(field = "catalog")
	PagedList<Element> list(String catalog, int page, int pagesize);

}
