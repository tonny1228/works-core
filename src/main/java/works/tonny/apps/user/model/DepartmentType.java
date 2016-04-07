/**
 * 
 */
package works.tonny.apps.user.model;

/**
 * 部门的类型
 * 
 * @author 祥栋
 */
public enum DepartmentType {
	/**
	 * 组织机构，指最高一级的组织
	 */
	Organization,
	/**
	 * 独立的管理单元，可以是子公司、分公司、具有管理权限的业务中心等
	 */
	BusinessUnit,
	/**
	 * 一般的子部门
	 */
	Department
}
