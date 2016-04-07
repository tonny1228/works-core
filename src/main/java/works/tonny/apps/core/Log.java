package works.tonny.apps.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import works.tonny.apps.Entity;

import javax.persistence.*;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "s_log")
public class Log extends Entity {

    /**
     * nullable persistent field
     */
    private Date logTime;

    /**
     * nullable persistent field
     */
    private String admin;

    /**
     * nullable persistent field
     */
    private String catalog;

    /**
     * nullable persistent field
     */
    private String action;

    /**
     * nullable persistent field
     */
    private String objectId;

    /**
     * nullable persistent field
     */
    private String info;

    /**
     * full constructor
     */
    public Log(String id, Date logTime, String admin, String catalog, String action, String objectId, String info) {
        this.id = id;
        this.logTime = logTime;
        this.catalog = catalog;
        this.admin = admin;
        this.action = action;
        this.objectId = objectId;
        this.info = info;
    }

    public Log(Date logTime, String admin, String catalog, String objectId, String info) {

        this.logTime = logTime;
        this.admin = admin;
        this.catalog = catalog;
        this.objectId = objectId;
        this.info = info;
    }

    /**
     * default constructor
     */
    public Log() {
    }

    /**
     * minimal constructor
     */
    public Log(String id) {
        this.id = id;
    }

    /**
     * @hibernate.id generator-class="assigned" type="java.lang.Long"
     * column="id"
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(length = 50)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @hibernate.property column="log_time" length="7"
     */
    @Column(name = "log_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLogTime() {
        return this.logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    /**
     * @hibernate.property column="admin" length="32"
     */
    @Column(length = 50)
    public String getAdmin() {
        return this.admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }


    /**
     * @hibernate.property column="action" length="32"
     */
    @Column(length = 50)
    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @hibernate.property column="object_id" length="32"
     */
    @Column(length = 100, name = "object_id")
    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * @hibernate.property column="info" length="200"
     */
    @Column(length = 1000)
    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof Log))
            return false;
        Log castOther = (Log) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

}
