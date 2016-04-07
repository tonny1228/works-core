/**
 * 
 */
package works.tonny.apps.user.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;

import works.tonny.apps.exception.WebException;
import works.tonny.apps.user.AuthedTag;
import works.tonny.apps.user.MemberAuthedTag;
import works.tonny.apps.user.Privilege;
import works.tonny.apps.user.SystemResource;

/**
 * @author 祥栋
 * @date 2013-2-11
 * @version 1.0.0
 */
public class MemberMenuTag extends MemberAuthedTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;

	/**
	 * 不处理任何动作
	 */
	public int doStartTag() throws JspException {
		if (loginedUser() == null) {
			return SKIP_BODY;
		}
		List<Privilege> privileges = loginedUser().getPrivileges();
		for (Privilege privilege : privileges) {
			Set<SystemResource> resources = privilege.getResources();
			for (SystemResource systemResource : resources) {
				if (systemResource.getId().equals(parentId)) {
					pageContext.getRequest().setAttribute("_menu", systemResource);
				} else if (systemResource.getParent() != null && parentId.equals(systemResource.getParent().getId())
						&& systemResource.getType() == 2) {
					List<SystemResource> subs = (List<SystemResource>) pageContext.getRequest()
							.getAttribute("_submenu");
					if (subs == null) {
						subs = new ArrayList<SystemResource>();
						pageContext.getRequest().setAttribute("_submenu", subs);
					}
					subs.add(systemResource);
				}

			}
		}
		if (pageContext.getRequest().getAttribute("_menu") == null) {
			return SKIP_BODY;
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		try {
			if (pageContext.getRequest().getAttribute("_menu") == null) {
				return SKIP_BODY;
			}
			pageContext.getOut().write(getBodyContent().getString());
		} catch (IOException e) {
			log.error("权限错误", e);
			throw new WebException(e);
		} finally {
			pageContext.getRequest().removeAttribute("_menu");
			pageContext.getRequest().removeAttribute("_submenu");
		}
		return EVAL_PAGE;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
