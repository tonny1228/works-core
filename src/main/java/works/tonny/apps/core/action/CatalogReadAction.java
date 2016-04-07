package works.tonny.apps.core.action;

import org.apache.commons.lang.StringUtils;
import works.tonny.apps.core.*;
import works.tonny.apps.user.AnonymousAction;
import works.tonny.apps.user.User;

import java.util.ArrayList;
import java.util.List;

public class CatalogReadAction extends AnonymousAction {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private CatalogService catalogService;

    private CatalogAuthService catalogAuthService;

    public String list() {
        // List<Catalog> catalogs = catalogService.root();
        // request.setAttribute("list", catalogs);
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

    /**
     * 分类选择树
     *
     * @return
     */
    public String tree() {
        Catalog root = null;
        String async = getParameter("async");
        String node = getParameter("node");
        if ("true".equalsIgnoreCase(async) && node == null) {
            if (id.equals(getParameter("root"))) {
                root = catalogService.get(id);
            } else {
                root = catalogService.root(id);
            }
            List<Catalog> catalogs = catalogService.listSubs(root.getId(), getIntParameter("type"), 2);
            catalogs.add(0, root);
            request.setAttribute("list", catalogs);
            request.setAttribute("current", id);
            return SUCCESS;
        }
        if ("true".equalsIgnoreCase(node)) {
            root = catalogService.get(id);
            entityLazyProxy.refresh(root);
            List<Catalog> catalogs = catalogService.listSubs(root.getId(), getIntParameter("type"), root.getTreeNode()
                    .getDepth() + 1);
            request.setAttribute("list", catalogs);
            return "node";
        }
        if (id != null && id.equals(getParameter("root"))) {
            root = catalogService.get(id);
        } else if (id != null) {
            root = catalogService.root(id);
        }
        List<Catalog> catalogs = null;

        if (root != null) {
            //			catalogs = catalogService.listSubs(root.getId(), getIntParameter("type"), getIntParameter("depth"));
            CatalogQuery catalogQuery = catalogService.createQuery().idLayerLike(root.getTreeNode().getIdLayer());
            if (getParameter("type") != null) {
                catalogQuery.type(getIntParameter("type"));
            }
            if (getParameter("depth") != null) {
                catalogQuery.depthGreateThan(root.getTreeNode().getDepth()).depthLessThan(getIntParameter("depth") + 1);
            }
            catalogs = catalogQuery.list();
        } else {
            catalogs = catalogService.root();
            List<Catalog> subs = null;
            subs = new ArrayList<Catalog>();
            for (Catalog catalog : catalogs) {
                subs.addAll(catalogService.listSubs(catalog.getId(), getIntParameter("type"), getIntParameter("depth")));
            }
            catalogs.addAll(subs);
        }
        // if (getIntParameter("depth") > 0)
        // catalogs.add(0, root);
        request.setAttribute("list", catalogs);
        request.setAttribute("current", id);
        return SUCCESS;

    }

    public CatalogAuthService getCatalogAuthService() {
        return catalogAuthService;
    }

    public void setCatalogAuthService(CatalogAuthService catalogAuthService) {
        this.catalogAuthService = catalogAuthService;
    }

    public String authedTree() {
        Catalog root = catalogService.root(id);
        List<CatalogAuth> catalogAuths = catalogAuthService
                .subSatalogAuth(root.getId(), (User) loginedUser().getUser());
        List<Catalog> catalogs = new ArrayList<Catalog>();
        for (CatalogAuth catalogAuth : catalogAuths) {
            if (catalogAuth.isExtend()) {
                catalogs.add(catalogAuth.getCatalog());
                catalogs.addAll(catalogService.listSubs(catalogAuth.getCatalog().getId(), 0, 6));
            } else {
                catalogs.add(catalogAuth.getCatalog());
            }
        }
        request.setAttribute("list", catalogs);
        request.setAttribute("current", id);
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
