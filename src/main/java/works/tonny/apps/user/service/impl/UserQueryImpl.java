/**
 * <p>
 * <p/>
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 *
 * @date 2015-1-21 下午2:39:16
 * @author tonny
 */
package works.tonny.apps.user.service.impl;

import works.tonny.apps.impl.AbstractCriteriaQuery;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.user.User;
import works.tonny.apps.user.UserQuery;
import works.tonny.apps.user.UserStatus;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tonny
 * @version 1.0.0
 */
public class UserQueryImpl extends AbstractCriteriaQuery<UserQuery, User> implements UserQuery {
    private String id;

    private String name;

    private String username;

    private String email;

    private String telNo;

    private String mobileNo;

    private String sex;

    private UserStatus status;

    private String positionId;

    private String departmentId;

    private String departmentIdLike;

    private String roleId;

    private String order;

    private String jobName;

    private String jobLevelName;

    /**
     *
     */
    public UserQueryImpl(BaseDAOSupport<User> dao) {
        this.dao = dao;
    }

    /**
     * @see works.tonny.apps.impl.AbstractCriteriaQuery#doBuild()
     */
    @Override
    protected void doBuild() {
        // addParameter(name, "name", ListSupport.MUST, ListSupport.LIKE);
        // addParameter(username, "username", ListSupport.MUST,
        // ListSupport.EQUALS);
        // addParameter(email, "email", ListSupport.MUST, ListSupport.EQUALS);
        // addParameter(id, "id", ListSupport.MUST, ListSupport.EQUALS);
        // addParameter(sex, "sex", ListSupport.MUST, ListSupport.EQUALS);
        // addParameter(telNo, "telNo", ListSupport.MUST, ListSupport.EQUALS);
        // addParameter(status, "status", ListSupport.MUST, ListSupport.EQUALS);
        // addParameter(mobileNo, "mobileNo", ListSupport.MUST,
        // ListSupport.EQUALS);
        // addParameter(positionId, "position.id", ListSupport.MUST,
        // ListSupport.EQUALS);
        // addParameter(departmentId, "position.department.id",
        // ListSupport.MUST, ListSupport.EQUALS);
        // addParameter(roleId, "position.department.id", ListSupport.MUST,
        // ListSupport.EQUALS);
        if (positionId != null || departmentId != null || departmentIdLike != null) {
            setQueryName("User.userinfoquery");
        } else {
            setQueryName("User.query");
        }
        addParameter(name != null, "name", new String[]{"name"}, new Object[]{name});
        addParameter(username != null, "username", new String[]{"username"}, new Object[]{username});
        addParameter(email != null, "email", new String[]{"email"}, new Object[]{email});
        addParameter(id != null, "id", new String[]{"id"}, new Object[]{id});
        addParameter(sex != null, "sex", new String[]{"sex"}, new Object[]{sex});
        addParameter(telNo != null, "telNo", new String[]{"telNo"}, new Object[]{telNo});
        addParameter(status != null, "status", new String[]{"status"}, new Object[]{String.valueOf(status)});
        addParameter(mobileNo != null, "mobileNo", new String[]{"mobileNo"}, new Object[]{mobileNo});
        addParameter(positionId != null, "position", new String[]{"position"}, new Object[]{positionId});
        addParameter(departmentId != null, "department", new String[]{"department"}, new Object[]{departmentId});
        addParameter(departmentIdLike != null, "departmentLike", new String[]{"department"}, new Object[]{departmentIdLike});
        addParameter(jobLevelName != null, "jobLevelName", new String[]{"jobLevelName"}, new Object[]{jobLevelName});
        addParameter(jobName != null, "jobName", new String[]{"jobLevelName"}, new Object[]{jobName});
        addParameter(roleId != null, "role", new String[]{"roleId"}, new Object[]{roleId});
        addParameter(order != null, order, null, null);
    }

    /**
     * @see works.tonny.apps.user.UserQuery#nameLike(java.lang.String)
     */
    @Override
    public UserQuery nameLike(String name) {
        this.name = name;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#username(java.lang.String)
     */
    @Override
    public UserQuery username(String username) {
        this.username = username;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#id(java.lang.String)
     */
    @Override
    public UserQuery id(String id) {
        this.id = id;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#telNo(java.lang.String)
     */
    @Override
    public UserQuery telNo(String telNo) {
        this.telNo = telNo;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#mobileNo(java.lang.String)
     */
    @Override
    public UserQuery mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#email(java.lang.String)
     */
    @Override
    public UserQuery email(String email) {
        this.email = email;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#sex(java.lang.String)
     */
    @Override
    public UserQuery sex(String sex) {
        this.sex = sex;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#status(works.tonny.apps.user.UserStatus)
     */
    @Override
    public UserQuery status(UserStatus status) {
        this.status = status;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#position(java.lang.String)
     */
    @Override
    public UserQuery position(String positionId) {
        this.positionId = positionId;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#department(java.lang.String)
     */
    @Override
    public UserQuery department(String departmentId) {
        this.departmentId = departmentId;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#departmentLike(java.lang.String)
     */
    @Override
    public UserQuery departmentLike(String departmentId) {
        this.departmentIdLike = departmentId;
        return this;
    }

    @Override
    public UserQuery jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    @Override
    public UserQuery jobLevelName(String jobLevelName) {
        this.jobLevelName = jobLevelName;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#role(java.lang.String)
     */
    @Override
    public UserQuery role(String roleId) {
        this.roleId = roleId;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#orderByUsername(works.tonny.apps.Query.Direction)
     */
    @Override
    public UserQuery orderByUsername(works.tonny.apps.Query.Direction direction) {
        this.order = "orderByUsername" + direction;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#orderByName(works.tonny.apps.Query.Direction)
     */
    @Override
    public UserQuery orderByName(works.tonny.apps.Query.Direction direction) {
        this.order = "orderByName" + direction;
        return this;
    }

    /**
     * @see works.tonny.apps.user.UserQuery#orderByOrderNo(works.tonny.apps.Query.Direction)
     */
    @Override
    public UserQuery orderByOrderNo(works.tonny.apps.Query.Direction direction) {
        this.order = "orderByOrderNo" + direction;
        return this;
    }

}
