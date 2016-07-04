/**
 * @Title: UserPrivilegeListener.java
 * @Package works.tonny.user.service.impl
 * @author Tonny
 * @date 2011-12-13 下午8:20:51
 */
package works.tonny.apps.user.service.impl;

import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageHandleException;
import works.tonny.apps.support.message.MessageListener;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.User;
import works.tonny.apps.user.service.AuthEntityService;

/**
 * @ClassName: UserPrivilegeListener
 * @Description:
 * @author Tonny
 * @date 2011-12-13 下午8:20:51
 * @version 1.0
 */

/**
 * @ClassName: UserPrivilegeListener
 * @Description:
 * @author Tonny
 * @date 2011-12-13 下午8:21:03
 * @version 1.0
 */
public class UserPrivilegeListener implements MessageListener<User> {

    private AuthEntityService authService;

    /**
     *
     */
    public UserPrivilegeListener() {
        MessageManager.register(User.class, this, MessageEvent.DELETED);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.support.message.MessageListener#onRecieved(works.tonny.apps.support.message.MessageEvent)
     */
    public void onRecieved(MessageEvent<User> message) throws MessageHandleException {
        User user = message.getData();
        authService.updateUserPrivilege(user.getId(), new String[]{});
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public AuthEntityService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthEntityService authService) {
        this.authService = authService;
    }

}
