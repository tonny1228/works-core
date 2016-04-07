package works.tonny.apps.user.dao;

import java.util.List;

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.User;

/**
 * 会话管理dao，用于会员信息查询、维护
 * 
 * @author tonny
 */
public interface UserDAO extends EntityDAO<User> {
	/**
	 * 根据会员名字和登录名查询会员列表
	 * 
	 * @param name 名字
	 * @param username 登录名
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 会员列表
	 */
	@ListSupport(field = { "name", "username" }, operator = { ListSupport.LIKE, ListSupport.LIKE }, defaults = { "id<>t.username" }, order = "username")
	PagedList<User> list(String name, String username, int page, int pagesize);

	/**
	 * 根据会员名字和登录名查询会员列表
	 * 
	 * @param name 名字
	 * @param username 登录名
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 会员列表
	 */
	@ListSupport(defaults = { "id=t.username" }, order = "username")
	List<User> sysUsers();

	/**
	 * 根据会员组编号查询会员列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 会员列表
	 */
	@ListSupport(order = "username")
	PagedList<User> list(int page, int pagesize);

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username 用户名
	 * @return 用户
	 */
	@ListSupport(field = "username")
	User getUserByUsername(String username);

}
