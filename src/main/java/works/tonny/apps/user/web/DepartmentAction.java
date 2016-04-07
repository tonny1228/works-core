/**  
 * @Title: DepartmentAction.java
 * @Package works.tonny.user.web
 * @author Tonny
 * @date 2011-11-16 上午10:27:37
 */
package works.tonny.apps.user.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PagedList;

import works.tonny.apps.exception.DataException;
import works.tonny.apps.user.AbstractUser;
import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.User;
import works.tonny.apps.user.UserService;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.DepartmentType;
import works.tonny.apps.user.model.Position;
import works.tonny.apps.user.model.UserInfo;
import works.tonny.apps.user.service.DepartmentEntityService;
import works.tonny.apps.user.service.OrgnizationHelper;
import works.tonny.apps.user.service.PositionEntityService;

/**
 * 部门管理
 * 
 * @ClassName: DepartmentAction
 * @Description:
 * @author Tonny
 * @date 2011-11-16 上午10:27:37
 * @version 1.1
 */
public class DepartmentAction extends AuthedAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected DepartmentEntityService departmentService;

	protected PositionEntityService positionService;

	protected UserService userService;

	protected OrgnizationHelper orgnizationHelper;

	private Department department;

	private String id;

	private String type;

	private String scopeId;

	/**
	 * 回调方法
	 */
	private String callback;

	private String select;

	private String scope;

	private String name;

	public String root() {
		if (StringUtils.isEmpty(id) && StringUtils.isNotEmpty(scopeId)) {
			List<Department> subs = new ArrayList<Department>();
			subs.add(departmentService.findById(scopeId));
			request.setAttribute("list", subs);
		} else if (StringUtils.isEmpty(id)) {
			List<Department> departments = departmentService.root();
			request.setAttribute("list", departments);
		} else {
			List<Department> subs = departmentService.getSubs(id);
			List<Position> pos = positionService.getPositions(id);
			request.setAttribute("list", subs);
			request.setAttribute("positions", pos);
		}
		return SUCCESS;
	}

	public String search() {
		List<Department> depts = departmentService.findByName(name);
		return SUCCESS;
	}

	public String add() {
		if (StringUtils.isNotBlank(id)) {
			Department department = departmentService.findById(id);
			request.setAttribute("parent", department);
		} else {
			List<Department> root = departmentService.root();
			if (root.size() > 0) {
				request.setAttribute("parent", root.get(0));
			}
		}
		return SUCCESS;
	}

	public String edit() {
		department = departmentService.findById(id);
		return SUCCESS;
	}

	public String update() {
		String parent = request.getParameter("parentId");

		Map<String, String> type = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(department.getProperties())) {
			String[] types = department.getProperties().split(", ");
			for (String string : types) {
				type.put(string, getParameter(string + "_deptId"));
			}
		}

		if (StringUtils.isNotBlank(department.getId()) && departmentService.findById(department.getId()) != null) {
			departmentService.update(department, type);
		} else if (StringUtils.isNotBlank(parent)) {
			departmentService.create(department, parent, type);
		} else {
			departmentService.create(department);
		}
		return SUCCESS;
	}

	public String save() {
		String parent = request.getParameter("parentId");
		Map<String, String> type = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(department.getProperties())) {
			String[] types = department.getProperties().split(", ");
			for (String string : types) {
				type.put(string, getParameter(string + "_deptId"));
			}
		}

		if (StringUtils.isNotBlank(parent)) {
			departmentService.create(department, parent, type);
		} else {
			departmentService.create(department);
		}
		return SUCCESS;
	}

	public String delete() {

		departmentService.remove(id);
		return SUCCESS;
	}

	/**
	 * 查询组织下所有的用户
	 * 
	 * @return
	 */
	public String listUser() {
		if (type != null
				&& (type.toString().equals(DepartmentType.Department.toString())
						|| type.toString().equals(DepartmentType.Organization.toString()) || type.toString().equals(
						DepartmentType.BusinessUnit.toString()))) {
			PagedList<User> users = userService.usersOfDepartment(id,
					StringUtils.stripToEmpty(request.getParameter("name")), getPage(), getPagesize());
			request.setAttribute("list", users);
		} else if (type != null && type.toString().equals("Position")) {
			PagedList<User> users = userService.usersOfPosition(id, null, request.getParameter("name"), getPage(),
					getPagesize());
			request.setAttribute("list", users);
		} else {
			PagedList<User> users = userService.list(request.getParameter("name"), request.getParameter("username"),
					getPage(), getPagesize());
			request.setAttribute("list", users);
		}
		return SUCCESS;
	}

	/**
	 * 用户选人树
	 * 
	 * @return
	 */
	public String tree() {
		// if ("u".equals(type) && StringUtils.isEmpty(scope)) {
		// return "userlist";
		// }
		if (StringUtils.isEmpty(callback)) {
			callback = "callback";
		}
		if (StringUtils.isEmpty(type)) {
			type = "obdpu";
		}
		if (StringUtils.isEmpty(select) || !select.equals("checkbox")) {
			select = "radio";
		}

		if (StringUtils.isEmpty(scope) || (!scope.equals("o") && !scope.equals("b"))) {
			scope = "d";
		}

		AbstractUser user = loginedUser().getUser();
		if ("admin".equals(user.getUsername())) {
			return SUCCESS;
		}
		if (!(user instanceof UserInfo)) {
			throw new DataException("您不是组织内用户");
		}

		UserInfo u = (UserInfo) user;

		// d本部门 b本管理单元o本机构
		if ("d".equals(scope)) {
			id = u.getPosition().getDepartment().getId();
		}

		if ("b".equals(scope)) {
			Department dept = orgnizationHelper.getBusinessUnit(u);
			id = dept.getId();
		}

		if ("o".equals(scope)) {
			id = "";// u.getPosition().getDepartment().getRootId();
		}

		String posName = getParameter("posName");
		if (StringUtils.isNotEmpty(posName)) {
			Position pos = positionService.findByName(posName, null);
			if (pos != null) {
				id = pos.getDepartment().getId();
				request.setAttribute("position", pos.getId());
			}
		}

		return SUCCESS;
	}

	public String move() {
		departmentService.move(id, getParameter("parentId"));
		return SUCCESS;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Department getDepartment() {
		return department;
	}

	public void setPositionService(PositionEntityService positionService) {
		this.positionService = positionService;
	}

	public String getId() {
		return id;
	}

	public DepartmentEntityService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentEntityService departmentService) {
		this.departmentService = departmentService;
	}

	public PositionEntityService getPositionService() {
		return positionService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * @param callback
	 *            the callback to set
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * @return the select
	 */
	public String getSelect() {
		return select;
	}

	/**
	 * @param select
	 *            the select to set
	 */
	public void setSelect(String select) {
		this.select = select;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the orgnizationHelper
	 */
	public OrgnizationHelper getOrgnizationHelper() {
		return orgnizationHelper;
	}

	/**
	 * @param orgnizationHelper
	 *            the orgnizationHelper to set
	 */
	public void setOrgnizationHelper(OrgnizationHelper orgnizationHelper) {
		this.orgnizationHelper = orgnizationHelper;
	}

	/**
	 * @return the scopeId
	 */
	public String getScopeId() {
		return scopeId;
	}

	/**
	 * @param scopeId
	 *            the scopeId to set
	 */
	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
