/**
 * 
 */
package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.model.BusinessUnit;

/**
 * @author 祥栋
 * @date 2012-12-7
 * @version 1.0.0
 */
public interface BusinessUnitDAO extends EntityDAO<BusinessUnit> {
	/**
	 * 按类型查找部门配置
	 * 
	 * @param type 业务类型
	 * @return
	 */
	@ListSupport(field = { "dept.id", "type" })
	BusinessUnit get(String id, String type);

	/**
	 * 按类型查找部门配置
	 * 
	 * @param type 业务类型
	 * @return
	 */
	@ListSupport(field = { "dept.id" })
	List<BusinessUnit> find(String deptId);

	/**
	 * 按类型查找部门配置
	 * 
	 * @param type 业务类型
	 * @return
	 */
	@ListSupport(field = { "idLayer", "type" }, operator = { ListSupport.RLIKE, ListSupport.EQUALS })
	List<BusinessUnit> subs(String idLayer, String type);
}
