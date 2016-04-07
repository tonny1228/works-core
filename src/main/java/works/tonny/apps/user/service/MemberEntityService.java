package works.tonny.apps.user.service;

// Generated 2012-7-20 14:43:00 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.support.CallBeforeUpdate;
import works.tonny.apps.user.Member;
import works.tonny.apps.user.MemberService;

/**
 * Service interface for domain model class Member.
 * 
 * @see entity.Member
 * @author Tonny Liu
 */
public interface MemberEntityService extends MemberService {

	/**
	 * 更新会员密码
	 * 
	 * @param id
	 * @param password
	 * @param parameter
	 */
	void updatePassword(String id, String password, String newPassword);

	/**
	 * @param page
	 * @param pagesize
	 * @return
	 */
	PagedList<Member> list(int page, int pagesize);

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.EntityService#save(java.lang.Object)
	 */
	public String save(Member t);

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.EntityService#update(java.lang.Object)
	 */
	public void update(Member t);

	/**
	 * 更新会员信息，待更新的字段自己定义
	 * 
	 * @param t
	 * @param call
	 */
	public void update(Member t, CallBeforeUpdate<Member> call);

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.EntityService#delete(java.lang.String)
	 */
	public void delete(String id);

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.EntityService#delete(java.lang.String[])
	 */
	public void delete(String[] ids);

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.EntityService#get(java.lang.String)
	 */
	public Member get(String id);

}
