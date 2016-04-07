/**
 * 
 */
package works.tonny.apps.user.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import works.tonny.apps.core.TreeNode;

/**
 * @author 祥栋
 */
@Entity
@Table(name = "u_position_tree")
public class PositionTreeNode extends TreeNode<Position> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4789238074645663263L;

	/**
	 * @param id
	 */
	public PositionTreeNode(String id) {
		super(id);
	}

	/**
	 * 
	 */
	public PositionTreeNode() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.core.TreeNode#getData()
	 */
	@Override
	@OneToOne(mappedBy = "treeNode")
	public Position getData() {
		return super.getData();
	}

}
