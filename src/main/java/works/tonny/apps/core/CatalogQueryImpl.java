/**
 *
 */
package works.tonny.apps.core;

import org.llama.library.cache.Cache;
import org.llama.library.cache.Failover;
import org.llama.library.utils.PagedList;
import works.tonny.apps.impl.AbstractCriteriaQuery;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.util.KeyBuilder;

import java.util.List;
import java.util.Set;

/**
 * @author 祥栋
 */
public class CatalogQueryImpl extends AbstractCriteriaQuery<CatalogQuery, Catalog> implements CatalogQuery {

    private String parentId;

    private String name;

    private String nameLike;

    /**
     * 栏目状态
     */
    private Integer status;

    /**
     * 是否显示栏目
     */
    private Integer display;

    /**
     * 栏目类型
     */
    private Integer type;

    private String catalogId;

    protected String idLayer;

    protected Integer depth;

    private String aliasLike;

    private String idLayerLike;

    private Integer depthGreateThan;

    private Integer depthLessThan;

    private Set<String> cachedKeys;

    private Cache cache;

    /**
     * @param catalogDAO
     */
    public CatalogQueryImpl(BaseDAOSupport catalogDAO, Cache cache, Set<String> cachedParentKeys) {
        this.dao = catalogDAO;
        this.cache = cache;
        this.cachedKeys = cachedParentKeys;
    }

    /**
     */
    protected void doBuild() {
        this.setQueryName("Catalog.query");
        addParameter(parentId != null, "parentId", new String[]{"parentId"}, new Object[]{parentId});
        addParameter(name != null, "name", new String[]{"name"}, new Object[]{name});
        addParameter(nameLike != null, "nameLike", new String[]{"name"}, new Object[]{nameLike});
        addParameter(aliasLike != null, "aliasLike", new String[]{"alias"}, new Object[]{aliasLike});
        addParameter(idLayer != null, "idLayer", new String[]{"idLayer"}, new Object[]{idLayer});
        addParameter(idLayerLike != null, "idLayerLike", new String[]{"idLayerLike"}, new Object[]{idLayerLike});
        addParameter(depthGreateThan != null, "depthGreateThan", new String[]{"depthGreateThan"}, new Object[]{depthGreateThan});
        addParameter(depthLessThan != null, "depthLessThan", new String[]{"depthLessThan"}, new Object[]{depthLessThan});
        addParameter(depth != null, "depth", new String[]{"depth"}, new Object[]{depth});
        addParameter(status != null, "status", new String[]{"status"}, new Object[]{status});
        addParameter(display != null, "display", new String[]{"display"}, new Object[]{display});
        addParameter(type != null, "type", new String[]{"type"}, new Object[]{type});


        if (order.contains("treeNode.idLayer ASC"))
            addParameter(order.contains("treeNode.idLayer ASC"), "orderIdLayer", new String[]{}, new Object[]{});
        else if (order.contains("treeNode.idLayer DESC"))
            addParameter(order.contains("treeNode.idLayer DESC"), "orderIdLayerDesc", new String[]{}, new Object[]{});
        else
            addParameter(order.contains("treeNode.orderNo ASC"), "orderNo", new String[]{}, new Object[]{});

        //        addParameter(parentId, "treeNode.parentId", ListSupport.MUST, ListSupport.EQUALS);
        //        addParameter(name, "name", ListSupport.MUST, ListSupport.EQUALS);
        //        // addParameter(nameLike, "name", ListSupport.MUST, ListSupport.LIKE);
        //        //        if (nameLike != null)
        //        //            addParameterGroup(ListSupport.MUST, ListSupport.SHOULD,
        //        //                    new GroupEntity("name", ListSupport.LIKE, nameLike), new GroupEntity("alias", ListSupport.LIKE,
        //        //                            nameLike));
        //        addParameter(aliasLike, "alias", ListSupport.MUST, ListSupport.LIKE);
        //        addParameter(catalogId, "id", ListSupport.MUST, ListSupport.EQUALS);
        //        addParameter(idLayer, "treeNode.idLayer", ListSupport.MUST, ListSupport.EQUALS);
        //        addParameter(idLayerLike, "treeNode.idLayer", ListSupport.MUST, ListSupport.RLIKE);
        //        addParameter(depthGreateThan, "treeNode.depth", ListSupport.MUST, ListSupport.GREATER);
        //        addParameter(depthLessThan, "treeNode.depth", ListSupport.MUST, ListSupport.LESS);
        //        addParameter(depth, "treeNode.depth", ListSupport.MUST, ListSupport.EQUALS);
        //        addParameter(status, "status", ListSupport.MUST, ListSupport.EQUALS);
        //        addParameter(display, "display", ListSupport.MUST, ListSupport.EQUALS);
        //        addParameter(type, "type", ListSupport.MUST, ListSupport.EQUALS);

    }

    @Override
    public Catalog singleResult() {
        if (cache == null) {
            return super.singleResult();
        }
        String key = buildKey();

        Catalog catalog = cache.getFromCache(key, new Failover() {
            @Override
            public Object getObject(String key) {
                Catalog catalog = CatalogQueryImpl.super.singleResult();
                cachedKeys.add(key);
                return catalog;
            }
        });

        return catalog;
    }

    @Override
    public List<Catalog> executeList() {
        if (cache == null) {
            return super.executeList();
        }
        String key = buildKey();

        List list = cache.getFromCache(key, new Failover() {
            @Override
            public Object getObject(String key) {
                cachedKeys.add(key);
                List list = CatalogQueryImpl.super.executeList();
                return list;
            }
        });
        return list;
    }

    private String buildKey() {
        return new KeyBuilder(CatalogService.KEY).append(parentId).append(name).append(nameLike).append(aliasLike).append(idLayer)
                .append(idLayerLike).append(depthGreateThan).append(depthLessThan).append(status)
                .append(display).append(order).toString();
    }

    @Override
    protected PagedList<Catalog> executeList(final int page, final int pagesize) {
        if (cache == null) {
            return super.executeList(page, pagesize);
        }
        String key = buildKey();

        PagedList list = cache.getFromCache(key, new Failover() {
            @Override
            public Object getObject(String key) {
                cachedKeys.add(key);
                List list = CatalogQueryImpl.super.executeList(page, pagesize);
                return list;
            }
        });
        return list;
    }

    @Override
    protected PagedList<Catalog> executeSubList(final int offset, final int limit) {
        if (cache == null) {
            return super.executeSubList(offset, limit);
        }
        String key = buildKey();

        PagedList list = cache.getFromCache(key, new Failover() {
            @Override
            public Object getObject(String key) {
                cachedKeys.add(key);
                List list = CatalogQueryImpl.super.executeSubList(offset, limit);
                return list;
            }
        });
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#catalogId(java.lang.String)
     */
    public CatalogQuery catalogId(String catalogId) {
        this.catalogId = catalogId;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#name(java.lang.String)
     */
    public CatalogQuery name(String name) {
        this.name = name;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#nameLike(java.lang.String)
     */
    public CatalogQuery nameLike(String name) {
        this.nameLike = name;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#aliasLike(java.lang.String)
     */
    public CatalogQuery aliasLike(String alias) {
        this.aliasLike = alias;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#status(int)
     */
    public CatalogQuery status(int status) {
        this.status = status;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#display(int)
     */
    public CatalogQuery display(int display) {
        this.display = display;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#type(int)
     */
    public CatalogQuery type(int type) {
        this.type = type;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#idLayer(java.lang.String)
     */
    public CatalogQuery idLayer(String idLayer) {
        this.idLayer = idLayer;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#idLayerLike(java.lang.String)
     */
    public CatalogQuery idLayerLike(String idLayer) {
        this.idLayerLike = idLayer;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#depth(int)
     */
    public CatalogQuery depth(int depth) {
        this.depth = depth;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#depthGreateThan(int)
     */
    public CatalogQuery depthGreateThan(int depth) {
        this.depthGreateThan = depth;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#depthLessThan(int)
     */
    public CatalogQuery depthLessThan(int depth) {
        this.depthLessThan = depth;
        return this;
    }

    /**
     * @param parentId the parentId to set
     */
    public CatalogQueryImpl parentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.CatalogQuery#orderById()
     */
    public CatalogQuery orderById() {
        return orderBy("id", Direction.ASC);
    }

    /**
     * {@inheritDoc}
     */
    public CatalogQuery orderByIdLayer(Direction direction) {
        orderBy("treeNode.idLayer", Direction.ASC);
        return orderBy("treeNode.orderNo", direction);
    }

    @Override
    public CatalogQuery orderByOrderNo() {
        orderBy("orderNo", Direction.ASC);
        return this;
    }
}
