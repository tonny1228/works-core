package works.tonny.apps.user.web;

import java.util.List;

import works.tonny.apps.exception.AuthException;
import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.Privilege;
import works.tonny.apps.user.UserService;
import works.tonny.apps.user.service.AuthEntityService;
import works.tonny.apps.user.service.RoleEntityService;

public class AuthAction extends AuthedAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8051545445242366407L;

	/**
	 * 权限服务
	 */
	protected AuthEntityService authService;

	/**
	 * 用户服务
	 */
	protected UserService userService;

	/**
	 * 角色服务
	 */
	protected RoleEntityService roleService;

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 权限编号
	 */
	private String[] privilegeId;

	/**
	 * 权限列表
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String userPrivilege() throws AuthException {

		List<Privilege> all = authService.listPrivileges();

		request.setAttribute("all", all);
		if ("user".equals(type)) {
			List<Privilege> list = authService.userPrivilege(id);
			request.setAttribute("list", list);
			request.setAttribute("user", userService.get(id));
		} else if ("role".equals(type)) {
			List<Privilege> list = authService.rolePrivilege(id);
			request.setAttribute("list", list);
			request.setAttribute("role", roleService.get(id));
		}
		return SUCCESS;
	}

	/**
	 * 更新用户权限
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String updateUserPrivilege() throws AuthException {
		if ("user".equals(type)) {
			authService.updateUserPrivilege(id, privilegeId);
		} else if ("role".equals(type)) {
			authService.updateRolePrivilege(id, privilegeId);
		}
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

	public String[] getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String[] privilegeId) {
		this.privilegeId = privilegeId;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleEntityService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleEntityService roleService) {
		this.roleService = roleService;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
