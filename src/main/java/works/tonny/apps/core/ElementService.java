package works.tonny.apps.core;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityService;


/**
 * Service interface for domain model class Element.
 * 
 * @see works.tonny.core.elearning.entity.Element
 * @author Tonny Liu
 */
public interface ElementService extends EntityService<Element> {
	/**
	 * 分页查询Element列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	PagedList<Element> list(int page, int pagesize);

	/**
	 * 分页查询Element列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	PagedList<Element> list(String catalog, int page, int pagesize);
}
