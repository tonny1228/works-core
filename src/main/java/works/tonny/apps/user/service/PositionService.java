package works.tonny.apps.user.service;

import java.util.List;

import works.tonny.apps.user.model.Position;

public interface PositionService {

	/**
	 * 根据id 获得岗位对象
	 * 
	 * @param id id
	 * @return 岗位对象
	 */
	Position findById(String id);

	/**
	 * 根据name 获得岗位对象
	 * 
	 * @param posName 岗位名称
	 * @param deptName 部门名称
	 * @return 岗位对象
	 */
	Position findByName(String posName, String deptName);

	/**
	 * 获得本岗位的子岗位
	 * 
	 * @param pId 岗位Id
	 * @return 子岗位
	 */
	List<Position> subs(String pId);

	/**
	 * 获得部门获得部门下的岗位
	 * 
	 * @param deptId deptId
	 * @return 部门下的角色id 存在一个String数组中
	 */
	List<Position> getPositions(String deptId);

	/**
	 * 获得部门的岗位或所有上级部门的岗位
	 * 
	 * @param deptId deptId
	 * @return 部门下的角色id 存在一个String数组中
	 */
	List<Position> parentPositions(String deptId);

}
