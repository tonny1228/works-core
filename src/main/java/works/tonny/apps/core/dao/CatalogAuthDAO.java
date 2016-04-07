/**  
 * @Title: CatalogAuthDAO.java
 * @Package works.tonny.cms.dao
 * @author Tonny
 * @date 2012-5-16 下午7:56:22
 */
package works.tonny.apps.core.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.CatalogAuth;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.support.SQLMappingListSupport;


/**
 * @ClassName: CatalogAuthDAO
 * @Description:
 * @author Tonny
 * @date 2012-5-16 下午7:56:22
 * @version 1.0
 */
public interface CatalogAuthDAO extends EntityDAO<CatalogAuth> {

	/**
	 * @param layer
	 * @param id
	 * @return
	 */
	@SQLMappingListSupport(name = "CatalogAuth.list", appender = { "parent", "role", "order" })
	List<CatalogAuth> findCatalogAuthByRole(String layer, String roleId);

	/**
	 * @param layer
	 * @param id
	 * @return
	 */
	@SQLMappingListSupport(name = "CatalogAuth.list", appender = { "parent", "position", "order" })
	List<CatalogAuth> findCatalogAuthByPosition(String layer, String positionId);

	/**
	 * @param layer
	 * @param id
	 * @return
	 */
	@ListSupport(field = { "catalogLayer", "role.id" }, operator = { ListSupport.RLIKE, ListSupport.EQUALS }, order = {
			"role.id", "catalogLayer", "catalog.treeNode.orderNo", "updateTime desc" })
	List<CatalogAuth> subCatalogAuthByRole(String layer, String roleId);

	/**
	 * @param layer
	 * @param id
	 * @return
	 */
	@ListSupport(field = { "catalog.id", "role.id" }, operator = { ListSupport.EQUALS, ListSupport.EQUALS })
	CatalogAuth getCatalogAuthByRole(String catalogId, String roleId);

	/**
	 * @param layer
	 * @param id
	 * @return
	 */
	@ListSupport(field = { "catalog.id", "position.id" }, operator = { ListSupport.EQUALS, ListSupport.EQUALS })
	CatalogAuth getCatalogAuthByPosition(String catalogId, String positionId);

	/**
	 * @param layer
	 * @param id
	 * @return
	 */
	@ListSupport(field = { "catalogLayer", "position.id" }, operator = { ListSupport.RLIKE, ListSupport.EQUALS }, order = {
			"role.id", "catalogLayer", "catalog.treeNode.orderNo", "updateTime desc" })
	List<CatalogAuth> subCatalogAuthByPosition(String layer, String positionId);

	/**
	 * @param layer
	 * @return
	 */
	@SQLMappingListSupport(name = "CatalogAuth.list", appender = { "parent", "orderuser" })
	List<CatalogAuth> findCatalogAuths(String layer);

}
