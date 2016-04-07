package works.tonny.apps.user.service;

import java.util.List;

import works.tonny.apps.user.model.Department;

/**
 * 用户组织结构管理接口
 */
public interface DepartmentService {

	/**
	 * 根据id 获得部门对象
	 * 
	 * @param id
	 *            id
	 * @return 部门对象
	 */
	Department findById(String id);

	/**
	 * 根据名称获得部门对象
	 * 
	 * @param name
	 *            名称
	 * @return 部门对象
	 */
	List<Department> findByName(String name);

	/**
	 * 获得本部门的子部门
	 * 
	 * @param deptId
	 *            deptId
	 * @return 子部门
	 */
	List<Department> getSubs(String deptId);

	/**
	 * 得到本部门的父部门id (想想dn和id的关系）
	 * 
	 * @param deptId
	 *            deptId
	 * @return 父部门id 如果为空表示挂在部门树根上
	 */
	Department getParent(String deptId);

	/**
	 * 获得本部门所属于的单位部门对象
	 * 
	 * @param deptId
	 *            deptId
	 * @return
	 */
	Department getRoot(String deptId);

	/**
	 * 根据状态获取所有的根部门
	 * 
	 * @param userStatus
	 *            部门状态
	 * @return 部门列表
	 */
	List<Department> root();

	/**
	 * 创建高级查询对象
	 * 
	 * @return
	 */
	DepartmentQuery createQuery();

}
