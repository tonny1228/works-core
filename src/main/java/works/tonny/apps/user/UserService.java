package works.tonny.apps.user;

import org.llama.library.utils.PagedList;

import works.tonny.apps.auth.AuthType;
import works.tonny.apps.auth.AuthVerify;

/**
 * 用户服务类
 * 
 * @author tonny
 * @version 1.1
 */
public interface UserService {

	/**
	 * 根据会员名字和登录名查询会员列表
	 * 
	 * @param name 名字
	 * @param username 登录名
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 会员列表
	 */
	PagedList<User> list(String name, String username, int page, int pagesize);

	/**
	 * 根据岗位编号查询用户
	 * 
	 * @param positionId 职位编号
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 会员列表
	 */
	PagedList<User> usersOfPosition(String positionId, String username, String name, int page, int pagesize);

	/**
	 * 根据部门编号查询用户
	 * 
	 * @param departmentId 部门编号
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 会员列表
	 */
	PagedList<User> usersOfDepartment(String departmentId, int page, int pagesize);

	/**
	 * 根据部门编号查询用户
	 * 
	 * @param departmentId 部门编号
	 * @param name 姓名
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 会员列表
	 */
	PagedList<User> usersOfDepartment(String departmentId, String name, int page, int pagesize);

	/**
	 * 根据部门编号查询用户
	 * 
	 * @param departmentId 部门编号
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 会员列表
	 */
	PagedList<User> usersOfTitle(String titleName, int page, int pagesize);

	/**
	 * 更新用户信息
	 * 
	 * @param user 用户
	 */
	@AuthVerify(AuthType.SPECIAL)
	void updateMine(User user);

	/**
	 * 获得用户信息
	 * 
	 * @param id
	 * @return
	 */
	User get(String id);

	/**
	 * 获得用户包含角色的详细信息
	 * 
	 * @param id
	 * @return
	 */
	User getUser(String id);

	/**
	 * 获得用户详细信息
	 * 
	 * @param id
	 * @return
	 */
	User getUserByUsername(String username);

	/**
	 * 获得当前登录用户信息
	 * 
	 * @return
	 * @author tonny
	 * @since 1.1
	 */
	LoginedUser getLoginedUser();

	/**
	 * 创建复合查询
	 * 
	 * @return
	 * @author tonny
	 */
	UserQuery createUserQuery();

}
