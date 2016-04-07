/**
 * 
 */
package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.model.PositionPrivilege;

/**
 * 
 * @author 祥栋
 * @date 2012-11-16
 * @version 1.0.0
 */
public interface PositionPrivilegeDAO extends EntityDAO<PositionPrivilege> {
	/**
	 * 查询用户权限
	 * 
	 * @Title: getPositionPrivileges
	 * @param positionId 职位编号
	 * @return 岗位权限
	 * @date 2011-11-9 下午8:37:58
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = "position.id")
	List<PositionPrivilege> getPositionPrivileges(String positionId);

}
