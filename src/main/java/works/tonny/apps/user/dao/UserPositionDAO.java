/**
 *
 */
package works.tonny.apps.user.dao;

import org.llama.library.utils.PagedList;
import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.support.SQLMappingListSupport;
import works.tonny.apps.user.User;
import works.tonny.apps.user.model.UserPosition;

import java.util.List;

/**
 * @author 祥栋
 * @version 1.0.0
 * @date 2012-12-11
 */
public interface UserPositionDAO extends EntityDAO<UserPosition> {
    /**
     * 查询用户岗位信息
     *
     * @param userId 用户编号
     * @return
     */
    @ListSupport(field = "id.user.id")
    List<UserPosition> list(String userId);

    /**
     * 查询岗位下所有用户
     *
     * @param positionId 岗位编号
     * @return
     */
    @ListSupport(field = "id.position.id")
    List<UserPosition> userOfPosition(String positionId);

    /**
     * 按岗位列出用户
     *
     * @param positionId 岗位编号
     * @param page       页码
     * @param pagesize   页码大小
     * @return
     */
    @SQLMappingListSupport(name = "Position.userlist", appender = "order")
    PagedList<User> usersOfPosition(String positionId, int page, int pagesize);

    /**
     * 按岗位列出用户
     *
     * @param positionId 岗位编号
     * @param page       页码
     * @param pagesize   页码大小
     * @return
     */
    @SQLMappingListSupport(name = "Position.userlist", appender = {"name", "order"})
    PagedList<User> usersOfPosition(String positionId, String name, int page, int pagesize);

    /**
     * 按岗位列出用户
     *
     * @param positionId 岗位编号
     * @param page       页码
     * @param pagesize   页码大小
     * @return
     */
    @SQLMappingListSupport(name = "Position.userlist", appender = {"username", "name", "order"})
    PagedList<User> usersOfPosition(String positionId, String username, String name, int page, int pagesize);

    /**
     * 按岗位列出用户
     *
     * @param departmentId 岗位编号
     * @param page         页码
     * @param pagesize     页码大小
     * @return
     */
    @SQLMappingListSupport(name = "Position.deptuserlist")
    PagedList<User> usersOfDepartment(String departmentId, int page, int pagesize);

    /**
     * 按岗位列出用户
     *
     * @param departmentId 岗位编号
     * @param name         姓名
     * @param page         页码
     * @param pagesize     页码大小
     * @return
     */
    @SQLMappingListSupport(name = "Position.deptuserlist", appender = "name")
    PagedList<User> usersOfDepartment(String departmentId, String name, int page, int pagesize);
}
