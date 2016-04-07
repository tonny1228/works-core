package works.tonny.apps.user.service;

import java.util.EventListener;

import works.tonny.apps.user.Role;

/**
 * 角色更新监听器，在新建角色、角色信息更新、角色删除后进行数据初始化
 * 
 * @author tonny
 */
@Deprecated
public interface RoleEntityListener extends EventListener {
	/**
	 * 角色创建监听
	 * 
	 * @param role 角色
	 */
	void roleSaved(Role role);

	/**
	 * 角色更新监听
	 * 
	 * @param role 角色
	 */
	void roleUpdated(Role role);

	/**
	 * 角色删除监听
	 * 
	 * @param role 角色
	 */
	void roleDeleted(Role role);
}
