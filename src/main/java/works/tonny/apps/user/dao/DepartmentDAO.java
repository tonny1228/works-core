package works.tonny.apps.user.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.support.SQLMappingListSupport;
import works.tonny.apps.user.model.Department;

/**
 * 部门信息dao,部门信息查询存储
 * 
 * @author tonny
 */
public interface DepartmentDAO extends EntityDAO<Department> {
	/**
	 * 根据部门名字模糊查询会员列表
	 * 
	 * @param name 部门名字
	 * @param page 页码
	 * @param pagesize 每页大小
	 * @return 部门列表
	 */
	@ListSupport(field = "name", operator = ListSupport.LIKE, order = { "treeNode.depth", "treeNode.orderNo" })
	List<Department> list(String name);

	/**
	 * 查询大于某状态的所有根单位
	 * 
	 * @param status 部门状态
	 * @return 部门列表
	 */
	@ListSupport(defaults = "treeNode.depth=0", order = { "treeNode.orderNo" })
	List<Department> root();

	// /**
	// * 获得下一个部门的序号
	// *
	// * @param parentId 父部门，可为空，代表根节点
	// * @return 最大
	// */
	// @SQLMappingListSupport(name = "Department.orderNo", appender = { "root" })
	// Integer maxRootOrderNo();

	// /**
	// * 获得下一个部门的序号
	// *
	// * @param parentId 父部门，可为空，代表根节点
	// * @return 最大
	// */
	// @SQLMappingListSupport(name = "Department.orderNo", appender = { "sub" })
	// int maxOrderNo(String parentId);

	/**
	 * 查询部门下设置的子部门
	 * 
	 * @param parentId 部门id
	 * @return 子部门列表
	 */
	@ListSupport(field = "treeNode.parentId", order = { "treeNode.orderNo" })
	List<Department> subs(String parentId);

	/**
	 * 查询部门下设置的所有层级子部门
	 * 
	 * @param parentId 部门id
	 * @return 子部门列表
	 */
	@ListSupport(field = "treeNode.idLayer", operator = ListSupport.RLIKE, order = { "treeNode.depth",
			"treeNode.orderNo" })
	List<Department> allSubs(String layer);

}
