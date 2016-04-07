/**
 *
 */
package works.tonny.apps.core.service;

import org.apache.commons.lang3.StringUtils;
import org.llama.library.utils.Assert;
import org.llama.library.utils.PagedList;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.core.TreeNode;
import works.tonny.apps.core.TreeService;
import works.tonny.apps.core.dao.TreeNodeDAO;
import works.tonny.apps.exception.DataException;

import java.util.Arrays;
import java.util.List;

/**
 * @author 祥栋
 */
public class TreeServiceImpl implements TreeService {

    /**
     *
     */
    private static final int STEP = 1000;
    private TreeNodeDAO treeNodeDAO;

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#addRootNode(works.tonny.apps.core.TreeNode)
     */
    public void addRootNode(TreeNode root) {
        Assert.notNull(root.getId());
        root.setDepth(0);
        root.setIdLayer(root.getId());
        root.setParentId(null);
        if (root.getOrderNo() == 0) {
            List<TreeNode> lastChild = treeNodeDAO.root();
            int nextNo = lastChild.isEmpty() ? 0 : lastChild.get(lastChild.size() - 1).getOrderNo() + STEP;
            root.setOrderNo(nextNo);
        }
        treeNodeDAO.save(root);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#addNodes(works.tonny.apps.core.TreeNode,
     * works.tonny.apps.core.TreeNode[])
     */
    public void addNodes(TreeNode parentNode, TreeNode... childNode) {
        for (TreeNode treeNode : childNode) {
            int nextNo = treeNode.getOrderNo();
            if (treeNode.getOrderNo() == 0) {
                PagedList<TreeNode> lastChild = treeNodeDAO.chilren(parentNode.getId(), 1, 1);
                nextNo = lastChild.isEmpty() ? 0 : lastChild.get(0).getOrderNo() + STEP;
            }
            buildTreeNode(parentNode, treeNode, nextNo);
            treeNodeDAO.save(treeNode);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#addNodeBefore(works.tonny.apps.core.TreeNode,
     * works.tonny.apps.core.TreeNode[])
     */
    public void addNodeBefore(TreeNode beforeNode, TreeNode... childNode) {
        TreeNode parentNode = treeNodeDAO.get(beforeNode.getParentId());
        for (TreeNode treeNode : childNode) {
            PagedList<TreeNode> previousSibling = treeNodeDAO.previousSibling(parentNode.getId(),
                    beforeNode.getOrderNo(), 1, 1);
            int nextNo = previousSibling.isEmpty() ? beforeNode.getOrderNo() - STEP
                    : (beforeNode.getOrderNo() + previousSibling.get(0).getOrderNo()) / 2;
            buildTreeNode(parentNode, treeNode, nextNo);
            treeNodeDAO.save(treeNode);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#addNodeAfter(works.tonny.apps.core.TreeNode,
     * works.tonny.apps.core.TreeNode[])
     */
    public void addNodeAfter(TreeNode afterNode, TreeNode... childNode) {
        TreeNode parentNode = treeNodeDAO.get(afterNode.getParentId());
        for (TreeNode treeNode : childNode) {
            PagedList<TreeNode> nextSibling = treeNodeDAO.nextSibling(parentNode.getId(), afterNode.getOrderNo(), 1, 1);
            int nextNo = nextSibling.isEmpty() ? afterNode.getOrderNo() + STEP : (afterNode.getOrderNo() + nextSibling
                    .get(0).getOrderNo()) / 2;
            buildTreeNode(parentNode, treeNode, nextNo);
            treeNodeDAO.save(treeNode);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#move(works.tonny.apps.core.TreeNode,
     * works.tonny.apps.core.TreeNode)
     */
    @Transactional(rollbackFor = Exception.class)
    public void move(TreeNode parentNode, TreeNode childNode) {
        if (isChild(childNode, parentNode)) {
            throw new DataException("不能移动到子节点中");
        }
        String oldLayer = childNode.getIdLayer();
        int oldLDepth = childNode.getDepth();
        PagedList<TreeNode> lastChild = treeNodeDAO.chilren(parentNode.getId(), 1, 1);
        int nextNo = lastChild.isEmpty() ? 0 : lastChild.get(0).getOrderNo() + STEP;
        buildTreeNode(parentNode, childNode, nextNo);
        treeNodeDAO.update(childNode);
        updateChilren(childNode, oldLayer, oldLDepth);
    }

    /**
     * 判断节点是否是其子节点
     *
     * @param parentNode
     * @param childNode
     * @return
     */
    private boolean isChild(TreeNode parentNode, TreeNode childNode) {
        return childNode.getIdLayer().startsWith(parentNode.getIdLayer())
                && !childNode.getIdLayer().equals(parentNode.getIdLayer());
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#moveToRoot(works.tonny.apps.core.TreeNode)
     */
    @Transactional(rollbackFor = Exception.class)
    public void moveToRoot(TreeNode root) {
        String oldLayer = root.getIdLayer();
        int oldLDepth = root.getDepth();
        root.setDepth(0);
        root.setIdLayer(root.getId());
        root.setParentId(null);
        List<TreeNode> lastChild = treeNodeDAO.root();
        int nextNo = lastChild.isEmpty() ? 0 : lastChild.get(lastChild.size() - 1).getOrderNo() + STEP;
        root.setOrderNo(nextNo);
        treeNodeDAO.update(root);
        updateChilren(root, oldLayer, oldLDepth);
    }

    /**
     * @param childNode
     * @param oldLayer
     * @param oldLDepth
     */
    protected void updateChilren(TreeNode childNode, String oldLayer, int oldLDepth) {
        List<TreeNode> list = treeNodeDAO.allChilren(oldLayer + ",");
        for (int i = 0; i < list.size(); i++) {
            TreeNode treeNode = list.get(i);
            treeNode.setDepth(treeNode.getDepth() - oldLDepth + childNode.getDepth());
            treeNode.setIdLayer(StringUtils.replace(treeNode.getIdLayer(), oldLayer + ",", childNode.getIdLayer() + ","));
            treeNodeDAO.update(treeNode);
            if (i > 0 && i % 50 == 0)
                treeNodeDAO.flush();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#moveAfter(works.tonny.apps.core.TreeNode,
     * works.tonny.apps.core.TreeNode)
     */
    @Transactional(rollbackFor = Exception.class)
    public void moveAfter(TreeNode afterNode, TreeNode childNode) {
        String oldLayer = childNode.getIdLayer();
        int oldLDepth = childNode.getDepth();

        TreeNode parentNode = treeNodeDAO.get(afterNode.getParentId());
        PagedList<TreeNode> nextSibling = treeNodeDAO.nextSibling(parentNode.getId(), afterNode.getOrderNo(), 1, 1);
        int nextNo = nextSibling.isEmpty() ? afterNode.getOrderNo() + STEP : (afterNode.getOrderNo() + nextSibling.get(
                0).getOrderNo()) / 2;
        buildTreeNode(parentNode, childNode, nextNo);
        treeNodeDAO.update(childNode);
        updateChilren(childNode, oldLayer, oldLDepth);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#reOrder(java.lang.String[])
     */
    public void reOrder(String[] ids) {
        TreeNode[] nodes = new TreeNode[ids.length];
        int[] order = new int[ids.length];
        int i = 0;
        for (String id : ids) {
            TreeNode node = get(id);
            nodes[i] = node;
            order[i] = node.getOrderNo();
            i++;
        }
        Arrays.sort(order);
        for (int j = 0; j < order.length; j++) {
            nodes[j].setOrderNo(order[j]);
            treeNodeDAO.update(nodes[j]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#moveBefore(works.tonny.apps.core.TreeNode,
     * works.tonny.apps.core.TreeNode)
     */
    @Transactional(rollbackFor = Exception.class)
    public void moveBefore(TreeNode beforeNode, TreeNode childNode) {
        String oldLayer = childNode.getIdLayer();
        int oldLDepth = childNode.getDepth();
        TreeNode parentNode = treeNodeDAO.get(beforeNode.getParentId());
        PagedList<TreeNode> previousSibling = treeNodeDAO.previousSibling(parentNode.getId(), beforeNode.getOrderNo(),
                1, 1);
        int nextNo = previousSibling.isEmpty() ? beforeNode.getOrderNo() - STEP
                : (beforeNode.getOrderNo() + previousSibling.get(0).getOrderNo()) / 2;
        buildTreeNode(parentNode, childNode, nextNo);
        treeNodeDAO.update(childNode);
        updateChilren(childNode, oldLayer, oldLDepth);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#removeNode(java.lang.String)
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeNode(String id) {
        TreeNode treeNode = get(id);
        List<TreeNode> list = treeNodeDAO.allChilren(treeNode.getIdLayer() + ",");
        for (int i = 0; i < list.size(); i++) {
            treeNodeDAO.delete(list.get(i));
            if (i > 0 && i % 50 == 0)
                treeNodeDAO.flush();
        }
        treeNodeDAO.delete(treeNode);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#removeChildren(java.lang.String)
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeChildren(String parentId) {
        TreeNode treeNode = get(parentId);
        List<TreeNode> list = treeNodeDAO.allChilren(treeNode.getIdLayer() + ",");
        for (int i = 0; i < list.size(); i++) {
            treeNodeDAO.delete(list.get(i));
            if (i > 0 && i % 50 == 0)
                treeNodeDAO.flush();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#chilrenNodes(java.lang.String)
     */
    public List<TreeNode> chilrenNodes(String parentId) {
        return treeNodeDAO.chilren(parentId, 1, 1000);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#root(java.lang.String)
     */
    public List<TreeNode> root() {
        return treeNodeDAO.root();
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.core.TreeService#get(java.lang.String)
     */
    public TreeNode get(String id) {
        return treeNodeDAO.get(id);
    }

    /**
     * @param parentNode
     * @param treeNode
     * @param nextNo
     */
    protected void buildTreeNode(TreeNode parentNode, TreeNode treeNode, int nextNo) {
        Assert.notNull(treeNode.getId());
        if (StringUtils.isEmpty(treeNode.getIdLayer()) || !parentNode.getId().equals(treeNode.getParentId())) {
            treeNode.setDepth(parentNode.getDepth() + 1);
            treeNode.setIdLayer(parentNode.getIdLayer() + "," + treeNode.getId());
            treeNode.setParentId(parentNode.getId());
        }
        treeNode.setOrderNo(nextNo);
    }

    /**
     * @return the treeNodeDAO
     */
    public TreeNodeDAO getTreeNodeDAO() {
        return treeNodeDAO;
    }

    /**
     * @param treeNodeDAO the treeNodeDAO to set
     */
    public void setTreeNodeDAO(TreeNodeDAO treeNodeDAO) {
        this.treeNodeDAO = treeNodeDAO;
    }
}
