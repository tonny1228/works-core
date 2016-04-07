/**  
 * @Title: RoleDAO.java
 * @Package works.tonny.user.dao.iface
 * @author Tonny
 * @date 2011-10-12 上午9:29:44
 */
package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.Role;

/**
 * @ClassName: RoleDAO
 * @Description:角色存储、查询服务
 * @author Tonny
 * @date 2011-10-12 上午9:29:44
 * @version 1.0
 */
public interface RoleDAO extends EntityDAO<Role> {
	/**
	 * 根据角色名字查询角色列表
	 * 
	 * @param name 名字
	 * @return 角色列表
	 */
	@ListSupport(field = { "name" })
	List<Role> list(String name);
}
