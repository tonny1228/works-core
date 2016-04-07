package works.tonny.apps.user.service.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.cryptography.Encryptable;
import org.llama.library.utils.Assert;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.PropertiesUtils;
import org.llama.library.utils.ThreadLocalMap;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.exception.AuthException;
import works.tonny.apps.exception.DataException;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.*;
import works.tonny.apps.user.dao.*;
import works.tonny.apps.user.model.Position;
import works.tonny.apps.user.model.UserInfo;
import works.tonny.apps.user.model.UserProperties;
import works.tonny.apps.user.service.UserEntityService;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 用户服务类
 *
 * @author tonny
 */
public class UserServiceImpl extends AuthedAbstractService implements UserEntityService {
    /**
     * 会员dao
     */
    private UserDAO userDAO;
    /**
     * 会员dao
     */
    private UserInfoDAO userinfoDAO;

    /**
     * 会员dao
     */
    private UserPositionDAO userPositionDAO;


    private UserInfoEntityDAO userInfoEntityDAO;

    private BaseDAOSupport<UserProperties> userPropertiesDAO;

    private RoleDAO roleDAO;
    /**
     * 会员dao
     */
    private PositionDAO positionDAO;

    /**
     * 会员dao
     */
    private TitleDAO titleDAO;

    private Encryptable encryptable;

    /**
     * 用户信息监听器
     */
    // private Map<String, UserUpdateListener> listeners;

    /**
     *
     */
    public UserServiceImpl() {
    }

    public PagedList<User> list(String name, String username, int page, int pagesize) {
        return userDAO.list(name, username, page, pagesize);
    }

    public PagedList<User> list(int page, int pagesize) {
        return userDAO.list(page, pagesize);
    }

    @Transactional(rollbackFor = Exception.class)
    public String create(User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new DataException("user.username.null");
        }
        // 判断用户是否存在
        User u = userDAO.getUserByUsername(user.getUsername());
        if (u != null) {
            throw new DataException("user.exist");
        }

        if (user.getRegTime() == null)
            user.setRegTime(new Date());
        String id = null;
        MessageManager.notify(User.class, MessageEvent.BEFORE_CREATE, user);
        if (user.getPassword() != null) {
            user.setPassword(encryptable.encrypt(user.getPassword()));
        }
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleDAO.get("everyone"));
        user.setRoles(roles);
        id = (String) userDAO.save(user);
        MessageManager.notify(User.class, MessageEvent.CREATED, user);
        // if (listeners != null) {
        // for (UserUpdateListener listener : listeners.values()) {
        // listener.userSaved(user);
        // }
        // }
        return id;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] ids) {
        for (String id : ids) {
            if (StringUtils.isEmpty(id) || id.equals("admin") || id.equals("anonymous")) {
                continue;
            }
            User user = userDAO.get(id);
            if (user == null) {
                throw new DataException("user.no");
            }
            // if (listeners != null) {
            // for (UserUpdateListener listener : listeners.values()) {
            // listener.userDeleted(user);
            // }
            // }
            MessageManager.notify(User.class, MessageEvent.BEFORE_DELETE, user);
            userDAO.delete(user);
            MessageManager.notify(User.class, MessageEvent.DELETED, user);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void lock(String id) {
        User user = userDAO.get(id);
        if (user == null) {
            throw new DataException("user.no");
        }
        user.setStatus(UserStatus.LOCKED);
        userDAO.update(user);
        MessageManager.notify(User.class, MessageEvent.DISABLED, user);
        // if (listeners != null) {
        // for (UserUpdateListener listener : listeners.values()) {
        // listener.userUpdated(user);
        // }
        // }
    }

    @Transactional(rollbackFor = Exception.class)
    public void unlock(String id) {
        User user = userDAO.get(id);
        if (user == null) {
            throw new DataException("user.no");
        }
        user.setStatus(UserStatus.ACTIVE);
        userDAO.update(user);
        MessageManager.notify(User.class, MessageEvent.ENABLED, user);
        // if (listeners != null) {
        // for (UserUpdateListener listener : listeners.values()) {
        // listener.userUpdated(user);
        // }
        // }
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(User user) {
        User u = userDAO.get(user.getId());
        if (user == null) {
            throw new DataException("user.no");
        }
        PropertiesUtils.copyExcludesProperties(u, user, "roles", "id", "password", "status", "regTime", "owner");
        if (StringUtils.isNotEmpty(user.getPassword())) {
            u.setPassword(encryptable.encrypt(user.getPassword()));
        }
        MessageManager.notify(User.class, MessageEvent.BEFORE_UPDATE, u);
        userDAO.update(u);
        MessageManager.notify(User.class, MessageEvent.UPDATED, u);
        // if (listeners != null) {
        // for (UserUpdateListener listener : listeners.values()) {
        // listener.userUpdated(user);
        // }
        // }
    }

    public User get(String id) {
        User user = userDAO.get(id);
        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User getUser(String id) {
        User user = userDAO.get(id);
        Assert.notNull(user, "没有找到用户");
        user.getRoles().size();
        if (user instanceof UserInfo) {
            ((UserInfo) user).getPositions().size();
        }
        return user;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /*
     * @see works.tonny.user.service.iface.UserService#move(java.lang.String,
     * java.lang.String)
     */
    @Transactional(rollbackFor = Exception.class)
    public void move(String userId, String positionId) {
        UserInfo user = (UserInfo) userDAO.get(userId);
        Position position = positionDAO.get(positionId);
        user.setPosition(position);
        position.getUsers().add(user);
        positionDAO.update(position);
        userDAO.update(user);
        MessageManager.notify(User.class, MessageEvent.MOVED, user);
        // if (listeners != null) {
        // for (UserUpdateListener listener : listeners.values()) {
        // listener.userUpdated(user);
        // }
        // }
    }

    public PositionDAO getPositionDAO() {
        return positionDAO;
    }

    public void setPositionDAO(PositionDAO positionDAO) {
        this.positionDAO = positionDAO;
    }

    /*
     * @see
     * works.tonny.user.service.iface.UserService#create(works.tonny.user.model
     * .User, java.lang.String)
     */
    @Transactional(rollbackFor = Exception.class)
    public String create(UserInfo user, String positionId) {
        Position position = positionDAO.get(positionId);
        user.setPosition(position);
        position.getUsers().add(user);
        Set<Position> positions = new HashSet<Position>();
        positions.add(position);
        user.setPositions(positions);
        create(user);
        positionDAO.update(position);
        // MessageManager.notify(User.class, MessageEvent.MOVED, user,
        // position);
        return user.getId();
    }

    /*
     * @see
     * works.tonny.user.service.iface.UserService#create(works.tonny.user.model
     * .User, java.lang.String)
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUserToPosition(String id, String positionId) {
        Position position = positionDAO.get(positionId);
        User user = get(id);
        UserInfo info = new UserInfo();
        try {
            PropertyUtils.copyProperties(info, user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        position.getUsers().add(info);
        positionDAO.update(position);
        info.setPosition(position);
        MessageManager.notify(User.class, MessageEvent.MOVED, info);
    }

    /*
     * @see
     * works.tonny.user.service.iface.UserService#create(works.tonny.user.model
     * .User, java.lang.String)
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeUserFromPosition(String id, String positionId) {
        Position position = positionDAO.get(positionId);
        // Set<UserInfo> users = position.getUsers();
        UserInfo user = (UserInfo) getUser(id);
        // users.remove(user);
        user.getPositions().remove(position);
        if (user.getPosition() != null && user.getPosition().equals(position)) {
            if (!user.getPositions().isEmpty()) {
                user.setPosition(user.getPositions().iterator().next());
                userDAO.update(user);
            } else {
                userInfoEntityDAO.delete(user.getId());
            }
        }

        MessageManager.notify("user_position", MessageEvent.DELETED, user, position);
        // positionDAO.update(position);
    }

    /*
     * @see works.tonny.user.service.iface.UserService#addRole(java.lang.String,
     * java.lang.String[])
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(String userId, String[] roleId) {
        User user = userDAO.get(userId);
        for (String rId : roleId) {
            Role role = roleDAO.get(rId);
            if (role == null) {
                continue;
            }
            user.getRoles().add(role);
        }
        MessageManager.notify("user_role", MessageEvent.UPDATED, user, user.getRoles());

        List<Role> toDelete = new ArrayList<Role>();

        for (Role role : user.getRoles()) {
            if (role.getId().equals("everyone")) {
                continue;
            }
            int idx = ArrayUtils.indexOf(roleId, role.getId());
            if (idx >= 0) {
                roleId[idx] = null;
            } else {
                // roleDAO.delete(role);
                toDelete.add(role);
            }
        }
        user.getRoles().removeAll(toDelete);
        MessageManager.notify("user_role", MessageEvent.DELETED, user, toDelete);
        // userDAO.update(user);
    }

    /*
     * @see
     * works.tonny.user.service.iface.UserService#removeRole(java.lang.String,
     * java.lang.String[])
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeRole(String userId, String[] roleId) {
        User user = userDAO.get(userId);
        Set<Role> roles = new HashSet<Role>();
        for (Role role : user.getRoles()) {
            if (role.getId().equals("everyone")) {
                continue;
            }
            if (ArrayUtils.contains(roleId, role.getId())) {
                roles.add(role);
            }
        }
        user.getRoles().removeAll(roles);
        userDAO.update(user);
        MessageManager.notify("user_role", MessageEvent.DELETED, user, roles);
    }

    public RoleDAO getRoleDAO() {
        return roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    /**
     * @param user
     * @see works.tonny.user.service.iface.UserService#updateMine(works.tonny.user.model.User)
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMine(User user) {
        LoginedUser admin = getLoginedUser();

        if (!user.getId().equals(admin.getUser().getId())) {
            throw new AuthException("user.cannot update");
        }
        User u = userDAO.get(user.getId());
        Assert.notNull(u);
        PropertiesUtils.copyExcludesProperties(u, user, "roles", "id", "password", "status", "regTime", "orderNo",
                "owner");
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(encryptable.encrypt(user.getPassword()));
        }
        userDAO.update(u);
        MessageManager.notify(User.class, MessageEvent.UPDATED, u);
        // if (listeners != null) {
        // for (UserUpdateListener listener : listeners.values()) {
        // listener.userUpdated(user);
        // }
        // }
        admin.setUser(u);
    }

    /**
     * @see com.zxtx.apps.user.UserEntityService#updateUserinfo(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String[])
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserinfo(String userId, String title, String positionId, String... position) {
        User user = get(userId);
        if (!(user instanceof UserInfo)) {
            // info = new UserInfo();
            // try {
            // PropertyUtils.copyProperties(info, user);
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
            // info.setRoles(null);
            // if (StringUtils.isNotEmpty(title)) {
            // info.setTitle(titleDAO.get(title));
            // }
            // info.setPositions(new HashSet<Position>());
            // if (position != null) {
            // Position pos = null;
            // for (String pid : position) {
            // pos = positionDAO.get(pid);
            // info.getPositions().add(pos);
            // }
            // if (position.length == 1) {
            // info.setPosition(pos);
            // } else {
            // info.setPosition(positionDAO.get(positionId));
            // }
            // }
            // userinfoDAO.save(info);
            UserProperties info = new UserProperties();
            info.setId(user.getId());
            if (StringUtils.isNotEmpty(title)) {
                info.setTitle(titleDAO.get(title));
            }
            info.setPositions(new HashSet<Position>());
            if (position != null) {
                Position pos = null;
                for (String pid : position) {
                    pos = positionDAO.get(pid);
                    info.getPositions().add(pos);
                }
                if (position.length == 1) {
                    info.setPosition(pos);
                } else {
                    info.setPosition(positionDAO.get(positionId));
                }
            }
            userPropertiesDAO.save(info);
            return;
        }
        UserInfo info = (UserInfo) user;

        // userDAO.refresh(info);

        if (StringUtils.isNotEmpty(title)) {
            info.setTitle(titleDAO.get(title));
        } else if (title != null) {
            info.setTitle(null);
        }
        if (info.getPositions() != null) {
            info.getPositions().clear();
        } else {
            info.setPositions(new HashSet<Position>());
        }
        if (position != null) {
            Position pos = null;
            for (String pid : position) {
                pos = positionDAO.get(pid);
                info.getPositions().add(pos);
            }
            if (position.length == 1) {
                info.setPosition(pos);
            } else {
                info.setPosition(positionDAO.get(positionId));
            }
        } else {
            info.setPosition(null);
        }

        userDAO.update(info);
        MessageManager.notify(User.class, MessageEvent.MOVED, info);
        MessageManager.notify("user_position", MessageEvent.UPDATED, user, info.getPositions());
    }

    /**
     * @see com.zxtx.apps.user.UserService#usersOfPosition(java.lang.String,
     * int, int)
     */
    public PagedList<User> usersOfPosition(String positionId, String username, String name, int page, int pagesize) {
        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(name)) {
            return userPositionDAO.usersOfPosition(positionId, page, pagesize);
        } else if (StringUtils.isEmpty(username)) {
            return userPositionDAO.usersOfPosition(positionId, name, page, pagesize);
        } else {
            return userPositionDAO
                    .usersOfPosition(positionId, username, StringUtils.stripToEmpty(name), page, pagesize);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.user.service.UserEntityService#registerListener(java.lang.String,
     *      works.tonny.apps.user.service.UserUpdateListener)
     */
    // public synchronized void registerListener(String name, UserUpdateListener
    // listener) {
    // if (listeners == null) {
    // listeners = new LinkedHashMap<String, UserUpdateListener>();
    // }
    // listeners.put(name, listener);
    // }
    //
    // /**
    // * {@inheritDoc}
    // *
    // * @see
    // works.tonny.apps.user.service.UserEntityService#removeListener(java.lang.String)
    // */
    // public void removeListener(String name) {
    // if (listeners != null) {
    // listeners.remove(name);
    // }
    // }

    /**
     * @see com.zxtx.apps.user.UserService#usersOfDepartment(java.lang.String,
     * int, int)
     */
    public PagedList<User> usersOfDepartment(String departmentId, int page, int pagesize) {
        return userPositionDAO.usersOfDepartment(departmentId, page, pagesize);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.user.UserService#usersOfDepartment(java.lang.String,
     * java.lang.String, int, int)
     */
    public PagedList<User> usersOfDepartment(String departmentId, String name, int page, int pagesize) {
        return userPositionDAO.usersOfDepartment(departmentId, name, page, pagesize);
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public TitleDAO getTitleDAO() {
        return titleDAO;
    }

    public void setTitleDAO(TitleDAO titleDAO) {
        this.titleDAO = titleDAO;
    }

    public UserPositionDAO getUserPositionDAO() {
        return userPositionDAO;
    }

    public void setUserPositionDAO(UserPositionDAO userPositionDAO) {
        this.userPositionDAO = userPositionDAO;
    }

    /**
     * @see works.tonny.apps.user.UserService#usersOfTitle(java.lang.String,
     * int, int)
     */
    public PagedList<User> usersOfTitle(String titleName, int page, int pagesize) {
        return userinfoDAO.listByTitle(titleName, page, pagesize);
    }

    public UserInfoDAO getUserinfoDAO() {
        return userinfoDAO;
    }

    public void setUserinfoDAO(UserInfoDAO userinfoDAO) {
        this.userinfoDAO = userinfoDAO;
    }

    /**
     * @see works.tonny.apps.user.UserService#getLoginedUser()
     */
    @Override
    public LoginedUser getLoginedUser() {
        LoginedUser admin = ThreadLocalMap.getInstance().getObject(LoginedUser.class);
        if (admin == null) {
            throw new AuthException("user not logined ");
        }
        return admin;
    }

    /**
     * @see works.tonny.apps.user.UserService#createUserQuery()
     */
    @Override
    public UserQuery createUserQuery() {
        try {
            return new UserQueryImpl((BaseDAOSupport) PropertyUtils.getProperty(userDAO, "targetSource.target"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see com.zxtx.apps.user.service.UserEntityService#sysUsers()
     */
    public List<User> sysUsers() {
        return userDAO.sysUsers();
    }

    /**
     * @return the listeners
     */
    // public Map<String, UserUpdateListener> getListeners() {
    // return listeners;
    // }
    //
    // /**
    // * @param listeners the listeners to set
    // */
    // public void setListeners(Map<String, UserUpdateListener> listeners) {
    // // this.listeners = listeners;
    // Set<String> keySet = listeners.keySet();
    // for (String key : keySet) {
    // registerListener(key, listeners.get(key));
    // }
    // }

    /**
     * @return the userPropertiesDAO
     */
    public BaseDAOSupport<UserProperties> getUserPropertiesDAO() {
        return userPropertiesDAO;
    }

    /**
     * @param userPropertiesDAO the userPropertiesDAO to set
     */
    public void setUserPropertiesDAO(BaseDAOSupport<UserProperties> userPropertiesDAO) {
        this.userPropertiesDAO = userPropertiesDAO;
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

    public UserInfoEntityDAO getUserInfoEntityDAO() {
        return userInfoEntityDAO;
    }

    public void setUserInfoEntityDAO(UserInfoEntityDAO userInfoEntityDAO) {
        this.userInfoEntityDAO = userInfoEntityDAO;
    }

}
