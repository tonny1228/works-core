package works.tonny.apps.core.dao;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.FormElement;
import works.tonny.apps.support.ListSupport;

/**
 * DAO interface for domain model class FormElement.
 * 
 * @see works.tonny.core.elearning.entity.FormElement
 * @author Tonny Liu
 */
public interface FormElementDAO extends EntityDAO<FormElement> {

	/**
	 * 通过表单编号和数据项名字查询表单数据项，有重名的返回第一个
	 * 
	 * @Title: get
	 * @param formId 表单编号
	 * @param name 数据项名字
	 * @return 表单数据项
	 * @date 2012-7-18 下午5:14:19
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = { "form.id", "name" })
	FormElement get(String formId, String name);
}
