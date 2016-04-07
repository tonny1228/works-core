/**
 * @Title: AuthServiceImpl.java
 * @Package works.tonny.user.service.impl
 * @author Tonny
 * @date 2011-11-8 下午2:47:43
 */
package works.tonny.apps.user.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.cryptography.Encryptable;
import org.llama.library.utils.Assert;
import org.llama.library.utils.PropertiesUtils;
import org.llama.library.utils.ThreadLocalMap;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.auth.DataOwnerConfig;
import works.tonny.apps.deploy.SessionFactoryManager;
import works.tonny.apps.exception.AuthException;
import works.tonny.apps.exception.DataException;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageEvent.Status;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.*;
import works.tonny.apps.user.UserStatus;
import works.tonny.apps.user.dao.*;
import works.tonny.apps.user.model.*;
import works.tonny.apps.user.service.AuthEntityService;
import works.tonny.apps.user.service.LoginInterceptor;

import java.util.*;

/**
 * @author Tonny
 * @version 1.0
 * @ClassName: AuthServiceImpl
 * @Description:
 * @date 2011-11-8 下午2:47:43
 */
public class AuthServiceImpl extends AuthedAbstractService implements AuthEntityService {

    private UserDAO userDAO;

    private RoleDAO roleDAO;

    private AuthDAO authDAO;

    private MemberService memberService;

    private SystemResourceDAO systemResourceDAO;

    private UserPrivilegeDAO userPrivilegeDAO;

    private RolePrivilegeDAO rolePrivilegeDAO;

    private PositionPrivilegeDAO positionPrivilegeDAO;

    private Encryptable encryptable;

    /*
      * @see works.tonny.user.service.iface.AuthService#login(java.lang.String,
      * java.lang.String)
      */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public LoginedUser login(String username, String password) {
        ThreadLocalMap.getInstance().putObject(DataOwnerConfig.Type.class, DataOwnerConfig.Type.NOT_VERIFY);
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            throw new DataException("用户不存在");
        }
        // 载入权限等
        user.getRoles().size();
        if ((user instanceof UserInfo) && ((UserInfo) user).getPosition() != null) {
            ((UserInfo) user).getPositions().size();
            ((UserInfo) user).getPosition().getDepartment().toString();
        }
        Assert.notNull(user, "user.null");
        Assert.isTrue(user.getPassword().equals(encryptable.encrypt(password)), "user.password.notmatch");
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new AuthException("user.not.active");
        }
        LoginedUserImpl loginedUser = new LoginedUserImpl(user);
        user.getRoles().add(roleDAO.get("everyone"));

        // log.debug(user.getRoles().size());
        for (Role role : user.getRoles()) {
            List<RolePrivilege> rps = rolePrivilegeDAO.getRolePrivileges(role.getId());
            for (RolePrivilege rp : rps) {
                Set<SystemResource> systemResources = new HashSet<SystemResource>();
                Set<SystemResource> resources = rp.getPrivilege().getResources();
                for (SystemResource resource : resources) {
                    SystemResource e = systemResourceDAO.get(SystemResource.class, resource.getId());
                    SystemResource systemResource = new SystemResource();
                    try {
                        BeanUtils.copyProperties(systemResource, e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    systemResources.add(systemResource);
                }
                rp.getPrivilege().setResources(systemResources);
                loginedUser.addPrivilege(rp);
            }
        }

        // List<RolePrivilege> rps =
        // rolePrivilegeDAO.getRolePrivileges("everyone");
        // loginedUser.addPrivileges(rps);

        if (user instanceof UserInfo && ((UserInfo) user).getPosition() != null) {
            UserInfo userInfo = (UserInfo) user;
            Position position = userInfo.getPosition();
            loginedUser.setPosition(position);
            position.getDepartment().getName();
            positionPrivilegeDAO.getPositionPrivileges(position.getId());
            userInfo.getPositions().size();
        }
        List<UserPrivilege> privileges = userPrivilegeDAO.getUserPrivileges(user.getId());
        for (UserPrivilege userPrivilege : privileges) {
            Set<SystemResource> systemResources = new HashSet<SystemResource>();
            Set<SystemResource> resources = userPrivilege.getPrivilege().getResources();
            for (SystemResource resource : resources) {
                SystemResource e = systemResourceDAO.get(SystemResource.class, resource.getId());
                log.debug(e.getPackageName());
                SystemResource systemResource = new SystemResource();
                try {
                    BeanUtils.copyProperties(systemResource, e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                systemResources.add(systemResource);
            }
            userPrivilege.getPrivilege().setResources(systemResources);
        }
        loginedUser.addPrivileges(privileges);
        // loginedUser.setPosition(user.getPosition());
        MessageManager.notify(LoginedUser.class, new Status("login"), loginedUser);
        return loginedUser;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public LoginedMember loginAsMember(String username, String password) {
        ThreadLocalMap.getInstance().putObject(DataOwnerConfig.Type.class, DataOwnerConfig.Type.NOT_VERIFY);
        Member member = memberService.getByUsername(username);
        Assert.notNull(member, "user.null");
        Assert.isTrue(member.getPassword().equals(encryptable.encrypt(password)), "user.password.notmatch");
        // if (!member.getStatus().equals(UserStatus.ACTIVE)) {
        // throw new AuthException("user.not.active");
        // }
        User user = userDAO.getUserByUsername(member.getUserId());
        Assert.notNull(user, "user.null");
        LoginedMember loginedUser = new LoginedMember(user, member);
        user.getRoles().add(roleDAO.get("everyone"));
        // log.debug(user.getRoles().size());
        for (Role role : user.getRoles()) {
            List<RolePrivilege> rps = rolePrivilegeDAO.getRolePrivileges(role.getId());
            for (RolePrivilege rp : rps) {
                Set<SystemResource> systemResources = new HashSet<SystemResource>();
                Set<SystemResource> resources = rp.getPrivilege().getResources();
                for (SystemResource resource : resources) {
                    SystemResource e = systemResourceDAO.get(SystemResource.class, resource.getId());
                    SystemResource systemResource = new SystemResource();
                    try {
                        BeanUtils.copyProperties(systemResource, e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    systemResources.add(systemResource);
                }
                rp.getPrivilege().setResources(systemResources);
                loginedUser.addPrivilege(rp);
            }
        }

        // List<RolePrivilege> rps =
        // rolePrivilegeDAO.getRolePrivileges("everyone");
        // loginedUser.addPrivileges(rps);

        List<UserPrivilege> privileges = userPrivilegeDAO.getUserPrivileges(user.getId());
        for (UserPrivilege userPrivilege : privileges) {
            Set<SystemResource> systemResources = new HashSet<SystemResource>();
            Set<SystemResource> resources = userPrivilege.getPrivilege().getResources();
            for (SystemResource resource : resources) {
                SystemResource e = systemResourceDAO.get(SystemResource.class, resource.getId());
                log.debug(e.getPackageName());
                SystemResource systemResource = new SystemResource();
                try {
                    BeanUtils.copyProperties(systemResource, e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                systemResources.add(systemResource);
            }
            userPrivilege.getPrivilege().setResources(systemResources);
        }
        loginedUser.addPrivileges(privileges);
        // loginedUser.setPosition(user.getPosition());
        MessageManager.notify(LoginedUser.class, new Status("login"), loginedUser);
        return loginedUser;
    }


    /**
     * @see works.tonny.apps.user.AuthService#login(works.tonny.apps.user.service.LoginInterceptor,
     * java.lang.String)
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public LoginedUser login(LoginInterceptor loginedInfo, String password) {
        ThreadLocalMap map = ThreadLocalMap.getInstance();
        map.init();
        if (loginedInfo.getSchema() != null)
            map.putObject(SessionFactoryManager.SCHEMA, "t_" + loginedInfo.getSchema());
        LoginedUser user = login(loginedInfo.getUsername(), password);
        if (loginedInfo.getSchema() != null)
            user.setSchema("t_" + loginedInfo.getSchema());
        return user;
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#loginOut(works.tonny.user.
     * model.User)
     */
    public void loginOut(User user) {
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#addSystemResource(works.tonny
     * .user.model.SystemResource)
     */
    public String addSystemResource(SystemResource resource) {
        resource.setAdmin(getLoginedUser().getUser().getUsername());
        resource.setUpdateTime(new Date());
        String id = (String) systemResourceDAO.save(resource);
        MessageManager.notify(SystemResource.class, MessageEvent.CREATED, resource);
        return id;

    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#deleteSystemResource(java.
     * lang.String[])
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteSystemResource(String[] ids) {
        for (String id : ids) {
            SystemResource resource = systemResourceDAO.get(id);
            Set<SystemResource> subs = resource.getSubs();
            for (SystemResource systemResource : subs) {
                systemResourceDAO.delete(systemResource);
                MessageManager.notify(SystemResource.class, MessageEvent.DELETED, systemResource);
            }
            systemResourceDAO.delete(resource);
            MessageManager.notify(SystemResource.class, MessageEvent.DELETED, resource);
        }
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#addPrivilege(java.lang.String,
     * java.lang.String[])
     */
    public String addPrivilege(String name, String[] resourceId) {
        Privilege privilege = new Privilege();
        Set<SystemResource> resources = new HashSet<SystemResource>();
        for (String id : resourceId) {
            resources.add(systemResourceDAO.get(id));
        }
        privilege.setResources(resources);
        privilege.setName(name);
        privilege.setUpdateTime(new Date());
        String id = (String) authDAO.save(privilege);
        MessageManager.notify(Privilege.class, MessageEvent.CREATED, privilege);
        return id;
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#getPrivilege(java.lang.String)
     */
    public Privilege getPrivilege(String id) {
        Privilege privilege = authDAO.get(id);
        return privilege;
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#updatePrivilegeResource(java
     * .lang.String, java.lang.String, java.lang.String[])
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePrivilegeResource(String privilegeId, String name, String[] resourceIds) {
        Privilege privilege = authDAO.get(privilegeId);
        privilege.getResources().clear();
        if (name != null)
            privilege.setName(name);
        if (resourceIds != null)
            for (String id : resourceIds) {
                SystemResource r = systemResourceDAO.get(id);
                if (r != null) {
                    privilege.getResources().add(r);
                    r.getPrivileges().add(privilege);
                }
            }
        // privilege.setAdmin(admin.getUsername());
        privilege.setUpdateTime(new Date());
        authDAO.update(privilege);
        MessageManager.notify(Privilege.class, MessageEvent.UPDATED, privilege);
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#deletePrivilege(java.lang.
     * String[])
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePrivilege(String[] ids) {
        for (String id : ids) {
            if (StringUtils.isBlank(id)) {
                continue;
            }
            Privilege p = authDAO.get(id);
            authDAO.delete(p);
            MessageManager.notify(Privilege.class, MessageEvent.DELETED, p);
        }
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#updateUserPrivilege(java.lang
     * .String, java.lang.String[])
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserPrivilege(String userId, String[] pgIds) {
        List<UserPrivilege> privileges = userPrivilegeDAO.getUserPrivileges(userId);
        User user = userDAO.get(userId);
        for (UserPrivilege userPrivileges : privileges) {
            int idx = ArrayUtils.indexOf(pgIds, userPrivileges.getPrivilege().getId());
            if (idx >= 0) {
                pgIds[idx] = null;
            } else {
                userPrivilegeDAO.delete(userPrivileges);
                MessageManager.notify(UserPrivilege.class, MessageEvent.DELETED, userPrivileges);
            }
        }
        if (pgIds != null) {
            for (String id : pgIds) {
                if (id == null)
                    continue;
                Privilege privilege = authDAO.get(id);
                if (privilege == null) {
                    continue;
                }
                UserPrivilege up = new UserPrivilege(user, privilege);
                userPrivilegeDAO.save(up);
                MessageManager.notify(UserPrivilege.class, MessageEvent.CREATED, up);
            }
        }
        // user.setPrivileges(auths);
        // userDAO.update(user);
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#updateRolePrivilege(java.lang
     * .String, java.lang.String[])
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRolePrivilege(String roleId, String[] pgeIds) {
        List<RolePrivilege> privileges = rolePrivilegeDAO.getRolePrivileges(roleId);
        Role role = roleDAO.get(roleId);
        for (RolePrivilege userPrivileges : privileges) {
            int idx = ArrayUtils.indexOf(pgeIds, userPrivileges.getPrivilege().getId());
            if (idx >= 0) {
                pgeIds[idx] = null;
            } else {
                rolePrivilegeDAO.delete(userPrivileges);
                MessageManager.notify(RolePrivilege.class, MessageEvent.DELETED, userPrivileges);
            }
        }
        if (pgeIds != null)
            for (String id : pgeIds) {
                if (id == null)
                    continue;
                Privilege privilege = authDAO.get(id);
                if (privilege == null) {
                    continue;
                }
                RolePrivilege rp = new RolePrivilege(role, privilege);
                rolePrivilegeDAO.save(rp);
                MessageManager.notify(RolePrivilege.class, MessageEvent.CREATED, rp);
            }
    }

    /*
     * @see works.tonny.user.service.iface.AuthService#listSystemResources()
     */
    public List<SystemResource> listSystemResources() {
        return systemResourceDAO.list();
    }

    /*
     * @see works.tonny.user.service.iface.AuthService#listSystemResources()
     */
    public List<SystemResource> rootSystemResources() {
        return systemResourceDAO.root();
    }

    /*
     * @see works.tonny.user.service.iface.AuthService#listPrivileges()
     */
    public List<Privilege> listPrivileges() {
        return authDAO.list();
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#getSystemResource(java.lang
     * .String)
     */
    public SystemResource getSystemResource(String id) {
        SystemResource systemResource = systemResourceDAO.get(id);
        return systemResource;
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#updateSystemResource(works
     * .tonny.user.model.SystemResource)
     */
    public void updateSystemResource(SystemResource resource) {
        SystemResource db = getSystemResource(resource.getId());
        db.setAdmin(getLoginedUser().getUser().getUsername());
        db.setUpdateTime(new Date());
        PropertiesUtils.copyProperties(db, resource, "name", "packageName", "api", "url", "type", "description");
        systemResourceDAO.update(db);
        MessageManager.notify(SystemResource.class, MessageEvent.UPDATED, db, resource);
        resource.setParent(db.getParent());
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#userPrivilege(java.lang.String
     * )
     */
    public List<Privilege> userPrivilege(String id) {
        List<UserPrivilege> privileges = userPrivilegeDAO.getUserPrivileges(id);
        List<Privilege> ps = new ArrayList<Privilege>();
        for (UserPrivilege up : privileges) {
            ps.add(up.getPrivilege());
        }
        return ps;
    }

    /*
     * @see
     * works.tonny.user.service.iface.AuthService#userPrivilege(java.lang.String
     * )
     */
    public List<Privilege> rolePrivilege(String id) {
        List<RolePrivilege> privileges = rolePrivilegeDAO.getRolePrivileges(id);
        List<Privilege> ps = new ArrayList<Privilege>();
        for (RolePrivilege up : privileges) {
            ps.add(up.getPrivilege());
        }
        return ps;
    }

    public SystemResourceDAO getSystemResourceDAO() {
        return systemResourceDAO;
    }

    public void setSystemResourceDAO(SystemResourceDAO systemResourceDAO) {
        this.systemResourceDAO = systemResourceDAO;
    }

    public UserPrivilegeDAO getUserPrivilegeDAO() {
        return userPrivilegeDAO;
    }

    public void setUserPrivilegeDAO(UserPrivilegeDAO userPrivilegeDAO) {
        this.userPrivilegeDAO = userPrivilegeDAO;
    }

    public RolePrivilegeDAO getRolePrivilegeDAO() {
        return rolePrivilegeDAO;
    }

    public void setRolePrivilegeDAO(RolePrivilegeDAO rolePrivilegeDAO) {
        this.rolePrivilegeDAO = rolePrivilegeDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public RoleDAO getRoleDAO() {
        return roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public AuthDAO getAuthDAO() {
        return authDAO;
    }

    public void setAuthDAO(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public PositionPrivilegeDAO getPositionPrivilegeDAO() {
        return positionPrivilegeDAO;
    }

    public void setPositionPrivilegeDAO(PositionPrivilegeDAO positionPrivilegeDAO) {
        this.positionPrivilegeDAO = positionPrivilegeDAO;
    }

    /**
     * @return the encryptable
     */
    public Encryptable getEncryptable() {
        return encryptable;
    }

    /**
     * @param encryptable the encryptable to set
     */
    public void setEncryptable(Encryptable encryptable) {
        this.encryptable = encryptable;
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

}
