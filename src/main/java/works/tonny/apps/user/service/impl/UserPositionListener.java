package works.tonny.apps.user.service.impl;

import org.llama.library.utils.PagedList;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageHandleException;
import works.tonny.apps.support.message.MessageListener;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.dao.UserInfoEntityDAO;
import works.tonny.apps.user.dao.UserPositionDAO;
import works.tonny.apps.user.model.Position;
import works.tonny.apps.user.model.UserInfoEntity;
import works.tonny.apps.user.model.UserPosition;

import java.util.List;

/**
 * Created by tonny on 2015/11/30.
 */
public class UserPositionListener implements MessageListener<Position> {
    //    UserInfoDAO userInfoDAO;
    UserPositionDAO userPositionDAO;
    UserInfoEntityDAO userInfoEntityDAO;

    public UserPositionListener() {
        MessageManager.register(Position.class, this, MessageEvent.BEFORE_DELETE);
    }

    @Override
    public void onRecieved(MessageEvent<Position> message) throws MessageHandleException {
        Position position = message.getData();
        List<UserPosition> userPositions = userPositionDAO.userOfPosition(position.getId());
        userPositionDAO.deleteAll(userPositions);
        PagedList<UserInfoEntity> list = userInfoEntityDAO.list(position.getId(), 1, 200);
        for (UserInfoEntity userInfo : list) {
            List<UserPosition> us = userPositionDAO.list(userInfo.getId());
            if (!us.isEmpty()) {
                userInfo.setPositionId(us.get(0).getId().getPosition().getId());
                userInfoEntityDAO.update(userInfo);
            } else {
                userInfo.setPositionId(null);
                userInfoEntityDAO.update(userInfo);
            }
        }
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    public UserPositionDAO getUserPositionDAO() {
        return userPositionDAO;
    }

    public void setUserPositionDAO(UserPositionDAO userPositionDAO) {
        this.userPositionDAO = userPositionDAO;
    }

    public UserInfoEntityDAO getUserInfoEntityDAO() {
        return userInfoEntityDAO;
    }

    public void setUserInfoEntityDAO(UserInfoEntityDAO userInfoEntityDAO) {
        this.userInfoEntityDAO = userInfoEntityDAO;
    }
}
