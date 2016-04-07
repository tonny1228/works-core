package works.tonny.apps.user.service;

import java.util.Map;

import works.tonny.apps.user.model.Department;

/**
 * 用户组织结构管理接口
 * 
 */
public interface DepartmentEntityService extends DepartmentService {
	/**
	 * 
	 * 创建新的部门
	 * 
	 * @param dept 新建的部门信息
	 * @return 部门id
	 */
	String create(Department dept);

	/**
	 * 
	 * 在父部门下创建新的部门
	 * 
	 * @param dept 新建的部门信息
	 * @param parentId 父部门编号
	 * @param buId 业务单位父id
	 * @return 部门id
	 */
	String create(Department dept, String parentId, Map<String, String> buId);

	/**
	 * 
	 * 更新部门信息。
	 * 
	 * @param dept 要修改的部门属性
	 */
	void update(Department dept, Map<String, String> buId);

	/**
	 * 
	 * 将部门移动新的部门中
	 * 
	 * @param deptId 要移动的部门id
	 * @param newParentDeptId 目标父部门id，父部门要处于激活状态
	 * @return
	 */
	void move(String deptId, String newParentDeptId);

	/**
	 * 删除部门及子部门、岗位、用户对应关系
	 * 
	 * @param uid 被删除的部门id
	 */
	void remove(String uid);
}
