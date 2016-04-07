package works.tonny.apps.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.Assert;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.core.TreeService;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.dao.BusinessUnitDAO;
import works.tonny.apps.user.dao.DepartmentDAO;
import works.tonny.apps.user.dao.PositionDAO;
import works.tonny.apps.user.model.BusinessUnit;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.DepartmentTreeNode;
import works.tonny.apps.user.service.DepartmentEntityService;
import works.tonny.apps.user.service.DepartmentQuery;
import works.tonny.apps.user.util.LayerUtil;

public class DepartmentServiceImpl extends AuthedAbstractService implements DepartmentEntityService {

	private DepartmentDAO departmentDAO;

	private PositionDAO positionDAO;

	private BusinessUnitDAO businessUnitDAO;

	private TreeService treeService;

	@Transactional(rollbackFor = Exception.class)
	public String create(Department dept) {
		// if (dept.getOrderNo() == 0) {
		// Integer i = departmentDAO.maxRootOrderNo();
		// dept.setOrderNo(i == null ? 0 : i + 1);
		// }
		if (dept.getCreateTime() == null) {
			dept.setCreateTime(new Date());
		}
		dept.setAdmin(getLoginedUser().getUser().getUsername());

		String id = (String) departmentDAO.save(dept);
		DepartmentTreeNode treeNode = dept.getTreeNode();
		if (treeNode == null)
			treeNode = new DepartmentTreeNode(id);
		if (treeNode.getId() == null) {
			treeNode.setId(id);
		}
		dept.setTreeNode(treeNode);
		treeService.addRootNode(treeNode);
		MessageManager.notify(Department.class, MessageEvent.CREATED, dept);
		return id;
	}

	@Transactional(rollbackFor = Exception.class)
	public String create(Department dept, String parentId, Map<String, String> buId) {
		Department parent = departmentDAO.get(parentId);
		Assert.notNull(parent, "dept.parent.null");
		// dept.setParentId(parentId);
		// dept.setIdLayer(LayerUtil.createLayer(parent.getIdLayer(),
		// parentId));
		// Integer i = departmentDAO.maxOrderNo(parentId);
		// dept.setOrderNo(i == null ? 0 : i + 1);

		String id = (String) departmentDAO.save(dept);
		DepartmentTreeNode treeNode = dept.getTreeNode();
		if (treeNode == null)
			treeNode = new DepartmentTreeNode(id);
		if (treeNode.getId() == null) {
			treeNode.setId(id);
		}
		treeService.addNodes(parent.getTreeNode(), treeNode);
		dept.setTreeNode(treeNode);
		dept.setParentDept(parent);
		MessageManager.notify(Department.class, MessageEvent.CREATED, dept);
		for (String type : buId.keySet()) {
			String pid = buId.get(type);
			BusinessUnit bu = businessUnitDAO.get(pid, type);
			BusinessUnit businessUnit = null;
			if (bu != null) {
				businessUnit = new BusinessUnit(dept, bu, type);
			} else {
				Department p = departmentDAO.get(pid);
				businessUnit = new BusinessUnit(dept, p, type);
			}

			businessUnitDAO.save(businessUnit);
			MessageManager.notify(BusinessUnit.class, MessageEvent.CREATED, businessUnit);
		}
		return id;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Department findById(String id) {
		//
		Department department = departmentDAO.get(id);
		if (department == null) {
			return null;
		}
		if (StringUtils.isNotEmpty(department.getTreeNode().getParentId())) {
			department.setParentDept(departmentDAO.get(department.getTreeNode().getParentId()));
		}
		if (department.getCommander() != null) {
			department.getCommander().getName();
		}
		if (StringUtils.isNotEmpty(department.getProperties())) {
			String[] types = department.getProperties().split(", ");
			Map<String, BusinessUnit> type = new HashMap<String, BusinessUnit>();
			for (String string : types) {
				type.put(string, businessUnitDAO.get(id, string));
			}
			department.setBu(type);
		}

		return department;
	}

	public List<Department> findByName(String name) {
		Assert.hasText(name);
		List<Department> list = departmentDAO.list(name);
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(Department dept, Map<String, String> buId) {
		Department department = findById(dept.getId());
		MessageManager.notify(Department.class, MessageEvent.BEFORE_UPDATE, department);
		String property = department.getProperties();
		PropertiesUtils.copyProperties(department, dept, "name", "properties", "description", "code",
				"treeNode.orderNo");
		for (String type : buId.keySet()) {
			if (property != null) {
				property = property.replace(type, "");
			}
			String pid = buId.get(type);
			BusinessUnit tbu = businessUnitDAO.get(dept.getId(), type);
			if (tbu != null && tbu.getParent() != null && tbu.getParent().getId().equals(pid)) {
				continue;
			} else if (tbu != null) {
				BusinessUnit parentNode = businessUnitDAO.get(pid, type);
				List<BusinessUnit> subs = businessUnitDAO.subs(tbu.getIdLayer(), type);
				tbu.setParent(departmentDAO.get(pid));
				String newLayer = null;
				if (parentNode != null) {
					newLayer = LayerUtil.createLayer(parentNode.getIdLayer(), pid);
				} else {
					newLayer = LayerUtil.createLayer(null, pid);
				}
				for (BusinessUnit unit : subs) {
					unit.setIdLayer(unit.getIdLayer().replaceAll(tbu.getIdLayer(), newLayer));
					businessUnitDAO.update(tbu);
					MessageManager.notify(BusinessUnit.class, MessageEvent.UPDATED, tbu);
				}
				continue;
			}

			BusinessUnit bu = businessUnitDAO.get(pid, type);
			BusinessUnit businessUnit = null;
			if (bu != null) {
				businessUnit = new BusinessUnit(dept, bu, type);
			} else {
				Department p = departmentDAO.get(pid);
				businessUnit = new BusinessUnit(dept, p, type);
			}

			List<BusinessUnit> subs = businessUnitDAO.subs(dept.getId(), type);
			for (BusinessUnit unit : subs) {
				unit.setIdLayer(unit.getIdLayer().replaceAll(dept.getId(),
						LayerUtil.createLayer(businessUnit.getIdLayer(), dept.getId())));
				businessUnitDAO.update(unit);
				MessageManager.notify(BusinessUnit.class, MessageEvent.UPDATED, unit);
			}
			businessUnitDAO.save(businessUnit);
			MessageManager.notify(BusinessUnit.class, MessageEvent.CREATED, businessUnit);

		}

		if (property != null) {
			String[] todel = property.split(", ");
			for (String type : todel) {
				if (StringUtils.isEmpty(type)) {
					continue;
				}
				BusinessUnit tbu = businessUnitDAO.get(dept.getId(), type);
				List<BusinessUnit> subs = businessUnitDAO.subs(tbu.getIdLayer(), type);
				for (int i = 0; i < subs.size(); i++) {
					BusinessUnit bu = subs.get(i);
					if (bu.getDept().getId().equals(dept.getId())) {
						continue;
					}
					bu.setIdLayer(bu.getIdLayer().replaceAll(dept.getId() + ",", ""));
					if (bu.getParent().getId().equals(dept.getId())) {
						bu.setParent(tbu.getParent());
					}
					businessUnitDAO.update(bu);
					MessageManager.notify(BusinessUnit.class, MessageEvent.UPDATED, bu);
				}
				businessUnitDAO.delete(tbu);
				MessageManager.notify(BusinessUnit.class, MessageEvent.DELETED, tbu);
			}
		}

		departmentDAO.update(department);
		MessageManager.notify(Department.class, MessageEvent.UPDATED, department);

	}

	public List<Department> getSubs(String deptId) {
		return departmentDAO.subs(deptId);
	}

	@Transactional(rollbackFor = Exception.class)
	public void move(String deptId, String newParentDeptId) {
		Department dept = departmentDAO.get(deptId);
		Assert.notNull(dept, "dept.null");
		Department parent = departmentDAO.get(newParentDeptId);
		Assert.notNull(parent, "dept.parent.null");
		// Integer i = departmentDAO.maxOrderNo(newParentDeptId);
		// dept.setOrderNo(i == null ? 0 : i + 1);
		// dept.setParentId(newParentDeptId);
		// dept.setIdLayer(BooleanUtils.toString(StringUtils.isNotEmpty(parent.getIdLayer()),
		// parent.getIdLayer() + ",",
		// "") + newParentDeptId);
		// departmentDAO.update(dept);
		treeService.move(parent.getTreeNode(), dept.getTreeNode());
		MessageManager.notify(Department.class, MessageEvent.MOVED, dept, parent);
	}

	public Department getParent(String deptId) {
		// Department dept = departmentDAO.get(deptId);
		// Assert.notNull(dept, "dept.null");
		Department parent = departmentDAO.get(treeService.get(deptId).getParentId());
		Assert.notNull(parent, "dept.parent.null");
		return parent;
	}

	public Department getRoot(String deptId) {
		// Department dept = departmentDAO.get(deptId);
		// Assert.notNull(dept, "dept.null");
		// if (StringUtils.isEmpty(dept.getParentId())) {
		// return dept;
		// }

		DepartmentTreeNode treeNode = (DepartmentTreeNode) treeService.get(deptId);
		if (treeNode.getDepth() == 0) {
			return treeNode.getData();
		}

		String rootId = StringUtils.substringBefore(treeNode.getIdLayer(), ",");
		Department root = departmentDAO.get(rootId);
		return root;
	}

	public void remove(String uid) {
		// List<Department> subs = departmentDAO.allSubs(uid);
		// for (Department department : subs) {
		//
		// }
		// List<Position> list = positionDAO.listOfDept(uid);
		// for (Position position : list) {
		// positionDAO.delete(position);
		// }
		List<BusinessUnit> bu = businessUnitDAO.find(uid);
		businessUnitDAO.deleteAll(bu);
		Department department = getDepartmentDAO().get(uid);
		treeService.removeNode(uid);
		MessageManager.notify(Department.class, MessageEvent.DELETED, department);
		// departmentDAO.delete(departmentDAO.get(uid));
	}

	private void remove(Department department) {
		// List<Department> subs = departmentDAO.allSubs(department.getId());
		// for (Department sub : subs) {
		// remove(sub);
		// }
		// List<Position> list = positionDAO.listOfDept(department.getId());
		// for (Position position : list) {
		// positionDAO.delete(position);
		// }
		// departmentDAO.delete(department);
		treeService.removeNode(department.getId());
		MessageManager.notify(Department.class, MessageEvent.DELETED, department);
	}

	@Override
	public DepartmentQuery createQuery() {
		try {
			return new DepartmentQueryImpl((BaseDAOSupport) PropertyUtils.getProperty(departmentDAO,
					"targetSource.target"));
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	public List<Department> root() {
		return departmentDAO.root();
	}

	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public PositionDAO getPositionDAO() {
		return positionDAO;
	}

	public void setPositionDAO(PositionDAO positionDAO) {
		this.positionDAO = positionDAO;
	}

	public BusinessUnitDAO getBusinessUnitDAO() {
		return businessUnitDAO;
	}

	public void setBusinessUnitDAO(BusinessUnitDAO businessUnitDAO) {
		this.businessUnitDAO = businessUnitDAO;
	}

	/**
	 * @return the treeService
	 */
	public TreeService getTreeService() {
		return treeService;
	}

	/**
	 * @param treeService
	 *            the treeService to set
	 */
	public void setTreeService(TreeService treeService) {
		this.treeService = treeService;
	}

}
