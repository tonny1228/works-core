package works.tonny.apps.user.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import works.tonny.apps.exception.AuthException;
import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.Role;
import works.tonny.apps.user.service.AuthEntityService;
import works.tonny.apps.user.service.RoleEntityService;

public class RoleAction extends AuthedAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8051545445242366407L;

	/**
	 * 权限服务
	 */
	protected AuthEntityService authService;

	/**
	 * 角色服务
	 */
	protected RoleEntityService roleService;

	/**
	 * 角色
	 */
	private Role role;

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 权限列表
	 * 
	 * @return
	 */
	public String list() {
		List<Role> list = roleService.list(getParameter("name"));
		request.setAttribute("list", list);
		return SUCCESS;
	}

	/**
	 * 新建资源
	 * 
	 * @return
	 */
	public String add() {
		return SUCCESS;
	}

	/**
	 * @return
	 * @throws AuthException
	 */
	public String edit() {
		role = roleService.get(id);
		request.setAttribute("role", role);
		return SUCCESS;
	}

	/**
	 * 更新角色
	 * 
	 * @return
	 */
	public String update() {
		roleService.update(role);
		return SUCCESS;
	}

	/**
	 * 更新角色
	 * 
	 * @return
	 */
	public String save() {
		roleService.create(role);
		return SUCCESS;
	}

	/**
	 * 删除权限
	 * 
	 * @return
	 */
	public String delete() {
		roleService.delete(request.getParameterValues("id"));
		return SUCCESS;
	}

	public AuthEntityService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthEntityService authService) {
		this.authService = authService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRoleService(RoleEntityService roleService) {
		this.roleService = roleService;
	}

	public RoleEntityService getRoleService() {
		return roleService;
	}

}
