package works.tonny.apps.user.web;

// Generated 2012-7-20 14:47:15 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.List;

import org.llama.library.cryptography.Encryptable;
import org.llama.library.utils.Assert;

import works.tonny.apps.core.Mail;
import works.tonny.apps.core.MailService;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.Member;
import works.tonny.apps.user.MemberAuthedAction;
import works.tonny.apps.user.UserStatus;
import works.tonny.apps.user.model.LoginedMember;
import works.tonny.apps.user.service.AuthEntityService;
import works.tonny.apps.user.service.MemberEntityService;
import works.tonny.apps.userevent.UserLoginEventService;

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
public class MemberAction extends MemberAuthedAction {
	/**
	 * 用户登录失败6次后锁定分钟
	 */
	public static final int LOCK_MINUTES = 5;

	public static final String USER_SESSION = "logined_member";

	private String id;

	private MemberEntityService memberService;

	/**
	 * 权限服务
	 */
	private AuthEntityService authService;

	private Member member;

	private String username;

	private String password;

	private String url;

	private UserLoginEventService userLoginEventService;

	private Encryptable encryptable;

	public String login() {
		int loginCount = 0;
		long loginTime = 0;
		if (request.getSession().getAttribute("_count_" + username) != null) {
			loginCount = ((Integer) request.getSession().getAttribute("_count_" + username)).intValue();
		}

		if (request.getSession().getAttribute("_date_" + username) != null) {
			loginTime = ((Long) request.getSession().getAttribute("_date_" + username)).longValue();
			long current = System.currentTimeMillis();
			if (current - loginTime < 1000 * 60 * LOCK_MINUTES) {
				this.addActionError(getText("action.user.locked", "用户账号被锁定，$1秒后再试",
						new String[] { String.valueOf(60 * LOCK_MINUTES - (current - loginTime) / 1000) }));
				return INPUT;
			} else {
				request.getSession().removeAttribute("_date_" + username);
				loginCount = 0;
			}
		}

		if (loginCount >= 6) {
			request.getSession().setAttribute("_date_" + username, System.currentTimeMillis());
			this.addActionError(getText("action.user.locked", "用户账号被锁定，$1分钟后再试",
					new String[] { String.valueOf(LOCK_MINUTES) }));
			return INPUT;
		}
		try {
			LoginedMember member = authService.loginAsMember(username, password);
			if (!member.getMember().getStatus().equals(UserStatus.ACTIVE)) {
				this.addActionError(getText("user.notallowedlogin", new String[] { getText("user.not.active") }));
				return "login";
			}
			request.getSession().invalidate();
			request.getSession().setAttribute(USER_SESSION, member);
			request.getSession().setAttribute("xxxxxxxxxxxxxx",
					new UserSessionLogListener(member, request, userLoginEventService));
		} catch (Exception e) {
			log.error(e);
			this.addActionError(getText("user.notallowedlogin", new String[] { getText(e.getCause().getCause()
					.getMessage()) }));
			return "login";
		}

		return SUCCESS;
	}

	/**
	 * 进入添加页面
	 */
	public String add() {
		return INPUT;
	}

	/**
	 * 保存数据
	 */
	public String save() {
		member.setStatus(UserStatus.ACTIVE);
		memberService.save(member);
		return SUCCESS;
	}

	/**
	 * 载入数据并进入编辑页面
	 */
	public String edit() {
		member = memberService.get(loginedUser().getMember().getId());
		return INPUT;
	}

	/**
	 * 检查用户是否存在
	 */
	public String check() {
		member = memberService.getByUsername(username);
		request.setAttribute("member", member == null);
		return SUCCESS;
	}

	/**
	 * 载入数据并查看
	 */
	public String view() {
		member = memberService.getByUsername(username);
		return VIEW;
	}

	/**
	 * 更新数据
	 */
	public String update() {
		memberService.update(member);
		return SUCCESS;
	}

	/**
	 * 更新数据
	 */
	public String updatePassword() {
		memberService.updatePassword(loginedUser().getMember().getId(), password, getParameter("newpassword"));
		return SUCCESS;
	}

	/**
	 * 更新数据
	 */
	public String sendUrl() {
		MailService mailService = ServiceManager.lookup(ServiceManager.MAIL_SERVICE);
		String email = getParameter("email");
		Member member = memberService.findByEmail(email);
		if (member == null) {
			member = memberService.getByUsername(email);
		}
		Assert.notNull(member);
		Mail mail = new Mail("您的注册密码", member.getPassword(), email);
		mailService.sendMail(mail);
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

	public AuthEntityService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthEntityService authService) {
		this.authService = authService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the encryptable
	 */
	public Encryptable getEncryptable() {
		return encryptable;
	}

	/**
	 * @param encryptable
	 *            the encryptable to set
	 */
	public void setEncryptable(Encryptable encryptable) {
		this.encryptable = encryptable;
	}

	public UserLoginEventService getUserLoginEventService() {
		return userLoginEventService;
	}

	public void setUserLoginEventService(UserLoginEventService userLoginEventService) {
		this.userLoginEventService = userLoginEventService;
	}
}
