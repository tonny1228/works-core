/**
 * <p>
 * <p/>
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 *
 * @date 2015-1-21 下午2:18:29
 * @author tonny
 */
package works.tonny.apps.user;

import works.tonny.apps.Query;

/**
 * <p>
 * 符合查询用户信息
 * </p>
 *
 * @author tonny
 * @version 1.0.0
 */
public interface UserQuery extends Query<UserQuery, User> {
    /**
     * 姓名包含
     *
     * @param name
     * @return
     * @author tonny
     */
    UserQuery nameLike(String name);

    /**
     * 登录名等于
     *
     * @param username
     * @return
     */
    UserQuery username(String username);

    /**
     * id等于
     *
     * @param id
     * @return
     */
    UserQuery id(String id);

    /**
     * 电话等于
     *
     * @param telNo
     * @return
     */
    UserQuery telNo(String telNo);

    /**
     * 手机等于
     *
     * @param mobileNo
     * @return
     */
    UserQuery mobileNo(String mobileNo);

    /**
     * 邮箱等于
     *
     * @param email
     * @return
     */
    UserQuery email(String email);

    /**
     * 性别等于
     *
     * @param sex
     * @return
     */
    UserQuery sex(String sex);

    /**
     * 状态等于
     *
     * @param status
     * @return
     */
    UserQuery status(UserStatus status);

    /*
    UserQuery bornAfter(Date date);

    UserQuery bornBefore(Date date);

    UserQuery bornBetween(Date fromDate, Date endDate);
*/

    /**
     * 岗位等于
     *
     * @param positionId
     * @return
     */
    UserQuery position(String positionId);

    /**
     * 部门等于
     *
     * @param departmentId
     * @return
     */
    UserQuery department(String departmentId);

    /**
     * 属于某个部门的子部门
     *
     * @param departmentId
     * @return
     */
    UserQuery departmentLike(String departmentId);


    /**
     * 职务名称等于
     *
     * @param departmentId
     * @return
     */
    UserQuery jobName(String jobName);

    /**
     * 职级名称
     *
     * @param departmentId
     * @return
     */
    UserQuery jobLevelName(String jobLevelName);


    /**
     * 属于某角色
     *
     * @param roleId
     * @return
     */
    UserQuery role(String roleId);

    /**
     * 按用户名排序
     *
     * @param direction
     * @return
     */
    UserQuery orderByUsername(Direction direction);

    /**
     * 按姓名排序
     *
     * @param direction
     * @return
     */
    UserQuery orderByName(Direction direction);

    /**
     * 按序号排序
     *
     * @param direction
     * @return
     */
    UserQuery orderByOrderNo(Direction direction);

}
