/**
 * 
 */
package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.support.SQLMappingListSupport;
import works.tonny.apps.user.SystemResource;

/**
 * 
 * @author 祥栋
 * @date 2012-11-16
 * @version 1.0.0
 */
public interface SystemResourceDAO extends EntityDAO<SystemResource> {
	/**
	 * 列出所有的系统资源
	 * 
	 * @return
	 */
	@SQLMappingListSupport(name="SystemResource.list")
	List<SystemResource> list();

	/**
	 * @return
	 */
	@ListSupport(defaults = { "parent.id is null" }, order = "api")
	List<SystemResource> root();

}
