/**
 * 
 */
package works.tonny.apps.user.service;

import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.UserInfo;

/**
 * 组织机构服务类，用户关联关系
 * 
 * @author 祥栋
 */
public interface OrgnizationHelper {
	/**
	 * 获取用户的直属领导
	 * 
	 * @param info 用户信息
	 * @return 领导信息
	 */
	UserInfo getLeader(UserInfo user);

	/**
	 * 获取用户的直属领导
	 * 
	 * @param info 用户信息
	 * @param jobName 职务名称
	 * @return 领导信息
	 */
	UserInfo getLeader(UserInfo user, String jobName);

	/**
	 * 在指导范围内查询用户的最高领导
	 * 
	 * @param info 用户信息
	 * @param level 查询的范围
	 * @return 最高的领导信息
	 */
	UserInfo getLeader(UserInfo user, DepartmentLevel level);

	/**
	 * 获取当前用户的直接的管理单元
	 * 
	 * @param user 用户信息
	 * @return 管理单元信息
	 */
	Department getBusinessUnit(UserInfo user);
}