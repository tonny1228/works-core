package works.tonny.apps.core;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.GenericGenerator;
import works.tonny.apps.Entity;
import works.tonny.apps.auth.DataOwnerAware;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Set;

/**
 * 目录
 */
@javax.persistence.Entity
@Table(name = "s_catalog")
@Inheritance(strategy = InheritanceType.JOINED)
public class Catalog extends Entity implements DataOwnerAware<CatalogDataOwner> {

    private static final long serialVersionUID = -6820054800483455316L;

    /**
     * 名称
     */
    private String name;

    /**
     * 显示名称
     */
    private String alias;

    /** 层 */
    // private String layer;

    /** 上级目录id */
    // private String parentId;

    // /** 路径 */
    // private String nameLayer;

    /** 序号 */
    // private int orderNo;

    /**
     * 栏目备注
     */
    private String description;

    /**
     * 栏目状态
     */
    private int status;

    /**
     * 是否显示栏目
     */
    private int display;

    /**
     * 栏目类型
     */
    private int type;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String admin;

    private CatalogTreeNode treeNode;

    private Set<CatalogDataOwner> owner;

    public Catalog() {
    }

    public Catalog(Catalog catalog, CatalogTreeNode node) {
        try {
            BeanUtils.copyProperties(this, catalog);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        this.setTreeNode(node);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.Entity#getId()
     */
    @Override
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "assigned")
    @Column(length = 50)
    public String getId() {
        return super.getId();
    }

    @Column(length = 300)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 300)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    // @Column(length = 350)
    // public String getLayer() {
    // return layer;
    // }
    //
    // public void setLayer(String layer) {
    // this.layer = layer;
    // }

    // @Column(length = 500, name = "name_layer")
    // public String getNameLayer() {
    // return nameLayer;
    // }
    //
    // public void setNameLayer(String nameLayer) {
    // this.nameLayer = nameLayer;
    // }
    //
    // @Column(length = 300, name = "order_no")
    // public int getOrderNo() {
    // return orderNo;
    // }
    //
    // public void setOrderNo(int orderNo) {
    // this.orderNo = orderNo;
    // }

    @Lob
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(length = 50)
    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    // @Column(length = 50, name = "parent_id")
    // public String getParentId() {
    // return parentId;
    // }
    //
    // public void setParentId(String parentId) {
    // this.parentId = parentId;
    // }

    /**
     * @return the treeNode
     */
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, optional = false)
    @JoinColumn(name = "id")
    public CatalogTreeNode getTreeNode() {
        return treeNode;
    }

    /**
     * @param treeNode the treeNode to set
     */
    public void setTreeNode(CatalogTreeNode treeNode) {
        this.treeNode = treeNode;
    }

    /**
     * @return the owner
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "data")
    public Set<CatalogDataOwner> getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Set<CatalogDataOwner> owner) {
        this.owner = owner;
    }

    @Override
    @Transient
    public Class getOwnerClass() {
        return CatalogDataOwner.class;
    }

}