/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-12-18 上午11:06:18
 * @author tonny
 */
package works.tonny.apps.auth;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.support.ListSupport;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public interface DataOwnerConfigDAO extends EntityDAO<DataOwnerConfig> {
	@ListSupport(order = "entity asc")
	List<DataOwnerConfig> list();
}