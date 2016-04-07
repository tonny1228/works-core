package works.tonny.apps.user.service;

import works.tonny.apps.user.model.Position;

public interface PositionEntityService extends PositionService {

	/**
	 * 创建新的岗位
	 * 
	 * @param position 新建的岗位信息
	 * @param deptId 部门id
	 * @return 岗位id
	 */
	String create(Position position, String deptId);

	/**
	 * 更新岗位信息。
	 * 
	 * @param position 要修改的岗位属性
	 */
	void update(Position position);

	/**
	 * 移动岗位到新的部门
	 * 
	 * @param posId 要移动的岗位id
	 * @param newDeptId 目标父部门id，父部门要处于激活状态
	 * @return
	 */
	void move(String posId, String newDeptId);

	/**
	 * 删除岗位及人员关联关系
	 * 
	 * @param id 被删除的岗位id
	 */
	void remove(String id);

	/**
	 * 注册用户更新监听
	 * 
	 * @param name 监听的名称
	 * @param listener 监听器
	 */
//	void registerListener(String name, EntityUpdateListener<Position> listener);

	/**
	 * 删除监听器
	 * 
	 * @param name 监听器名称
	 */
//	void removeListener(String name);
}
