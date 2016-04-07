/**
 *
 */
package works.tonny.apps.user.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import works.tonny.apps.core.TreeNode;

/**
 * @author 祥栋
 */
@Entity
@Table(name = "u_department_tree")
public class DepartmentTreeNode extends TreeNode<Department> {

    /**
     *
     */
    private static final long serialVersionUID = 4789238074645663263L;

    /**
     * @param id
     */
    public DepartmentTreeNode(String id) {
        super(id);
    }

    /**
     *
     */
    public DepartmentTreeNode() {
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeNode#getData()
     */
    @Override
    @OneToOne(cascade = CascadeType.REMOVE,mappedBy = "treeNode")
    public Department getData() {
        return super.getData();
    }

}
