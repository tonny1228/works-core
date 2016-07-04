package works.tonny.apps.user.web;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PagedList;
import works.tonny.apps.Query;
import works.tonny.apps.user.User;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.UserInfo;

/**
 * 子管理单元用户管理，只管理本部门所在的管理单元及其子部门、管理单元的所有用户
 * Created by Tonny on 2016/6/12.
 */
public class BusinessUnitUserAction extends UserAction {

    /**
     * 子用户列表，列出此用户创建的用户
     *
     * @return
     */
    public String list() {
        Department businessUnit = orgnizationHelper.getBusinessUnit((UserInfo) loginedUser().getUser());
        PagedList<User> users = userService.createUserQuery().departmentLike(businessUnit.getId())
                .orderByName(Query.Direction.ASC).listRange(getOffset(), getLimit());
        request.setAttribute("list", users);
        if (StringUtils.isNotEmpty(forward)) {
            return "forward";
        }
        return SUCCESS;
    }

}
