/**
 * 
 */
package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.model.UserPrivilege;

/**
 * @author 祥栋
 * @date 2012-11-16
 * @version 1.0.0
 */
public interface UserPrivilegeDAO extends EntityDAO<UserPrivilege> {
	/**
	 * 查询用户权限
	 * 
	 * @Title: getUserPrivileges
	 * @param userId 用户编号
	 * @return 用户权限
	 * @date 2011-11-9 下午8:37:58
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = "user.id")
	List<UserPrivilege> getUserPrivileges(String userId);

}
