package works.tonny.apps.core.action;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.core.Element;
import works.tonny.apps.core.ElementService;
import works.tonny.apps.user.AuthedAction;


/**
 * <p>
 * Struts2 authed action for domain model class Element.
 * </p>
 * <p>
 * Element管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see works.tonny.core.elearning.entity.Element
 * @author Tonny Liu
 * @version 1.0.0
 */
public class ElementAction extends AuthedAction {
	private String id;

	private ElementService elementService;

	private Element element;

	public String add() {
		return INPUT;
	}

	public String save() {
		elementService.save(element);
		return SUCCESS;
	}

	public String edit() {
		element = elementService.get(id);
		request.setAttribute("element", element);
		return INPUT;
	}

	public String view() {
		element = elementService.get(id);
		request.setAttribute("element", element);
		return VIEW;
	}

	public String update() {
		elementService.update(element);
		return SUCCESS;
	}

	public String delete() {
		String[] ids = id.split(", ");
		elementService.delete(ids);
		return SUCCESS;
	}

	public String list() {
		PagedList<Element> list = elementService.list(getPage(), getPagesize());
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ElementService getElementService() {
		return elementService;
	}

	public void setElementService(ElementService service) {
		this.elementService = service;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}
}
