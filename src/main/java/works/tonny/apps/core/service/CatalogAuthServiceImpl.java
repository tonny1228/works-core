/**  
 * @Title: CatalogServiceImpl.java
 * @Package works.tonny.cms.service
 * @author Tonny
 * @date 2012-5-16 下午7:54:31
 */
package works.tonny.apps.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.CatalogAuth;
import works.tonny.apps.core.CatalogAuthService;
import works.tonny.apps.core.dao.CatalogAuthDAO;
import works.tonny.apps.core.dao.CatalogDAO;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.Role;
import works.tonny.apps.user.RoleService;
import works.tonny.apps.user.User;

/**
 * @ClassName: CatalogServiceImpl
 * @Description:
 * @author Tonny
 * @date 2012-5-16 下午7:54:31
 * @version 1.0
 */
public class CatalogAuthServiceImpl extends AuthedAbstractService implements CatalogAuthService {

	private CatalogAuthDAO catalogAuthDAO;

	private CatalogDAO catalogDAO;

	/**
	 * @see com.zxtx.apps.core.CatalogAuthService#catalogAuth(java.lang.String,
	 *      com.zxtx.apps.user.User)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CatalogAuth catalogAuth(String catalogId, User user) {
		Catalog catalog = catalogDAO.get(catalogId);
		Set<Role> roles = user.getRoles();
		CatalogAuth auth = new CatalogAuth();

		for (Role role : roles) {
			List<CatalogAuth> auths = catalogAuthDAO.findCatalogAuthByRole(catalog.getTreeNode().getIdLayer(),
					role.getId());
			if (auths == null || auths.size() == 0) {
				continue;
			}
			CatalogAuth catalogAuth = auths.get(0);
			auth.setEdit(auth.isEdit() || catalogAuth.isEdit());
			auth.setRead(auth.isRead() || catalogAuth.isRead());
			auth.setRemove(auth.isRemove() || catalogAuth.isRemove());
		}
		// Set<Position> positions = user.getPositions();
		// for (Position position : positions) {
		// List<CatalogAuth> auths =
		// catalogAuthDAO.findCatalogAuthByPosition(catalog.getTreeNode().getIdLayer(),
		// position.getId());
		// if (auths == null || auths.size() == 0) {
		// continue;
		// }
		// CatalogAuth catalogAuth = auths.get(0);
		// auth.setEdit(auth.isEdit() || catalogAuth.isEdit());
		// auth.setRead(auth.isRead() || catalogAuth.isRead());
		// auth.setRemove(auth.isRemove() || catalogAuth.isRemove());
		// }
		return auth;
	}

	/**
	 * @see com.zxtx.apps.core.CatalogAuthService#catalogAuth(java.lang.String,
	 *      com.zxtx.apps.user.User)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CatalogAuth> subSatalogAuth(String catalogId, User user) {
		Catalog catalog = catalogDAO.get(catalogId);
		Set<Role> roles = user.getRoles();
		List<CatalogAuth> catalogAuths = new ArrayList<CatalogAuth>();
		for (Role role : roles) {
			List<CatalogAuth> auths = catalogAuthDAO.subCatalogAuthByRole(catalog.getTreeNode().getIdLayer(),
					role.getId());
			if (auths == null || auths.size() == 0) {
				continue;
			}
			catalogAuths.addAll(auths);
		}
		// Set<Position> positions = user.getPositions();
		// for (Position position : positions) {
		// List<CatalogAuth> auths =
		// catalogAuthDAO.subCatalogAuthByPosition(catalog.getTreeNode().getIdLayer(),
		// position.getId());
		// if (auths == null || auths.size() == 0) {
		// continue;
		// }
		// catalogAuths.addAll(auths);
		// }
		return catalogAuths;
	}

	/**
	 * @see com.zxtx.apps.core.CatalogAuthService#updateCatalogAuth(com.zxtx.apps.core.CatalogAuth,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateCatalogAuth(CatalogAuth catalogAuth, String catalogId, String roleId, String positionId) {

		RoleService roleService = ServiceManager.lookup(ServiceManager.ROLE_SERVICE);
		if (StringUtils.isNotEmpty(roleId)) {
			CatalogAuth auth = catalogAuthDAO.getCatalogAuthByRole(catalogId, roleId);
			if (auth != null) {
				catalogAuth.setId(auth.getId());
				PropertiesUtils.copyProperties(auth, catalogAuth, "extend", "read", "edit", "remove", "verify");
				catalogAuth = auth;
			}

			catalogAuth.setRole(roleService.get(roleId));
		}
		// PositionService positionService =
		// ServiceManager.lookup(ServiceManager.POSITION_SERVICE);
		// if (StringUtils.isNotEmpty(positionId)) {
		// CatalogAuth auth = catalogAuthDAO.getCatalogAuthByPosition(catalogId,
		// positionId);
		// if (auth != null) {
		// catalogAuth.setId(auth.getId());
		// PropertiesUtils.copyProperties(auth, catalogAuth, "extend", "read",
		// "edit", "remove");
		// catalogAuth = auth;
		// }
		// catalogAuth.setPosition(positionService.findById(positionId));
		// }

		Catalog catalog = catalogDAO.get(catalogId);
		catalogAuth.setCatalog(catalog);
		if (catalogAuth.isExtend()) {
			catalogAuth.setCatalogLayer(catalog.getTreeNode().getIdLayer() + "%");
		} else {
			catalogAuth.setCatalogLayer(catalog.getTreeNode().getIdLayer());
		}
		save(catalogAuth);
	}

	/**
	 * @see com.zxtx.apps.core.CatalogAuthService#removeCatalogAuth(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void removeCatalogAuth(String catalogId, String roleId, String positionId, boolean extend) {
		Catalog catalog = catalogDAO.get(catalogId);
		if (StringUtils.isNotEmpty(roleId)) {
			List<CatalogAuth> auths = catalogAuthDAO.findCatalogAuthByRole(catalog.getTreeNode().getIdLayer(), roleId);
			if (auths == null || auths.size() == 0) {
				return;
			}
			removeOrClearAuth(extend, catalog, auths);
			if (extend) {
				List<CatalogAuth> subAuths = catalogAuthDAO.subCatalogAuthByRole(catalog.getTreeNode().getIdLayer(),
						roleId);
				if (subAuths.size() > 1 && subAuths.get(0).getCatalogLayer().equals(catalog.getTreeNode().getIdLayer())) {
					subAuths.remove(subAuths.size() - 1);
				}
				catalogAuthDAO.deleteAll(subAuths);
			}
		}
		if (StringUtils.isNotEmpty(positionId)) {
			List<CatalogAuth> auths = catalogAuthDAO.findCatalogAuthByPosition(catalog.getTreeNode().getIdLayer(),
					positionId);
			removeOrClearAuth(extend, catalog, auths);
			if (extend) {
				List<CatalogAuth> subAuths = catalogAuthDAO.subCatalogAuthByPosition(
						catalog.getTreeNode().getIdLayer(), positionId);
				if (subAuths.size() > 1 && subAuths.get(0).getCatalogLayer().equals(catalog.getTreeNode().getIdLayer())) {
					subAuths.remove(subAuths.size() - 1);
				}
				catalogAuthDAO.deleteAll(subAuths);
			}
		}

	}

	/**
	 * @param extend
	 * @param catalog
	 * @param auths
	 */
	@Transactional(rollbackFor = Exception.class)
	private void removeOrClearAuth(boolean extend, Catalog catalog, List<CatalogAuth> auths) {
		if (auths.size() > 1 && auths.get(0).getCatalogLayer().equals(catalog.getTreeNode().getIdLayer())) {
			catalogAuthDAO.delete(auths.get(0));
		} else {
			CatalogAuth auth = new CatalogAuth();
			auth.setCatalog(catalog);
			auth.setExtend(extend);
			auth.setCatalogLayer(catalog.getTreeNode().getIdLayer() + (extend ? "%" : ""));
			auth.setEdit(false);
			auth.setRead(false);
			auth.setRemove(false);
			save(auth);
		}
	}

	/**
	 * @param auth
	 */
	private void save(CatalogAuth auth) {
		LoginedUser admin = getLoginedUser();

		auth.setUpdateTime(new Date());
		auth.setOperator(admin.getUser().getUsername());
		if (StringUtils.isEmpty(auth.getId())) {
			catalogAuthDAO.save(auth);
		} else {
			catalogAuthDAO.update(auth);
		}
	}

	/**
	 * @see com.zxtx.apps.core.CatalogAuthService#catalogAuths(java.lang.String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CatalogAuth> catalogAuths(String catalogId) {
		Catalog catalog = catalogDAO.get(catalogId);
		List<CatalogAuth> auths = catalogAuthDAO.findCatalogAuths(catalog.getTreeNode().getIdLayer());
		List<CatalogAuth> itsAuth = new ArrayList<CatalogAuth>();
		String temp = null;
		for (CatalogAuth catalogAuth : auths) {
			// String id = (catalogAuth.getRole() != null ?
			// catalogAuth.getRole().getId() : null)
			// + (catalogAuth.getPosition() != null ?
			// catalogAuth.getPosition().getId() : null);
			String id = catalogAuth.getRole() != null ? catalogAuth.getRole().getId() : null;
			if (temp != null && temp.startsWith(id)) {
				continue;
			}
			itsAuth.add(catalogAuth);
			temp = id;
		}
		return itsAuth;
	}

	/**
	 * @see com.zxtx.apps.core.CatalogAuthService#removeCatalogAuth(java.lang.String)
	 */
	public void removeCatalogAuth(String catalogAuthId) {
		catalogAuthDAO.delete(catalogAuthDAO.get(catalogAuthId));
	}

	public CatalogAuthDAO getCatalogAuthDAO() {
		return catalogAuthDAO;
	}

	public void setCatalogAuthDAO(CatalogAuthDAO catalogAuthDAO) {
		this.catalogAuthDAO = catalogAuthDAO;
	}

	public CatalogDAO getCatalogDAO() {
		return catalogDAO;
	}

	public void setCatalogDAO(CatalogDAO catalogDAO) {
		this.catalogDAO = catalogDAO;
	}
}
