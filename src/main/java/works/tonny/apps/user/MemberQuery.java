/**
 *
 */
package works.tonny.apps.user;

import works.tonny.apps.Query;

/**
 * @author 祥栋
 */
public interface MemberQuery extends Query<MemberQuery, Member> {

    /**
     * 姓名匹配
     *
     * @param name
     * @return
     * @author tonny
     */
    MemberQuery name(String name);

    /**
     * 登录名等于
     *
     * @param username
     * @return
     * @author tonny
     */
    MemberQuery username(String username);

    /**
     * 邮箱匹配
     *
     * @param email
     * @return
     * @author tonny
     */
    MemberQuery email(String email);

    /**
     * 会员后台用户id等于
     *
     * @param userId
     * @return
     * @author tonny
     */
    MemberQuery userId(String userId);

    /**
     * gender等于
     *
     * @param gender
     * @return
     * @author tonny
     */
    MemberQuery gender(String gender);

    /**
     * id等于
     *
     * @param userId
     * @return
     * @author tonny
     */
    MemberQuery idNo(String idNo);

    /**
     * id等于
     *
     * @param no
     * @return
     * @author tonny
     */
    MemberQuery no(String no);

    /**
     * id等于
     *
     * @param userId
     * @return
     * @author tonny
     */
    MemberQuery mobile(String mobile);

    /**
     * info匹配
     *
     * @param info
     * @return
     * @author tonny
     */
    MemberQuery info(String info);

    /**
     * 按用户名排序
     *
     * @param direction
     * @return
     * @author tonny
     */
    MemberQuery orderByUsername(Direction direction);

    /**
     * 按姓名排序
     *
     * @param direction
     * @return
     * @author tonny
     */
    MemberQuery orderByName(Direction direction);

    /**
     * 按邮箱匹配
     *
     * @param direction
     * @return
     * @author tonny
     */
    MemberQuery orderByEmail(Direction direction);
}