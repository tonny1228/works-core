package works.tonny.apps.user.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.Privilege;
import works.tonny.apps.user.Role;
import works.tonny.apps.user.SystemResource;
import works.tonny.apps.user.User;

/**
 * 登录系统的用户
 * 
 * @author tonny
 */
public class LoginedUserImpl extends LoginedUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<String> apiCodes;

	private Map<String, EntityPrivilege> mappedPrivilege;

	private Position position;

	/**
	 * 
	 */
	public LoginedUserImpl() {
		mappedPrivilege = new HashMap<String, EntityPrivilege>();
		apiCodes = new HashSet<String>();
		privileges = new ArrayList<Privilege>();
	}

	/**
	 * <p>
	 * </p>
	 */
	public LoginedUserImpl(User user) {
		this();
		this.user = user;
	}

	public LoginedUserImpl(User user, List<Privilege> privileges) {
		this();
		this.user = user;
		this.privileges = privileges;
	}

	/**
	 * 验证用户功能权限
	 * 
	 * @Title: hasAuth
	 * @param authCode
	 *            <p>
	 *            {role:role1,role2,role3;username:user1,user2,user3;api:
	 *            user.add,auth.delete }
	 *            </p>
	 * @return
	 * @date 2011-11-21 上午11:34:52
	 * @author tonny
	 * @version 1.0
	 */
	public boolean hasAuth(String authCode) {
		// if (mappedPrivilege.keySet().contains(authCode)) {
		// return true;
		// }
		// for (String api : mappedPrivilege.keySet()) {
		// if (api.matches(apiRegx(authCode))) {
		// return true;
		// }
		// }
		//
		if (!authCode.startsWith("{") || !authCode.endsWith("}")) {
			authCode = "{api:" + authCode + "}";
		}
		authCode = StringUtils.substringBetween(authCode, "{", "}");

		String[] codes = authCode.split(";");
		for (String code : codes) {
			String command = StringUtils.substringBefore(code, ":");
			String value = StringUtils.substringAfter(code, ":");
			if (command.equals("api")) {
				if (apiCodes.contains(value)) {
					return true;
				}
				for (String api : apiCodes) {
					if (api.matches(apiRegx(value))) {
						return true;
					}
				}
				return apiCodes.contains(value);
			} else if (command.equals("role")) {
				for (String role : value.split(",")) {
					if (((User) user).getRoles().contains(new Role(role, null))) {
						return true;
					}
				}
			} else if (command.equals("username")) {
				for (String username : value.split(",")) {
					if (username.equals(user.getUsername())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * @Title: apiRegx
	 * @param authCode
	 * @return
	 * @date 2011-12-17 下午1:26:07
	 * @author tonny
	 * @version 1.0
	 */
	private String apiRegx(String authCode) {
		return authCode.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
	}

	/**
	 * 获取拥有权限的身份（用户、岗位、角色）
	 * 
	 * @param api
	 *            api串
	 * @return 拥有权限的身份（用户、岗位、角色）
	 */
	public Object getEntity(String id) {
		EntityPrivilege entityPrivilege = mappedPrivilege.get(id);
		if (entityPrivilege == null) {
			return null;
		}
		return entityPrivilege.getEntity();
	}

	// /**
	// * 初始化用户权限
	// *
	// * @Title: setPrivileges
	// * @param privileges
	// * @date 2011-11-21 上午11:30:58
	// * @author tonny
	// * @version 1.0
	// */
	// public void setPrivileges1(List<Privilege> privileges) {
	// this.privileges = privileges;
	// // this.apiCodes = new HashSet<String>();
	// // for (Privilege privilege : privileges) {
	// // for (SystemResource resource : privilege.getResources()) {
	// // apiCodes.add(resource.getApi());
	// // }
	// // }
	// }

	/**
	 * 添加用户的权限配置信息。以最后的权限配置为准。如先后为用户和用户的角色添加user.add权限，以后角色有权限为准
	 * 
	 * @param entityPrivilege
	 */
	public void addPrivilege(EntityPrivilege entityPrivilege) {
		if (privileges.contains(entityPrivilege.getPrivilege())) {
			return;
		}
		privileges.add(entityPrivilege.getPrivilege());
		Set<SystemResource> resources = entityPrivilege.getPrivilege().getResources();
		for (SystemResource systemResource : resources) {
			mappedPrivilege.put(systemResource.getId(), entityPrivilege);
			apiCodes.add(systemResource.getPackageName() + "." + systemResource.getApi());
		}
	}

	/**
	 * 添加用户的权限配置信息。以最后的权限配置为准。如先后为用户和用户的角色添加user.add权限，以后角色有权限为准
	 * 
	 * @param entityPrivilege
	 */
	public void addPrivileges(List<? extends EntityPrivilege> entityPrivileges) {
		for (EntityPrivilege entityPrivilege : entityPrivileges) {
			addPrivilege(entityPrivilege);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		if (position != null) {
			string.append("dp=").append(position.getDepartment().getId()).append(";ps=").append(position.getId())
					.append(";");
		}
		string.append("un=").append(user.getUsername()).append(";");
		return string.toString();
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}