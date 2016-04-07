package works.tonny.apps.core.action;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import works.tonny.apps.core.Form;
import works.tonny.apps.core.FormService;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AuthedTag;


/**
 * 表单填报表单
 * 
 * @author tonny
 */
public class FormEditTag extends AuthedTag {
	/**
	 * 
	 */
	public static final String FORM_SERVICE = "formService";

	private String id;

	private String name;

	private String itemId;

	public int doStartTag() throws JspException {
		FormService formService = ServiceManager.lookup(FORM_SERVICE);
		Form form = null;
		if (StringUtils.isNotEmpty(name) && StringUtils.isEmpty(id)) {
			form = formService.getByName(name);
			id = form.getId();
		}
		if (StringUtils.isNotEmpty(itemId)) {
			form = formService.readFormValues(id, itemId);
		} else if (form == null) {
			form = formService.get(id);
		}
		pageContext.getRequest().setAttribute("_form", form);
		return EVAL_PAGE;
	}

	/**
	 * @throws JspException
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doInitBody()
	 */
	@Override
	public void doInitBody() throws JspException {
		super.doInitBody();
	}

	/**
	 * @return
	 * @throws JspException
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		pageContext.getRequest().removeAttribute("_form");
		return super.doEndTag();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
