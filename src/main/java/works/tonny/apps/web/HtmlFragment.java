/**  
 * @Title: HtmlHeader.java
 * @Package works.tonny.framework.web
 * @author Tonny
 * @date 2011-12-20 下午2:53:47
 */
package works.tonny.apps.web;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

/**
 * html模板片断，默认名称为_header。将tag中的输出内容存到request中，并到模板中输出。
 * 
 * @ClassName: HtmlHeader
 * @Description:
 * @author Tonny
 * @date 2011-12-20 下午2:53:47
 * @version 1.0
 */
public class HtmlFragment extends CommonTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 模板名称
	 */
	private String name;

	private String param;

	/**
	 * 不处理任何动作
	 */
	public int doStartTag() throws JspException {
		// ((List<HtmlFragment>)
		// pageContext.getRequest().getAttribute(HtmlTemplate.HTML_CONTEXT)).add(this);

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		if (bodyContent != null)
			pageContext.getRequest().setAttribute(getName(), bodyContent.getString());
		if (StringUtils.isNotEmpty(param)) {
			pageContext.getRequest().setAttribute(getName() + "_param", param);
		}
		if (bodyContent != null) {
			try {
				// bodyContent.writeOut(bodyContent.getEnclosingWriter());
				bodyContent.clearBuffer();
				bodyContent.clearBody();
			} catch (Exception e) {
				throw new JspException(e.getMessage());
			}
		}
		return super.SKIP_BODY;
	}

	public String getName() {
		return StringUtils.defaultIfBlank(name, "_header");
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

}
