package works.tonny.apps.user.web;

// Generated 2012-7-20 14:47:15 by Hibernate Tools 3.4.0.CR1

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PagedList;

import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.Member;
import works.tonny.apps.user.MemberQuery;
import works.tonny.apps.user.UserStatus;
import works.tonny.apps.user.service.MemberEntityService;

/**
 * <p>
 * Struts2 authed action for domain model class Member.
 * </p>
 * <p>
 * Member管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see entity.Member
 * @author Tonny Liu
 * @version 1.0.0
 */
public class MemberManageAction extends AuthedAction {

	private String id;

	private MemberEntityService memberService;

	private Member member;

	private String username;

	private String name;

	private String email;

	private String userId;

	private String gender;

	private String idNo;

	private String info;

	/**
	 * 载入数据并查看
	 */
	public String view() {
		if (StringUtils.isNotEmpty(id)) {
			member = memberService.get(id);
		}

		if (StringUtils.isNotEmpty(username)) {
			member = memberService.getByUsername(username);
		}

		return VIEW;

	}

	/**
	 * 批量删除数据
	 */
	public String delete() {
		String[] ids = id.split(", ");
		memberService.delete(ids);
		return SUCCESS;
	}

	/**
	 * 查询列表并进入列表页
	 */
	public String list() {
		MemberQuery query = memberService.query();
		if (StringUtils.isNotEmpty(email)) {
			query.email(email);
		}
		if (StringUtils.isNotEmpty(name)) {
			query.name(name);
		}
		if (StringUtils.isNotEmpty(userId)) {
			query.userId(userId);
		}
		if (StringUtils.isNotEmpty(gender)) {
			query.gender(gender);
		}
		if (StringUtils.isNotEmpty(info)) {
			query.info(info);
		}
		if (StringUtils.isNotEmpty(idNo)) {
			query.idNo(idNo);
		}
		if (StringUtils.isNotEmpty(username)) {
			query.username(username);
		}
		PagedList<Member> list = query.list(getPage(), getPagesize());
		request.setAttribute("list", list);
		return SUCCESS;
	}

	/**
	 * 查询列表并进入列表页
	 */
	public String edit() {
		if (StringUtils.isNotEmpty(id)) {
			member = memberService.get(id);
		}

		if (StringUtils.isNotEmpty(username)) {
			member = memberService.getByUsername(username);
		}
		return SUCCESS;
	}

	/**
	 * 查询列表并进入列表页
	 */
	public String lock() {
		Member member = memberService.get(id);
		member.setStatus(UserStatus.LOCKED);
		memberService.update(member);
		return SUCCESS;
	}

	/**
	 * 查询列表并进入列表页
	 */
	public String unlock() {
		Member member = memberService.get(id);
		member.setStatus(UserStatus.ACTIVE);
		memberService.update(member);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MemberEntityService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberEntityService service) {
		this.memberService = service;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

}
