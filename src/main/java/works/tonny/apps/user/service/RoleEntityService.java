package works.tonny.apps.user.service;

import works.tonny.apps.user.Role;
import works.tonny.apps.user.RoleService;

/**
 * 角色服务接口
 * 
 * @author tonny
 */
public interface RoleEntityService extends RoleService {

	/**
	 * 添加角色
	 * 
	 * @param role 角色
	 * @return 角色id
	 */
	String create(Role role);

	/**
	 * 更新角色信息
	 * 
	 * @param role 角色
	 */
	void update(Role role);

	/**
	 * 添加角色
	 * 
	 * @param id 角色编号
	 */
	void delete(String[] id);

	/**
	 * 注册用户更新监听
	 * 
	 * @param name 监听的名称
	 * @param listener 监听器
	 */
//	void registerListener(String name, RoleEntityListener listener);

	/**
	 * 删除监听器
	 * 
	 * @param name 监听器名称
	 */
//	void removeListener(String name);
}
