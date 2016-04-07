package works.tonny.apps.user.service;

import works.tonny.apps.Query;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.DepartmentType;

public interface DepartmentQuery extends Query<DepartmentQuery, Department> {
    /**
     * id＝
     *
     * @param id
     * @return
     */
    DepartmentQuery id(String id);

    /**
     * 名字模糊查询
     *
     * @param nameLike
     * @return
     */
    DepartmentQuery nameLike(String nameLike);

    /**
     * 所有的子部门
     *
     * @param id
     * @return
     */
    DepartmentQuery subs(String id);

    /**
     * 指定级别的子部门
     *
     * @param id
     * @param depth
     * @return
     */
    DepartmentQuery subs(String id, int depth);


    /**
     * 指定级别的子部门
     *
     * @param id
     * @param depth
     * @return
     */
    DepartmentQuery type(DepartmentType... types);
}
