package works.tonny.apps.user.model;

import works.tonny.apps.user.User;

import javax.persistence.Embeddable;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by tonny on 2015/11/30.
 */

@Embeddable
@IdClass(UserPosition.class)
public class UserPositionID implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private User user;

    private Position position;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "position_id")
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof UserPositionID)) {
            return false;
        }
        UserPositionID id = (UserPositionID) obj;
        if (user == null || position == null || user.getId() == null || position.getId() == null
                || id.getUser() == null || id.getPosition().getId() == null) {
            return false;
        }
        return user.getId().equals(id.getUser().getId()) && position.getId().equals(id.getPosition().getId());
    }

    /**
     * {@inheritDoc}
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (user.getId() + position.getId()).hashCode();
    }

}
