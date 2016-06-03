package works.tonny.apps.user;

import works.tonny.apps.auth.DataOwnerAware;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Set;

/**
 * 系统后台用户
 */
@Entity
@Table(name = "u_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends AbstractUser implements DataOwnerAware<UserDataOwner> {
    /**
     * 所有未登录的用户都为匿名用户
     */
    public static final User ANONYMOUS = new User("anonymous", "匿名用户", "anonymous", "anonymous_!@#$%^&*()", new Date());

    /**
     * 创建用户，子用户管理用
     */
    protected String createUser;

    protected transient Set<Role> roles;

    private transient Set<UserDataOwner> owner;

    public User() {
        super();
    }

    public User(String id, String name, String username, String password, Date regTime) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.regTime = regTime;
    }

    public User(String name, String username, String password, Date regTime) {
        super();
        this.name = name;
        this.username = username;
        this.password = password;
        this.regTime = regTime;
    }

    /**
     * 判断用户是否某角色
     *
     * @param roleId
     * @return
     * @author tonny
     */
    public boolean isRole(String roleId) {
        for (Role role : roles) {
            if (role.getId().equals(roleId)) {
                return true;
            }
        }
        return false;
    }

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinTable(name = "u_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "data")
    public Set<UserDataOwner> getOwner() {
        return owner;
    }

    public void setOwner(Set<UserDataOwner> owner) {
        this.owner = owner;
    }

    @Override
    @Transient
    public Class getOwnerClass() {
        return UserDataOwner.class;
    }

    @Column(name = "create_user", length = 50)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
