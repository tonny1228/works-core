package works.tonny.apps.user.dao;

// Generated 2012-12-3 13:14:29 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;
import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.User;
import works.tonny.apps.user.model.UserInfo;
import works.tonny.apps.user.model.UserProperties;

/**
 * DAO interface for domain model class UserInfo.
 *
 * @author Tonny Liu
 * @see entity.UserInfo
 */
public interface UserInfoDAO extends EntityDAO<UserInfo> {

    void save(UserProperties properties);

    /**
     * 分页查询UserInfo列表
     *
     * @param page     页码
     * @param pagesize 每页条数
     * @return 分页列表
     */
    @ListSupport(field = "position.id")
    PagedList<UserInfo> list(String positionId, int page, int pagesize);

    /**
     * @param titleName
     * @param page
     * @param pagesize
     * @return
     */
    @ListSupport(field = "title.name")
    PagedList<User> listByTitle(String titleName, int page, int pagesize);

}
