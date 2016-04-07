package works.tonny.apps.user;

import org.llama.library.utils.ThreadLocalMap;
import works.tonny.apps.AbstractService;
import works.tonny.apps.exception.AuthException;
import works.tonny.apps.user.model.LoginedMember;

/**
 * 可用于权限控制的service，此service中可取得的登录用户信息
 *
 * @author tonny
 */
public abstract class AuthedAbstractService extends AbstractService {

    /**
     * 验证用户权限
     *
     * @param admin    用户
     * @param authCode 权限代码
     * @throws AuthException 没有权限异常
     */
    public static void validateAuth(String authCode) {
        LoginedUser admin = ThreadLocalMap.getInstance().getObject(LoginedUser.class);
        if (admin == null) {
            admin = UserServiceFactory.getInstance().getAuthService().login(User.ANONYMOUS.getUsername(), User.ANONYMOUS.getPassword());
            //throw new AuthException("user not logined ");
        }
        if (!admin.hasAuth(authCode)) {
            throw new AuthException("user " + admin.getUser().getUsername() + " can not access " + authCode);
        }
    }

    /**
     * 取得当前登录的用户
     *
     * @return
     */
    protected LoginedUser getLoginedUser() {
        LoginedUser admin = ThreadLocalMap.getInstance().getObject(LoginedUser.class);
        if (admin == null) {
            throw new AuthException("user not logined ");
        }
        return admin;
    }

    /**
     * 取得当前登录的用户
     *
     * @return
     */
    protected User getCurrentUser() {
        LoginedUser admin = ThreadLocalMap.getInstance().getObject(LoginedUser.class);
        if (admin == null) {
            throw new AuthException("user not logined ");
        }
        if (admin instanceof LoginedMember) {
            return (User) ((LoginedMember) (admin)).getUser();
        }
        return (User) admin.getUser();
    }
}
