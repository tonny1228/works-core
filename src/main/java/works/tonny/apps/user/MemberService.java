/**
 * 
 */
package works.tonny.apps.user;

import org.llama.library.utils.PagedList;

/**
 * 会员服务类
 * 
 * @author tonny
 */
public interface MemberService {

	/**
	 * 通过用户名查询会员
	 * 
	 * @Title: getByUsername
	 * @param username
	 * @return
	 * @date 2012-7-21 下午5:13:56
	 * @author tonny
	 * @version 1.0
	 */
	Member getByUsername(String username);

	/**
	 * 通过邮件查询用户
	 * 
	 * @param email 邮件
	 * @return 会员
	 */
	Member findByEmail(String email);

	/**
	 * 为用户计分
	 * 
	 * @param username 会员登录名
	 * @param score 分数
	 */
	void updateScore(String username, int score);

	/**
	 * 通过id查询会员
	 * 
	 * @param memberId 编号
	 * @return
	 */
	Member get(String memberId);

	/**
	 * 按名字查询会员
	 * 
	 * @param name 姓名
	 * @param page 分页
	 * @param pagesize
	 * @return
	 */
	PagedList<Member> lookup(String name, int page, int pagesize);

	MemberQuery query();

}
