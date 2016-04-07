package works.tonny.apps.core;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import java.util.List;
import java.util.Map;

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityService;


/**
 * Service interface for domain model class Form.
 * 
 * @see works.tonny.core.elearning.entity.Form
 * @author Tonny Liu
 */
public interface FormService extends EntityService<Form> {
	/**
	 * 分页查询Form列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	PagedList<Form> list(int page, int pagesize);

	/**
	 * 通过名称获取表单
	 * 
	 * @param name
	 * @return
	 */
	Form getByName(String name);

	/**
	 * 分页查询Element列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	PagedList<Form> list(String catalog, int page, int pagesize);

	/**
	 * 保存表单数据
	 * 
	 * @Title: saveFormValues
	 * @param itemId 数据编号
	 * @param formElementIds 表单元素编号
	 * @param values 数据
	 * @date 2012-7-12 上午10:49:56
	 * @author tonny
	 * @version 1.0
	 */
	void saveFormValues(String itemId, List<String> formElementIds, List<String> values);

	/**
	 * 通过表单数据项名字保存表单数据
	 * 
	 * @Title: saveFormValues
	 * @param formId 表单编号
	 * @param itemId 数据编号
	 * @param values 数据，键是字段的名字
	 * @date 2012-7-12 上午10:49:56
	 * @author tonny
	 * @version 1.0
	 */
	void saveFormValues(String formId, String itemId, Map<String, String> values);

	/**
	 * 读取表单信息及数据
	 * 
	 * @Title: readFormValues
	 * @param formId 表单编号
	 * @param itemId 数据编号
	 * @return 表单与数据
	 * @date 2012-7-12 上午10:51:45
	 * @author tonny
	 * @version 1.0
	 */
	Form readFormValues(String formId, String itemId);

	/**
	 * 读取所有的表单信息及数据
	 * 
	 * @Title: readFormValues
	 * @param formId 表单编号
	 * @param itemId 数据编号
	 * @return 表单与数据
	 * @date 2012-7-12 上午10:51:45
	 * @author tonny
	 * @version 1.0
	 */
	List<Form> readFormValues(String itemId);

	/**
	 * 删除表单的某条数据
	 * 
	 * @Title: deleteValues
	 * @param formId 表单编号
	 * @param itemId 数据编号
	 * @date 2012-7-18 上午9:42:56
	 * @author tonny
	 * @version 1.0
	 */
	void deleteValues(String formId, String itemId);

	/**
	 * 删除某条数据
	 * 
	 * @Title: deleteValues
	 * @param itemId 数据编号
	 * @date 2012-7-18 上午9:42:56
	 * @author tonny
	 * @version 1.0
	 */
	void deleteValues(String itemId);
}
