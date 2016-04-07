package works.tonny.apps.userevent;


import javax.persistence.*;
import java.util.Date;


/**
 * 用户操作事件
 *
 * @author tonny
 */
@Entity
@Table(name = "ue_operating")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEventLog extends UserEvent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 事件标题
     */
    private String title;

    /**
     * 事件类型
     */
    private String type;

    /**
     * 事件描述
     */
    private String detail;

    /**
     * 关联数据编号
     */
    private String dataId;


    @Column(length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = 20)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(length = 500)
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Column(name = "data_id", length = 50)
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }


}