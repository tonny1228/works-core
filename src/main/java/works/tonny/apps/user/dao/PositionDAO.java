package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.support.SQLMappingListSupport;
import works.tonny.apps.user.model.Position;

/**
 * 岗位存储、查询接口
 * 
 * @ClassName: PositionDAO
 * @Description: 岗位信息查询、存储
 * @author Tonny
 * @date 2011-10-12 下午3:14:04
 * @version 1.0
 */
public interface PositionDAO extends EntityDAO<Position> {
	/**
	 * 查询部门下设置的岗位
	 * 
	 * @param deptId 部门编号
	 * @return 岗位列表
	 */
	@ListSupport(field = { "department.id" }, order = "jobLevel.level desc")
	List<Position> listOfDept(String deptId);

	/**
	 * 获得下一个部门的序号
	 * 
	 * @param parentId 父部门，可为空，代表根节点
	 * @return 最大
	 */
	@SQLMappingListSupport(name = "Position.orderNo")
	int maxOrderNo(String deptId);

	/**
	 * 查询岗位下设置的岗位
	 * 
	 * @param id 岗位编号
	 * @return 岗位列表
	 */
	@ListSupport(field = "parentId", order = { "department.idLayer", "jobLevel.level desc" })
	List<Position> subs(String id);

	/**
	 * 按部门和岗位名称查询岗位
	 * 
	 * @param posName 岗位名称
	 * @param deptName 部门名称
	 * @return
	 */
	@ListSupport(field = { "name", "department.name" })
	List<Position> list(String posName, String deptName);

	/**
	 * 查询岗位下设置所有层级的子岗位
	 * 
	 * @param id 岗位编号
	 * @return 岗位列表
	 */
	@ListSupport(field = "idLayer", operator = ListSupport.LIKE, order = "jobLevel.level desc")
	List<Position> allSubs(String id);

}
