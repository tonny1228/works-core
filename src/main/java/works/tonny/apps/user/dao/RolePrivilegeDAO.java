/**
 * 
 */
package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.model.RolePrivilege;

/**
 * 
 * @author 祥栋
 * @date 2012-11-16
 * @version 1.0.0
 */
public interface RolePrivilegeDAO extends EntityDAO<RolePrivilege> {
	/**
	 * 查询用户权限
	 * 
	 * @Title: getRolePrivileges
	 * @param roleId 角色编号
	 * @return 角色权限
	 * @date 2011-11-9 下午8:37:58
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = "role.id")
	List<RolePrivilege> getRolePrivileges(String roleId);

}
