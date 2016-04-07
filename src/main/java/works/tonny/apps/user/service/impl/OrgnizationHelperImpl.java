/**
 * 
 */
package works.tonny.apps.user.service.impl;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PagedList;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.user.User;
import works.tonny.apps.user.UserService;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.DepartmentType;
import works.tonny.apps.user.model.Position;
import works.tonny.apps.user.model.UserInfo;
import works.tonny.apps.user.service.DepartmentLevel;
import works.tonny.apps.user.service.DepartmentService;
import works.tonny.apps.user.service.OrgnizationHelper;
import works.tonny.apps.user.service.PositionService;

/**
 * @author 祥栋
 */
public class OrgnizationHelperImpl implements OrgnizationHelper {

	private PositionService positionService;

	private DepartmentService departmentService;

	private UserService userService;

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.user.service.OrgnizationHelper#getLeader(works.tonny.apps.user.model.UserInfo)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserInfo getLeader(UserInfo userInfo) {
		Position position = positionService.findById(userInfo.getPosition().getTreeNode().getParentId());
		return findLeader(position);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.user.service.OrgnizationHelper#getLeader(works.tonny.apps.user.model.UserInfo,
	 *      java.lang.String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserInfo getLeader(UserInfo user, String jobName) {
		Position position = user.getPosition();

		while (true) {
			if (position.getJobLevel().getJob().getName().equals(jobName)) {
				return findLeader(position);
			}
			if (StringUtils.isNotEmpty(position.getTreeNode().getParentId())) {
				position = positionService.findById(position.getTreeNode().getParentId());
			} else {
				break;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.user.service.OrgnizationHelper#getLeader(works.tonny.apps.user.model.UserInfo,
	 *      works.tonny.apps.user.service.DepartmentLevel)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserInfo getLeader(UserInfo userInfo, DepartmentLevel level) {
		Position position = userInfo.getPosition();
		if (StringUtils.isEmpty(position.getTreeNode().getIdLayer())) {
			return null;
		}

		String[] ids = position.getTreeNode().getIdLayer().split(",");
		for (int i = 0; i < ids.length; i++) {
			Position parent = positionService.findById(ids[i]);
			if (level.equals(DepartmentLevel.Department)) {
				return findLeader(parent);
			}
			if (level.equals(DepartmentLevel.Parent)) {
				if (!parent.getDepartment().getId().equals(position.getDepartment().getId())) {
					return findLeader(parent);
				}
			}
			if (level.equals(DepartmentLevel.BusinessUnit)
					&& parent.getDepartment().getType().equals(DepartmentType.BusinessUnit)) {
				return findLeader(parent);
			}
			if (level.equals(DepartmentLevel.BusinessUnit)
					&& org.apache.commons.lang3.StringUtils.isEmpty(parent.getDepartment().getTreeNode().getIdLayer())) {
				return findLeader(parent);
			}
		}

		return null;
	}

	/**
	 * 按岗位查找用户
	 * 
	 * @param parent 领导岗位
	 * @return 领导用户信息
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserInfo findLeader(Position parent) {
		if (parent == null) {
			return null;
		}
		PagedList<User> usersOfPosition = userService.usersOfPosition(parent.getId(), null, null, 1, 1);
		return (UserInfo) (usersOfPosition.size() > 0 ? usersOfPosition.get(0) : null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.user.service.OrgnizationHelper#getBusinessUnit(works.tonny.apps.user.model.UserInfo)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Department getBusinessUnit(UserInfo user) {
		Department department = user.getPosition().getDepartment();
		if (department.getType().equals(DepartmentType.BusinessUnit)) {
			return department;
		}
		while (!department.getType().equals(DepartmentType.BusinessUnit)) {
			department = departmentService.findById(department.getTreeNode().getParentId());
		}
		return department;
	}

	/**
	 * @return the positionService
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * @param positionService the positionService to set
	 */
	public void setPositionService(PositionService positionService) {
		this.positionService = positionService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the departmentService
	 */
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	/**
	 * @param departmentService the departmentService to set
	 */
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
}
