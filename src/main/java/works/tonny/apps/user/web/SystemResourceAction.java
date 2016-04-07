package works.tonny.apps.user.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;

import works.tonny.apps.exception.AuthException;
import works.tonny.apps.exception.DataException;
import works.tonny.apps.support.EntityLazyProxy;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.SystemResource;
import works.tonny.apps.user.UserService;
import works.tonny.apps.user.service.AuthEntityService;

public class SystemResourceAction extends AuthedAction {
	/**
	 * 权限服务
	 */
	protected AuthEntityService authService;

	/**
	 * 用户服务
	 */
	protected UserService userService;

	/**
	 * 系统资源
	 */
	protected SystemResource resource;

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 资源列表
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String list() throws AuthException {
		List<SystemResource> list = authService.rootSystemResources();
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String add() throws AuthException {
		// authService.setAdmin(loginedUser());
		String parentId = getParameter("parentId");
		if (StringUtils.isNotEmpty(parentId)) {
			SystemResource systemResource = authService.getSystemResource(parentId);
			// EntityLazyProxy proxy =
			// ServiceManager.lookup(ServiceManager.LAZY_SERVICE);
			// proxy.refresh(systemResource.getSubs());
			request.setAttribute("parent", systemResource);
		}
		return SUCCESS;
	}

	/**
	 * 新建资源
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String save() throws AuthException {
		try {
			authService.addSystemResource(resource);
		} catch (DataIntegrityViolationException e) {
			throw new DataException("已有错编号的数据");
		}
		if (resource.getParent() != null) {
			return "parent";
		}
		return SUCCESS;
	}

	/**
	 * 编辑资源
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String edit() throws AuthException {
		// authService.setAdmin(loginedUser());
		resource = authService.getSystemResource(id);
		EntityLazyProxy proxy = ServiceManager.lookup(ServiceManager.LAZY_SERVICE);
		proxy.refresh(resource.getSubs());
		// System.out.println(proxy.lookup(resource, "subs"));
		// request.setAttribute("resource", resource);
		return SUCCESS;
	}

	/**
	 * 新建资源
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String update() throws AuthException {
		authService.updateSystemResource(resource);
		if (resource.getParent() != null) {
			return "parent";
		}
		return SUCCESS;
	}

	/**
	 * 新建资源
	 * 
	 * @return
	 * @throws AuthException
	 */
	public String delete() throws AuthException {
		// authService.setAdmin(loginedUser());
		authService.deleteSystemResource(request.getParameterValues("id"));
		if (StringUtils.isNotEmpty(getParameter("parentId"))) {
			id = getParameter("parentId");
			return "parent";
		}
		return SUCCESS;
	}

	public AuthEntityService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthEntityService authService) {
		this.authService = authService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SystemResource getResource() {
		return resource;
	}

	public void setResource(SystemResource resource) {
		this.resource = resource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
