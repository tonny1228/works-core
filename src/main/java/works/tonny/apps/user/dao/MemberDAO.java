package works.tonny.apps.user.dao;

// Generated 2012-7-20 14:43:00 by Hibernate Tools 3.4.0.CR1

import org.llama.library.sqlmapping.QueryParameters;
import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.support.SQLMappingListSupport;
import works.tonny.apps.user.Member;

/**
 * DAO interface for domain model class Member.
 * 
 * @see entity.Member
 * @author Tonny Liu
 */
public interface MemberDAO extends EntityDAO<Member> {
	/**
	 * 分页查询Member列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	@ListSupport(order = "username")
	PagedList<Member> list(int page, int pagesize);

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
	@ListSupport(field = "username")
	Member getByUsername(String username);

	/**
	 * @param name
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@ListSupport(field = "name", operator = ListSupport.LIKE)
	PagedList<Member> find(String name, int page, int pagesize);

	/**
	 * 通过email查询用户
	 * 
	 * @param email
	 * @return 用户信息
	 */
	@ListSupport(field = "email")
	Member findByEmail(String email);

	/**
	 * 最大值
	 * 
	 * @return
	 */
	@SQLMappingListSupport(name = "Member.maxNo")
	int maxNo();

	/**
	 * @Title: list
	 * @param querys
	 * @param page
	 * @param pagesize
	 * @return
	 * @date 2012-5-15 下午4:09:02
	 * @author tonny
	 * @version 1.0
	 */
	@SQLMappingListSupport(name = "Member.query", appender = "order")
	PagedList<Member> list(QueryParameters paramter, int page, int pagesize);

}
