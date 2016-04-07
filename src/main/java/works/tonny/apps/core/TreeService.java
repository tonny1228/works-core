/**
 * 
 */
package works.tonny.apps.core;

import java.util.List;

/**
 * @author 祥栋
 */
public interface TreeService {

	/**
	 * 添加根节点
	 * 
	 * @param
	 */
	void addRootNode(TreeNode root);

	/**
	 * 在树中添加新的节点
	 * 
	 * @param parentNode 父节点
	 * @param childNode 子节点
	 */
	void addNodes(TreeNode parentNode, TreeNode... childNode);

	/**
	 * 添加节点到当前节点之前
	 * 
	 * @param beforeNode 当前的节点
	 * @param childNode 新添加的节点
	 */
	void addNodeBefore(TreeNode beforeNode, TreeNode... childNode);

	/**
	 * 添加节点到当前节点之后
	 * 
	 * @param afterNode 当前节点
	 * @param childNode 新的节点
	 */
	void addNodeAfter(TreeNode afterNode, TreeNode... childNode);

	/**
	 * 移动节点到父节点最后
	 * 
	 * @param parentNode 父节点
	 * @param childNode 新的节点
	 */
	void move(TreeNode parentNode, TreeNode childNode);

	/**
	 * 移动节点到父节点最后
	 * 
	 * @param parentNode 父节点
	 * @param childNode 新的节点
	 */
	void moveToRoot(TreeNode childNode);

	/**
	 * 移动节点到当前节点之后
	 * 
	 * @param afterNode 当前节点
	 * @param childNode 新的节点
	 */
	void moveAfter(TreeNode afterNode, TreeNode childNode);

	/**
	 * 移动节点到当前节点之前
	 * 
	 * @param beforeNode 当前的节点
	 * @param childNode 新添加的节点
	 */
	void moveBefore(TreeNode beforeNode, TreeNode childNode);

	/**
	 * 删除节点
	 * 
	 * @param id id
	 */
	void removeNode(String id);

	/**
	 * 程序排序同级节点
	 * 
	 * @param id
	 */
	void reOrder(String[] id);

	/**
	 * 删除所有子节点
	 * 
	 * @param parentId
	 */
	void removeChildren(String parentId);

	/**
	 * 通过目录层获取下级子目录
	 * 
	 * @Title: listSubs
	 * @Description:
	 * @param id 目录编号
	 * @return List<Catalog> 返回子目录集合
	 * @throws
	 */
	List<? extends TreeNode> chilrenNodes(String parentId);

	/**
	 * 查询所有的root节点
	 * 
	 * @param catalog
	 * @return
	 */
	List<TreeNode> root();

	/**
	 * 查询节点
	 * 
	 * @param id
	 * @return
	 */
	TreeNode get(String id);

}