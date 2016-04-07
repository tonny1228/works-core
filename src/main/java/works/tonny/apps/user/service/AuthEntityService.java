package works.tonny.apps.user.service;

import java.util.List;

import works.tonny.apps.user.AuthService;
import works.tonny.apps.user.Privilege;
import works.tonny.apps.user.SystemResource;

/**
 * 
 * @author 祥栋
 * @date 2012-11-27
 * @version 1.0.0
 */
public interface AuthEntityService extends AuthService {

	/**
	 * 新建权限资源
	 * 
	 * @param resource 权限资源
	 * @return 资源编号
	 */
	String addSystemResource(SystemResource resource);

	/**
	 * 读取权限资源
	 * 
	 * @param id 资源编号
	 * @return 资源
	 */
	SystemResource getSystemResource(String id);

	/**
	 * 更新权限资源
	 * 
	 * @param resource 权限资源
	 */
	void updateSystemResource(SystemResource resource);

	/**
	 * 删除权限资源
	 * 
	 * @param id 权限编号
	 */
	void deleteSystemResource(String[] id);

	/**
	 * 新建权限
	 * 
	 * @param privilege 权限
	 * @return 权限编号
	 */
	String addPrivilege(String name, String[] resourceId);

	/**
	 * 获取权限信息
	 * 
	 * @param id 权限编号
	 */
	Privilege getPrivilege(String id);

	/**
	 * 更新权限、权限与资源对应关系
	 * 
	 * @param privilegeId 权限编号
	 * @param name 名称
	 * @param resourceIds 资源编号
	 */
	void updatePrivilegeResource(String privilegeId, String name, String[] resourceIds);

	/**
	 * 删除权限
	 * 
	 * @param id 权限编号
	 */
	void deletePrivilege(String[] id);

	/**
	 * 更新用户权限
	 * 
	 * @param userId 用户编号
	 * @param pgeIds 权限编号数组
	 */
	void updateUserPrivilege(String userId, String[] pgeIds);

	/**
	 * 更新用户权限
	 * 
	 * @param roleId 角色编号
	 * @param pgeIds 权限编号数组
	 */
	void updateRolePrivilege(String roleId, String[] pgeIds);

	/**
	 * 列出所有系统资源
	 * 
	 * @return
	 */
	List<SystemResource> listSystemResources();

	/**
	 * 列出所有系统资源
	 * 
	 * @return
	 */
	List<SystemResource> rootSystemResources();

	/**
	 * 列出所有的权限
	 * 
	 * @return
	 */
	List<Privilege> listPrivileges();

}
