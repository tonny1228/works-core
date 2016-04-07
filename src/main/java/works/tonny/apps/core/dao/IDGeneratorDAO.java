/**
 * 
 */
package works.tonny.apps.core.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.IDGenerator;
import works.tonny.apps.support.ListSupport;

/**
 * @author 祥栋
 */
public interface IDGeneratorDAO extends EntityDAO<IDGenerator> {
	/**
	 * 所有数据列表
	 * 
	 * @return
	 */
	@ListSupport(order = "name")
	List<IDGenerator> list();
}