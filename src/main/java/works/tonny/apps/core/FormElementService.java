package works.tonny.apps.core;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import works.tonny.apps.EntityService;

/**
 * Service interface for domain model class FormElement.
 * 
 * @see works.tonny.core.elearning.entity.FormElement
 * @author Tonny Liu
 */
public interface FormElementService extends EntityService<FormElement> {
	/**
	 * 为表单添加表单元素
	 * 
	 * @Title: save
	 * @param formId 表单编号
	 * @param elementId 元数据编号
	 * @date 2012-7-12 上午10:59:22
	 * @author tonny
	 * @version 1.0
	 */
	void save(String formId, String elementId);

}
