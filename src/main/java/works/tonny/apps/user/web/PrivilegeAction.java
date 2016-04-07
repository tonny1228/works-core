package works.tonny.apps.user.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import works.tonny.apps.exception.AuthException;
import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.Privilege;
import works.tonny.apps.user.SystemResource;
import works.tonny.apps.user.service.AuthEntityService;

public class PrivilegeAction extends AuthedAction {

	/**
	 * 权限服务
	 */
	protected AuthEntityService authService;

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 编号
	 */
	private String name;

	/**
	 * 编号
	 */
	private String[] resourceId;

	/**
	 * 权限列表
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String list() throws AuthException {
		// authService.setAdmin(loginedUser());
		List<Privilege> list = authService.listPrivileges();
		request.setAttribute("list", list);
		return SUCCESS;
	}

	/**
	 * 新建权限
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String add() throws AuthException {
		List<SystemResource> systemResources = authService.listSystemResources();
		request.setAttribute("resource", systemResources);
		return SUCCESS;
	}

	/**
	 * 编辑权限
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String edit() throws AuthException {
		Privilege privilege = authService.getPrivilege(id);
		request.setAttribute("privilege", privilege);
		List<SystemResource> systemResources = authService.listSystemResources();
		request.setAttribute("resource", systemResources);
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String save() throws AuthException {
		if (StringUtils.isNotBlank(id)) {
			authService.updatePrivilegeResource(id, name, resourceId);
		} else {
			id = authService.addPrivilege(name, resourceId);
		}
		return SUCCESS;
	}

	/**
	 * 删除权限
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String delete() throws AuthException {
		// authService.setAdmin(loginedUser());
		authService.deletePrivilege(id.split(","));
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setResourceId(String[] resourceId) {
		this.resourceId = resourceId;
	}

	public String[] getResourceId() {
		return resourceId;
	}

}
