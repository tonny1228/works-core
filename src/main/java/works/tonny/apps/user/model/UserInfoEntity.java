package works.tonny.apps.user.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by tonny on 2015/9/10.
 */
@Entity()
@Table(name = "u_user_info")
public class UserInfoEntity {
    private String id;

    private String positionId;

    private String titleId;

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "assigned")
    @Column(length = 50, name = "user_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(length = 50, name = "position_id")
    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @Column(length = 50, name = "title_id")
    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }
}
