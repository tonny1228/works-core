/**  
 * @Title: CatalogAuthService.java
 * @Package works.tonny.cms.service
 * @author Tonny
 * @date 2012-5-16 下午7:49:34
 */
package works.tonny.apps.core;

import java.util.List;

import works.tonny.apps.user.User;


/**
 * @ClassName: CatalogAuthService
 * @Description:
 * @author Tonny
 * @date 2012-5-16 下午7:49:34
 * @version 1.0
 */
public interface CatalogAuthService {
	/**
	 * 用户权限
	 * 
	 * @param catalogId
	 * @param user
	 * @return
	 */
	CatalogAuth catalogAuth(String catalogId, User user);

	/**
	 * 用户所有子栏目权限
	 * 
	 * @param catalogId 父栏目id
	 * @param user 用户
	 * @return
	 */
	List<CatalogAuth> subSatalogAuth(String catalogId, User user);

	/**
	 * 用户权限
	 * 
	 * @param catalogId
	 * @param user
	 * @return
	 */
	List<CatalogAuth> catalogAuths(String catalogId);

	/**
	 * 更新用户权限
	 * 
	 * @param catalogAuth
	 * @param positionId2
	 */
	void updateCatalogAuth(CatalogAuth catalogAuth, String catalogId, String roleId, String positionId);

	/**
	 * 
	 * @param catalogId
	 * @param roleId
	 * @param positionId
	 */
	void removeCatalogAuth(String catalogId, String roleId, String positionId, boolean extend);

	/**
	 * 
	 * @param catalogId
	 * @param roleId
	 * @param positionId
	 */
	void removeCatalogAuth(String catalogAuthId);

}
