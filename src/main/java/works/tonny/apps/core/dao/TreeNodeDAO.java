/**
 * 
 */
package works.tonny.apps.core.dao;

import java.util.List;

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.TreeNode;
import works.tonny.apps.support.ListSupport;

/**
 * @author 祥栋
 */
public interface TreeNodeDAO extends EntityDAO<TreeNode> {

	/**
	 * 查询所有的子节点
	 * 
	 * @param parentId 父节点id
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@ListSupport(field = { "parentId" }, operator = { ListSupport.EQUALS }, order = "orderNo desc")
	PagedList<TreeNode> chilren(String parentId, int page, int pagesize);

	/**
	 * 查询之前的兄弟节点
	 * 
	 * @param parentId
	 * @param orderNo
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@ListSupport(field = { "parentId", "orderNo" }, operator = { ListSupport.EQUALS, ListSupport.LESS }, order = "orderNo desc")
	PagedList<TreeNode> previousSibling(String parentId, int orderNo, int page, int pagesize);

	/**
	 * 查询之后的兄弟节点
	 * 
	 * @param parentId
	 * @param orderNo
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@ListSupport(field = { "parentId", "orderNo" }, operator = { ListSupport.EQUALS, ListSupport.GREATER }, order = "orderNo")
	PagedList<TreeNode> nextSibling(String parentId, int orderNo, int page, int pagesize);

	/**
	 * @param parentId
	 * @return
	 */
	@ListSupport(field = { "idLayer" }, operator = { ListSupport.RLIKE }, order = { "idLayer", "orderNo" })
	List<TreeNode> allChilren(String parentLayer);

	/**
	 * @return
	 */
	@ListSupport(defaults = "depth=0", order = "orderNo")
	List<TreeNode> root();

}
