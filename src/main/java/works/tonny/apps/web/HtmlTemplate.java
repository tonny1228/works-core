package works.tonny.apps.web;

/**  
 * @Title: HtmlTemplate.java
 * @Package works.tonny.framework.web
 * @author Tonny
 * @date 2011-12-20 下午3:21:11
 */

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import works.tonny.apps.exception.WebException;

/**
 * 模板工具，定义使用模板、主题，并将标签输出内容作为片断_body输出。
 * 
 * @ClassName: HtmlTemplate
 * @Description:
 * @author Tonny
 * @date 2011-12-20 下午3:21:11
 * @version 1.0
 */
public class HtmlTemplate extends CommonTag {
	public static final String HTML_CONTEXT = "works.tonny.html";
	/**
	 * 模板名称
	 */
	private String name;

	private String charset;

	private String menuId;

	private String theme;

	private String param;

	/**
	 * 不处理任何动作
	 */
	public int doStartTag() throws JspException {
		// pageContext.getRequest().setAttribute(HTML_CONTEXT, new
		// ArrayList<HtmlFragment>());
		pageContext.getRequest().setAttribute("_menuId", menuId);

		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		try {
			if (StringUtils.isNotEmpty(param)) {
				Properties properties = new Properties();
				properties.load(new StringReader(param.replaceAll(";", "\n")));
				pageContext.getRequest().setAttribute("_page_param", properties);
			}
			if (bodyContent != null)
				pageContext.getRequest().setAttribute("_body", bodyContent.getString());
			if (bodyContent != null) {
				try {
					// bodyContent.writeOut(bodyContent.getEnclosingWriter());
					bodyContent.clearBuffer();
					bodyContent.clearBody();
				} catch (Exception e) {
					throw new JspException(e.getMessage());
				}
			}

			if (theme == null && pageContext.getRequest().getAttribute("_view.theme") != null) {
				theme = pageContext.getRequest().getAttribute("_view.theme").toString();
			}
			if (theme == null && pageContext.getSession().getAttribute("_view.theme") != null) {
				theme = pageContext.getSession().getAttribute("_view.theme").toString();
			}

			if (theme == null) {
				theme = "default";
			}

			pageContext
					.getRequest()
					.getRequestDispatcher(
							"/WEB-INF/views/" + theme + "/" + StringUtils.defaultIfBlank(name, "default") + ".jsp")
					.forward(pageContext.getRequest(), pageContext.getResponse());
			// pageContext.include("/WEB-INF/views/" +
			// StringUtils.defaultIfBlank(name, "default") + ".jsp");
			// pageContext.getRequest().removeAttribute(HTML_CONTEXT);
		} catch (IOException e) {
			throw new WebException(e);
		} catch (ServletException e) {
			throw new WebException(e);
		}
		theme = null;
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
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
