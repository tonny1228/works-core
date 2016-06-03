/**
 * @Title: AttachList.java
 * @Package works.tonny.core.action
 * @author Tonny
 * @date 2012-6-6 下午9:23:23
 */
package works.tonny.apps.core.action;

import org.apache.commons.lang.StringUtils;
import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.CatalogService;
import works.tonny.apps.support.EntityLazyProxy;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AnonymousTag;

import javax.servlet.jsp.JspException;
import java.util.List;

/**
 * @ClassName: AttachList
 * @Description:
 * @author Tonny
 * @date 2012-6-6 下午9:23:23
 * @version 1.0
 */
public class CatalogListTag extends AnonymousTag {

    /**
     *
     */
    private static final String NAME = "_catalog";

    private String parentId;

    private String sblingId;

    private int type = -1;

    private List<Catalog> catalogs;

    private String name;

    private int depth = -1;

    private int idx;

    public int doStartTag() throws JspException {
        catalogs = null;
        loginedUser();
        CatalogService catalogService = ServiceManager.lookup(ServiceManager.CATALOG_SERVICE);
        String columnId = parentId;
        if (StringUtils.isEmpty(columnId) && StringUtils.isNotEmpty(sblingId)) {
            Catalog catalog = catalogService.get(sblingId);
            EntityLazyProxy entityLazyProxy = ServiceManager.lookup(EntityLazyProxy.class);
            entityLazyProxy.refresh(catalog);
            columnId = catalog.getTreeNode().getParentId();
            if (depth == -1)
                depth = catalog.getTreeNode().getDepth();
        }
        idx = 0;
        if (StringUtils.isEmpty(columnId)) {
            catalogs = catalogService.root();
        } else if (type > -1 || depth > -1) {
            catalogs = catalogService.listSubs(columnId, type, depth);
        } else {
            catalogs = catalogService.listSubs(columnId);
        }
        pageContext.getRequest().setAttribute("_util", actionUtils);
        if (catalogs != null && catalogs.size() > 0) {
            pageContext.getRequest().setAttribute("_catalogList", catalogs);
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
        pageContext.getRequest().setAttribute(StringUtils.defaultIfEmpty(name, NAME), catalogs.get(idx++));
    }

    /**
     * @return
     * @throws JspException
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
     */
    @Override
    public int doAfterBody() throws JspException {
        if (idx >= catalogs.size()) {
            return SKIP_BODY;
        }
        pageContext.getRequest().setAttribute(StringUtils.defaultIfEmpty(name, NAME), catalogs.get(idx++));
        return EVAL_BODY_AGAIN;
    }

    public int doEndTag() throws JspException {
        if (name == null) {
            pageContext.getRequest().removeAttribute(NAME);
        }
        catalogs = null;
        return super.doEndTag();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSblingId() {
        return sblingId;
    }

    public void setSblingId(String sblingId) {
        this.sblingId = sblingId;
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

    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

}
