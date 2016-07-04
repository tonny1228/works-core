package works.tonny.apps.user.web;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PagedList;
import works.tonny.apps.Query;
import works.tonny.apps.user.User;

/**
 * 子用户管理
 * Created by Tonny on 2016/6/12.
 */
public class SubUserAction extends UserAction {

    /**
     * 添加子用户
     *
     * @return
     */
    public String save() {
        user.setCreateUser(loginedUser().getUser().getUsername());
        return super.save();
    }


    /**
     * 子用户列表，列出此用户创建的用户
     *
     * @return
     */
    public String list() {
        PagedList<User> users = userService.createUserQuery().createUser(loginedUser().getUser().getUsername())
                .orderByName(Query.Direction.ASC).listRange(getOffset(), getLimit());
        request.setAttribute("list", users);
        if (StringUtils.isNotEmpty(forward)) {
            return "forward";
        }
        return SUCCESS;
    }


}
