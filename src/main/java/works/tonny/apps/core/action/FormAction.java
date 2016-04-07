package works.tonny.apps.core.action;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PagedList;

import works.tonny.apps.core.Form;
import works.tonny.apps.core.FormService;
import works.tonny.apps.user.AuthedAction;


/**
 * <p>
 * Struts2 authed action for domain model class Form.
 * </p>
 * <p>
 * Form管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see works.tonny.core.elearning.entity.Form
 * @author Tonny Liu
 * @version 1.0.0
 */
public class FormAction extends AuthedAction {
	private String id;

	private FormService formService;

	private Form form;

	private String itemId;

	public String add() {
		return INPUT;
	}

	public String save() {
		formService.save(form);
		return SUCCESS;
	}

	public String edit() {
		form = formService.get(id);
		request.setAttribute("form", form);
		return INPUT;
	}

	public String view() {
		form = formService.get(id);
		request.setAttribute("form", form);
		return VIEW;
	}

	public String update() {
		formService.update(form);
		return SUCCESS;
	}

	public String delete() {
		String[] ids = id.split(", ");
		formService.delete(ids);
		return SUCCESS;
	}

	public String list() {
		PagedList<Form> list = formService.list(getPage(), getPagesize());
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String write() {
		if (StringUtils.isNotEmpty(itemId)) {
			form = formService.readFormValues(id, itemId);
		} else {
			form = formService.get(id);
		}
		request.setAttribute("form", form);
		return SUCCESS;
	}

	public String deleteAValues() {
		formService.deleteValues(id, itemId);
		return SUCCESS;
	}

	public String saveFormValues() {
		Enumeration<String> names = request.getParameterNames();
		List<String> formElementIds = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			if (name.startsWith("ele_")) {
				formElementIds.add(name.substring(4));
				values.add(getParameter(name));
			}
		}
		formService.saveFormValues(itemId, formElementIds, values);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService service) {
		this.formService = service;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
