/**
 * 
 */
package works.tonny.apps.user.service;

import java.util.List;

import works.tonny.apps.user.User;
import works.tonny.apps.user.UserService;
import works.tonny.apps.user.model.UserInfo;

/**
 * 用户实体服务接口
 * 
 * @author tonny
 * @date 2012-8-22
 * @version 1.0.0
 */
public interface UserEntityService extends UserService {
	/**
	 * 添加用户
	 * 
	 * @param user 用户
	 * @param privilegeId 用户权限
	 * @param roleId 用户角色
	 * @return 用户id
	 */
	String create(User user);

	/**
	 * 添加用户
	 * 
	 * @param user 用户
	 * @param privilegeId 用户权限
	 * @param roleId 用户角色
	 * @return 用户id
	 */
	String create(UserInfo user, String positionId);

	/**
	 * 添加用户
	 * 
	 * @param user 用户
	 * @param privilegeId 用户权限
	 * @param roleId 用户角色
	 * @return 用户id
	 */
	void addUserToPosition(String userId, String positionId);

	/**
	 * 添加用户
	 * 
	 * @param user 用户
	 * @param privilegeId 用户权限
	 * @param roleId 用户角色
	 * @return 用户id
	 */
	void removeUserFromPosition(String userId, String positionId);

	/**
	 * 添加用户
	 * 
	 * @param user 用户
	 * @param privilegeId 用户权限
	 * @param roleId 用户角色
	 * @return 用户id
	 */
	void move(String userId, String positionId);

	/**
	 * 更新用户信息
	 * 
	 * @param user 用户
	 */
	void update(User user);

	/**
	 * 添加用户角色信息
	 * 
	 * @param userId 用户编号
	 * @param roleId 角色编号
	 */
	void updateRole(String userId, String[] roleId);

	/**
	 * 添加用户
	 * 
	 * @param id 用户编号
	 */
	void delete(String[] id);

	/**
	 * 锁定用户
	 * 
	 * @param id 用户
	 */
	void lock(String id);

	/**
	 * 解锁用户
	 * 
	 * @param id 用户
	 */
	void unlock(String id);

	/**
	 * 更新用户组织机构 信息
	 * 
	 * @param id
	 * @param parameter
	 * @param positionId
	 * @param parameterValues
	 */
	void updateUserinfo(String id, String title, String positionId, String... position);

	/**
	 * 系统用户
	 * 
	 * @return
	 */
	List<User> sysUsers();

	/**
	 * 注册用户更新监听
	 * 
	 * @param name 监听的名称
	 * @param listener 监听器
	 */
//	void registerListener(String name, UserUpdateListener listener);
//
//	/**
//	 * 删除监听器
//	 * 
//	 * @param name 监听器名称
//	 */
//	void removeListener(String name);
}
