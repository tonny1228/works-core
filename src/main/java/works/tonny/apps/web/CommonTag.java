package works.tonny.apps.web;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import works.tonny.apps.support.ActionUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

/**
 * 通用的自定义标签抽象类
 * 
 * @author 祥栋
 */
public abstract class CommonTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * page context
	 */
	// protected PageContext pageContext;
	/**
	 * 日志
	 */
	protected final Logger log = LogFactory.getLogger(getClass());

    protected ActionUtils actionUtils = new ActionUtils();

	/**
	 * 父对象
	 */
	protected Tag parent;

	public int doEndTag() throws JspException {
		if (bodyContent != null) {
			try {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
				bodyContent.clearBody();
			} catch (Exception e) {
				log.error(e);
				// throw new JspException(e.getMessage());
			}
		}
		return EVAL_PAGE;
	}

	public void setParent(Tag parent) {
		this.parent = parent;
	}

	public void release() {
	}

	public Tag getParent() {
		return parent;
	}

    public ActionUtils getActionUtils() {
        return actionUtils;
    }

}
