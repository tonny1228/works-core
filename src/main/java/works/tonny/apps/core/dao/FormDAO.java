package works.tonny.apps.core.dao;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.Form;
import works.tonny.apps.support.ListSupport;


/**
 * DAO interface for domain model class Form.
 * 
 * @see works.tonny.core.elearning.entity.Form
 * @author Tonny Liu
 */
public interface FormDAO extends EntityDAO<Form> {
	/**
	 * 分页查询Form列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	@ListSupport()
	PagedList<Form> list(int page, int pagesize);

	/**
	 * 分页查询Element列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	@ListSupport(field = "catalog")
	PagedList<Form> list(String catalog, int page, int pagesize);
	
	/**
	 * 通过名称获取表单
	 * @param name
	 * @return
	 */
	@ListSupport(field = "name")
	Form getFormByName(String name);

}
