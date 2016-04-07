package works.tonny.apps.core.dao;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.FormValue;
import works.tonny.apps.support.ListSupport;


/**
 * DAO interface for domain model class FormValue.
 * 
 * @see works.tonny.core.elearning.entity.FormValue
 * @author Tonny Liu
 */
public interface FormValueDAO extends EntityDAO<FormValue> {

	/**
	 * 根据表单编号和数据编号查询表单项值
	 * 
	 * @Title: get
	 * @param formElementId 表单元素项编号
	 * @param itemId 数据编号
	 * @return 表单数据项值
	 * @date 2012-7-12 上午11:05:44
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = { "formElement.id", "itemId" }, order = { "formElement.form.id", "formElement.orderNo" })
	FormValue get(String formElementId, String itemId);

	/**
	 * 按表单查询FormValue列表
	 * 
	 * @return 表单数据
	 * 
	 * @param formId 表单编号
	 * @param itemId 数据编号
	 */
	@ListSupport(field = { "formElement.form.id", "itemId" }, order = { "formElement.form.id", "formElement.orderNo" })
	List<FormValue> list(String formId, String itemId);

	/**
	 * 按表单查询FormValue列表
	 * 
	 * @return 表单数据
	 * 
	 * @param formId 表单编号
	 * @param itemId 数据编号
	 */
	@ListSupport(field = { "itemId" }, order = { "formElement.form.id", "formElement.orderNo" })
	List<FormValue> list(String itemId);
}
