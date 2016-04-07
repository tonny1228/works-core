/**
 *
 */
package works.tonny.apps.core;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author 祥栋
 */
@Entity
@Table(name = "s_catalog_tree")
public class CatalogTreeNode extends TreeNode<Catalog> {
    /**
     *
     */
    private static final long serialVersionUID = 2746126343600839121L;

    /**
     *
     */
    public CatalogTreeNode() {
    }

    public CatalogTreeNode(CatalogTreeNode n, Catalog c) {
        this.setDepth(n.getDepth());
        this.setIdLayer(n.getIdLayer());
        this.setData(c);
    }


    /**
     * @param id
     * @param catalog
     */
    public CatalogTreeNode(String id) {
        super(id);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeNode#getData()
     */
    @Override
    @OneToOne(mappedBy = "treeNode")
    public Catalog getData() {
        return super.getData();
    }

}
