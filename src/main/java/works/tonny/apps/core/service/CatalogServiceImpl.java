package works.tonny.apps.core.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.Assert;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.core.*;
import works.tonny.apps.core.dao.CatalogDAO;
import works.tonny.apps.exception.DataException;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.AuthedAbstractService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CatalogServiceImpl extends AuthedAbstractService implements CatalogService {

    /**
     * @Fields LAYER_LENGTH :
     */
    protected static final int LAYER_LENGTH = 4;

    private CatalogDAO catalogDAO;

    // private Map<String, EntityUpdateListener<Catalog>> listeners;

    private TreeService treeService;

    /**
     * @param catalog
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String save(Catalog catalog) {
        validateProperty(catalog);
        validateName(catalog.getId(), catalog.getTreeNode() == null ? null : catalog.getTreeNode().getParentId(),
                catalog.getName());
        MessageManager.notify(Catalog.class, MessageEvent.BEFORE_CREATE, catalog);
        String id = catalogDAO.save(catalog);
        // /
        // CatalogTreeNode node = new CatalogTreeNode(id);
        if (catalog.getTreeNode() != null && StringUtils.isNotEmpty(catalog.getTreeNode().getParentId())) {
            catalog.getTreeNode().setId(id);
            treeService.addNodes(treeService.get(catalog.getTreeNode().getParentId()), catalog.getTreeNode());
        } else {
            CatalogTreeNode node = new CatalogTreeNode(id);
            treeService.addRootNode(node);
        }
        // /
        // dispatchCreateListner(catalog);
        MessageManager.notify(Catalog.class, MessageEvent.CREATED, catalog);
        return id;
    }

    /**
     * @param catalog
     * @param parentId
     * @return java.lang.String)
     */
    @Transactional(rollbackFor = Exception.class)
    public String save(Catalog catalog, String parentId) {
        if (catalog.getUpdateTime() == null) {
            catalog.setUpdateTime(new Date());
        }
        Catalog parent = get(parentId);
        Assert.notNull(parent);
        // int nextNo =
        // catalogDAO.nextOrderNo(parent.getTreeNode().getIdLayer(),
        // parent.getTreeNode().getIdLayer().length() + 4) + 1;
        // catalog.setLayer(catalogLayer(parent.getTreeNode().getIdLayer(),
        // nextNo));
        // catalog.setOrderNo(nextNo);
        // catalog.setParentId(parentId);
        // catalog.setNameLayer(parent.getNameLayer() + "/" +
        // catalog.getName());
        CatalogTreeNode node = new CatalogTreeNode();
        node.setParentId(parentId);
        catalog.setTreeNode(node);
        MessageManager.notify(Catalog.class, MessageEvent.BEFORE_CREATE, catalog);
        String id = save(catalog);
        MessageManager.notify(Catalog.class, MessageEvent.CREATED, catalog);
        // ///
        // CatalogTreeNode node = new CatalogTreeNode(id);
        // treeService.addNodes(treeService.get(catalog.getTreeNode().getParentId()),
        // node);
        // //
        return id;
    }

    /**
     * 验证同级下是否重名
     *
     * @param id
     * @param parentId
     * @param name
     */
    protected void validateName(String id, String parentId, String name) {
        List<Catalog> list = catalogDAO.list(id, parentId, name);
        if (list.size() > 0) {
            throw new DataException("名称为" + name + "的栏目已存在!");
        }
    }

    /**
     * @Title: dispatchListner
     * @param catalog
     * @date 2012-6-15 下午9:02:37
     * @author tonny
     * @version 1.0
     */
    // private void dispatchCreateListner(Catalog catalog) {
    // if (listeners != null) {
    // for (EntityUpdateListener<Catalog> listener : listeners.values()) {
    // listener.created(catalog);
    // }
    // }
    // }

    /**
     * @param t
     * @Title: validateProperty
     * @date 2012-5-15 下午2:54:56
     * @author tonny
     * @version 1.0
     */
    private void validateProperty(Catalog t) {

        if (t.getUpdateTime() == null) {
            t.setUpdateTime(new Date());
        }
        if (StringUtils.isEmpty(t.getId())) {
            t.setId(UUID.randomUUID().toString());
        }
        if (StringUtils.isEmpty(t.getAlias())) {
            t.setAlias(t.getName());
        }
        // if (StringUtils.isEmpty(t.getTreeNode().getIdLayer())) {
        // String parentLayer = "";
        // int nextNo = catalogDAO.nextOrderNo(parentLayer, parentLayer.length()
        // + 4) + 1;
        // t.setLayer(catalogLayer("", nextNo));
        // t.setOrderNo(nextNo);
        // }
        // if (StringUtils.isEmpty(t.getNameLayer())) {
        // t.setNameLayer(t.getName());
        // }
        t.setAdmin(getLoginedUser().getUser().getUsername());
        t.setUpdateTime(new Date());
    }

    /**
     * @param id
     * @return
     */
    public Catalog get(String id) {
        Catalog catalog = catalogDAO.get(id);
        //        System.out.println(catalog.getTreeNode().getIdLayer());
        log.debug(catalog.getTreeNode().getIdLayer());
        return catalog;
    }

    /**
     * @param catalog
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Catalog catalog) {
        Catalog loaded = catalogDAO.get(catalog.getId());
        validateName(loaded.getId(), loaded.getTreeNode().getParentId(), catalog.getName());
        // if (!(loaded.getName().equals(catalog.getName()))) {
        // String layer = loaded.getTreeNode().getIdLayer();
        // String oldNameLayer = loaded.getNameLayer();
        // String newNameLayer = null;
        //
        // // 根栏目
        // if (layer.length() == LAYER_LENGTH) {
        // newNameLayer = catalog.getName();
        // } else {
        // newNameLayer = StringUtils.substringBeforeLast(oldNameLayer, "/") +
        // "/" +
        // catalog.getName();
        // }
        //
        // updateSubCatalogs(layer, layer, layer, oldNameLayer, newNameLayer);
        // loaded.setNameLayer(newNameLayer);
        // }
        // validateProperty(catalog);
        // catalog.setParentId(loaded.getTreeNode().getParentId());
        PropertiesUtils.copyNotNullProperties(loaded, catalog, "name", "alias", "description", "status", "display",
                "type");
        MessageManager.notify(Catalog.class, MessageEvent.BEFORE_UPDATE, loaded);
        loaded.setUpdateTime(new Date());
        catalogDAO.update(loaded);
        MessageManager.notify(Catalog.class, MessageEvent.UPDATED, loaded);
        try {
            BeanUtils.copyProperties(catalog, loaded);
        } catch (Exception e) {
            log.warn(e);
        }
        // if (listeners != null) {
        // for (EntityUpdateListener<Catalog> listener : listeners.values()) {
        // listener.updated(loaded);
        // }
        // }
    }

    /**
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Catalog catalog = catalogDAO.get(id);
        MessageManager.notify(Catalog.class, MessageEvent.BEFORE_DELETE, catalog);
        Assert.notNull(catalog);
        treeService.removeNode(id);
        MessageManager.notify(Catalog.class, MessageEvent.DELETED, catalog);
        // catalogDAO.delete(catalogDAO.get(id));
        // //
        // //
        // if (listeners != null) {
        // for (EntityUpdateListener<Catalog> listener : listeners.values()) {
        // listener.deleted(catalog);
        // }
        // }
        /*
         * PagedList<Catalog> subs =
		 * catalogDAO.listSubsByLayer(catalog.getTreeNode().getIdLayer(), 100,
		 * catalog.getTreeNode().getIdLayer().length(), 1, 100); int page = 2;
		 * while (subs.size() > 0) { for (Catalog sub : subs) {
		 * catalogDAO.delete(sub); if (listeners != null) { for
		 * (EntityUpdateListener<Catalog> listener : listeners.values()) {
		 * listener.deleted(sub); } } } subs =
		 * catalogDAO.listSubsByLayer(catalog.getTreeNode().getIdLayer(), 100,
		 * catalog.getTreeNode().getIdLayer().length(), page++, 100); }
		 */
    }

    /**
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            delete(ids[i]);
        }
    }

    /**
     * @param id
     * @param parentId java.lang.String)
     */
    @Transactional(rollbackFor = Exception.class)
    public void moveCatalog(String id, String parentId) {
        Catalog catalog = get(id);
        Catalog parent = null;
        if (StringUtils.isNotEmpty(parentId))
            parent = get(parentId);
        // Assert.notNull(parent);
        // String oldLayer = catalog.getTreeNode().getIdLayer();
        // String oldNameLayer = catalog.getNameLayer();
        // int nextNo =
        // catalogDAO.nextOrderNo(parent.getTreeNode().getIdLayer(),
        // parent.getTreeNode().getIdLayer().length()) + 1;
        // String newLayer = catalogLayer(parent.getTreeNode().getIdLayer(),
        // nextNo);
        // String newNameLayer = parent.getNameLayer() + "/" +
        // catalog.getName();
        // catalog.setLayer(newLayer);
        // catalog.setParentId(parentId);
        // catalog.setNameLayer(newNameLayer);
        // updateSubCatalogs(oldLayer, oldLayer, newNameLayer, oldNameLayer,
        // newNameLayer);
        // catalogDAO.update(catalog);
        // ///

        MessageManager.notify(Catalog.class, MessageEvent.BEFORE_MOVE, catalog, parent);
        if (parent != null) {
            treeService.move(parent.getTreeNode(), catalog.getTreeNode());
        } else
            treeService.moveToRoot(catalog.getTreeNode());
        MessageManager.notify(Catalog.class, MessageEvent.MOVED, catalog, parent);
        // ///
        // if (listeners != null) {
        // for (EntityUpdateListener<Catalog> listener : listeners.values()) {
        // listener.updated(catalog);
        // }
        // }
    }

    /**
     * 移动子栏目
     *
     * @Title: moveSubCatalogs
     * @param oldLayer
     * @param oldNameLayer
     * @param newLayer
     * @param newNameLayer
     * @date 2012-5-24 上午9:36:54
     * @author tonny
     * @version 1.0
     * @param newNameLayer
     * @param oldNameLayer
     */
    // protected void updateSubCatalogs(String parentLayer, String oldLayer,
    // String newLayer, String
    // oldNameLayer,
    // String newNameLayer) {
    // // 移动栏目的所有子栏目
    // List<Catalog> list = catalogDAO.listSubsByLayer(parentLayer, 100,
    // parentLayer.length(), 1,
    // 10000);
    // // 更新所有栏目的layer和层路径
    // for (Catalog subCatalog : list) {
    // String cplayer = subCatalog.getTreeNode().getIdLayer();
    // if (!oldLayer.equals(newLayer)) {
    // subCatalog.setLayer(subCatalog.getTreeNode().getIdLayer().replace(oldLayer,
    // newLayer));
    // }
    // if (!oldNameLayer.equals(newNameLayer)) {
    // subCatalog.setNameLayer(subCatalog.getNameLayer().replace(oldNameLayer,
    // newNameLayer));
    // }
    // catalogDAO.update(subCatalog);
    // if (listeners != null) {
    // for (EntityUpdateListener<Catalog> listener : listeners.values()) {
    // listener.updated(subCatalog);
    // }
    // }
    // updateSubCatalogs(cplayer, oldLayer, newLayer, oldNameLayer,
    // newNameLayer);
    // }
    // catalogDAO.flush();
    // }

    /**
     * @param id
     * @return
     */
    public List<Catalog> listSubs(String id) {
        // Catalog catalog = get(id);
        // Assert.notNull(catalog);
        //        List<Catalog> subs = catalogDAO.listSubs(id);
        // subs.remove(0);
        List<Catalog> list = createQuery().parentId(id).orderByOrderNo().list();
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogService#subs(java.lang.String)
     */
    public List<CatalogTreeNode> subs(String id) {
        return (List<CatalogTreeNode>) treeService.chilrenNodes(id);
    }

    public CatalogDAO getCatalogDAO() {
        return catalogDAO;
    }

    public void setCatalogDAO(CatalogDAO catalogDAO) {
        this.catalogDAO = catalogDAO;
    }

    /**
     * @return
     */
    public List<Catalog> root() {
        //        List<TreeNode> root = treeService.root();
        //        List<Catalog> catalogs = new ArrayList<Catalog>();
        //        for (TreeNode node : root) {
        //            catalogs.add(((CatalogTreeNode) node).getData());
        //        }
        List<Catalog> list = createQuery().depth(0).list();
        return list;
        // return catalogDAO.listSubsByLayer("", 4, 0, 1, 10000);
    }

    /**
     * {@inheritDoc}
     */
    public CatalogQuery createQuery() {
        try {
            return new CatalogQueryImpl((BaseDAOSupport) PropertyUtils.getProperty(catalogDAO, "targetSource.target"));
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * @param id
     * @param type
     * @return
     */
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Catalog> listSubs(String id, int type, int depth) {
        Catalog catalog = get(id);
        if (depth <= 0) {
            return createQuery().idLayerLike(catalog.getTreeNode().getIdLayer()).type(type).list();
            //            return catalogDAO.listSubsByLayer(catalog.getTreeNode().getIdLayer(), type);
        } else {
            return createQuery().idLayerLike(catalog.getTreeNode().getIdLayer()).depthGreateThan(catalog.getTreeNode().getDepth()).depthLessThan(depth + 1).type(type).list();
            //            return catalogDAO.listSubsByLayer(catalog.getTreeNode().getIdLayer(), new Integer[]{
            //                    catalog.getTreeNode().getDepth(), depth}, type);
        }

    }

    /**
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Catalog root(String id) {
        Catalog catalog = get(id);
        Assert.notNull(catalog);
        if (StringUtils.isEmpty(catalog.getTreeNode().getParentId())) {
            return catalog;
        }

        return get(StringUtils.substringBefore(catalog.getTreeNode().getIdLayer(), ","));
        //
        // List<Catalog> list =
        // catalogDAO.listSubsByLayer(catalog.getTreeNode().getIdLayer().substring(0,
        // 4), 4, 0, 1,
        // 2);
        // if (list.size() > 0) {
        // return list.get(0);
        // }
        // return null;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * int)
     */
    public Catalog updateStatus(String id, int status) {
        Catalog catalog = get(id);
        catalog.setStatus(status);
        catalogDAO.update(catalog);
        return catalog;
    }

    /**
     * {@inheritDoc}
     */
    public List<Catalog> listSubs(String id, int type) {
        //        return catalogDAO.listSubs(id, type);
        return createQuery().parentId(id).type(type).list();
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogService#registerListener(java.lang.String,
     *      works.tonny.apps.support.message.EntityUpdateListener)
     */
    // public void registerListener(String name, EntityUpdateListener<Catalog>
    // listener) {
    // if (listeners == null) {
    // listeners = new LinkedHashMap<String, EntityUpdateListener<Catalog>>();
    // }
    // listeners.put(name, listener);
    // }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogService#removeListener(java.lang.String)
     */
    // public void removeListener(String name) {
    // if (listeners != null) {
    // listeners.remove(name);
    // }
    // }

    /**
     * @return the treeService
     */
    public TreeService getTreeService() {
        return treeService;
    }

    /**
     * @param treeService the treeService to set
     */
    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
    }

    /**
     * @return the listeners
     */
    // public Map<String, EntityUpdateListener<Catalog>> getListeners() {
    // return listeners;
    // }
    //
    // /**
    // * @param listeners the listeners to set
    // */
    // public void setListeners(Map<String, EntityUpdateListener<Catalog>>
    // listeners) {
    // this.listeners = listeners;
    // }

}
