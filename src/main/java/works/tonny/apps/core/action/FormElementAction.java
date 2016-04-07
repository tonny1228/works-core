package works.tonny.apps.core.action;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import works.tonny.apps.core.ElementService;
import works.tonny.apps.core.Form;
import works.tonny.apps.core.FormElement;
import works.tonny.apps.core.FormElementService;
import works.tonny.apps.core.FormService;
import works.tonny.apps.user.AuthedAction;

/**
 * <p>
 * Struts2 authed action for domain model class FormElement.
 * </p>
 * <p>
 * FormElement管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see works.tonny.core.elearning.entity.FormElement
 * @author Tonny Liu
 * @version 1.0.0
 */
public class FormElementAction extends AuthedAction {
	private String id;

	private FormElementService formElementService;

	private FormService formService;

	private ElementService elementService;

	private FormElement formElement;

	private String form;

	public String add() {
		request.setAttribute("form", formService.get(form));
		request.setAttribute("elements", elementService.list(1, 200));
		return INPUT;
	}

	public String save() {
		formElement.setElement(elementService.get(getParameter("elementId")));
		Form f = formService.get(form);
		formElement.setForm(f);
		if (getParameter("titled").equals("1")) {
			f.setTitle(String.valueOf(formElement.hashCode()));
		}
		formElementService.save(formElement);
		return SUCCESS;
	}

	public String edit() {
		formElement = formElementService.get(id);
		request.setAttribute("elements", elementService.list(1, 200));
		request.setAttribute("formElement", formElement);
		return INPUT;
	}

	public String view() {
		formElement = formElementService.get(id);
		request.setAttribute("formElement", formElement);
		return VIEW;
	}

	public String update() {
		formElement.setElement(elementService.get(getParameter("elementId")));
		Form f = formService.get(form);
		formElement.setForm(f);
		if (getParameter("titled").equals("1")) {
			f.setTitle(formElement.getId());
		} else if (formElement.getId().equals(f.getTitle())) {
			f.setTitle(null);
		}
		formElementService.update(formElement);
		return SUCCESS;
	}

	public String delete() {
		String[] ids = id.split(", ");
		formElementService.delete(ids);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FormElementService getFormElementService() {
		return formElementService;
	}

	public void setFormElementService(FormElementService service) {
		this.formElementService = service;
	}

	public FormElement getFormElement() {
		return formElement;
	}

	public void setFormElement(FormElement formElement) {
		this.formElement = formElement;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public FormService getFormService() {
		return formService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public ElementService getElementService() {
		return elementService;
	}

	public void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}
}
