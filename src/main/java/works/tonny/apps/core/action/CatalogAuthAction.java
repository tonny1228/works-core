package works.tonny.apps.core.action;

// Generated 2012-5-5 11:21:00 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.CatalogAuth;
import works.tonny.apps.core.CatalogAuthService;
import works.tonny.apps.core.CatalogService;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.Role;
import works.tonny.apps.user.RoleService;


/**
 * <p>
 * Struts2 authed action for domain model class Auth.
 * </p>
 * <p>
 * Catalog管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see works.tonny.elearning.entity.Auth
 * @see works.tonny.user.zxtx.apps.web.AuthedAction
 * @author Tonny Liu
 * @version 1.0.0
 */
public class CatalogAuthAction extends AuthedAction {
	private String catalogId;

	private String id;

	private String roleId;

	private String positionId;

	private CatalogService catalogService;

	private CatalogAuthService catalogAuthService;

	private CatalogAuth catalogAuth;

	public String update() {
		catalogAuthService.updateCatalogAuth(catalogAuth, catalogId, roleId, positionId);
		return SUCCESS;
	}

	public String delete() {
		catalogAuthService.removeCatalogAuth(id);
		return SUCCESS;
	}

	public String addAuth() {
		Catalog catalog = catalogService.get(catalogId);
		request.setAttribute("catalog", catalog);
		RoleService roleService = ServiceManager.lookup(ServiceManager.ROLE_SERVICE);
		List<Role> roles = roleService.list();
		request.setAttribute("roles", roles);
		return SUCCESS;
	}

	public String list() {
		List<CatalogAuth> auths = catalogAuthService.catalogAuths(catalogId);
		request.setAttribute("list", auths);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public CatalogAuthService getCatalogAuthService() {
		return catalogAuthService;
	}

	public void setCatalogAuthService(CatalogAuthService catalogAuthService) {
		this.catalogAuthService = catalogAuthService;
	}

	public CatalogAuth getCatalogAuth() {
		return catalogAuth;
	}

	public void setCatalogAuth(CatalogAuth catalogAuth) {
		this.catalogAuth = catalogAuth;
	}

}
