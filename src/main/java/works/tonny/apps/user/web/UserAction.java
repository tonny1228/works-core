package works.tonny.apps.user.web;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.cryptography.Encryptable;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.PropertiesUtils;
import works.tonny.apps.Query;
import works.tonny.apps.exception.AuthException;
import works.tonny.apps.exception.DataException;
import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.Role;
import works.tonny.apps.user.User;
import works.tonny.apps.user.model.UserInfo;
import works.tonny.apps.user.service.AuthEntityService;
import works.tonny.apps.user.service.RoleEntityService;
import works.tonny.apps.user.service.TitleService;
import works.tonny.apps.user.service.UserEntityService;

import java.util.List;

public class UserAction extends AuthedAction {

    /**
     * 权限服务
     */
    protected AuthEntityService authService;

    /**
     * 用户服务
     */
    protected UserEntityService userService;

    /**
     * 用户服务
     */
    protected RoleEntityService roleService;

    private Encryptable encryptable;

    protected TitleService titleService;

    private User user;

    private UserInfo userinfo;

    private String id;

    private String positionId;

    private String[] roleId;

    /**
     * 用户列表与查询用户
     *
     * @return
     * @throws AuthException
     */
    public String list() throws AuthException {
        PagedList<User> list = userService.list(getParameter("name"), getParameter("username"), getPage(),
                getPagesize());
        request.setAttribute("list", list);
        if (StringUtils.isNotEmpty(forward)) {
            return "forward";
        }
        return SUCCESS;
    }


    public String subList() {
        PagedList<User> users = userService.createUserQuery().createUser(loginedUser().getUser().getUsername()).orderByName(Query.Direction.ASC).listRange(getOffset(), getLimit());
        request.setAttribute("list", users);
        if (StringUtils.isNotEmpty(forward)) {
            return "forward";
        }
        return SUCCESS;
    }


    /**
     * 用户列表与查询用户
     *
     * @return
     * @throws AuthException
     */
    public String sysUsers() throws AuthException {
        List<User> list = userService.sysUsers();
        request.setAttribute("list", list);
        return SUCCESS;
    }

    /**
     * 用户列表与查询用户
     *
     * @return
     * @throws AuthException
     */
    public String positionList() {
        PagedList<User> list = userService.usersOfPosition(positionId, getParameter("username"), getParameter("name"),
                getPage(), getPagesize());
        request.setAttribute("list", list);
        return SUCCESS;
    }

    /**
     * 用户列表与查询用户
     *
     * @return
     * @throws AuthException
     */
    public String departmentList() {
        PagedList<User> list = userService.usersOfDepartment(getParameter("departmentId"), getPage(), getPagesize());
        request.setAttribute("list", list);
        return SUCCESS;
    }

    /**
     * 用户列表与查询用户
     *
     * @return
     * @throws AuthException
     */
    public String deleteUserOfPosition() {
        userService.removeUserFromPosition(id, positionId);
        return SUCCESS;
    }

    /**
     * 添加用户，当用户信息为空时到添加页面
     *
     * @return
     * @throws AuthException
     */
    public String add() throws AuthException {
        return SUCCESS;
    }

    /**
     * 检查用户名是否已经被使用
     *
     * @return
     */
    public String check() {
        String username = getParameter("username");
        boolean exits = false;
        if (StringUtils.isNotEmpty(username)) {
            User user = userService.createUserQuery().username(username).singleResult();
            exits = user != null;
        }
        request.setAttribute("exist", exits);
        return SUCCESS;
    }


    /**
     * 编辑用户
     *
     * @return
     */
    public String edit() {
        user = userService.get(id);

        if (user == null) {
            request.setAttribute(ERROR_MESSAGE, "用户不存在");
            return ERROR;
        }
        request.setAttribute("user", user);
        return SUCCESS;
    }

    /**
     * 编辑用户
     *
     * @return
     */
    /**
     * @return
     */
    public String editUserinfo() {
        user = userService.getUser(id);
        if (user == null) {
            request.setAttribute(ERROR_MESSAGE, "用户不存在");
            return ERROR;
        }
        request.setAttribute("titles", titleService.list());

        return SUCCESS;
    }

    /**
     * 编辑用户
     *
     * @return
     */
    /**
     * @return
     */
    public String updateUserinfo() {
        userService.updateUserinfo(id, getParameter("title"), positionId, request.getParameterValues("position"));
        return SUCCESS;
    }

    /**
     * 编辑用户
     *
     * @return
     */
    public String editMine() {
        userService.updateMine(user);
        PropertiesUtils.copyProperties(loginedUser().getUser(), user, new String[]{"name", "email", "info",
                "birthday", "telNo", "mobileNo", "email", "address", "zip", "sex"});
        return SUCCESS;
    }

    /**
     * 编辑用户
     *
     * @return
     */
    public String me() {
        request.setAttribute("user", loginedUser().getUser());
        return SUCCESS;
    }

    /**
     * 编辑用户角色
     *
     * @return
     */
    public String editUserRole() {
        List<Role> roles = roleService.list(getParameter("name"));
        User user = userService.getUser(id);
        request.setAttribute("roles", roles);
        request.setAttribute("user", user);
        return SUCCESS;
    }

    /**
     * 更新用户角色
     *
     * @return
     */
    public String updateUserRole() {
        userService.updateRole(id, roleId);
        return SUCCESS;
    }


    public String saveSubUser(){
        user.setCreateUser(loginedUser().getUser().getUsername());
        return save();
    }

    /**
     * 编辑用户
     *
     * @return
     */
    public String save() {
        if (user != null && StringUtils.isNotBlank(user.getId())) {
            userService.update(user);
            if (user.getId().equals(loginedUser().getUser().getId())) {
                PropertiesUtils.copyProperties(loginedUser().getUser(), user, new String[]{"name", "email", "info",
                        "birthday", "telNo", "mobileNo", "email", "address", "zip", "sex"});
                return "me";
            }
        } else if (StringUtils.isNotEmpty(positionId)) {
            UserInfo userinfo = new UserInfo();
            try {
                BeanUtils.copyProperties(userinfo, user);
            } catch (Exception e) {
                throw new DataException(e);
            }
            userService.create(userinfo, positionId);
            user = userinfo;
        } else {
            userService.create(user);
        }
        return SUCCESS;
    }

    /**
     * 重置密码
     *
     * @return
     */
    public String resetPassword() {
        User user = userService.getUser(id);
        user.setPassword("1234");
        userService.update(user);
        return SUCCESS;
    }

    /**
     * 修改密码
     *
     * @return
     * @author tonny
     */
    public String updateMyPassword() {
        User user = userService.getUser(loginedUser().getUser().getId());
        if (!user.getPassword().equals(encryptable.encrypt(getParameter("oldpassword")))) {
            throw new AuthException("原密码不正确");
        }
        user.setPassword(getParameter("newpassword"));
        userService.update(user);
        return SUCCESS;
    }

    /**
     * 锁定用户
     *
     * @return
     * @Title: lock
     * @date 2011-11-30 下午5:08:42
     * @author tonny
     * @version 1.0
     */
    public String lock() {
        userService.lock(id);
        return SUCCESS;
    }

    /**
     * 解锁用户
     *
     * @return
     * @Title: lock
     * @date 2011-11-30 下午5:08:42
     * @author tonny
     * @version 1.0
     */
    public String unlock() {
        userService.unlock(id);
        return SUCCESS;
    }

    /**
     * 删除用户信息
     *
     * @return
     * @throws AuthException
     */
    public String delete() throws AuthException {
        userService.delete(id.split(","));
        return SUCCESS;
    }

    public AuthEntityService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthEntityService authService) {
        this.authService = authService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public void setRoleService(RoleEntityService roleService) {
        this.roleService = roleService;
    }

    public String getId() {
        return id;
    }

    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }

    public RoleEntityService getRoleService() {
        return roleService;
    }

    public String getPositionId() {
        return positionId;
    }

    public String[] getRoleId() {
        return roleId;
    }

    public UserEntityService getUserService() {
        return userService;
    }

    public void setUserService(UserEntityService userService) {
        this.userService = userService;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public TitleService getTitleService() {
        return titleService;
    }

    public void setTitleService(TitleService titleService) {
        this.titleService = titleService;
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
}
