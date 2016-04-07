package works.tonny.apps.user.dao;

import org.llama.library.utils.PagedList;
import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.model.UserInfoEntity;

/**
 * Created by tonny on 2015/9/10.
 */
public interface UserInfoEntityDAO extends EntityDAO<UserInfoEntity> {

    @ListSupport(field = "positionId")
    PagedList<UserInfoEntity> list(String positionId, int page, int pagesize);
}
