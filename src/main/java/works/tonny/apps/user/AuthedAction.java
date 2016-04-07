package works.tonny.apps.user;

import org.apache.commons.lang.BooleanUtils;
import org.llama.library.utils.ThreadLocalMap;
import works.tonny.apps.exception.AuthException;
import works.tonny.apps.support.CommonAction;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 通用struts2 action，实现取request和session
 *
 * @author 刘祥栋
 */
public class AuthedAction extends CommonAction {
    protected String url;

    private UserAutoLogin userAutoLogin;

    /**
     * 获得当前登录用户
     *
     * @return
     * @throws AuthException 用户没有登录则抛出异常
     */
    public LoginedUser loginedUser() {
        if (session == null) {
            return null;
        }
        //		LoginedUser user = (LoginedUser) session.get(LoginAction.USER_SESSION);
        LoginedUser user = ThreadLocalMap.getInstance().getObject(LoginedUser.class);
        if (user == null && userAutoLogin != null) {
            user = userAutoLogin.login(request);
        }
        return user;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        super.setSession(session);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl() {
        try {
            this.url = URLEncoder.encode(
                    request.getRequestURL().toString()
                            + BooleanUtils.toString(request.getQueryString() == null, "",
                            "?" + request.getQueryString()), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setUserAutoLogin(UserAutoLogin userAutoLogin) {
        this.userAutoLogin = userAutoLogin;
    }
}
