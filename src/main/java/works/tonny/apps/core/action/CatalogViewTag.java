/**
 * 
 */
package works.tonny.apps.core.action;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.CatalogService;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AnonymousTag;

/**
 * 分类信息输出标签
 * 
 * @author 祥栋
 */
public class CatalogViewTag extends AnonymousTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final String NAME = "_catalog";

	/**
	 * 分类id
	 */
	private String id;

	/**
	 * 输出变量名称
	 */
	private String name;

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		CatalogService catalogService = ServiceManager.lookup(ServiceManager.CATALOG_SERVICE);
		if (StringUtils.isEmpty(id)) {
			return SKIP_BODY;
		}
		Catalog catalog = catalogService.get(id);
		if (catalog == null) {
			return SKIP_BODY;
		}
		pageContext.setAttribute(StringUtils.defaultIfEmpty(name, NAME), catalog);
		return super.doStartTag();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.web.CommonTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		pageContext.removeAttribute(StringUtils.defaultIfEmpty(name, NAME));
		return super.doEndTag();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
