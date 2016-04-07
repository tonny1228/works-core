package works.tonny.apps.user;

import java.util.List;

import works.tonny.apps.user.Role;

/**
 * 角色服务接口
 * 
 * @author tonny
 * 
 */
public interface RoleService {

	/**
	 * 根据角色名字查询角色列表
	 * 
	 * @param name 名字
	 * @return 角色列表
	 */
	List<Role> list(String name);

	/**
	 * 获得角色信息
	 * 
	 * @param id
	 * @return 角色信息
	 */
	Role get(String id);

	/**
	 * 所有的角色
	 * 
	 * @return
	 */
	List<Role> list();
}
