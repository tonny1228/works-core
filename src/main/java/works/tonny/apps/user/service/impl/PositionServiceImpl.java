package works.tonny.apps.user.service.impl;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.Assert;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.core.TreeNode;
import works.tonny.apps.core.TreeService;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.dao.DepartmentDAO;
import works.tonny.apps.user.dao.PositionDAO;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.Position;
import works.tonny.apps.user.model.PositionTreeNode;
import works.tonny.apps.user.service.PositionEntityService;

import java.util.ArrayList;
import java.util.List;

public class PositionServiceImpl extends AuthedAbstractService implements PositionEntityService {

	private PositionDAO positionDAO;

	private DepartmentDAO departmentDAO;

	// private Map<String, EntityUpdateListener<Position>> listeners;

	private TreeService treeService;

	@Transactional(rollbackFor = Exception.class)
	public String create(Position position, String deptId) {
		Department department = departmentDAO.get(deptId);
		Assert.notNull(department);
		// if (position.getTreeNode() != null &&
		// StringUtils.isNotBlank(position.getTreeNode().getParentId())) {
		// Position parent =
		// positionDAO.get(position.getTreeNode().getParentId());
		// //position.setIdLayer(LayerUtil.createLayer(parent.getTreeNode().getIdLayer(),
		// parent.getId()));
		// treeService.addNodes(parent.getTreeNode(), new PositionTreeNode(id));
		// }
		// Integer maxOrderNo = positionDAO.maxOrderNo(deptId);
		// position.setOrderNo(maxOrderNo == null ? 0 : maxOrderNo + 1);
		position.setDepartment(department);
		String id = positionDAO.save(position);
		MessageManager.notify(Position.class, MessageEvent.CREATED, position);

		if (position.getTreeNode() != null && StringUtils.isNotBlank(position.getTreeNode().getParentId())) {
			Position parent = positionDAO.get(position.getTreeNode().getParentId());
			// position.setIdLayer(LayerUtil.createLayer(parent.getTreeNode().getIdLayer(),
			// parent.getId()));
			position.getTreeNode().setId(id);
			treeService.addNodes(parent.getTreeNode(), position.getTreeNode());
		} else {
			treeService.addRootNode(new PositionTreeNode(id));
		}

		if (position.isCommander()) {
			MessageManager.notify(Department.class, MessageEvent.BEFORE_UPDATE, department);
			position.setDepartment(department);
			department.setCommander(position);
			departmentDAO.update(department);
			MessageManager.notify(Department.class, MessageEvent.UPDATED, department);
		}
		// if (listeners != null) {
		// for (EntityUpdateListener<Position> listener : listeners.values()) {
		// listener.created(position);
		// }
		// }

		return (String) id;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Position findById(String id) {
		if (id == null) {
			return null;
		}
		Position position = positionDAO.get(id);
		if (position.getDepartment().getCommanderPositionId() != null
				&& position.getDepartment().getCommanderPositionId().equals(position.getId())) {
			position.setCommander(true);
		}
		position.getUsers().size();
		return position;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.user.service.PositionService#findByName(java.lang.String,java.lang.String
	 *      )
	 */
	public Position findByName(String posName, String deptName) {
		List<Position> list = positionDAO.list(posName, deptName);
		return list.isEmpty() ? null : list.get(0);
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(Position position) {
		Position pos = findById(position.getId());
		PropertiesUtils.copyProperties(pos, position, "name", "description", "jobLevel");
		boolean layerChanged = false;
		String oldParentId = null;

		if (position.getTreeNode() != null
				&& StringUtils.isNotEmpty(position.getTreeNode().getParentId())
				&& (pos.getTreeNode() != null && !position.getTreeNode().getParentId()
						.equals(pos.getTreeNode().getParentId()))) {
			treeService.move(treeService.get(position.getTreeNode().getParentId()), pos.getTreeNode());
		} else if ((position.getTreeNode() == null || StringUtils.isEmpty(position.getTreeNode().getParentId()))
				&& pos.getTreeNode() != null) {
			// List<? extends TreeNode> chilrenNodes =
			// treeService.chilrenNodes(position.getId());
			// for (TreeNode treeNode : chilrenNodes) {
			// treeService.moveToRoot(treeNode);
			// }
			treeService.moveToRoot(pos.getTreeNode());
		}

		// if (StringUtils.isEmpty(position.getParentId()) &&
		// StringUtils.isNotEmpty(pos.getParentId())) {
		// oldParentId = pos.getParentId();
		// pos.setParentId(null);
		// pos.setIdLayer(null);
		// layerChanged = true;
		// } else if ((StringUtils.isNotEmpty(position.getParentId()) &&
		// StringUtils.isEmpty(pos.getParentId()))
		// || !position.getParentId().equals(pos.getParentId())) {
		// oldParentId = pos.getParentId();
		// pos.setParentId(position.getParentId());
		// Position parent = positionDAO.get(position.getParentId());
		// pos.setIdLayer(BooleanUtils.toString(StringUtils.isNotEmpty(parent.getIdLayer()),
		// parent.getIdLayer() + ",", "") + parent.getId());
		// layerChanged = true;
		// }

		positionDAO.update(pos);
		MessageManager.notify(Position.class, MessageEvent.UPDATED, pos);
		// if (layerChanged) {
		// List<Position> subs = positionDAO.allSubs(pos.getId());
		// for (Position sub : subs) {
		// if (StringUtils.isEmpty(oldParentId)) {
		// sub.setIdLayer(pos.getIdLayer() + "," + sub.getIdLayer());
		// } else if (StringUtils.isEmpty(pos.getParentId())) {
		// sub.setIdLayer(sub.getIdLayer().replace(position.getIdLayer() + ",",
		// ""));
		// } else {
		// sub.setIdLayer(sub.getIdLayer().replace(position.getIdLayer(),
		// pos.getIdLayer()));
		// }
		// }
		// }

		Department department = pos.getDepartment();
		if (position.isCommander() && !pos.getId().equals(department.getCommanderPositionId())) {
			department.setCommanderPositionId(pos.getId());
			departmentDAO.update(pos.getDepartment());
			MessageManager.notify(Department.class, MessageEvent.UPDATED, department);
		} else if (!position.isCommander() && pos.getId().equals(department.getCommanderPositionId())) {
			department.setCommanderPositionId(null);
			departmentDAO.update(pos.getDepartment());
			MessageManager.notify(Department.class, MessageEvent.UPDATED, department);
		}
		// if (listeners != null) {
		// for (EntityUpdateListener<Position> listener : listeners.values()) {
		// listener.updated(pos);
		// }
		// }
	}

	public List<Position> subs(String pId) {
		return positionDAO.subs(pId);
	}

	public List<Position> getPositions(String deptId) {
		return positionDAO.listOfDept(deptId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void move(String posId, String newDeptId) {
		Position pos = findById(posId);
		Assert.notNull(pos);
		Department department = departmentDAO.get(newDeptId);
		Assert.notNull(department);
		pos.setDepartment(department);
		// Integer maxOrderNo = positionDAO.maxOrderNo(newDeptId);
		// pos.setOrderNo(maxOrderNo == null ? 0 : maxOrderNo + 1);
		positionDAO.update(pos);
		MessageManager.notify(Position.class, MessageEvent.MOVED, pos);
		// if (listeners != null) {
		// for (EntityUpdateListener<Position> listener : listeners.values()) {
		// listener.updated(pos);
		// }
		// }
	}

	@Transactional(rollbackFor = Exception.class)
	public void remove(String id) {
		List<? extends TreeNode> chilrenNodes = treeService.chilrenNodes(id);
		for (TreeNode treeNode : chilrenNodes) {
			treeService.moveToRoot(treeNode);
		}

		// List<Position> subs = positionDAO.subs(id);
		// for (Position position : subs) {
		// Position temp = new Position(position.getName(),
		// position.getDescription(), null);
		// temp.setOrderNo(position.getOrderNo());
		// temp.setId(position.getId());
		// update(temp);
		// if (listeners != null) {
		// for (EntityUpdateListener<Position> listener : listeners.values()) {
		// listener.updated(temp);
		// }
		// }
		// }
		Position pos = positionDAO.get(id);
		MessageManager.notify(Position.class, MessageEvent.BEFORE_DELETE, pos);
		positionDAO.delete(pos);
		MessageManager.notify(Position.class, MessageEvent.DELETED, pos);
		// if (listeners != null) {
		// for (EntityUpdateListener<Position> listener : listeners.values()) {
		// listener.deleted(pos);
		// }
		// }
	}

	/*
	 * @see
	 * works.tonny.user.service.iface.PositionService#parentPositions(java.lang
	 * .String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Position> parentPositions(String deptId) {
		Department department = departmentDAO.get(deptId);
		// List<Position> list = positionDAO.listOfDept(deptId);
		String idLayer = department.getTreeNode().getIdLayer();
		// if (department.getTreeNode().getDepth() == 0) {
		// return list;
		// }
		List<Position> positions = new ArrayList<Position>();
		String[] parentIds = idLayer.split(",");
		for (String id : parentIds) {
			Department parent = departmentDAO.get(id);
			List<Position> parentPositions = positionDAO.listOfDept(parent.getId());
			positions.addAll(parentPositions);
		}
		// positions.addAll(list);
		return positions;
	}



	public PositionDAO getPositionDAO() {
		return positionDAO;
	}

	public void setPositionDAO(PositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}

	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	/**
	 * @return the listeners
	 */
	// public Map<String, EntityUpdateListener<Position>> getListeners() {
	// return listeners;
	// }

	/**
	 * @param listeners the listeners to set
	 */
	// public void setListeners(Map<String, EntityUpdateListener<Position>>
	// listeners) {
	// Set<String> keySet = listeners.keySet();
	// for (String key : keySet) {
	// registerListener(key, listeners.get(key));
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

}
