package works.tonny.apps.core.service;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.llama.library.utils.Assert;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.core.FormElement;
import works.tonny.apps.core.FormElementService;
import works.tonny.apps.core.dao.FormDAO;
import works.tonny.apps.core.dao.FormElementDAO;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.LoginedUser;

/**
 * Service object for domain model class FormElement.
 * 
 * @see works.tonny.core.elearning.entity.FormElement
 * @author Tonny Liu
 */
public class FormElementServiceImpl extends AuthedAbstractService implements FormElementService {
	private FormElementDAO formElementDAO;

	private FormDAO formDAO;

	/**
	 * 读取FormElement
	 * 
	 * @param id 编号
	 * @return FormElement
	 */
	public FormElement get(String id) {
		FormElement formElement = formElementDAO.get(id);
		return formElement;
	}

	/**
	 * 创建FormElement
	 * 
	 * @param formElement
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String save(FormElement formElement) {
		verify(formElement);
		String id = formElementDAO.save(formElement);
		if (String.valueOf(formElement.hashCode()).equals(formElement.getForm().getTitle())) {
			formElement.getForm().setTitle(id);
			formDAO.update(formElement.getForm());
		}
		return id;
	}

	/**
	 * @Title: verify
	 * @param formElement
	 * @date 2012-7-17 下午5:06:04
	 * @author tonny
	 * @version 1.0
	 */
	private void verify(FormElement formElement) {
		formElement.setUpdateTime(new Date());
		LoginedUser admin = getLoginedUser();

		formElement.setAdmin(admin.getUser().getUsername());
	}

	/**
	 * 更新FormElement
	 * 
	 * @param formElement
	 */
	@Transactional(rollbackFor = Exception.class)
	public void update(FormElement formElement) {
		FormElement db = get(formElement.getId());
		if ((db.getForm().getTitle() == null && formElement.getForm().getTitle() != null)
				|| (db.getForm().getTitle() == null && formElement.getForm().getTitle() != null)
				|| (db.getForm().getTitle() != null && formElement.getForm().getTitle() == null)
				|| (db.getForm().getTitle() != null && formElement.getForm().getTitle() != null && !db.getForm()
						.getTitle().equals(formElement.getForm().getTitle()))) {
			db.getForm().setTitle(formElement.getForm().getTitle());
			formDAO.update(db.getForm());
		}
		PropertiesUtils.copyProperties(db, formElement, "element", "name", "foreignForm", "foreignKey", "list",
				"search", "orderNo");
		verify(formElement);
		formElementDAO.update(db);
	}

	/**
	 * 通过id删除FormElement
	 * 
	 * @param id 编号
	 */
	public void delete(String id) {
		FormElement formElement = formElementDAO.get(id);
		Assert.notNull(formElement);
		formElementDAO.delete(formElement);
	}

	/**
	 * 通过多个id删除FormElement
	 * 
	 * @param ids id数组
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String[] ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	public FormElementDAO getFormElementDAO() {
		return formElementDAO;
	}

	public void setFormElementDAO(FormElementDAO formElementDAO) {
		this.formElementDAO = formElementDAO;
	}

	/**
	 * @param formId
	 * @param elementId
	 * @see com.zxtx.apps.core.FormElementService#save(java.lang.String, java.lang.String)
	 */
	public void save(String formId, String elementId) {
	}

	public FormDAO getFormDAO() {
		return formDAO;
	}

	public void setFormDAO(FormDAO formDAO) {
		this.formDAO = formDAO;
	}

}
