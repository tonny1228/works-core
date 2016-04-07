package works.tonny.apps.core.service;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.llama.library.utils.Assert;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.core.Form;
import works.tonny.apps.core.FormClobvalue;
import works.tonny.apps.core.FormElement;
import works.tonny.apps.core.FormService;
import works.tonny.apps.core.FormTinyvalue;
import works.tonny.apps.core.FormValue;
import works.tonny.apps.core.dao.FormDAO;
import works.tonny.apps.core.dao.FormElementDAO;
import works.tonny.apps.core.dao.FormValueDAO;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.LoginedUser;

/**
 * Service object for domain model class Form.
 * 
 * @see works.tonny.core.elearning.entity.Form
 * @author Tonny Liu
 */
public class FormServiceImpl extends AuthedAbstractService implements FormService {
	private FormDAO formDAO;

	private FormValueDAO formValueDAO;

	private FormElementDAO formElementDAO;

	/**
	 * 读取Form
	 * 
	 * @param id 编号
	 * @return Form
	 */
	public Form get(String id) {
		Form form = formDAO.get(id);
		form.getFormElements().size();
		return form;
	}

	/**
	 * @see com.zxtx.apps.core.FormService#getByName(java.lang.String)
	 */
	public Form getByName(String name) {
		Form form = formDAO.getFormByName(name);
		form.getFormElements().size();
		return form;
	}

	/**
	 * 创建Form
	 * 
	 * @param form
	 * @return
	 */
	public String save(Form form) {
		verify(form);
		return formDAO.save(form);
	}

	/**
	 * @Title: verify
	 * @param form
	 * @date 2012-7-17 下午4:08:33
	 * @author tonny
	 * @version 1.0
	 */
	private void verify(Form form) {
		LoginedUser admin = getLoginedUser();

		form.setUpdateTime(new Date());
		form.setAdmin(admin.getUser().getUsername());
	}

	/**
	 * 更新Form
	 * 
	 * @param form
	 */
	public void update(Form form) {
		Form db = get(form.getId());
		PropertiesUtils.copyProperties(db, form, "name", "title", "info", "catelog");
		verify(db);
		formDAO.update(db);
	}

	/**
	 * 通过id删除Form
	 * 
	 * @param id 编号
	 */
	public void delete(String id) {
		Form form = formDAO.get(id);
		Assert.notNull(form);
		formDAO.delete(form);
	}

	/**
	 * 通过多个id删除Form
	 * 
	 * @param ids id数组
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String[] ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	/**
	 * 分页查询Form列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	public PagedList<Form> list(int page, int pagesize) {
		return formDAO.list(page, pagesize);
	}

	public FormDAO getFormDAO() {
		return formDAO;
	}

	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}

	/**
	 * @param catalog
	 * @param page
	 * @param pagesize
	 * @return
	 * @see com.zxtx.apps.core.FormService#list(java.lang.String, int, int)
	 */
	public PagedList<Form> list(String catalog, int page, int pagesize) {
		return null;
	}

	/**
	 * @param formId
	 * @param itemId
	 * @param formElementIds
	 * @param values
	 * @see com.zxtx.apps.core.FormService#saveFormValues(java.lang.String, java.lang.String,
	 *      java.lang.String[], java.lang.String[])
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveFormValues(String itemId, List<String> formElementIds, List<String> values) {
		for (int i = 0; i < formElementIds.size(); i++) {
			String formElementId = formElementIds.get(i);
			String eleValue = values.get(i);
			saveFormValue(itemId, formElementId, eleValue);
		}
	}

	/**
	 * 更新一条记录
	 * 
	 * @Title: saveFormValue
	 * @param itemId
	 * @param formElementId
	 * @param eleValue
	 * @date 2012-7-18 下午5:17:32
	 * @author tonny
	 * @version 1.0
	 */
	private void saveFormValue(String itemId, String formElementId, String eleValue) {
		FormValue value = formValueDAO.get(formElementId, itemId);
		if (value == null) {
			if (eleValue != null && eleValue.length() > 400) {
				value = new FormClobvalue(formElementDAO.get(formElementId), itemId, eleValue);
			} else {
				value = new FormTinyvalue(formElementDAO.get(formElementId), itemId, eleValue);
			}
			formValueDAO.save(value);
		} else {
			if (value instanceof FormTinyvalue && eleValue != null && eleValue.length() > 400) {
				formValueDAO.delete(value);
				value = new FormClobvalue(formElementDAO.get(formElementId), itemId, eleValue);
				formValueDAO.save(value);
			} else {
				value.setValue(eleValue);
				formValueDAO.update(value);
			}
		}
	}

	/**
	 * @param formId
	 * @param itemId
	 * @param values
	 * @see com.zxtx.apps.core.FormService#saveFormValues(java.lang.String, java.lang.String,
	 *      java.util.Map)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveFormValues(String formId, String itemId, Map<String, String> values) {
		for (String name : values.keySet()) {
			FormElement formElement = formElementDAO.get(formId, name);
			Assert.notNull(formElement, "formelement.null");
			String formElementId = formElement.getId();
			String eleValue = values.get(name);
			saveFormValue(itemId, formElementId, eleValue);
		}
	}

	/**
	 * @param formId
	 * @return
	 * @see com.zxtx.apps.core.FormService#readFormValues(java.lang.String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Form readFormValues(String formId, String itemId) {
		Form form = get(formId);
		List<FormValue> values = formValueDAO.list(formId, itemId);
		for (FormValue value : values) {
			form.getElements().remove(value.getFormElement().getName());
		}
		Set<String> keys = form.getElements().keySet();
		for (String key : keys) {
			FormElement formElement = form.getElements().get(key);
			FormTinyvalue tinyvalue = new FormTinyvalue(formElement, itemId, null);
			values.add(tinyvalue);
		}
		form.setFormValues(values);
		return form;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see works.tonny.core.service.FormService#readFormValues(java.lang.String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Form> readFormValues(String itemId) {
		List<FormValue> formValues = formValueDAO.list(itemId);
		List<Form> forms = new ArrayList<Form>();
		Form form = null;
		List<FormValue> values = null;
		for (FormValue formValue : formValues) {
			if (form == null || !formValue.getFormElement().getForm().getId().equals(form.getId())) {
				form = formValue.getFormElement().getForm();
				values = new ArrayList<FormValue>();
				form.setFormValues(values);
				forms.add(form);
			}
			values.add(formValue);
		}
		return forms;
	}

	/**
	 * @param formId
	 * @return
	 * @see com.zxtx.apps.core.FormService#readFormValues(java.lang.String)
	 */
	public void deleteValues(String formId, String itemId) {
		List<FormValue> values = formValueDAO.list(formId, itemId);
		formValueDAO.deleteAll(values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see works.tonny.core.service.FormService#deleteValues(java.lang.String)
	 */
	public void deleteValues(String itemId) {
		List<FormValue> values = formValueDAO.list(itemId);
		formValueDAO.deleteAll(values);
	}

	public FormValueDAO getFormValueDAO() {
		return formValueDAO;
	}

	public void setFormValueDAO(FormValueDAO formValueDAO) {
		this.formValueDAO = formValueDAO;
	}

	public FormElementDAO getFormElementDAO() {
		return formElementDAO;
	}

	public void setFormElementDAO(FormElementDAO formElementDAO) {
		this.formElementDAO = formElementDAO;
	}

}
