package works.tonny.apps.core.action;

// Generated 2012-5-5 11:21:00 by Hibernate Tools 3.4.0.CR1

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.ThreadLocalMap;
import works.tonny.apps.auth.DataOwnerConfig;
import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.CatalogAuthService;
import works.tonny.apps.core.CatalogService;
import works.tonny.apps.core.CoreServiceFactory;
import works.tonny.apps.user.AuthedAction;

import java.util.List;

/**
 * <p>
 * Struts2 authed action for domain model class Auth.
 * </p>
 * <p>
 * Catalog管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 *
 * @author Tonny Liu
 * @version 1.0.0
 * @see works.tonny.elearning.entity.Auth
 * @see works.tonny.user.zxtx.apps.web.AuthedAction
 */
public class CatalogAction extends AuthedAction {
    private String id;

    private CatalogService catalogService;

    private CatalogAuthService catalogAuthService;

    private Catalog catalog;

    public String add() {
        String parent = getParameter("parent");
        if (StringUtils.isNotEmpty(parent)) {
            Catalog parentCatalog = catalogService.get(parent);
            request.setAttribute("parent", parentCatalog);
        }
        return INPUT;
    }

    public String save() {
        String cid = null;
        String parent = getParameter("parent");
        if (StringUtils.isNotEmpty(parent)) {
            cid = catalogService.save(catalog, parent);
            id = parent;
        } else {
            cid = catalogService.save(catalog);
            id = catalog.getId();
        }
        CoreServiceFactory.getInstance().getAttachReferenceService().updateReference(cid, getParameter("token"));
        return SUCCESS;
    }

    public String edit() {
        catalog = catalogService.get(id);
        request.setAttribute("catalog", catalog);
        return INPUT;
    }

    public String view() {
        if (StringUtils.isEmpty(id)) {
            return "list";
        }
        catalog = catalogService.get(id);
        List<Catalog> catalogs = catalogService.listSubs(id);
        request.setAttribute("catalog", catalog);
        request.setAttribute("list", catalogs);
        return VIEW;
    }

    public String update() {
        catalogService.update(catalog);
        id = catalog.getTreeNode().getParentId();
        return SUCCESS;
    }

    public String updateStatus() {
        catalog = catalogService.updateStatus(id, getIntParameter("status"));
        id = catalog.getTreeNode().getParentId();
        return SUCCESS;
    }

    public String delete() {
        final String[] split = id.split(", ");
        catalogService.delete(split);
        for (int i = 0; i < split.length; i++) {
            CoreServiceFactory.getInstance().getAttachReferenceService().deleteReference(split[i]);
        }
        id = getParameter("catalogId");
        return SUCCESS;
    }

    public String list() {
        // List<Catalog> catalogs = catalogService.root();
        // request.setAttribute("list", catalogs);
        ThreadLocalMap.getInstance().putObject(DataOwnerConfig.Type.class, DataOwnerConfig.Type.EDIT);
        List<Catalog> catalogs = null;
        if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(getParameter("type"))) {
            Catalog catalog = catalogService.get(id);
            request.setAttribute("catalog", catalog);
            catalogs = catalogService.listSubs(id, getIntParameter("type"), -1);
        } else if (StringUtils.isNotEmpty(id) && StringUtils.isEmpty(getParameter("type"))) {
            Catalog catalog = catalogService.get(id);
            request.setAttribute("catalog", catalog);
            catalogs = catalogService.listSubs(id);
        } else {
            catalogs = catalogService.root();
        }
        request.setAttribute("list", catalogs);
        return SUCCESS;
    }

    public String move() {
        String parentId = getParameter("parentId");
        catalogService.moveCatalog(id, parentId);
        id = parentId;
        return SUCCESS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CatalogService getCatalogService() {
        return catalogService;
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public CatalogAuthService getCatalogAuthService() {
        return catalogAuthService;
    }

    public void setCatalogAuthService(CatalogAuthService catalogAuthService) {
        this.catalogAuthService = catalogAuthService;
    }

}
