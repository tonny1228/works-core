/**  
 * @Title: UserListTag.java
 * @Package works.tonny.user.web
 * @author Tonny
 * @date 2012-6-18 下午4:48:20
 */
package works.tonny.apps.user.web;

import javax.servlet.jsp.JspException;

import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.Member;
import works.tonny.apps.user.MemberService;
import works.tonny.apps.web.CommonTag;

/**
 * @ClassName: UserListTag
 * @Description:
 * @author Tonny
 * @date 2012-6-18 下午4:48:20
 * @version 1.0
 */
public class MemberTag extends CommonTag {
	private String username;

	private Member member;

	public int doStartTag() throws JspException {
		MemberService memberService = ServiceManager.lookup("memberService");
		Member member = memberService.getByUsername(username);
		pageContext.getRequest().setAttribute("_member", member);
		if (member != null) {
			return EVAL_PAGE;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
