/**  
 * @Title: UserListTag.java
 * @Package works.tonny.user.web
 * @author Tonny
 * @date 2012-6-18 下午4:48:20
 */
package works.tonny.apps.user.web;

import java.util.List;

import javax.servlet.jsp.JspException;

import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.User;
import works.tonny.apps.user.UserService;
import works.tonny.apps.web.CommonTag;

/**
 * @ClassName: UserListTag
 * @Description:
 * @author Tonny
 * @date 2012-6-18 下午4:48:20
 * @version 1.0
 */
public class UserListTag extends CommonTag {
	private String title;

	private int num = 20;

	List<User> list;

	private int idx;

	public int doStartTag() throws JspException {
		UserService userService = ServiceManager.lookup("userService");
		list = userService.usersOfTitle(title, 1, num);
		idx = 0;
		pageContext.getRequest().setAttribute("_articleList", list);
		if (list.size() > 0) {
			return EVAL_PAGE;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * @throws JspException
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doInitBody()
	 */
	@Override
	public void doInitBody() throws JspException {
		super.doInitBody();
		pageContext.getRequest().setAttribute("_user", list.get(idx++));
	}

	/**
	 * @return
	 * @throws JspException
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		if (idx >= list.size()) {

			return SKIP_BODY;
		}
		pageContext.getRequest().setAttribute("_user", list.get(idx++));
		return EVAL_BODY_AGAIN;
	}

	public int doEndTag() throws JspException {
		pageContext.getRequest().removeAttribute("_user");
		list = null;
		return super.doEndTag();
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
